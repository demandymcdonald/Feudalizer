package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.conditions.ApplyConditions;
import com.base.timeline.change.conditions.Condition;
import com.base.timeline.change.conditions.ConditionResult;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.House;
import com.simulation.title.Title;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static com.base.timeline.change.CauseOfDeath.NOT_LOADED;

public abstract class BoundaryChange<B extends BoundaryChange<B,T>,T extends DateMutableEntity<T,?>> extends TimelineChange<T> {
    private final DMEReference<T> subject;
    private final boolean isBirth;
    protected BoundaryChange(LocalDate date, DMEReference<T> subject, boolean isBirth) {
        super(date, subject);
        this.subject = subject;
        this.isBirth = isBirth;
    }
    @Override
    protected JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.add("subject", subject.serialize());
        o.add("additionalData", additionalData());
        return o;
    }
    @Override
    protected List<Condition<StateError, ?>> buildApplyConditions() {
        return List.of(ApplyConditions.OUT_OF_BOUNDS);
    }
    @Override
    protected TimelineState<T> onApply(T entity, boolean saveChangeToDiff) {
        return null;
    }
    @Override
    public HashSet<DMEReference<?>> getScope() {
        return new HashSet<>();
    }
    public DMEReference<T> getSubject() {
        return subject;
    }
    @Override
    protected ChangeTags[] getTags() {
        return new ChangeTags[0];
    }
    public boolean isBirth() {
        return isBirth;
    }
    @Override
    protected List<Condition<ConditionResult.Nullify, ?>> buildNullifyConditions() {
        return List.of();
    }
    protected static <T extends DateMutableEntity<T,?>> DMEReference<T> unpack(JsonObject o){
        return DMEReference.deserialize(o.getAsJsonObject("subject"));
    }
    protected abstract JsonObject additionalData();
    protected abstract B onLoad(JsonObject additionalData);

    //==================================================================================================================

    public static class CharacterBirth extends BoundaryChange<CharacterBirth,BookCharacter>{
        protected CharacterBirth(LocalDate date, DMEReference<BookCharacter> subject) {
            super(date, subject,true);
        }
        @Override
        protected JsonObject additionalData() {
            return new JsonObject();
        }
        @Override
        protected CharacterBirth onLoad(JsonObject additionalData) {
            return this;
        }
        @Override
        protected String getText() {
            return getSubject() + " was born on " + getDate();
        }
        public static CharacterBirth fromJson(LocalDate date, JsonObject json){
            DMEReference<BookCharacter> subject = BoundaryChange.unpack(json);
            return new CharacterBirth(date,subject);
        }
    }
    public static class CharacterDeath extends BoundaryChange<CharacterDeath,BookCharacter>{
        private CauseOfDeath death;
        public CharacterDeath(LocalDate date, DMEReference<BookCharacter> subject, CauseOfDeath death) {
            super(date, subject,false);
            this.death = death;
        }
        @Override
        protected JsonObject additionalData() {
            JsonObject o = new JsonObject();
            o.addProperty("cod", death.name());
            return o;
        }
        @Override
        protected CharacterDeath onLoad(JsonObject additionalData) {
            death = CauseOfDeath.valueOf(additionalData.get("cod").getAsString());
            return this;
        }

        @Override
        protected String getText() {
            return getSubject() + " died on " + getDate() + " because of " + death;
        }
        public static CharacterDeath fromJson(LocalDate date, JsonObject json){
            DMEReference<BookCharacter> subject = BoundaryChange.unpack(json);
            return new CharacterDeath(date,subject,NOT_LOADED);
        }
    }
    public static class TitleBirth<T extends Title<T>> extends BoundaryChange<TitleBirth<T>,T>{
        protected TitleBirth(LocalDate date, DMEReference<T> subject) {
            super(date, subject, true);
        }

        @Override
        protected JsonObject additionalData() {
            return new JsonObject();
        }
        @Override
        protected TitleBirth<T> onLoad(JsonObject additionalData) {
            return this;
        }
        @Override
        protected String getText() {
            return getSubject() + " was created on " + getDate();
        }
        public static<T extends Title<T>> TitleBirth<T> fromJson(LocalDate date, JsonObject json){
            DMEReference<T> subject = BoundaryChange.unpack(json);
            return new TitleBirth<>(date,subject);
        }
    }
    public static class TitleDeath<T extends Title<T>> extends BoundaryChange<TitleDeath<T>,T>{
        private CauseOfDeath death;
        public TitleDeath(LocalDate date, DMEReference<T> subject, CauseOfDeath death) {
            super(date, subject,false);
            this.death = death;
        }
        @Override
        protected JsonObject additionalData() {
            JsonObject o = new JsonObject();
            o.addProperty("cod", death.name());
            return o;
        }
        @Override
        protected TitleDeath<T> onLoad(JsonObject additionalData) {
            death = CauseOfDeath.valueOf(additionalData.get("cod").getAsString());
            return this;
        }

        @Override
        protected String getText() {
            return getSubject() + " was destroyed " + getDate() + " because " + death;
        }
        public static<T extends Title<T>> TitleDeath<T> fromJson(LocalDate date, JsonObject json){
            DMEReference<T> subject = BoundaryChange.unpack(json);
            return new TitleDeath<>(date,subject,NOT_LOADED);
        }
    }
    public static class FamilyBirth extends BoundaryChange<FamilyBirth, Family>{
        protected FamilyBirth(LocalDate date, DMEReference<Family> subject) {
            super(date, subject,true);
        }
        @Override
        protected JsonObject additionalData() {
            return new JsonObject();
        }
        @Override
        protected FamilyBirth onLoad(JsonObject additionalData) {
            return this;
        }
        @Override
        protected String getText() {
            return getSubject() + " was formed on " + getDate();
        }
        public static FamilyBirth fromJson(LocalDate date, JsonObject json){
            DMEReference<Family> subject = BoundaryChange.unpack(json);
            return new FamilyBirth(date,subject);
        }
    }
    public static class FamilyDeath extends BoundaryChange<FamilyDeath,Family>{
        private CauseOfDeath death;
        public FamilyDeath(LocalDate date, DMEReference<Family> subject, CauseOfDeath death) {
            super(date, subject,false);
            this.death = death;
        }
        @Override
        protected JsonObject additionalData() {
            JsonObject o = new JsonObject();
            o.addProperty("cod", death.name());
            return o;
        }
        @Override
        protected FamilyDeath onLoad(JsonObject additionalData) {
            death = CauseOfDeath.valueOf(additionalData.get("cod").getAsString());
            return this;
        }

        @Override
        protected String getText() {
            return getSubject() + " was dissolved on " + getDate() + " because of " + death;
        }
        public static FamilyDeath fromJson(LocalDate date, JsonObject json){
            DMEReference<Family> subject = BoundaryChange.unpack(json);
            return new FamilyDeath(date,subject,NOT_LOADED);
        }
    }
    public static class HouseBirth extends BoundaryChange<HouseBirth, House>{
        protected HouseBirth(LocalDate date, DMEReference<House> subject) {
            super(date, subject,true);
        }
        @Override
        protected JsonObject additionalData() {
            return new JsonObject();
        }
        @Override
        protected HouseBirth onLoad(JsonObject additionalData) {
            return this;
        }
        @Override
        protected String getText() {
            return getSubject() + " was formed on " + getDate();
        }
        public static HouseBirth fromJson(LocalDate date, JsonObject json){
            DMEReference<House> subject = BoundaryChange.unpack(json);
            return new HouseBirth(date,subject);
        }
    }
    public static class HouseDeath extends BoundaryChange<HouseDeath,House>{
        private CauseOfDeath death;
        public HouseDeath(LocalDate date, DMEReference<House> subject, CauseOfDeath death) {
            super(date, subject,false);
            this.death = death;
        }
        @Override
        protected JsonObject additionalData() {
            JsonObject o = new JsonObject();
            o.addProperty("cod", death.name());
            return o;
        }
        @Override
        protected HouseDeath onLoad(JsonObject additionalData) {
            death = CauseOfDeath.valueOf(additionalData.get("cod").getAsString());
            return this;
        }

        @Override
        protected String getText() {
            return getSubject() + " was dissolved on " + getDate() + " because of " + death;
        }
        public static HouseDeath fromJson(LocalDate date, JsonObject json){
            DMEReference<House> subject = BoundaryChange.unpack(json);
            return new HouseDeath(date,subject,NOT_LOADED);
        }
    }
}
