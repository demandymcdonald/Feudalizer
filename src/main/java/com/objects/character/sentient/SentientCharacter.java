package com.objects.character.sentient;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.multi.TLMultiChange;
import com.base.timeline.change.multi.MiddlemanMap;
import com.google.common.collect.Maps;
import com.objects.character.LivingCreature;
import com.objects.character.Sex;
import com.objects.character.species.genetics.GeneticContainer;
import com.objects.character.opinion.Opinion;
import com.objects.character.opinion.OpinionReason;
import com.objects.character.sentient.change.CharacterChanges;
import com.objects.character.sentient.change.SentientMapChange;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.dynamic.tenets.Religion;
import com.objects.family.Family;
import com.objects.government.GoverningEntity;
import com.objects.title.Title;
import com.objects.title.succession.rules.SuccessionEntry;
import com.utilities.id.SimpleUUID;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;

public abstract class SentientCharacter<T extends SentientCharacter<T>> extends LivingCreature<T> implements CultureObject<T> {
    private GeneticContainer<?> geneticContainer;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Orientation orientation;
    private SuccessionEntry<?> preferredSuccession;
    private MiddlemanMap<SimpleUUID, Opinion,UUID,T> opinions;


    private Optional<GoverningEntity<?>> linked_government;
    private final Map<Family, Family.Relationship> linked_families = Maps.newHashMap();
    private final List<DMEReference<? extends Title<?>>> linked_titles = new ArrayList<>();


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
    public SentientCharacter(LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        getTimeline().internalAddChange(new SentientMapChange.OpinionMapChange<>(getReference(),created));
    }
    public SentientCharacter(DMEReference<T> dme) {
        super(dme);
    }

    public SentientCharacter(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
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
            opinions.setChanged(true,TLMultiChange.ChangeType.VALUE,Map.of(simpleUUID,consumer));
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
    public final void internalSetOpinionMap(MiddlemanMap<SimpleUUID, Opinion,UUID,T> opinions){
        this.opinions = opinions;
    }
    public final void internalSetPreferredSuccession(SuccessionEntry<?> succession){
        this.preferredSuccession = succession;
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
    public final Religion getReligion(){

    }

    public final SuccessionEntry<?> getPreferredSuccession(){
        return preferredSuccession;
    }

    @Override
    public Sex getSex() {
        return gender.getSexAtBirth();
    }
}
