package com.objects.culture.tenet.interest;

import com.base.component.InstanceType;
import com.base.component.immutable.ImmutableComponent;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.PassiveCultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.objects.organization.government.GoverningEntity;
import com.utilities.IDisplayable;
import com.utilities.number.bound_int.BoundInt;

import java.util.Map;

public abstract class InterestGroup extends ImmutableComponent<InterestGroup> implements IDisplayable, PassiveCultureObject {
    private Dimension dimension;
    private String displayName;
    private String description;
    public enum Dimension{
        Sex_At_Birth("sex:",5),
        Gender_Identity("gender:",4.5),
        Race_Ethnicity("race:",5),
        Class_Caste("class:",3.5),
        Sexual_Orientation("orientation:",4),
        Religion("religion:",4),
        Political_Ideology("ideology:",3),
        Lifestyle("lifestyle:",.5),
        Culture("culture:",1.5),
        Education("education:",.8),
        Profession("profession:",.9),
        Disability("disability:",3.5),
        ;
        private final String prefix;
        private final double coreIdentityMult;
        Dimension(String prefix, double coreIdentityMult) {
            this.prefix = prefix;
            this.coreIdentityMult = coreIdentityMult;
        }
        public double getMult() {
            return coreIdentityMult;
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
        government.get().
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
        return getCompass(culture).getAcceptanceContainer(object.getCompass(), factorOtherTolerance);
    }
    public abstract Map<IGPointer, BoundInt> getRelations();
    public abstract GovernmentGroups.GovernmentGroup getRightsGroup();
    public abstract SocietyGroups.SocietyGroup getSocialStatusGroup();
    public final Dimension getDimension(){
        return dimension;
    };
    public abstract PoliticalCompass getCompass(Culture culture);
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
