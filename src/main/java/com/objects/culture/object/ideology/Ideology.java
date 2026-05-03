package com.objects.culture.object.ideology;

import com.base.component.immutable.ImmutableComponent;
import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.PassiveCultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.utilities.IDisplayable;

public class Ideology extends ImmutableComponent<Ideology> implements PassiveCultureObject, IDisplayable{
    private PoliticalCompass compass;
    private String name;
    private String description;

    public Ideology(InstanceType type, String id, String name, String description, PoliticalCompass compass) {
        super(type,id);
        this.compass = compass;
        this.description = description;
        this.name = name;
    }
    public Ideology(InstanceType type, String id) {
        super(type,id);
    }

    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return compass.getAcceptanceContainer(other.getCompass(),factorOtherTolerance);
    }


    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("ideology:name", name);
        data.addProperty("ideology:description", description);
        data.add("ideology:compass",compass.toJson());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        name = data.get("ideology:name").getAsString();
        description = data.get("ideology:description").getAsString();
        compass = PoliticalCompass.build(data.get("ideology:compass").getAsJsonObject());
    }

    public PoliticalCompass getCompass() {
        return compass;
    }

    @Override
    public String getDisplayID() {
        return getID();
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Ideology getNewObject(InstanceType type, String id, JsonObject data) {
        return new Ideology(type, id);
    }
}
