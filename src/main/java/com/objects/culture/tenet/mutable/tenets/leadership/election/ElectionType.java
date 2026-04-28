package com.objects.culture.tenet.mutable.tenets.leadership.election;

import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.organization.NonGovernmentEntity;
import com.objects.organization.government.GoverningEntity;
import com.objects.title.land.habitable.HabitableLand;
import com.utilities.IDisplayable;

import java.util.Map;

public abstract class ElectionType<E extends ICultureOpinionated> implements IDisplayable {
    private final String id;
    private final String name;
    private final String description;
    public ElectionType(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        ElectionTypes.registerType(this);
    }
    public String getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    public abstract Map<E,Long> fromLand(HabitableLand<?> land);
    public abstract Map<E,Long> fromGovernment(GoverningEntity<?> land);
    public abstract Map<E,Long> fromOrganization(NonGovernmentEntity<?> land);
    public abstract PoliticalCompass getPoliticalCompass();

}
