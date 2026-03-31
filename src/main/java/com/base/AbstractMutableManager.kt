package com.base

import com.base.reference.DMEReference
import com.base.timeline.TimelineState
import com.base.timeline.change.TimelineChange
import com.google.gson.JsonObject
import com.utilities.SuperclassRegistry
import java.time.LocalDate
import java.util.UUID

abstract class AbstractMutableManager<M : AbstractMutableManager<M, T, BA>, T : DateMutableEntity<T>, BA>(
    uniqueKey: String
) : SuperclassRegistry<M, T, UUID, BA>(uniqueKey) {

    init {
        DMRegistry.registerManager(this)
    }

    fun accepts(dme: DMEReference<*>): Boolean {
        return accepts(dme.javaClass)
    }

    fun accepts(clazz: Class<*>): Boolean {
        return instanceClass().isAssignableFrom(clazz)
    }

    fun onLink() {
        doIterate { it.onLink() }
    }

    fun onDateChange() {
        doIterate { it.onDateChange() }
    }

    fun <R : DateMutableEntity<R>> loadObject(dme: DMEReference<R>, obj: JsonObject): R {
        if (!accepts(dme)) {
            throw IllegalArgumentException("Cannot load object of type ${dme.type} into ${this::class.java}")
        }
        @Suppress("UNCHECKED_CAST")
        val td = dme as DMEReference<out T>
        return super.loadObject(td.type, td.id, obj) as R
    }

    abstract fun instanceClass(): Class<T>

    abstract fun getBirthChange(dme: DMEReference<T>, date: LocalDate): TimelineChange<T>

    abstract fun getDeathChange(dme: DMEReference<T>, date: LocalDate): TimelineChange<T>

    fun buildBirth(
        dme: DMEReference<T>,
        date: LocalDate,
        defaults: MutableList<TimelineChange<in T>>
    ): TimelineState<T> {
        defaults.add(0, getBirthChange(dme, date))
        return TimelineState(dme, date, date, true, defaults)
    }

    fun buildDeath(
        dme: DMEReference<T>,
        date: LocalDate,
        defaults: MutableList<TimelineChange<in T>>
    ): TimelineState<T> {
        defaults.add(0, getDeathChange(dme, date))
        return TimelineState(dme, date, date, true, defaults)
    }
}