package com.objects.culture.tenet.interest;

import com.base.instanced.AbstractIO;
import com.base.instanced.InstanceType;
import com.base.instanced.bi.IOBi;
import com.base.instanced.single.IOSingle;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
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

public abstract class InterestGroup extends IOSingle<InterestGroup,IGInstance,DMEReference<Culture>> implements IDisplayable, PassiveCultureObject {
    private Dimension dimension;
    private String displayName;
    private String description;
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
    public InterestGroup(InstanceType type, Dimension dimension, String id, String displayName, String description) {
        super(type, "ig_" + dimension.prefix +id);
        this.displayName = displayName;
        this.description = description;
        this.dimension = dimension;
    }
    public <C extends SentientCharacter<C>> boolean isMember(DMEReference<? extends SentientCharacter<?>> reference){
        return isMember((C) reference.get());
    }
    public abstract <C extends SentientCharacter<C>> boolean isMember(C character);
    public String getQuickID(){
        return getID().substring(3,10);
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
    public final Dimension getDimension(){
        return dimension;
    };
    public abstract PoliticalCompass makeCompass(Culture culture);
    @Override
    public final String getDisplayID() {
        return getID();
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final String getDisplayName() {
        return displayName;
    }
    @Override
    public InterestGroup getFreshInstance(String id) {
        return null;
    }
    @Override
    public final IGInstance instance(DMEReference<Culture> culture) {
        return new IGInstance(this.getReference(),culture);
    }

    public InterestGroup(InstanceType type, String id) {
        super(type, id);
    }

    @Override
    public void additionalSave(JsonObject object) {
        dimension = Dimension.valueOf(object.get("dimension").getAsString());
        displayName = object.get("displayName").getAsString();
        description = object.get("description").getAsString();
    }

    @Override
    public void additionalLoad(JsonObject object) {
        super.mainSave(object);
        object.addProperty("id", getID());
        object.addProperty("displayName", getDisplayName());
        object.addProperty("description", getDescription());
    }
}
