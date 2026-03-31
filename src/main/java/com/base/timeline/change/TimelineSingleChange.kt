package com.base.timeline.change

import com.base.DateMutableEntity
import com.base.reference.DMEReference
import com.google.gson.JsonObject
import java.time.LocalDate

abstract class TimelineSingleChange<T : DateMutableEntity<T>>(
    owner: DMEReference<T>,
    date: LocalDate
) : TimelineChange<T>(owner, date) {

    final override fun mainSave(o: JsonObject) {
        super.mainSave(o)
    }

    final override fun mainLoad(`object`: JsonObject) {
        super.mainLoad(`object`)
    }
}