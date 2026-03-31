package com.base.timeline.change

import com.base.DateMutableEntity
import com.base.ObjectType
import com.base.reference.DMEReference
import com.base.timeline.Timeline
import com.base.timeline.TimelineHelper
import com.base.timeline.TimelineState
import com.base.timeline.change.conditions.ApplyConditions
import com.base.timeline.change.conditions.Condition
import com.base.timeline.change.conditions.ConditionResult
import com.base.timeline.change.conditions.NullifyConditions
import com.base.timeline.flags.SandboxCode
import com.base.timeline.flags.StateError
import com.base.timeline.sandbox.core.Objective
import com.base.timeline.sandbox.core.SandboxHandler
import com.google.common.base.Suppliers
import com.google.common.hash.Hashing
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.utilities.JsonSerializable
import com.utilities.SuperclassSerializable
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

abstract class TimelineChange<T : DateMutableEntity<T>>(val owner: DMEReference<out T>, initialDate: LocalDate) : SuperclassSerializable {
    private var start: LocalDate = initialDate
    private val id: Long by lazy { generateID() }
    private val breadcrumb = SandboxBreadcrumb()
    private var deactivated: Boolean = false
    private var isStatic: Boolean = false
    
    fun getId(): Long = id

    fun getOwner(): DMEReference<out T> = owner


    // ==== Base Methods
    fun apply(entity: DMEReference<out T>, currentState: TimelineState<out T>) {
        onApply(entity.get(), currentState)
    }

    fun advance(currentState: TimelineState<out T>, newState: TimelineChange<in T>, sandbox: Boolean) {
        // Implementation of advance logic
        onAdvance()
    }

    fun overwrite(currentState: TimelineState<out T>, beingOverwritten: TimelineChange<out T>, destructive: Boolean) {
        onOverwrite(owner.get(), currentState, beingOverwritten, destructive)
        if (destructive) {
            currentState.removeChange(beingOverwritten.getId())
            TimelineHelper.breadcrumbCleanup(owner.get().timeline, beingOverwritten.getId(), beingOverwritten.getStart(), beingOverwritten.getEnd())
        } else {
            beingOverwritten.deactivate(true)
        }
        currentState.insertChange(this)
    }

    fun nullify(entity: DMEReference<out T>, state: TimelineState<out T>, changeToNullify: TimelineChange<in T>) {
        onNullify(entity.get(), state, changeToNullify)
        changeToNullify.deactivate(true)
    }

    fun deactivate(sandbox: Boolean) {
        var code = SandboxCode.END_SAVE
        if (sandbox) {
            code = SandboxHandler.sandboxApplyChange(Objective(owner, this), start, null)
        }
        if (code == SandboxCode.END_SAVE) {
            onDeactivate()
            deactivated = true
            val timeline: Timeline<in T> = owner.get().timeline
            TimelineHelper.breadcrumbCleanup(timeline, id, start, breadcrumb.getEndOfPropagation())
            val lastData = TimelineHelper.getLastValidChange(
                timeline, timeline.getStateAt(this.start), this
            )
            lastData.moveChange(null, breadcrumb.getEndOfPropagation())
        }
    }

    fun reactivate(sandbox: Boolean) {
        onReactivate()
        deactivated = false
    }

    fun moveChange(newStart: LocalDate?, newEnd: LocalDate?) {
        val currentStart = start
        val currentEnd = breadcrumb.getEndOfPropagation()
        val timeline = owner.get().timeline
        if (newStart != null) {
            start = newStart
            val newState = timeline.getOrMakeState(newStart)
            timeline.getStateAt(currentStart).removeChange(this.id)
            newState.insertChange(this)
        }
        if (newEnd != null) {
            breadcrumb.addEndPoint(newEnd)
        }
        TimelineHelper.breadcrumbCleanup(timeline, id, currentStart, currentEnd)
        TimelineHelper.propagateBreadcrumb(timeline, this)
    }


    ///

    abstract fun onApply(entity: T, currentState: TimelineState<T>)

    protected open fun onOverwrite(
        entity: T,
        currentState: TimelineState<out T>,
        beingOverwritten: TimelineChange<in T>,
        destructive: Boolean
    ) {}

    protected open fun onNullify(entity: T, currentState: TimelineState<out T>, beingNullified: TimelineChange<in T>) {}

    protected open fun onDeactivate() {}

    protected open fun onReactivate() {}

    open fun onAdvance() {}

    protected open fun onMove(
        newStart: LocalDate,
        newEnd: LocalDate,
        newState: TimelineState<T>,
        oldState: TimelineState<in T>
    ) {}

    fun isOpposite(state: TimelineChange<in T>): Boolean = oppositeChanges().contains(state::class.java)

    fun isSame(state: TimelineChange<in T>): Boolean = this::class.java == state::class.java

    abstract fun oppositeChanges(): List<Class<out TimelineChange<in T>>>

    open fun siblingChanges(): List<Class<out TimelineChange<in T>>> = emptyList()

    abstract fun isPositive(): Boolean


    fun canNullify(toNullify: TimelineChange<in T>): Boolean {
        var hasYes = false
        for (condition in nullConditions.get()) {
            val result = condition.check(owner.get(), this, toNullify).orElse(NullifyConditions.NOT_NULLIFY_NON_EXCLUSIVE)
            if (result.canNullify()) {
                hasYes = true
                if (!result.isOr()) return true
            } else if (!result.isOr()) return false
        }
        return hasYes
    }

    fun doesConflict(state: TimelineChange<in T>): List<StateError> {
        val errors = mutableListOf<StateError>()
        for (condition in applyConditions.get()) {
            condition.check(owner.get(), this, state)?.let { errors.add(it) }
        }
        return errors
    }

    fun getScope(): HashSet<DMEReference<*>> = hashSetOf(owner)

    protected abstract fun getText(): String

    private val nullConditions by lazy {
        listOf<Condition<ConditionResult.Nullify, in T>>(
            *NullifyConditions.baseConditions(),
            *buildNullifyConditions().toTypedArray()
        )
    }

    private val applyConditions by lazy {
        listOf<Condition<StateError, in T>>(
            *ApplyConditions.baseConditions(),
            *buildApplyConditions().toTypedArray()
        )
    }

    protected abstract fun buildApplyConditions(): List<Condition<StateError, in T>>

    protected abstract fun buildNullifyConditions(): List<Condition<ConditionResult.Nullify, in T>>

    private fun generateID(): Long {
        return generateID(this.javaClass, start, additionalIDVars())
    }

    companion object {
        fun <T : DateMutableEntity<T>> generateID(
            clazz: Class<out TimelineChange<T>>,
            start: LocalDate,
            additionalVars: List<DMEReference<*>>
        ): Long {
            val hasher = Hashing.murmur3_128().newHasher()
            hasher.putLong(start.toEpochDay())
            hasher.putString(clazz.simpleName, StandardCharsets.UTF_8)
            additionalVars.forEach { hasher.putLong(it.hash()) }
            return hasher.hash().asLong()
        }
    }

    fun shouldSandbox(): Boolean = true

    protected open fun setStaticFlag() {
        isStatic = true
    }

    fun isStatic(): Boolean = isStatic

    open fun additionalIDVars(): List<DMEReference<*>> = emptyList()

    override fun mainSave(o: JsonObject) {
        o.add("breadcrumb", breadcrumb.toJson())
    }

    override fun mainLoad(o: JsonObject) {
        breadcrumb.fromJson(o.get("breadcrumb").asJsonObject)
    }

    override fun metadataSave(o: JsonObject) {
        o.add("subject", owner.serialize())
        o.addProperty("date", start.toEpochDay())
    }

    val




    class SandboxBreadcrumb : JsonSerializable<SandboxBreadcrumb> {
        private var endOfPropagation: LocalDate? = null
        private val errorLog = HashMap<Long, String>()

        fun addEndPoint(date: LocalDate) {
            endOfPropagation = date
        }

        fun insertError(
            error: StateError,
            newChange: TimelineChange<*>,
            existingChange: TimelineChange<*>,
            proceduralInteger: Int?,
            resolutionCode: String
        ) {
            errorLog[error.generateID(newChange, existingChange, proceduralInteger)] = resolutionCode
        }

        fun insertError(errorID: Long, resolutionCode: String) {
            errorLog[errorID] = resolutionCode
        }

        fun getResolutionCode(errorID: Long): Optional<String> = Optional.ofNullable(errorLog[errorID])

        fun getResolutionCode(
            error: StateError,
            newChange: TimelineChange<*>,
            existingChange: TimelineChange<*>,
            proceduralInteger: Int?
        ): Optional<String> {
            return getResolutionCode(error.generateID(newChange, existingChange, proceduralInteger))
        }

        fun isComplete(): Boolean = endOfPropagation != null

        fun getEndOfPropagation(): LocalDate = endOfPropagation ?: throw IllegalStateException("End of propagation not set")

        override fun toJson(): JsonObject {
            val jsonObject = JsonObject()
            endOfPropagation?.let { jsonObject.addProperty("eop", it.toEpochDay()) }
            val jsonErrors = JsonArray()
            errorLog.forEach { (id, resolution) ->
                val errorObj = JsonObject()
                errorObj.addProperty("i", id)
                errorObj.addProperty("r", resolution)
                jsonErrors.add(errorObj)
            }
            jsonObject.add("errors", jsonErrors)
            return jsonObject
        }

        override fun fromJson(json: JsonObject) {
            json.get("eop")?.let { this.endOfPropagation = LocalDate.ofEpochDay(it.asLong) }
            val jsonErrors = json.getAsJsonArray("errors")
            for (errorElem in jsonErrors) {
                val error = errorElem.asJsonObject
                val id = error["i"].asLong
                val resolution = error["r"].asString
                errorLog[id] = resolution
            }
        }

        override fun empty(): SandboxBreadcrumb = SandboxBreadcrumb()
    }
}