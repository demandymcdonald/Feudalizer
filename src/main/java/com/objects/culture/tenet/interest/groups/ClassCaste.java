package com.objects.culture.tenet.interest.groups;

import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.objects.culture.tenet.interest.IGPointer;
import com.objects.culture.tenet.interest.InterestGroup;
import com.utilities.number.bound_int.BoundInt;
import com.utilities.number.bound_int.BoundInts;

import java.util.Map;

import static com.base.component.InstanceType.HARDCODED;
import static com.objects.culture.tenet.interest.InterestGroup.Dimension.Class_Caste;

public abstract class ClassCaste extends InterestGroup {
    private double prestigeMultiplier;

    public ClassCaste(InstanceType type, String id, String displayName, double prestigeMultiplier, String description) {
        super(type, Class_Caste, "class_" + id, displayName, description);
        this.prestigeMultiplier = prestigeMultiplier;
    }

    @Override
    public PoliticalCompass getCompass(Culture culture) {
        return null;
    }

    @Override
    public InterestGroup getNewObject(String id) {
        return null;
    }

    @Override
    public void additionalLoad(JsonObject object) {
        super.additionalLoad(object);
        prestigeMultiplier = object.get("prestigeMultiplier").getAsDouble();
    }

    @Override
    public void additionalSave(JsonObject object) {
        super.additionalSave(object);
        object.addProperty("prestigeMultiplier", prestigeMultiplier);
    }

    public double getPrestigeMultiplier() {
        return prestigeMultiplier;
    }

    public static final ClassCaste ELITE = new ClassCaste(HARDCODED, "elite", "Elites", 2, "") {
        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5),
                    new IGPointer.Single(WORKING), BoundInts.Percent(true, -10));
        }

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.ELITE_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.ELITE_CLASS;
        }
    };
    public static final ClassCaste PROFESSIONAL = new ClassCaste(HARDCODED, "professional", "Professional", .95, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.PROFESSIONAL_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.PROFESSIONAL_CLASS;
        }
    };
    public static final ClassCaste ACADEMIC = new ClassCaste(HARDCODED, "academic", "Academics", .9, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.PROFESSIONAL_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.PROFESSIONAL_CLASS;
        }
    };
    public static final ClassCaste ARTIST = new ClassCaste(HARDCODED, "artist", "Artist", .85, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.ARTIST_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.ARTIST_CLASS;
        }
    };
    public static final ClassCaste OFFICER = new ClassCaste(HARDCODED, "officer", "Officers", 1.25, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.OFFICER_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.OFFICER_CLASS;
        }
    };
    public static final ClassCaste BUSINESS = new ClassCaste(HARDCODED, "business", "Business", 1.25, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.BUSINESS_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.BUSINESS_CLASS;
        }
    };
    public static final ClassCaste MIDDLE = new ClassCaste(HARDCODED, "middle", "Middle", .67, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.MIDDLE_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.MIDDLE_CLASS;
        }
    };
    public static final ClassCaste SOLDIER = new ClassCaste(HARDCODED, "soldier", "Soldier", .55, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.PROFESSIONAL_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.PROFESSIONAL_CLASS;
        }
    };
    public static final ClassCaste WORKING = new ClassCaste(HARDCODED, "working", "Working", .5, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.PROFESSIONAL_CLASS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.PROFESSIONAL_CLASS;
        }
    };
    public static final ClassCaste DISENFRANCHISED = new ClassCaste(HARDCODED, "disenfranchised", "Disenfranchised", .3, "") {


        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.DISENFRANCHISED;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.DISENFRANCHISED;
        }
    };
    public static final ClassCaste SLAVE = new ClassCaste(HARDCODED, "slave", "Slave", .25, "") {

        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.SLAVE;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.SLAVE;
        }
    };
    public static final ClassCaste OUTSIDER = new ClassCaste(HARDCODED, "outsider", "Outsider", .4, "") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return false;
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of();
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.OUTSIDER;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.OUTSIDER;
        }
    };
}
