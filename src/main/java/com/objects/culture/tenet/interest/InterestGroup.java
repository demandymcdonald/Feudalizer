package com.objects.culture.tenet.interest;

import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.PassiveCultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.organization.government.GoverningEntity;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.BoundInt;

import java.util.Map;

public abstract class InterestGroup implements IDisplayable, StringIdentifiable, PassiveCultureObject {

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
        Education("education:"),
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
        TenetManager.InterestGroups.register(this);
    }
    public <C extends SentientCharacter<C>> boolean isMember(DMEReference<? extends SentientCharacter<?>> reference){
        return isMember((C) reference.get());
    }
    public abstract <C extends SentientCharacter<C>> boolean isMember(C character);
    public String getQuickID(){
        return id.substring(3,10);
    };
    public AcceptanceContainer getSocialAcceptance(Culture culture){

    }
    public AcceptanceContainer getRights(DMEReference<? extends GoverningEntity<?>> government){

    }
    @Override
    public AcceptanceContainer getAcceptanceTenet(TenetReference tenet, boolean includeInfluencers) {
        return PassiveCultureObject.super.getAcceptanceTenet(tenet, false);
    }
    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return getAcceptanceObject(other,other.getCulture().get(),factorOtherTolerance);
    }
    public AcceptanceContainer getAcceptanceObject(ICultureObject object, Culture culture, boolean factorOtherTolerance) {
        return makeCompass(culture).getAcceptanceContainer(object.getCompass(), factorOtherTolerance);
    }
    public abstract Map<IGPointer, BoundInt> getRelations();
    public abstract TenetGroup getRightsGroup();
    public abstract TenetGroup getSocialStatusGroup();
    public abstract Dimension getDimension();
    public abstract PoliticalCompass makeCompass(Culture culture);
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
