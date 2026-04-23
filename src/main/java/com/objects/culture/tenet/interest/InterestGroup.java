package com.objects.culture.tenet.interest;

import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.BoundInt;

import java.util.Map;

public abstract class InterestGroup implements IDisplayable, StringIdentifiable {

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
        Political_Ideology("ideology:"),
        Lifestyle("lifestyle:"),
        Culture("culture:"),
        Disability("disability:"),
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
    public abstract Map<IGPointer, BoundInt> getRelations();
    public abstract TenetGroup getRightsGroup();
    public abstract TenetGroup getSocialStatusGroup();
    public abstract Dimension getDimension();
    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getID() {
        return getDisplayID();
    }
}
