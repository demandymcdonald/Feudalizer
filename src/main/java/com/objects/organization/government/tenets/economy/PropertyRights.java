package com.objects.organization.government.tenets.economy;

import com.Global.*;
import com.base.component.InstanceType;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.culture.tenet.mutable.augments.IRightsTenet;
import com.objects.organization.government.GoverningEntity;
import com.objects.organization.government.rights.Right;
import com.objects.organization.government.rights.RightInstance;

public class PropertyRights extends Right<PropertyRights> {

    public PropertyRights() {
        super(InstanceType.HARDCODED, id, name, description);
    }

    @Override
    public RightInstance<PropertyRights> instance(DMEReference<? extends GoverningEntity<?>> dmeReference, DMEReference<? extends SentientCharacter<?>> dmeReference2) {
        return null;
    }

    @Override
    public PropertyRights getNewObject(InstanceType type, String id, JsonObject data) {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
