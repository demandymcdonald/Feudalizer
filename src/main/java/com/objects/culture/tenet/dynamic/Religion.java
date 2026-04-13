package com.objects.culture.tenet.dynamic;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.Multimap;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.ReligionGroups;
import com.objects.culture.tenet.instance.TOReference;
import com.objects.culture.tenet.reference.TenetReference;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Religion extends DynamicTenet<Religion>{
    public Religion(String name, LocalDate created, LocalDate ended, List<ChangeSupplier<Religion, ?>> initialState) {
        super(ReligionGroups.RELIGION, name, created, ended, initialState);
    }

    public Religion(DMEReference<Religion> dme) {
        super(ReligionGroups.RELIGION, dme);
    }

    public Religion(String name, UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Religion, ?>> initialState) {
        super(ReligionGroups.RELIGION, name, id, created, ended, initialState);
    }

    @Override
    public void updateProceduralInfluencers() {

    }

    @Override
    public double influencerResistance(TOReference<?> influencer) {
        return 0;
    }

    @Override
    public TenetReference getTenetReference() {
        return null;
    }

    @Override
    public Multimap<Class<? extends TimelineChange<?>>, TenetCondition<?, ?, ?>> getConditions() {
        return null;
    }

    @Override
    public Map<TenetReference, Acceptance> getRelated() {
        return Map.of();
    }
}
