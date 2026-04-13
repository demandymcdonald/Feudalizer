package com.objects.culture.tenet.dynamic;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.culture.AbstractCulture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.Tenet;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class DynamicTenet<T extends DynamicTenet<T>> extends AbstractCulture<T> implements Tenet, CultureObject<T> {
    private final TenetGroup tenetGroup;
    private final CultureObjectContainer<T> container;
    String displayID;
    String displayName;
    String description;
    public DynamicTenet(TenetGroup group, String name, LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
        this.tenetGroup = group;
        displayID = buildID(group,name);
        container = new CultureObjectContainer<>(this.getReference());
    }

    public DynamicTenet(TenetGroup group, DMEReference<T> dme) {
        super(dme);
        this.tenetGroup = group;
        container = new CultureObjectContainer<>(this.getReference());
    }

    public DynamicTenet(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
        this.tenetGroup = group;
        displayID = buildID(group,name);
        container = new CultureObjectContainer<>(this.getReference());
    }

    @Override
    protected void onLink() {

    }

    @Override
    public void doDateChange() {

    }

    @Override
    public final CultureObjectContainer<T> getContainer() {
        return container;
    }
    @Override
    public TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date, CauseOfEnd<? super T> cOd) {
        return null;
    }
    @Override
    public CauseOfEnd<? super T> defaultDeathCause() {
        return null;
    }

    @Override
    public TenetGroup getGroup() {
        return tenetGroup;
    }

    @Override
    public String displayName() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
    private static String buildID(TenetGroup tenetGroup, String name) {
        return tenetGroup.getDisplayID() + "/" + name.toLowerCase(Locale.ROOT);
    }
}
