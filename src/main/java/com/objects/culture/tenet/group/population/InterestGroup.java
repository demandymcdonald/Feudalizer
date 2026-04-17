package com.objects.culture.tenet.group.population;

import com.Global.*;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TGType;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.Displayable;
import com.utilities.id.StringIdentifiable;

import java.util.List;

import static com.objects.culture.tenet.group.groups.GovernmentGroups.POPULATION_GROUP_RIGHTS;

public abstract class InterestGroup implements Displayable, StringIdentifiable {

    private final String id;
    private final String displayName;
    private final String description;
    public enum Dimension{
        Gender,
        Race_Ethnicity,
        Class_Caste,
        Sexual_Orientation,

    }
    public InterestGroup(String id, String displayName, String description) {
        this.id = "ig_" + id;
        this.displayName = displayName;
        this.description = description;
    }
    public abstract <C extends SentientCharacter<C>> boolean isMember(C character);
    public TenetGroup.AcceptanceContainer getSocialAcceptance(Culture culture){

    }
    public TenetGroup.AcceptanceContainer getRights(Culture culture){

    }
    public TenetGroup.AcceptanceContainer getOpinion(CultureObject<?> object){

    }
    public abstract TenetGroup getRightsGroup();
    public abstract TenetGroup getSocialStatusGroup();
    public abstract Dimension getDimension();
    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public String getID() {
        return getDisplayID();
    }
}
