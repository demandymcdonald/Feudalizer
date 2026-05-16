package com.objects.culture.tenet.flag;

import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.base.component.instanced.AbstractInstancedComponent;
import com.base.component.instanced.base.IOBase;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.IActivatable;
import com.objects.culture.TenetManager;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.change.OpinionChange;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.SubTenet;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;

import java.util.Optional;
import java.util.Set;

public abstract class FlagTenet extends AbstractComponent<FlagTenet> implements SubTenet {
    private TenetGroup group;
    private PoliticalCompass politicalCompass;
    private String name;
    private String description;
    private TenetReference parent;
    public FlagTenet(InstanceType type, TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(type, "flag_"+id);
        this.parent = parent;
        this.group = group;
        this.politicalCompass = entry;
        this.name = name;
        this.description = description;
    }
    public FlagTenet(InstanceType type, String id) {
        super(type, id);
    }
    @Override
    public final TenetGroup getGroup() {
        return group;
    }

    @Override
    public final TenetReference getTenetReference() {
        return SubTenet.super.getTenetReference();
    }
    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return parent.get().getAcceptanceObject(other,includeInfluencers,factorOtherTolerance);
    }
    @Override
    public final Type getType() {
        return SubTenet.super.getType();
    }

    @Override
    public final String getDisplayID() {
        return getID();
    }

    @Override
    public final String getDisplayName() {
        return name;
    }

    @Override
    public final String getDescription() {
        return description;
    }
    @Override
    public IPoliticalCompass getCompass(DMEReference<Culture> culture) {
        return politicalCompass;
    }
    @Override
    public DMEReference<Culture> getCulture() {
        return parent.get().getCulture();
    }
    @Override
    public final Optional<StateError> canBeActive(DMEReference<? extends IActivatable<?>> holder, TenetReference reference, OpinionChange<? extends IActivatable<?>> existing) {
        return SubTenet.super.canBeActive(holder, reference, existing);
    }

    @Override
    public void additionalSave(JsonObject object) {
        object.addProperty("ft:name", name);
        object.addProperty("ft:description", description);
        object.addProperty("ft:group", group.getDisplayID());
        object.add("ft:parent",parent.serialize());
        object.add("ft:compass", politicalCompass.toJson());
    }
    @Override
    public void additionalLoad(JsonObject object) {
        name = object.get("ft:name").getAsString();
        description = object.get("ft:description").getAsString();
        group = TenetManager.Group.INSTANCE.get(object.get("ft:group").getAsString());
        parent = TenetReference.deserialize(object.get("ft:parent").getAsJsonObject());
        politicalCompass = PoliticalCompass.build(object.get("ft:compass").getAsJsonObject());
    }

}
