package com.objects.culture.tenet.interest;

import com.base.instanced.InstanceType;
import com.google.gson.JsonObject;
import com.objects.character.sentient.Gender;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

import java.util.Map;

import static com.base.instanced.InstanceType.DATA_DRIVEN;
import static com.base.instanced.InstanceType.HARDCODED;
import static com.objects.character.sentient.Gender.*;
import static com.objects.culture.tenet.interest.InterestGroup.Dimension.*;

public class InterestGroups {


    public static abstract class Sex extends InterestGroup{
        protected Sex(InstanceType type, String id, String displayName, String description) {
            super(type, Sex_At_Birth, id, displayName, description);
        }
        @Override
        public PoliticalCompass makeCompass(Culture culture) {
            return null;
        }

        public static final Sex MALE = new Sex(HARDCODED,"male", "Male", "Group for people who were born male.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getSex().equals(com.objects.character.Sex.MALE);
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.SEX_MALE;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.SEX_MALE;
            }

        };
        public static final Sex FEMALE = new Sex(HARDCODED,"female", "Female", "Group for people who were born female.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getSex().equals(com.objects.character.Sex.FEMALE);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.SEX_FEMALE;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.SEX_FEMALE;
            }
        };
    }


    public static abstract class Gender_Identity extends InterestGroup {

        protected Gender_Identity(InstanceType type, String id, String displayName, String description) {
            super(type, Gender_Identity, id, displayName, description);
        }

        @Override
        public PoliticalCompass makeCompass(Culture culture) {
            return null;
        }

        @Override
        public InterestGroup getNewObject(String id) {
            return null;
        }
        public static final Gender_Identity MEN = new Gender_Identity(HARDCODED,"men", "Men", "Group for Cisgendered Men.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getGender().equals(Gender.Male);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.GENDER_MALE;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.GENDER_MALE;
            }


        };
        public static final Gender_Identity WOMEN = new Gender_Identity(HARDCODED,"women", "Women", "Group for Cisgendered Women.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getGender().equals(Gender.Female);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.GENDER_FEMALE;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.GENDER_FEMALE;
            }


        };
        public static final Gender_Identity TRANS = new Gender_Identity(HARDCODED,"trans", "Transgender Men & Women", "Group for people whose gender is different to the sex assigned at birth") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getGender().equals(Gender.Trans_Female) || character.getGender().equals(Gender.Trans_Male);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.GENDER_TRANS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.GENDER_TRANS;
            }


        };
        public static final Gender_Identity NB_OTHER = new Gender_Identity(HARDCODED,"nb", "Non-Binary", "Group for people who identify as non-binary") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getGender().equals(Non_Binary_Male) || character.getGender().equals(Non_Binary_Female);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.GENDER_NB_OTHER;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.GENDER_NB_OTHER;
            }


        };
    }
    public static abstract class Orientation extends InterestGroup{
        public Orientation(InstanceType type, String id, String displayName, String description) {
            super(type, Sexual_Orientation, id, displayName, description);
        }

        @Override
        public PoliticalCompass makeCompass(Culture culture) {
            return null;
        }

        @Override
        public InterestGroup getNewObject(String id) {
            return null;
        }
        public static final Orientation HETERO = new Orientation(HARDCODED,"hetero","Heterosexual","Is attracted to members of the opposite sex.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getOrientation().equals(SentientCharacter.Orientation.Heterosexual);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.HETERO;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.HETERO;
            }


        };
        public static final Orientation HOMO =  new Orientation(HARDCODED,"homo","Homosexual","Is attracted to members of the same sex.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getOrientation().equals(SentientCharacter.Orientation.Homosexual);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.HOMO;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.HOMO;
            }


        };
        public static final Orientation BI =  new Orientation(HARDCODED,"bi","Bisexual","Is attracted to members of either sex.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getOrientation().equals(SentientCharacter.Orientation.Bisexual);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.BI;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.BI;
            }


        };
        public static final Orientation AE =  new Orientation(HARDCODED,"ae","Asexual","Does not find people sexually attractive.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getOrientation().equals(SentientCharacter.Orientation.Asexual);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.AE;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.AE;
            }


        };
        public static final Orientation PAN =  new Orientation(HARDCODED,"pan","Pansexual","Is willing to date a person of any sex or gender.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getOrientation().equals(SentientCharacter.Orientation.Pansexual);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.PAN;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.PAN;
            }


        };
        public static final Orientation QUESTIONING =  new Orientation(HARDCODED,"questioning","Questioning","Is in the process of figuring out who they're attracted to.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getOrientation().equals(SentientCharacter.Orientation.Questioning);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.QUESTIONING;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.QUESTIONING;
            }


        };
        //Todo Demi is nearly impossible to model, so I won't include it for now.. I want to think on it though.
        public static final Orientation OTHER =  new Orientation(HARDCODED,"other","Other","Is attracted to something or someone") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getOrientation().equals(SentientCharacter.Orientation.Other);
            }
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5));
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.OTHER;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.OTHER;
            }


        };


    }
    public static abstract class ClassCaste extends InterestGroup {
        private double prestigeMultiplier;
        public ClassCaste(InstanceType type, String id, String displayName, double prestigeMultiplier, String description) {
            super(type,Class_Caste,"class_"+id, displayName, description);
            this.prestigeMultiplier = prestigeMultiplier;
        }

        @Override
        public PoliticalCompass makeCompass(Culture culture) {
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
        public static final ClassCaste ELITE = new ClassCaste(HARDCODED,"elite","Elites",2,"") {
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of(new IGPointer.OtherDimension(this),BoundInts.Percent(true,-5),
                new IGPointer.Single(WORKING),BoundInts.Percent(true,-10));
            }
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.ELITE_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.ELITE_CLASS;
            }
        };
        public static final ClassCaste PROFESSIONAL = new ClassCaste(HARDCODED,"professional","Professional",.95,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.PROFESSIONAL_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.PROFESSIONAL_CLASS;
            }
        };
        public static final ClassCaste ACADEMIC = new ClassCaste(HARDCODED,"academic","Academics",.9,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.PROFESSIONAL_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.PROFESSIONAL_CLASS;
            }
        };
        public static final ClassCaste ARTIST = new ClassCaste(HARDCODED,"artist","Artist",.85,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.ARTIST_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.ARTIST_CLASS;
            }
        };
        public static final ClassCaste OFFICER = new ClassCaste(HARDCODED,"officer","Officers",1.25,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.OFFICER_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.OFFICER_CLASS;
            }
        };
        public static final ClassCaste BUSINESS = new ClassCaste(HARDCODED,"business","Business",1.25,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.BUSINESS_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.BUSINESS_CLASS;
            }
        };
        public static final ClassCaste MIDDLE = new ClassCaste(HARDCODED,"middle","Middle",.67,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.MIDDLE_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.MIDDLE_CLASS;
            }
        };
        public static final ClassCaste SOLDIER = new ClassCaste(HARDCODED,"soldier","Soldier",.55,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.PROFESSIONAL_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.PROFESSIONAL_CLASS;
            }
        };
        public static final ClassCaste WORKING = new ClassCaste(HARDCODED,"working","Working",.5,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.PROFESSIONAL_CLASS;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.PROFESSIONAL_CLASS;
            }
        };
        public static final ClassCaste DISENFRANCHISED = new ClassCaste(HARDCODED,"disenfranchised","Disenfranchised",.3,"") {


            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.DISENFRANCHISED;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.DISENFRANCHISED;
            }
        };
        public static final ClassCaste SLAVE = new ClassCaste(HARDCODED,"slave","Slave",.25,"") {

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.SLAVE;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.SLAVE;
            }
        };
        public static final ClassCaste OUTSIDER = new ClassCaste(HARDCODED,"outsider","Outsider",.4,"") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.OUTSIDER;
            }
            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.OUTSIDER;
            }
        };
    }
}
