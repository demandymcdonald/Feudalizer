package com.objects.culture.tenet.mutable.tenets.leadership.election;

import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.organization.NonGovernmentEntity;
import com.objects.organization.government.GoverningEntity;
import com.objects.title.land.habitable.HabitableLand;

import java.util.Map;

public abstract class ElectionType<E extends ICultureOpinionated> {
    private final String id;
    public ElectionType(String id) {
        this.id = id;
        ElectionTypes.registerType(this);
    }
    public String getId() {
        return id;
    }
    public abstract Map<E,Long> fromLand(HabitableLand<?> land);
    public abstract Map<E,Long> fromGovernment(GoverningEntity<?> land);
    public abstract Map<E,Long> fromOrganization(NonGovernmentEntity<?> land);
    public abstract PoliticalCompass getPoliticalCompass();
}
