package com.objects.culture.tenet.group.population;

import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;

import static com.objects.character.LivingCreature.Gender;
public class InterestGroups {
    public static class SexGender {
        public static final InterestGroup MEN = new InterestGroup("gender:male", "Men", "Group for people who were born male and identify as such.") {
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
        };
        public static final InterestGroup WOMEN = new InterestGroup("gender:female", "Female", "Group for people who were born female and identify as such.") {
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
        };
        public static final InterestGroup TRANS = new InterestGroup("gender:trans", "Transgender", "Group for people who identify as transgender.") {
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
        };
        public static final InterestGroup NB_OTHER = new InterestGroup("gender:nb_other", "Non-Binary & Other", "Group for people who identify as  non-binary or another gender identity") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getGender().equals(Gender.Non_Binary);
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
    public static class Religion {
        public static final InterestGroup RELIGION_MAJORITY = new InterestGroup("religion:majority", "Majority Religion", "Group for people who identify with or practice the majority religion of the culture") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {

            }

            @Override
            public TenetGroup getRightsGroup() {
                return null;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return null;
            }
        };
        public static final InterestGroup RELIGION_MINORITY = new InterestGroup("religion:minority", "Minority Religion", "Group for people who identify with or practice a minority religion of the culture") {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {

            }

            @Override
            public TenetGroup getRightsGroup() {
                return null;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return null;
            }
        }

    }
}
