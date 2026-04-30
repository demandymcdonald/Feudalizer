package com.objects.character.sentient;

import com.Global;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.objects.character.LivingCreature;
import com.objects.character.Sex;
import com.objects.character.physical.PhysicalAppearance;
import com.objects.character.sentient.change.SentientChange;
import com.objects.character.sentient.change.SentientMapChange;
import com.objects.culture.Culture;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.organization.education.EducationInstance;
import com.objects.organization.government.IGoverned;
import com.objects.culture.object.CultureObject;
import com.objects.organization.religion.IReligious;
import com.objects.organization.religion.Religion;
import com.objects.family.Family;
import com.objects.organization.government.GoverningEntity;
import com.objects.family.FamilyRelationship;
import com.objects.succession.held.ICharacterHeld;
import com.objects.title.Title;

import com.objects.succession.plan.SuccessionPlan;
import com.objects.succession.rules.RuleEntry;
import com.utilities.caching.CachingSupplier;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public abstract class SentientCharacter<T extends SentientCharacter<T>> extends LivingCreature<T>
        implements CultureObject<T>, IGoverned<T>, IReligious<T> {
    private String firstName;
    private String lastName;
    private Gender gender;
    private Orientation orientation;
    private final CultureObjectContainer<T> cultureContainer;
    private final SuccessionPlan succession;
    private DMEReference<? extends GoverningEntity<?>> government;
    private DMEReference<Culture> culture;
    private DMEReference<Religion> religion;
    private TLSet<EducationInstance> education;
    private final Map<DMEReference<Family>, FamilyRelationship> linked_families = new HashMap<>();
    private final Set<DMEReference<? extends ICharacterHeld<?>>> linked_holdings = new HashSet<>();
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
        cultureContainer = new CultureObjectContainer<>(getReference());
        succession = new SuccessionPlan(this.getReference());
        getTimeline().internalAddChange(new SentientMapChange.EducationListChange<>(getReference(),created));
    }
    public SentientCharacter(DMEReference<T> dme) {
        super(dme);
        cultureContainer = new CultureObjectContainer<>(dme);
        succession = new SuccessionPlan(dme);
    }
    public final void addEducation(EducationInstance education){
        this.education.add(education);
    }
    public final void setFirstName(String firstName) {
        getTimeline().addChange(new SentientChange.SetForename<>(getReference(), Global.getDate(),firstName));
    }
    public final void setLastName(String lastName) {
        getTimeline().addChange(new SentientChange.SetSurname<>(getReference(),Global.getDate(),lastName));
    }
    public final void setGender(Gender gender) {
        getTimeline().addChange(new SentientChange.SetGender<>(getReference(),Global.getDate(),gender));
    }
    public final void setOrientation(Orientation orientation) {
        getTimeline().addChange(new SentientChange.SetOrientation<>(getReference(),Global.getDate(),orientation));
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
    public final void internalSetPreferredSuccession(TLSet<RuleEntry<?>> succession){
        this.succession.internalSetRules(succession);
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
    public final SuccessionPlan getSuccession(){
        return succession;
    }
    public final TLSet<EducationInstance> getEducation(){
        return education;
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
    private final CachingSupplier<Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship>> spousesSupplier = new CachingSupplier<>(() -> buildSpouses((T) this));
    public final Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> getSpouses(){
        return new HashMap<>(spousesSupplier.get());
    }
    private final CachingSupplier<Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship>> childrenSupplier = new CachingSupplier<>(() -> buildChildren((T) this));
    public final Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> getChildren(){
        return new HashMap<>(childrenSupplier.get());
    }
    private static <T extends SentientCharacter<T>> Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> buildSpouses(T character){
        Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> spouses = new HashMap<>();
        for(Map.Entry<DMEReference<Family>, FamilyRelationship> family : character.getFamilies().entrySet()){
            Family f = family.getKey().get();
            if(family.getValue().getType().equals(FamilyRelationship.MemberType.HEAD) || family.getValue().getType().equals(FamilyRelationship.MemberType.PARTNER)){
                DMEReference<? extends SentientCharacter<?>> spouse = f.getSpouse();
                spouses.put(spouse,f.getRelationship(spouse));
            }
        }
        return spouses;
    }
    private static <T extends SentientCharacter<T>> Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> buildChildren(T character){
        Map<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> children = new HashMap<>();
        for(Map.Entry<DMEReference<Family>, FamilyRelationship> family : character.getFamilies().entrySet()){
            Family f = family.getKey().get();
            if(family.getValue().getType().equals(FamilyRelationship.MemberType.HEAD) || family.getValue().getType().equals(FamilyRelationship.MemberType.PARTNER)){
                Set<DMEReference<? extends SentientCharacter<?>>> childs = f.getChildren();
                childs.remove(character.getReference());
                for(DMEReference<? extends SentientCharacter<?>> child : childs){
                    children.put(child,f.getRelationship(child));
                }
            }
        }
        return children;
    }
    public final Set<DMEReference<? extends ICharacterHeld<?>>> getTitles(){
        return new HashSet<>(linked_holdings);
    }
    public final void linkHeld(DMEReference<? extends ICharacterHeld<?>> title){
        linked_holdings.add(title);
    }
    public final void linkFamily(DMEReference<Family> family, FamilyRelationship relationship){
        linked_families.put(family,relationship);
    }
    @Override
    public void doDateChange() {
        linked_families.clear();
        linked_holdings.clear();
        spousesSupplier.clear();
        childrenSupplier.clear();
    }

    @Override
    public void onLink() {
        IGoverned.super.onLink();
    }

    @Override
    public Sex getSex() {
        return gender.getSexAtBirth();
    }


    @Override
    public void updateProceduralInfluencers() {

    }

    @Override
    public double influencerResistance(COReference<?> influencer) {
        return 0;
    }

    @Override
    public CultureObjectContainer<T> getContainer() {
        return cultureContainer;
    }

    @Override
    public Type getType() {
        return Type.Character;
    }
}
