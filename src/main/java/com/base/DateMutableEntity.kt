package com.base

import com.Global
import com.base.reference.DMEReference
import com.base.timeline.Timeline
import com.base.timeline.TimelineState
import com.base.timeline.change.TimelineChange
import com.google.gson.JsonObject
import com.utilities.SuperclassSerializable
import java.time.LocalDate
import java.util.*

/**
 * Represents an abstract class for date-aware mutable entities that track state changes over time.
 * This class maintains a timeline of states, which are stored as objects of the nested `DateState` record.
 * Each state includes metadata such as creation and end dates, associated keys for state changes, and a payload
 * representing the state value.
 *
 * @param T The type representing the state of the entity.
 */
abstract class DateMutableEntity<T : DateMutableEntity<T>> : SuperclassSerializable {
    private val id: UUID
    private val timeline: Timeline<T>
    private val reference: DMEReference<T>

    constructor(
        id: UUID,
        created: LocalDate,
        ended: LocalDate?,
        initialState: List<TimelineChange<in T>>
    ) {
        this.id = id
        this.reference = DMEReference.of(this::class.java, id)
        this.timeline = Timeline(this as T, reference, created, ended, initialState)
    }

    constructor(
        created: LocalDate,
        ended: LocalDate?,
        initialState: List<TimelineChange<in T>>
    ) : this(UUID.randomUUID(), created, ended, initialState)

    constructor(dme: DMEReference<T>) {
        requireNotNull(dme) { "DMEReference cannot be null" }
        require(dme.type == this::class.java) {
            "DMEReference: $dme must be of type ${this::class.java}"
        }
        this.id = dme.id
        this.timeline = Timeline(dme)
        this.reference = dme
    }

    val entityId: UUID
        get() = id

    override fun mainSave(json: JsonObject) {
        json.add("timeline", timeline.save())
    }

    override fun mainLoad(json: JsonObject) {
        val timelineJson = json["timeline"].asJsonObject
        timeline.load(timelineJson)
    }

    val createdDate: LocalDate
        get() = timeline.earliestDate

    val endedDate: LocalDate
        get() = timeline.latestDate

    val dmeReference: DMEReference<T>
        get() = reference

    // Use to add any shortcut/linked entries to other objects (e.g., family adding a shortcut link to itself in every member)
    abstract fun onLink()

    // Use to clear any shortcut/linked variables.
    abstract fun doDateChange()

    fun onDateChange() {
        doDateChange()
        timeline.doTimeChange(this, current())
    }

    fun relink() {
        // Placeholder for implementation in subclasses
    }

    protected fun current(): LocalDate {
        return Global.date
    }

    protected fun setCreated(created: LocalDate) {
        timeline.moveBirth(this as T, created)
    }

    protected Thanfun setEnded(ended: LocalDate) {
        timeline.moveDeath(this as T, ended)
    }

    fun getAllStates(): Array<TimelineState<T>> {
        return timeline.states
    }

    override fun equals(other: Any?): Boolean {
        return if (other is DateMutableEntity<*> && this::class == other::class) {
            this.entityId == other.entityId
        } else false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun getTimeline(): Timeline<T> {
        return timeline
    }

    abstract fun <M : AbstractMutableManager<M, T, *>> getManager(): M

}