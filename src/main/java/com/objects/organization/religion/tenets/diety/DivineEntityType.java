package com.objects.organization.religion.tenets.diety;

import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.ReligionGroups;

public enum DivineEntityType {
    DEMIURGE(ReligionGroups.DEMIURGE),
    SUPREME_GOD(ReligionGroups.SUPREME_GOD),
    MAJOR_GOD(ReligionGroups.MAJOR_GOD),
    MINOR_GOD(ReligionGroups.MINOR_GOD),
    DEMI_GOD(ReligionGroups.DEMIGOD),
    APOTHEOSIZED_HUMAN(ReligionGroups.APOTHEOSIZED),
    ANGEL(ReligionGroups.ANGEL),
    FALLEN_ANGEL(ReligionGroups.FALLEN_ANGEL),
    DEMON(ReligionGroups.DEMON),
    INTERMEDIARY_SPIRIT(ReligionGroups.INTERMEDIARY_SPIRIT);
    private final TenetGroup group;

    DivineEntityType(TenetGroup group) {
        this.group = group;
    }
    public TenetGroup getGroup(){
        return group;
    }
}
