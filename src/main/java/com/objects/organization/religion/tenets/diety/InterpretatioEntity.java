package com.objects.organization.religion.tenets.diety;

import com.Global.*;
import com.base.component.InstanceType;
import com.base.component.immutable.ImmutableComponent;
import com.google.gson.JsonObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.organization.religion.IReligionObject;
import com.objects.organization.religion.utility.ReligionEdge;
import org.jgrapht.Graph;

import java.util.Set;

public class InterpretatioEntity extends ImmutableComponent<InterpretatioEntity> implements IReligionObject {

    public InterpretatioEntity(InstanceType type, String id) {
        super(type, id);
    }

    @Override
    public InterpretatioEntity getNewObject(InstanceType type, String id, JsonObject data) {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
