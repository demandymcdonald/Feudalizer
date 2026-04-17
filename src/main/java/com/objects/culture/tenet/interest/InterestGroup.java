package com.objects.culture.tenet.interest;

import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.Displayable;
import com.utilities.id.StringIdentifiable;

public abstract class InterestGroup implements Displayable, StringIdentifiable {

    private final String id;
    private final String displayName;
    private final String description;
    public enum Dimension{
        Sex_At_Birth("sex:"),
        Gender_Identity("gender:"),
        Race_Ethnicity("race:"),
        Class_Caste("class:"),
        Sexual_Orientation("orientation:"),
        Religion("religion:"),
        ;
        private final String prefix;
        Dimension(String prefix) {
            this.prefix = prefix;
        }
    }
    public InterestGroup(String id, String displayName, String description) {
        this.id = "ig_" + getDimension().prefix +id;
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
