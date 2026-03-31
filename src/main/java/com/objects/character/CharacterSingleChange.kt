package com.objects.character

import com.base.timeline.TimelineState
import com.base.timeline.change.TimelineChange
import com.base.reference.DMEReference
import com.base.timeline.change.TimelineSingleChange
import com.base.timeline.change.conditions.Condition
import com.base.timeline.change.conditions.ConditionResult
import com.base.timeline.flags.StateError
import com.google.gson.JsonObject
import com.objects.CauseOfEnd
import java.time.LocalDate
import java.util.Optional

import com.base.timeline.change.conditions.NullifyConditions.NEVER_NULLIFY

abstract class CharacterSingleChange(primary: DMEReference<BookCharacter>, date: LocalDate) :
    TimelineSingleChange<BookCharacter>(primary, date) {


    class Birth(primary: DMEReference<BookCharacter>, date: LocalDate) :
        CharacterSingleChange(primary, date) {

        override fun onApply(entity: BookCharacter, currentState: TimelineState<BookCharacter>) {
        }

        override fun oppositeChanges(): List<Class<TimelineChange<in BookCharacter>>> {
            return listOf()
        }

        override fun isPositive(): Boolean {
            return true
        }

        override fun buildApplyConditions(): List<Condition<StateError, in BookCharacter>> {
            return listOf()
        }

        override fun buildNullifyConditions(): List<Condition<ConditionResult.Nullify, in BookCharacter>> {
            return listOf(NEVER_NULLIFY as Condition<ConditionResult.Nullify, in BookCharacter>)
        }

        override fun getText(): String {
            return "${getOwner()} was born."
        }

        override fun additionalSave(data: JsonObject) {
        }

        override fun additionalLoad(data: JsonObject) {
        }
    }

    class Death(primary: DMEReference<BookCharacter>, date: LocalDate, private var cause: CauseOfEnd.BookCharacter) :
        CharacterSingleChange(primary, date) {

        override fun onApply(entity: BookCharacter, currentState: TimelineState<BookCharacter>) {
        }

        override fun oppositeChanges(): List<Class<TimelineChange<in BookCharacter>>> {
            return listOf()
        }

        override fun isPositive(): Boolean {
            return true
        }

        override fun buildApplyConditions(): List<Condition<StateError, in BookCharacter>> {
            return listOf()
        }

        override fun buildNullifyConditions(): List<Condition<ConditionResult.Nullify, in BookCharacter>> {
            return listOf(NEVER_NULLIFY as Condition<ConditionResult.Nullify, in BookCharacter>)
        }

        override fun getText(): String {
            return "${getOwner()} died"
        }

        override fun additionalSave(data: JsonObject) {
            data.addProperty("cause", cause.name)
        }

        override fun additionalLoad(data: JsonObject) {
            cause = if (data.has("cause")) {
                CauseOfEnd.BookCharacter.valueOf(data.get("cause").asString)
            } else {
                CauseOfEnd.BookCharacter.CHARACTER_ERROR
            }
        }
    }

    class SetForename(
        primary: DMEReference<BookCharacter>,
        date: LocalDate,
        private var forename: String? = null,
        private var old: Optional<String> = Optional.empty()
    ) : CharacterSingleChange(primary, date) {

        constructor(primary: DMEReference<BookCharacter>, date: LocalDate, forename: String) : this(
            primary, date, forename, Optional.ofNullable(primary.get().forename)
        )

        override fun onApply(entity: BookCharacter, currentState: TimelineState<BookCharacter>) {
            entity.internalSetForename(forename.orEmpty())
        }

        override fun oppositeChanges(): List<Class<TimelineChange<in BookCharacter>>> {
            return listOf()
        }

        override fun isPositive(): Boolean {
            return true
        }

        override fun getText(): String {
            val second = getOwner().get().surname
            return if (old.isPresent) {
                "${old.get()} $second changed their forename to: $forename"
            } else {
                "$forename $second changed their forename"
            }
        }

        override fun buildApplyConditions(): List<Condition<StateError, in BookCharacter>> {
            return listOf()
        }

        override fun buildNullifyConditions(): List<Condition<ConditionResult.Nullify, in BookCharacter>> {
            return listOf()
        }

        override fun additionalSave(data: JsonObject) {
            data.addProperty("forename", forename)
            old.ifPresent { data.addProperty("old", it) }
        }

        override fun additionalLoad(data: JsonObject) {
            forename = data["forename"]?.asString
            old = if (data.has("old")) Optional.of(data.get("old").asString) else Optional.empty()
        }
    }

    class SetSurname(
        primary: DMEReference<BookCharacter>,
        date: LocalDate,
        private var surname: String? = null,
        private var old: Optional<String> = Optional.empty()
    ) : CharacterSingleChange(primary, date) {

        constructor(primary: DMEReference<BookCharacter>, date: LocalDate, surname: String) : this(
            primary, date, surname, Optional.ofNullable(primary.get().forename)
        )

        override fun onApply(entity: BookCharacter, currentState: TimelineState<BookCharacter>) {
            entity.internalSetSurname(surname.orEmpty())
        }

        override fun oppositeChanges(): List<Class<TimelineChange<in BookCharacter>>> {
            return listOf()
        }

        override fun isPositive(): Boolean {
            return true
        }

        override fun getText(): String {
            val second = getOwner().get().surnameProperty
            return if (old.isPresent) {
                "$second ${old.get()} changed their surname to: $surname"
            } else {
                "$second ${old.get()} changed their surname."
            }
        }

        override fun buildApplyConditions(): List<Condition<StateError, in BookCharacter>> {
            return listOf()
        }

        override fun buildNullifyConditions(): List<Condition<ConditionResult.Nullify, in BookCharacter>> {
            return listOf()
        }

        override fun additionalSave(data: JsonObject) {
            data.addProperty("surname", surname)
            old.ifPresent { data.addProperty("old", it) }
        }

        override fun additionalLoad(data: JsonObject) {
            surname = data["surname"]?.asString
            old = if (data.has("old")) Optional.of(data.get("old").asString) else Optional.empty()
        }
    }

    class SetGender(
        primary: DMEReference<BookCharacter>,
        date: LocalDate,
        private var gender: BookCharacter.Gender,
        private var old: Optional<BookCharacter.Gender> = Optional.empty()
    ) : CharacterSingleChange(primary, date) {

        constructor(primary: DMEReference<BookCharacter>, date: LocalDate, gender: BookCharacter.Gender) : this(
            primary, date, gender, Optional.ofNullable(primary.get().characterGender)
        )

        override fun onApply(entity: BookCharacter, currentState: TimelineState<BookCharacter>) {
            entity.internalSetGender(gender)
        }

        override fun oppositeChanges(): List<Class<TimelineChange<in BookCharacter>>> {
            return listOf()
        }

        override fun isPositive(): Boolean {
            return true
        }

        override fun getText(): String {
            val name = getOwner().get().fullName
            return if (old.isPresent) {
                "$name changed their gender from: ${old.get()}, to: ${gender.getFlavor()}."
            } else {
                "$name changed their gender to: ${gender.getFlavor()}."
            }
        }

        override fun buildApplyConditions(): List<Condition<StateError, in BookCharacter>> {
            return listOf()
        }

        override fun buildNullifyConditions(): List<Condition<ConditionResult.Nullify, in BookCharacter>> {
            return listOf()
        }

        override fun additionalSave(data: JsonObject) {
            data.addProperty("gender", gender.name)
            old.ifPresent { data.addProperty("old", it.name) }
        }

        override fun additionalLoad(data: JsonObject) {
            gender = BookCharacter.Gender.valueOf(data["gender"].asString)
            old = if (data.has("old")) {
                Optional.of(BookCharacter.Gender.valueOf(data.get("old").asString))
            } else {
                Optional.empty()
            }
        }
    }

    class SetOrientation(
        primary: DMEReference<BookCharacter>,
        date: LocalDate,
        private var orientation: BookCharacter.Orientation,
        private var old: Optional<BookCharacter.Orientation> = Optional.empty()
    ) : CharacterSingleChange(primary, date) {

        constructor(primary: DMEReference<BookCharacter>, date: LocalDate, orientation: BookCharacter.Orientation) : this(
            primary, date, orientation, Optional.ofNullable(primary.get().sexualOrientation)
        )

        override fun onApply(entity: BookCharacter, currentState: TimelineState<BookCharacter>) {
            entity.internalSetOrientation(orientation)
        }

        override fun oppositeChanges(): List<Class<TimelineChange<in BookCharacter>>> {
            return listOf()
        }

        override fun isPositive(): Boolean {
            return true
        }

        override fun getText(): String {
            val name = getOwner().get().fullName
            return if (old.isPresent) {
                "$name changed their sexual orientation from: ${old.get()}, to: ${orientation.getFlavor()}."
            } else {
                "$name changed their sexual orientation to: ${orientation.getFlavor()}."
            }
        }

        override fun buildApplyConditions(): List<Condition<StateError, in BookCharacter>> {
            return listOf()
        }

        override fun buildNullifyConditions(): List<Condition<ConditionResult.Nullify, in BookCharacter>> {
            return listOf()
        }

        override fun additionalSave(data: JsonObject) {
            data.addProperty("orientation", orientation.name)
            old.ifPresent { data.addProperty("old", it.name) }
        }

        override fun additionalLoad(data: JsonObject) {
            orientation = BookCharacter.Orientation.valueOf(data["orientation"].asString)
            old = if (data.has("old")) {
                Optional.of(BookCharacter.Orientation.valueOf(data.get("old").asString))
            } else {
                Optional.empty()
            }
        }
    }
}