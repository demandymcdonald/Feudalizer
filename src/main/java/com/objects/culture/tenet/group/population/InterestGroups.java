package com.objects.culture.tenet.group.population;

import com.objects.character.Sex;
import com.objects.character.sentient.Gender;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;

import static com.objects.character.sentient.Gender.*;
import static com.objects.culture.tenet.group.population.InterestGroup.Dimension.Gender_Identity;
import static com.objects.culture.tenet.group.population.InterestGroup.Dimension.Sex_At_Birth;

public class InterestGroups {


    public static class Sex{
        public static final InterestGroup MALE = new InterestGroup("male", "Male", "Group for people who were born male.") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getSex().equals(com.objects.character.Sex.MALE);
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
}
