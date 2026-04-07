package com.objects.culture.tenet;

import com.objects.culture.tenet.tenets.ReligionTenets;

import java.util.HashMap;
import java.util.Map;

public class TenetManager {
    private static final Map<String,Tenet<?,?>> tenets = new HashMap<>();
    public static void register(Tenet<?,?> tenet) {
        tenets.put(tenet.getID(), tenet);
    }

    public static Tenet<?,?> getTenet(String id) {
        return tenets.get(id);
    }

    public static void init(){
        ReligionTenets.init();
    }
}

