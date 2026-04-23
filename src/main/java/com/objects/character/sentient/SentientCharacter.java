package com.objects.character.sentient;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.objects.character.LivingCreature;
import com.objects.character.Sex;
import com.objects.character.physical.PhysicalAppearance;
import com.objects.character.opinion.Opinion;
import com.objects.character.opinion.OpinionReason;
import com.objects.character.sentient.change.CharacterChanges;
import com.objects.character.sentient.change.SentientMapChange;
import com.objects.culture.Culture;
import com.objects.organization.education.EducationInstance;
import com.objects.organization.government.IGoverned;
import com.objects.culture.object.CultureObject;
import com.objects.organization.religion.IReligious;
import com.objects.organization.religion.Religion;
import com.objects.family.Family;
import com.objects.organization.government.GoverningEntity;
import com.objects.family.FamilyRelationship;
import com.objects.title.Title;
import com.objects.title.succession.rules.SuccessionEntry;
import com.utilities.id.SimpleUUID;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;

public abstract class SentientCharacter<T extends SentientCharacter<T>> extends LivingCreature<T>
        implements CultureObject<T>, IGoverned<T>, IReligious<T> {
    private String firstName;
    private String lastName;
    private Gender gender;
    private Orientation orientation;
    private SuccessionEntry<?> preferredSuccession;
    private TLMap<SimpleUUID,Opinion> opinions;
    private DMEReference<? extends GoverningEntity<?>> government;
    private DMEReference<Culture> culture;
    private DMEReference<Religion> religion;
    private TLSet<EducationInstance> education;
    private final Map<DMEReference<Family>, FamilyRelationship> linked_families = new HashMap<>();
    private final Set<DMEReference<? extends Title<?>>> linked_titles = new HashSet<>();
    public enum Orientation {
        Heterosexual("Heterosexual"),
        Homosexual("Homosexual"),
        Bisexual("Bisexual"),
        Asexual("Asexual"),
        Pansexual("Pansexual"),
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
    public SentientCharacter(LocalDate created, @Nullable LocalDate ended, PhysicalAppearance appearance, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended,appearance, initialState);
        getTimeline().internalAddChange(new SentientMapChange.OpinionMapChange<>(getReference(),created));
    }
    public SentientCharacter(DMEReference<T> dme) {
        super(dme);
    }

    public final void addEducation(EducationInstance education){
        this.education.add(education);
    }
    public final void setFirstName(String firstName) {
        getTimeline().addChange(new CharacterChanges.SetForename<>(getReference(), Global.getDate(),firstName));
    }
    public final void setLastName(String lastName) {
        getTimeline().addChange(new CharacterChanges.SetSurname<>(getReference(),Global.getDate(),lastName));
    }
    public final void setGender(Gender gender) {
        getTimeline().addChange(new CharacterChanges.SetGender<>(getReference(),Global.getDate(),gender));
    }
    public final void setOrientation(Orientation orientation) {
        getTimeline().addChange(new CharacterChanges.SetOrientation<>(getReference(),Global.getDate(),orientation));
    }
    public final void setPreferredSuccession(SuccessionEntry<?> preferredSuccession) {
        getTimeline().addChange(new CharacterChanges.SetDefaultSuccession<>(getReference(),Global.getDate(),preferredSuccession));
    }
    public final void addOpinion(Opinion opinion) {
        SimpleUUID simpleUUID = new SimpleUUID(opinion.getOther().getID());
        if (opinions.containsKey(simpleUUID)) {
            BiConsumer<SimpleUUID,Opinion> consumer = (uuid,op) -> {
                for (OpinionReason r : opinion.getActiveReasons()){
                    op.addOpinions(r);
                }
            };
            opinions.setChanged(true, ChangeType.VALUE,Map.of(simpleUUID,consumer));
        } else {
            opinions.put(simpleUUID,opinion);
        }
    }
    public final void internalSetForename(String forename){
        this.firstName = forename;
    }
    public final void internalSetSurname(String surname){
        this.lastName = surname;
    }
    public final void internalSetGender(Gender gender){
        this.gender = gender;
    }
    public final void internalSetOrientation(Orientation orientation){
        this.orientation = orientation;
    }
    public final void internalSetOpinionMap(TLMap<SimpleUUID, Opinion> opinions){
        this.opinions = opinions;
    }
    public final void internalSetPreferredSuccession(SuccessionEntry<?> succession){
        this.preferredSuccession = succession;
    }
    public final void internalSetGovernment(DMEReference<? extends GoverningEntity<?>> government){
        this.government = government;
    }
    public final void internalSetEducation(TLSet<EducationInstance> education){
        this.education = education;
    }
    @Override
    public final void internalSetCulture(DMEReference<Culture> culture) {
        this.culture = culture;
    }
    public final void internalSetReligion(DMEReference<Religion> religion){
        this.religion = religion;
    }
    public final DMEReference<? extends GoverningEntity<?>> getGovernment() {
        return government;
    }
    public final String getForename(){
        return firstName;
    }
    public final String getSurname(){
        return lastName;
    }
    public final String getFullName(){
        return firstName + " " + lastName;
    }
    public final Gender getGender(){
        return gender;
    }
    public final Orientation getOrientation(){
        return orientation;
    }
    public final DMEReference<Religion> getReligion(){
        return religion;
    }
    public final Pronouns getPronouns(){
        return gender.getPronouns();
    }
    @Override
    public final DMEReference<Culture> getCulture() {
        return culture;
    }
    public final SuccessionEntry<?> getPreferredSuccession(){
        return preferredSuccession;
    }
    public final TLSet<EducationInstance> getEducation(){
        return education;
    }
    public final TLMap<SimpleUUID, Opinion> getOpinion(){
        return opinions;
    }
    public final Map<DMEReference<Family>,FamilyRelationship> getFamilies(){
        return new HashMap<>(linked_families);
    }
    public final Optional<DMEReference<Family>> getBioFamily(){
        return Optional.ofNullable(linked_families.entrySet().stream().filter((e) -> e.getValue().getType().equals(FamilyRelationship.MemberType.OFFSPRING_BIOLOGIC)).findFirst().get().getKey());
    }
    public final Optional<DMEReference<Family>> getAdoptedFamily(){
        return Optional.ofNullable(linked_families.entrySet().stream().filter((e) -> e.getValue().getType().equals(FamilyRelationship.MemberType.OFFSPRING_ADOPTED)).findFirst().get().getKey());
    }
    public final void linkTitle(DMEReference<? extends Title<?>> title){
        linked_titles.add(title);
    }
    public final void linkFamily(DMEReference<Family> family, FamilyRelationship relationship){
        linked_families.put(family,relationship);
    }
    @Override
    public void doDateChange() {
        linked_families.clear();
        linked_titles.clear();
    }

    @Override
    public void onLink() {
        IGoverned.super.onLink();
    }

    @Override
    public Sex getSex() {
        return gender.getSexAtBirth();
    }
}
