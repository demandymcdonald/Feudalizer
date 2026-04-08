package com.objects.culture.tenet;

import com.objects.culture.tenet.group.TenetGroup;

public class TenetVariables {



    public static final TenetGroup.AcceptanceContainer SORT_ONLY = new TenetGroup.AcceptanceContainer(0,Acceptance.getAll());
    public static final TenetGroup.AcceptanceContainer PILLAR = new TenetGroup.AcceptanceContainer(PILLAR_MAX,Acceptance.INTEGRATED,Acceptance.CORE,Acceptance.CORE_FANATIC);
    public static final TenetGroup.AcceptanceContainer SYSTEM = new TenetGroup.AcceptanceContainer(SYSTEM_MAX,Acceptance.INTEGRATED,Acceptance.CORE,Acceptance.CORE_FANATIC,Acceptance.INTEGRATED);
}
