package com.objects.character.sentient;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.multi.TimelineMap;
import com.objects.character.LivingCreature;
import com.objects.character.genetics.GeneticContainer;
import com.objects.character.opinion.Opinion;
import com.objects.character.opinion.OpinionReason;
import com.objects.character.sentient.change.CharacterChanges;
import com.objects.character.sentient.change.SentientMapChange;
import com.objects.culture.object.instance.TenetInstance;
import com.objects.culture.term.CulturalObject;
import com.objects.title.succession.rules.SuccessionEntry;
import com.utilities.id.SimpleUUID;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class SentientCharacter<T extends SentientCharacter<T,S>,S extends SentientSpecies> extends LivingCreature<T> implements CulturalObject<T> {
    private GeneticContainer<S> geneticContainer;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Orientation orientation;
    private SuccessionEntry<?> preferredSuccession;
    private TimelineMap<SimpleUUID, Opinion,T> opinions;
    public enum Pronouns {
        Masculine,
        Feminine,
        Neutral,
    }
    public enum Gender {
        Male("Male", HumanCharacter.Pronouns.Masculine),
        Female("Female", HumanCharacter.Pronouns.Feminine),
        Trans_Male("Trans-Male", HumanCharacter.Pronouns.Masculine),
        Trans_Female("Trans-Female", HumanCharacter.Pronouns.Feminine),
        Non_Binary("Non-Binary", HumanCharacter.Pronouns.Neutral);
//        Other("Other");

        private final String display;
        private final HumanCharacter.Pronouns pronouns;
        Gender(String d, HumanCharacter.Pronouns pronouns){
            display = d;
            this.pronouns = pronouns;
        }
        public String getFlavor(){
            return display;
        }
        public HumanCharacter.Pronouns getPronouns(){
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
        UUID id = opinion.getOther().getID();
        SimpleUUID simpleUUID = new SimpleUUID(opinion.getOther().getID());
        if (opinions.containsKey(simpleUUID)) {
            Opinion o = opinions.get(simpleUUID);
            for (OpinionReason r : opinion.getActiveReasons()){
                o.addOpinions(r);
            }
        }
        opinions.put(,opinion);
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
    public final void internalSetOpinionMap(TimelineMap<SimpleUUID, Opinion,T> opinions){
        this.opinions.clear();
        this.opinions.putAll(opinions);
    }
    public final void internalSetPreferredSuccession(SuccessionEntry<?> succession){
        this.preferredSuccession = succession;
    }
    public final void internalSetOpinions(TimelineMap<SimpleUUID, Opinion,T> opinions){
        this.opinions.clear();
        this.opinions = opinions;
    }
    public final String getForename(){
        return firstName;
    }
    public final String getSurname(){
        return lastName;
    }
    public final Gender getGender(){
        return gender;
    }
    public final Orientation getOrientation(){
        return orientation;
    }
    public final TimelineMap<SimpleUUID,Opinion,T> getOpinions(){
        return opinions;
    }
    public final SuccessionEntry<?> getPreferredSuccession(){
        return preferredSuccession;
    }
}
