package com.objects.culture.tenet.mutable.tenets.leadership.election;

import com.base.component.InstanceType;
import com.base.component.immutable.ImmutableComponent;
import com.base.component.mutable.MutableComponent;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.object.ideology.Ideology;
import com.objects.organization.NonGovernmentEntity;
import com.objects.organization.government.GoverningEntity;
import com.objects.title.land.habitable.HabitableLand;
import com.utilities.IDisplayable;

import java.util.Map;
import java.util.function.Supplier;

public abstract class ElectionType<E extends ICultureOpinionated> extends MutableComponent<ElectionType<?>> implements IDisplayable{
    private String name;
    private String description;
    private final Supplier<PoliticalCompass> compass = Suppliers.memoize(this::makeCompass);
    public ElectionType(InstanceType type, String id, String name, String description) {
        super(type, id);
        this.name = name;
        this.description = description;
        ElectionTypes.registerType(this);
    }
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDisplayID() {
        return getID();
    }

    @Override
    public String getDisplayName() {
        return name;
    }
    public abstract Map<Ideology,Integer> getIdeologyWeights();
    public abstract PoliticalCompass getBase();
    public abstract Map<E,Long> fromLand(HabitableLand<?> land);
    public abstract Map<E,Long> fromGovernment(GoverningEntity<?> land);
    public abstract Map<E,Long> fromOrganization(NonGovernmentEntity<?> land);
    public final PoliticalCompass getPoliticalCompass(){
        return compass.get();
    };
    private PoliticalCompass makeCompass(){
        return PoliticalCompass.of(getBase(),getIdeologyWeights());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        name = data.get("et:name").getAsString();
        description = data.get("et:description").getAsString();
    }
    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("et:name", name);
        data.addProperty("et:description", description);
    }
}
