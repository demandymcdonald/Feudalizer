package com.objects.culture.tenet.interest.groups;

import com.base.component.InstanceType;
import com.objects.culture.tenet.interest.InterestGroup;

public abstract class Profession extends InterestGroup {

    public Profession(InstanceType type, String id, String displayName, String description) {
        super(type, Dimension.Profession, id, displayName, description);
    }

    public Profession(InstanceType type, String id) {
        super(type, id);
    }
}
