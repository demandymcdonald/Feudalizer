package com.objects.culture.tenet.interest.groups;

import com.base.component.InstanceType;
import com.google.gson.JsonObject;
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
import org.geotools.api.filter.Or;

import java.util.Map;

import static com.base.component.InstanceType.HARDCODED;
import static com.objects.culture.tenet.interest.InterestGroup.Dimension.Sexual_Orientation;

public abstract class Orientation extends InterestGroup {
    public Orientation(InstanceType type, String id, String displayName, String description) {
        super(type, Sexual_Orientation, id, displayName, description);
    }

    public Orientation(InstanceType type, String id) {
        super(type, id);
    }

    @Override
    public PoliticalCompass makeCompass(Culture culture) {
        return new PoliticalCompass();
    }
    @Override
    public Orientation getNewObject(InstanceType type, String id, JsonObject data) {

    }
    public static final Orientation HETERO = new Orientation(HARDCODED, "hetero", "Heterosexual", "Is attracted to members of the opposite sex.") {


        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getOrientation().equals(SentientCharacter.Orientation.Heterosexual);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
    public static final Orientation HOMO = new Orientation(HARDCODED, "homo", "Homosexual", "Is attracted to members of the same sex.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getOrientation().equals(SentientCharacter.Orientation.Homosexual);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
    public static final Orientation BI = new Orientation(HARDCODED, "bi", "Bisexual", "Is attracted to members of either sex.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getOrientation().equals(SentientCharacter.Orientation.Bisexual);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
    public static final Orientation AE = new Orientation(HARDCODED, "ae", "Asexual", "Does not find people sexually attractive.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getOrientation().equals(SentientCharacter.Orientation.Asexual);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
    public static final Orientation PAN = new Orientation(HARDCODED, "pan", "Pansexual", "Is willing to date a person of any sex or gender.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getOrientation().equals(SentientCharacter.Orientation.Pansexual);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
    public static final Orientation QUESTIONING = new Orientation(HARDCODED, "questioning", "Questioning", "Is in the process of figuring out who they're attracted to.") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getOrientation().equals(SentientCharacter.Orientation.Questioning);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
    public static final Orientation OTHER = new Orientation(HARDCODED, "other", "Other", "Is attracted to something or someone") {
        @Override
        public <C extends SentientCharacter<C>> boolean isMember(C character) {
            return character.getOrientation().equals(SentientCharacter.Orientation.Other);
        }

        @Override
        public Map<IGPointer, BoundInt> getRelations() {
            return Map.of(new IGPointer.OtherDimension(this), BoundInts.Percent(true, -5));
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
