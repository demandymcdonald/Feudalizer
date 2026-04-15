package com.objects.culture.tenet.types.mutable.tenets.general;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.types.mutable.MutableTenet;

import java.util.UUID;

public abstract class GroupAcceptance extends MutableTenet {
    public GroupAcceptance(TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, group, entry, id, name, description);
    }

    public GroupAcceptance(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, uuid, group, entry, id, name, description);
    }

    protected static PoliticalCompass calculate(TenetReference parent, Type type){}
}
