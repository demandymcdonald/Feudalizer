package com.base.timeline.change

import com.base.DateMutableEntity
import com.base.reference.DMEReference
import com.base.timeline.Timeline
import com.base.timeline.TimelineHelper
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.apache.commons.lang3.tuple.Pair
import java.time.LocalDate

abstract class TimelineMapChange<M : TimelineMapChange<M, K, V, T>, K, V, T : DateMutableEntity<T>>(
    private val base: Class<M>,
    owner: DMEReference<T>,
    date: LocalDate
) : TimelineChange<T>(owner, date) {

    private var leapfrog: Pair<Long, LocalDate>? = null
    private var leapfrogSize: Int = 0
    private val changes: MutableMap<K, V> = mutableMapOf()

    fun buildMap(t: Timeline<T>): Map<K, V> {
        val toReturn = mutableMapOf<K, V>()
        val finalTotal = leapfrogSize + changes.size

        val consumer: (M, MutableMap<K, V>) -> Unit = { m, kvMap ->
            val newMap = m.getChangeFragment()
            for ((key, value) in newMap) {
                if (!kvMap.containsKey(key)) {
                    kvMap[key] = value
                }
            }
        }

        val function: (M, MutableMap<K, V>) -> Int = { m, r ->
            val newMap = m.getChangeFragment()
            newMap.count { (key, _) -> !r.containsKey(key) }
        }

        return TimelineHelper.doMapChangeLeapFrog(
            t, leapfrog, function, base, finalTotal, consumer, toReturn, true, TimelineHelper.Direction.BACKWARD
        )
    }

    private fun addChange(vararg changes: Pair<K, V>) {
        var totalNew = 0
        changes.forEach { (key, value) ->
            if (!this.changes.containsKey(key)) {
                this.changes[key] = value
                totalNew++
            }
        }
        // TODO finish once I figure out how commits will work XD I think I need a commit method in TLChange.
    }

    fun getFullMap(): Map<K, V> = changes

    fun merge(combine: M) {
        // Implementation to be added if needed
    }

    fun getPreviousLeapFrog(): Pair<Long, LocalDate>? = leapfrog

    fun makeLeapFrog(): Pair<Long, LocalDate> = Pair.of(this.id, this.start)

    fun setLeapFrog(leapFrog: Pair<Long, LocalDate>) {
        this.leapfrog = leapFrog
    }

    fun getChangeFragment(): Map<K, V> = changes

    override fun mainSave(o: JsonObject) {
        super.mainSave(o)
        // Additional implementation if needed
    }

    override fun mainLoad(o: JsonObject) {
        super.mainLoad(o)
        // Additional implementation if needed
    }

    protected abstract fun serializeK(key: K): JsonElement
    protected abstract fun deserializeK(json: JsonElement): K
    protected abstract fun serializeV(value: V): JsonElement
    protected abstract fun deserializeV(json: JsonElement): V
}