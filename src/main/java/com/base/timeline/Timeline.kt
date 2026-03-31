package com.base.timeline

import com.Global
import com.base.AbstractMutableManager
import com.base.DMRegistry
import com.base.DateMutableEntity
import com.base.reference.DMEReference
import com.base.timeline.change.TimelineChange
import com.base.timeline.sandbox.core.Objective
import com.base.timeline.sandbox.core.SandboxHandler
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.time.LocalDate
import java.util.*

class Timeline<T : DateMutableEntity<T>> {

    private val timeline: TreeMap<LocalDate, TimelineState<T>> = TreeMap()
    var isLoaded = false
        private set
    private val owner: DMEReference<T>

    constructor(o: T, owner: DMEReference<T>, start: LocalDate, end: LocalDate?, initialState: List<TimelineChange<in T>>) {
        val manager = o.getManager()
        this.owner = owner
        timeline[start] = manager.buildBirth(owner, start, initialState)
        timeline[end ?: Global.MAX_DATE] = manager.buildDeath(owner, end ?: Global.MAX_DATE, initialState)
        isLoaded = true
    }

    constructor(dme: DMEReference<T>) {
        owner = dme
    }

    fun unsafeInsertState(state: TimelineState<T>) {
        insertState(state)
    }

    fun getTimelineMin(): LocalDate = timeline.firstKey()

    fun getTimelineMax(): LocalDate = timeline.lastEntry().value.end

    fun addChange(change: TimelineChange<T>) {
        getOrMakeState(change) // We just need a state at the exact start date.
        SandboxHandler.SandboxApplyChange(Objective(owner, change), change.start, null)
    }

    fun isEmpty(): Boolean = timeline.isEmpty()

    private fun insertState(state: TimelineState<T>) {
        timeline[state.start] = state
    }

    private fun removeState(state: TimelineState<T>) {
        timeline.remove(state.start)
    }

    private fun removeState(start: LocalDate) {
        timeline.remove(start)
    }

    fun getEarliestDate(): LocalDate = timeline.firstKey()

    fun getLatestDate(): LocalDate = timeline.lastKey()

    fun isLast(date: LocalDate): Boolean {
        return timeline.lastEntry().value.start.isBefore(date)
    }

    fun moveBirth(ref: T, date: LocalDate) {
        val oldBirthDate = getEarliestDate()
        val template = DMRegistry.getManager(owner.type)

        if (oldBirthDate.isAfter(date)) {
            val state = getStateAt(oldBirthDate)
            timeline.remove(oldBirthDate)
            timeline[date] = template.buildBirth(owner, date, state.changes(true))
        } else {
            val bt = template.getBirthChange(owner, date)
            SandboxHandler.SandboxApplyChange(Objective(owner, bt), oldBirthDate, null)
            // TODO Sandbox out moving the birth later
        }
    }

    fun moveDeath(ref: T, date: LocalDate) {
        val oldDeathDate = getLatestDate()
        val template = DMRegistry.getManager(owner.type)

        if (oldDeathDate.isBefore(date)) {
            val state = getStateAt(oldDeathDate)
            timeline.remove(oldDeathDate)
            timeline[date] = template.buildDeath(owner, date, state.changes(true))
        } else {
            val bt = template.getDeathChange(owner, date)
            SandboxHandler.SandboxApplyChange(Objective(owner, oldDeathDate, bt), date, null)
        }
    }

    fun doTimeChange(entity: T, date: LocalDate) {
        val t = getStateAt(date)
        t.getChanges(false).forEach { it.apply(entity, t) }
    }

    fun getStateNullable(date: LocalDate): TimelineState<T>? = timeline.floorEntry(date)?.value

    fun getNextState(date: LocalDate): TimelineState<T>? = timeline.higherEntry(date)?.value

    fun getStateAt(date: LocalDate): TimelineState<T> {
        return timeline.floorEntry(date)?.value
            ?: throw IllegalArgumentException("No state found at $date")
    }

    fun getStateAtExact(date: LocalDate, throwIfNotFound: Boolean): TimelineState<T>? {
        val state = timeline[date]
        if (state == null && throwIfNotFound) {
            throw IllegalArgumentException("No state found at $date")
        }
        return state
    }

    fun getStateBefore(date: LocalDate): TimelineState<T> {
        val state = timeline.floorEntry(date.minusDays(1))?.value
        return timeline.floorEntry(state?.start?.minusDays(1))?.value ?: state!!
    }

    fun getStateAfter(date: LocalDate): TimelineState<T> {
        val state = timeline.ceilingEntry(date.plusDays(1))?.value
        return timeline.floorEntry(state?.start?.minusDays(1) ?: date)?.value ?: state!!
    }

    fun getOrMakeState(d: LocalDate): TimelineState<T> {
        return timeline[d] ?: makeNewState(d)
    }

    fun getStates(): Array<TimelineState<T>> {
        return timeline.values.toTypedArray()
    }

    fun getLastState(): TimelineState<T> {
        return timeline.lastEntry().value
    }

    fun save(): JsonObject {
        val obj = JsonObject()
        val states = JsonArray()
        timeline.forEach { (_, value) -> states.add(value.serialize()) }
        obj.add("states", states)
        return obj
    }

    fun load(obj: JsonObject) {
        val states = obj.getAsJsonArray("states")
        for (i in 0 until states.size()) {
            val state = TimelineState.deserialize(states[i].asJsonObject)
            timeline[state.start] = state
        }
    }

    fun makeNewState(start: LocalDate): TimelineState<T> {
        val before = timeline.floorEntry(start.minusDays(1))?.value
        val after = timeline.ceilingEntry(start.plusDays(1))?.value
        if (before?.start == start) return before
        if (after?.start == start) return after

        if (before?.isDuring(start) == true) {
            before.setEnd(start.minusDays(1))
        }

        val breadcrumbs = TimelineHelper.extendTrail(this, before, start)
        val newState = TimelineState(owner, start, after?.start?.minusDays(1), false, breadcrumbs, HashMap())
        timeline[start] = newState
        return newState
    }

    fun replaceTimeline(obj: JsonObject) {
        isLoaded = false
        timeline.clear()
        load(obj)
        isLoaded = true
    }

    fun getOwner(): DMEReference<T> = owner
}