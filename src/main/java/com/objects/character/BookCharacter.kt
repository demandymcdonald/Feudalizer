package com.objects.character

import com.base.*
import com.base.reference.DMEReference
import com.google.common.collect.Maps
import com.google.gson.JsonObject
import com.objects.character.opinion.Opinion
import com.objects.people.Family
import com.objects.people.House
import com.objects.title.Title
import java.time.LocalDate
import java.util.*

class BookCharacter : DateMutableEntity<BookCharacter> {

    override fun additionalSave(data: JsonObject) {
        // Implement additional save logic here
    }

    override fun additionalLoad(data: JsonObject) {
        // Implement additional load logic here
    }

    enum class Gender(val display: String) {
        Male("Male"),
        Female("Female"),
        Trans_Male("Trans-Male"),
        Trans_Female("Trans-Female"),
        Non_Binary("Non-Binary"),
        Other("Other");

        fun getFlavor(): String = display
    }

    enum class Orientation(val display: String) {
        Heterosexual("Heterosexual"),
        Homosexual("Homosexual"),
        Bisexual("Bisexual"),
        Asexual("Asexual"),
        Questioning("Questioning"),
        Other("Other");

        fun getFlavor(): String = display
    }

    private var givenName: String? = null
    private var surname: String? = null
    private var gender: Gender? = null
    private var orientation: Orientation? = null
    private val opinions: MutableMap<UUID, Opinion> = HashMap()

    // TODO Add: Religion, Culture, Political Ideology.

    private var linkedHouse: Optional<House> = Optional.empty()
    private val linkedFamilies: MutableMap<Family, Family.Relationship> = Maps.newHashMap()
    private val linkedTitles: MutableList<Title<*>> = mutableListOf()

    constructor(
        givenName: String,
        surname: String,
        dateOfBirth: LocalDate,
        dateOfDeath: LocalDate?,
        gender: Gender,
        orientation: Orientation
    ) : super(dateOfBirth, dateOfDeath) {
        this.givenName = givenName
        this.surname = surname
        this.gender = gender
        this.orientation = orientation
    }

    constructor(ref: DMEReference<BookCharacter>) : super(ref)

    override fun onLink() {
        // I don't think Character will ever call out to any objects. Most objects should populate it?
    }

    override fun getObjectType(): ObjectType = ObjectType.CHARACTER

    override fun doDateChange() {
        linkedHouse = Optional.empty()
        linkedFamilies.clear()
        linkedTitles.clear()
    }

    override fun <M : AbstractMutableManager<M, BookCharacter, *>> getManager(): M? = null

    fun setForename(name: String) {
        timeline.addChange(CharacterSingleChange.SetForename(dmeReference, current(), name))
    }

    fun setSurname(name: String) {
        timeline.addChange(CharacterSingleChange.SetSurname(dmeReference, current(), name))
    }

    fun setGender(gender: Gender) {
        timeline.addChange(CharacterSingleChange.SetGender(dmeReference, current(), gender))
    }

    fun setSexualOrientation(orientation: Orientation) {
        timeline.addChange(CharacterSingleChange.SetOrientation(dmeReference, current(), orientation))
    }

    fun linkHouse(house: House) {
        linkedHouse = Optional.of(house)
    }

    fun linkFamily(family: Family, rel: Family.Relationship) {
        linkedFamilies[family] = rel
    }

    fun linkTitle(title: Title<*>) {
        linkedTitles.add(title)
    }

    fun internalSetForename(forename: String) {
        this.givenName = forename
    }

    fun internalSetSurname(surname: String) {
        this.surname = surname
    }

    fun internalSetGender(gender: Gender) {
        this.gender = gender
    }

    fun internalSetOrientation(orientation: Orientation) {
        this.orientation = orientation
    }

    val fullName: String
        get() = "$givenName $surname" // TODO When culture gets implemented, add rules for name formatting.

    val forename: String?
        get() = givenName

    val surnameProperty: String?
        get() = surname

    val characterGender: Gender?
        get() = gender

    val sexualOrientation: Orientation?
        get() = orientation

    val characterOpinions: Map<UUID, Opinion>
        get() = opinions
}