package com.objects.organization.government.rights;

import com.base.component.ComponentReference;
import com.base.component.instanced.bi.IOIBi;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.government.GoverningEntity;

public class RightInstance<T extends Right<T>> extends IOIBi<T,RightInstance<T>, DMEReference<? extends GoverningEntity<?>>,DMEReference<? extends SentientCharacter<?>>> {
   private InterestGroup group;
   private RightLevel level;

    public RightInstance(ComponentReference<T> reference) {
        super(reference);
    }

    public RightInstance(JsonObject reference) {
        super(reference);
    }


    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
