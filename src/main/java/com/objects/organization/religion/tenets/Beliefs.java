package com.objects.organization.religion.tenets;

import com.Global.*;
import com.base.component.InstanceType;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.mutable.MutableTenet;

public abstract class Beliefs extends MutableTenet {
    public Beliefs(InstanceType type, TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(type, parent, group, entry, id, name, description);
    }
    public Beliefs(InstanceType type, String id) {
        super(type, id);
    }

}
