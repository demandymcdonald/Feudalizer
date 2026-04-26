package com.objects.culture.tenet.interest;

import com.objects.character.sentient.Gender;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

import java.util.Map;

import static com.objects.character.sentient.Gender.*;
import static com.objects.culture.tenet.interest.InterestGroup.Dimension.*;

public class InterestGroups {


    public static class Sex{
        public static final InterestGroup MALE = new InterestGroup("male", "Male", "Group for people who were born male.") {
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

            @Override
            public Dimension getDimension() {
                return Sex_At_Birth;
            }
        };
        public static final InterestGroup FEMALE = new InterestGroup("female", "Female", "Group for people who were born female.") {
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

            @Override
            public Dimension getDimension() {
                return Sex_At_Birth;
            }
        };
    }


    public static class Gender_Identity {
        public static final InterestGroup MEN = new InterestGroup("men", "Men", "Group for Cisgendered Men.") {
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

            @Override
            public Dimension getDimension() {
                return Gender_Identity;
            }
        };
        public static final InterestGroup WOMEN = new InterestGroup("women", "Women", "Group for Cisgendered Women.") {
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

            @Override
            public Dimension getDimension() {
                return Gender_Identity;
            }
        };
        public static final InterestGroup TRANS = new InterestGroup("trans", "Transgender Men & Women", "Group for people whose gender is different to the sex assigned at birth") {
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

            @Override
            public Dimension getDimension() {
                return Gender_Identity;
            }
        };
        public static final InterestGroup NB_OTHER = new InterestGroup("nb", "Non-Binary", "Group for people who identify as non-binary") {
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

            @Override
            public Dimension getDimension() {
                return Gender_Identity;
            }
        };
    }
    public static class Orientation {
        public static final InterestGroup HETERO = new InterestGroup("hetero","Heterosexual","Is attracted to members of the opposite sex.") {
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

            @Override
            public Dimension getDimension() {
                return Dimension.Sexual_Orientation;
            }
        };
        public static final InterestGroup HOMO = new InterestGroup("homo","Homosexual","Is attracted to members of the same sex.") {
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

            @Override
            public Dimension getDimension() {
                return Dimension.Sexual_Orientation;
            }
        };
        public static final InterestGroup BI = new InterestGroup("bi","Bisexual","Is attracted to members of either sex.") {
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

            @Override
            public Dimension getDimension() {
                return Dimension.Sexual_Orientation;
            }
        };
        public static final InterestGroup AE = new InterestGroup("ae","Asexual","Does not find people sexually attractive.") {
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

            @Override
            public Dimension getDimension() {
                return Dimension.Sexual_Orientation;
            }
        };
        public static final InterestGroup PAN = new InterestGroup("pan","Pansexual","Is willing to date a person of any sex or gender.") {
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

            @Override
            public Dimension getDimension() {
                return Dimension.Sexual_Orientation;
            }
        };
        public static final InterestGroup QUESTIONING = new InterestGroup("questioning","Questioning","Is in the process of figuring out who they're attracted to.") {
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

            @Override
            public Dimension getDimension() {
                return Dimension.Sexual_Orientation;
            }
        };
        //Todo Demi is nearly impossible to model, so I won't include it for now.. I want to think on it though.
        public static final InterestGroup OTHER = new InterestGroup("other","Other","Is attracted to something or someone") {
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

            @Override
            public Dimension getDimension() {
                return Dimension.Sexual_Orientation;
            }
        };
    }
    public static abstract class ClassCaste extends InterestGroup {
        private final double prestigeMultiplier;
        public ClassCaste(String id, String displayName, double prestigeMultiplier, String description) {
            super("class_"+id, displayName, description);
            this.prestigeMultiplier = prestigeMultiplier;
        }
        @Override
        public Dimension getDimension() {
            return Class_Caste;
        }
        public double getPrestigeMultiplier() {
            return prestigeMultiplier;
        }
        public static final ClassCaste ELITE = new ClassCaste("elite","Elites",2,"") {
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
        public static final ClassCaste PROFESSIONAL = new ClassCaste("professional","Professional",.95,"") {

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
        public static final ClassCaste ACADEMIC = new ClassCaste("academic","Academics",.9,"") {

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
        public static final ClassCaste ARTIST = new ClassCaste("artist","Artist",.85,"") {

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
        public static final ClassCaste OFFICER = new ClassCaste("officer","Officers",1.25,"") {

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
        public static final ClassCaste BUSINESS = new ClassCaste("business","Business",1.25,"") {

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
        public static final ClassCaste MIDDLE = new ClassCaste("middle","Middle",.67,"") {

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
        public static final ClassCaste SOLDIER = new ClassCaste("soldier","Soldier",.55,"") {

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
        public static final ClassCaste WORKING = new ClassCaste("working","Working",.5,"") {

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
        public static final ClassCaste DISENFRANCHISED = new ClassCaste("disenfranchised","Disenfranchised",.3,"") {


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
        public static final ClassCaste SLAVE = new ClassCaste("slave","Slave",.25,"") {

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
        public static final ClassCaste OUTSIDER = new ClassCaste("outsider","Outsider",.4,"") {
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
