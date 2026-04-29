package com.objects.culture.tenet.interest.groups;

import com.base.component.InstanceType;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.objects.culture.tenet.interest.IGPointer;
import com.objects.culture.tenet.interest.InterestGroup;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

import java.util.Map;

import static com.base.component.InstanceType.HARDCODED;
import static com.objects.culture.tenet.interest.InterestGroup.Dimension.Sex_At_Birth;

public abstract class Sex extends InterestGroup {
    protected Sex(InstanceType type, String id, String displayName, String description) {
        super(type, Sex_At_Birth, id, displayName, description);
    }

    @Override
    public PoliticalCompass makeCompass(Culture culture) {
        return null;
    }

    public static final Sex MALE = new Sex(HARDCODED, "male", "Male", "Group for people who were born male.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getSex().equals(com.objects.character.Sex.MALE);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
    public static final Sex FEMALE = new Sex(HARDCODED, "female", "Female", "Group for people who were born female.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getSex().equals(com.objects.character.Sex.FEMALE);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
