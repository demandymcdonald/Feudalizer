package com.objects.culture.tenet.interest.groups;

import com.base.component.InstanceType;
import com.objects.character.sentient.Gender;
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
import static com.objects.character.sentient.Gender.Non_Binary_Female;
import static com.objects.character.sentient.Gender.Non_Binary_Male;
import static com.objects.culture.tenet.interest.InterestGroup.Dimension.Gender_Identity;

public abstract class Gender_Identity extends InterestGroup {

    protected Gender_Identity(InstanceType type, String id, String displayName, String description) {
        super(type, Gender_Identity, id, displayName, description);
    }

    @Override
    public PoliticalCompass getCompass(Culture culture) {
        return null;
    }

    @Override
    public InterestGroup getNewObject(String id) {
        return null;
    }

    public static final Gender_Identity MEN = new Gender_Identity(HARDCODED, "men", "Men", "Group for Cisgendered Men.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getGender().equals(Gender.Male);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.GENDER_MALE;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.GENDER_MALE;
        }


    };
    public static final Gender_Identity WOMEN = new Gender_Identity(HARDCODED, "women", "Women", "Group for Cisgendered Women.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getGender().equals(Gender.Female);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.GENDER_FEMALE;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.GENDER_FEMALE;
        }


    };
    public static final Gender_Identity TRANS = new Gender_Identity(HARDCODED, "trans", "Transgender Men & Women", "Group for people whose gender is different to the sex assigned at birth") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getGender().equals(Gender.Trans_Female) || character.getGender().equals(Gender.Trans_Male);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.GENDER_TRANS;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.GENDER_TRANS;
        }


    };
    public static final Gender_Identity NB_OTHER = new Gender_Identity(HARDCODED, "nb", "Non-Binary", "Group for people who identify as non-binary") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getGender().equals(Non_Binary_Male) || character.getGender().equals(Non_Binary_Female);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
        }

        @Override
        public GovernmentGroups.GovernmentGroup getRightsGroup() {
            return GovernmentGroups.GENDER_NB_OTHER;
        }

        @Override
        public SocietyGroups.SocietyGroup getSocialStatusGroup() {
            return SocietyGroups.GENDER_NB_OTHER;
        }


    };
}
