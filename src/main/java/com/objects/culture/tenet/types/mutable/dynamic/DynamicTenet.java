package com.objects.culture.tenet.types.mutable.dynamic;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.google.gson.JsonObject;
import com.objects.culture.AbstractCulture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.CultureObjectContainer;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.types.mutable.Tenet;
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
        displayID = buildID(group, name);
        container = new CultureObjectContainer<>(this.getReference());
    }
    @Override
    public final TenetReference getTenetReference() {
        return TenetReference.of(this);
    }

    @Override
    public final CultureObjectContainer<T> getContainer() {
        return container;
    }

    @Override
    public TenetGroup getGroup() {
        return tenetGroup;
    }

    @Override
    public final String displayName() {
        return displayName;
    }
    public final void internalDisplayName(String name){
        this.displayName = name;
    }
    public final void setDisplayName(String name){
        getTimeline().addChange(new DynamicBaseChanges.setDisplayName<>(getReference(), Global.getDate(), name));
    }

    @Override
    public final String description() {
        return description;
    }
    public final void internalDescription(String description){
        this.description = description;
    }
    public final void setDescription(String name){
        getTimeline().addChange(new DynamicBaseChanges.setDescription<>(getReference(), Global.getDate(), name));
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("displayID", displayID);
    }
    public final boolean isAllowedTenet(Tenet tenet){
        return getGroup().isParentOf(tenet.getGroup());
    }
    @Override
    public void additionalLoad(JsonObject data) {
        displayID = data.get("displayID").getAsString();
    }
    private static String buildID(TenetGroup tenetGroup, String name) {
        return tenetGroup.getDisplayID() + "/" + name.toLowerCase(Locale.ROOT);
    }
}
