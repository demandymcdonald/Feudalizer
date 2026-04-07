package com.objects.character.human;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.CharacterSingleChange;
import com.objects.character.LivingCreature;
import com.objects.character.opinion.Opinion;
import com.objects.culture.Culture;
import com.objects.culture.term.CulturalObject;
import com.objects.family.Family;
import com.objects.government.House;
import com.objects.title.Title;
import com.objects.title.succession.rules.SuccessionEntry;

import java.time.LocalDate;
import java.util.*;

public class HumanCharacter extends LivingCreature<HumanCharacter> implements CulturalObject<HumanCharacter> {

    public enum Pronouns {
        Masculine,
        Feminine,
        Neutral,
    }


    public enum Gender {
        Male("Male",Pronouns.Masculine),
        Female("Female",Pronouns.Feminine),
        Trans_Male("Trans-Male",Pronouns.Masculine),
        Trans_Female("Trans-Female",Pronouns.Feminine),
        Non_Binary("Non-Binary",Pronouns.Neutral);
//        Other("Other");

        private final String display;
        private final Pronouns pronouns;
        Gender(String d, Pronouns pronouns){
            display = d;
            this.pronouns = pronouns;
        }
        public String getFlavor(){
            return display;
        }
        public Pronouns getPronouns(){
            return pronouns;
        }
    }
    public enum Orientation {
        Heterosexual("Heterosexual"),
        Homosexual("Homosexual"),
        Bisexual("Bisexual"),
        Asexual("Asexual"),
        Questioning("Questioning"),
        Other("Other");

        private final String display;
        Orientation(String d){
            display = d;
        }
        public String getFlavor(){
            return display;
        }
    }
    private String givenName;
    private String surname;
    private Gender gender;
    private Orientation orientation;
    private SuccessionEntry<?> preferredSuccession;
    private DMEReference<Culture> culture;

    private final Map<UUID, Opinion> opinions = new HashMap<>();

    //TODO Add: Religion, Culture, Political Ideology.

    private Optional<House> linked_house;
    private final Map<Family, Family.Relationship> linked_families = Maps.newHashMap();
    private final List<DMEReference<? extends Title<?>>> linked_titles = new ArrayList<>();


    public HumanCharacter(String givenName, String surname, LocalDate dateOfBirth, LocalDate dateOfDeath,
                          Gender gender, Orientation orientation) {
        super(dateOfBirth,dateOfDeath,List.of(
            new ChangeSupplier<HumanCharacter, CharacterSingleChange.setForename>(){
                @Override
                public CharacterSingleChange.setForename supply(LocalDate date, DMEReference<HumanCharacter> subject) {
                    return new CharacterSingleChange.setForename(subject,date,givenName);
                }
            },
            new ChangeSupplier<HumanCharacter,CharacterSingleChange.setSurname>(){
                @Override
                public CharacterSingleChange.setSurname supply(LocalDate date, DMEReference<HumanCharacter> subject) {
                    return new CharacterSingleChange.setSurname(subject,date,surname);
                }
            },
            new ChangeSupplier<HumanCharacter,CharacterSingleChange.setGender>(){
                @Override
                public CharacterSingleChange.setGender supply(LocalDate date, DMEReference<HumanCharacter> subject) {
                    return new CharacterSingleChange.setGender(subject,date,gender);
                }
            },
            new ChangeSupplier<HumanCharacter,CharacterSingleChange.setOrientation>(){
                @Override
                public CharacterSingleChange.setOrientation supply(LocalDate date, DMEReference<HumanCharacter> subject) {
                    return new CharacterSingleChange.setOrientation(subject,date,orientation);
                }
            }
        ));
        this.givenName = givenName;
        this.surname = surname;
        this.gender = gender;
        this.orientation = orientation;
    }
    public HumanCharacter(DMEReference<HumanCharacter> ref) {
        super(ref);
    }

    @Override
    public void onLink() {
        //I Don't think Character will ever call out to any objects. Most objects should populate it?
    }

    @Override
    public void doDateChange() {
        opinions.clear();
        linked_house = Optional.empty();
        linked_families.clear();
        linked_titles.clear();
    }

    @Override
    public TimelineChange<HumanCharacter> getBirthChange(DMEReference<HumanCharacter> dme, LocalDate date) {
        return new CharacterSingleChange.Birth(dme,date);
    }

    @Override
    public TimelineChange<HumanCharacter> getDeathChange(DMEReference<HumanCharacter> dme, LocalDate date, CauseOfEnd<? super HumanCharacter> cOd) {
        return new CharacterSingleChange.Death(dme,date,cOd);
    }

    @Override
    public CauseOfEnd<? super HumanCharacter> defaultDeathCause() {
        return CauseOfEnd.Character.CHARACTER_OLD_AGE;
    }


    public void setForename(String name) {
        getTimeline().addChange(new CharacterSingleChange.setForename(getReference(),current(),name));
    }
    public void setSurname(String name) {
        getTimeline().addChange(new CharacterSingleChange.setSurname(getReference(),current(),name));
    }
    public void setGender(Gender gender) {
        getTimeline().addChange(new CharacterSingleChange.setGender(getReference(),current(),gender));
    }
    public void setSexualOrientation(Orientation orientation) {
        getTimeline().addChange(new CharacterSingleChange.setOrientation(getReference(),current(),orientation));
    }



    public void linkHouse(House house){
        linked_house = Optional.of(house);
    }
    public void linkFamily(Family family, Family.Relationship rel){
        linked_families.put(family, rel);
    }
    public void linkTitle(DMEReference<? extends Title<?>> title){
        linked_titles.add(title);
    }


    public void internalSetCulture(DMEReference<Culture> culture){
        this.culture = culture;
    }
    public void internalSetForename(String forename){
        this.givenName = forename;
    }
    public void internalSetSurname(String surname){
        this.surname = surname;
    }
    public void internalSetGender(Gender gender){
        this.gender = gender;
    }
    public void internalSetOrientation(Orientation orientation){
        this.orientation = orientation;
    }
    public void internalSetOpinionMap(Map<UUID, Opinion> opinions){
        this.opinions.clear();
        this.opinions.putAll(opinions);
    }

//    public void internalSetOpinion(Map<UUID, Opinion> opinions){
//        this.opinions.clear();
//        this.opinions.putAll(opinions);
//    }

    public String getFullName(){
        //TODO When culture gets implemented, we'll flip have a rule setting how this'll be handled.
        return givenName + " " + surname;
    }
    public String getForename(){
        return givenName;
    }
    public String getSurname(){
        return surname;
    }
    public Gender getGender(){
        return gender;
    }
    public Orientation getOrientation(){
        return orientation;
    }
    public Map<UUID,Opinion> getOpinions(){
        return opinions;
    }
    public List<DMEReference<? extends Title<?>>> getTitles(){
        return linked_titles;
    }
    public SuccessionEntry<?> getPreferredSuccession(){
        return preferredSuccession;
    }

    //==== Family Stuff ====
    public Family getOriginFamily(boolean adopted){
        List<Family.Relationship> rel = new ArrayList<>();
       if (adopted){
           rel.add(Family.Relationship.CHILD_ADOPTED);
           rel.add(Family.Relationship.CHILD_ADOPTED_DISOWNED);
       } else {
           rel.add(Family.Relationship.CHILD_BORN);
           rel.add(Family.Relationship.CHILD_BORN_DISOWNED);
       }
        for (Family family : linked_families.keySet()){
            Family.Relationship fRel = linked_families.get(family);
            if (rel.contains(fRel)){
                return family;
            }
        }
        if(adopted){
            return getOriginFamily(false);
        } else {
            return null;
        }
    }
    public List<Family> getFamiliesOfRelations(){
        List<Family> families = new ArrayList<>();
        for (Map.Entry<Family, Family.Relationship> family : linked_families.entrySet()){
            if (!family.getValue().getType().equals(Family.MemberType.OFFSPRING)){
                families.add(family.getKey());
            }
        }
        return families;
    }
    public List<DMEReference<HumanCharacter>> getSpouses(){
        List<DMEReference<HumanCharacter>> spouses = new ArrayList<>();
        for (Map.Entry<Family, Family.Relationship> family : linked_families.entrySet()){
            Family.MemberType type = family.getValue().getType();
            if (type.equals(Family.MemberType.HEAD) || type.equals(Family.MemberType.PARTNER)){
                List<DMEReference<HumanCharacter>> sp = family.getKey().getParents();
                sp.remove(this.getReference());
                spouses.addAll(sp);
            }
        }
        return spouses;
    }
    @Override
    public DMEReference<Culture> getCulture() {
        return culture;
    }

    //==== Conditional Logic ====

    public boolean isAdopted(){
        return linked_families.containsValue(Family.Relationship.CHILD_ADOPTED) || linked_families.containsValue(Family.Relationship.CHILD_ADOPTED_DISOWNED);
    }
    public boolean isSpouse(DMEReference<HumanCharacter> other){
        return getSpouses().contains(other);
    }



    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
