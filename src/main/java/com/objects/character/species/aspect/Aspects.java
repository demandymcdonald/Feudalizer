package com.objects.character.species.aspect;

import java.util.HashMap;
import java.util.Map;

public class Aspects {
    private static final Map<String,PhysicalAspect> aspects = new HashMap<>();
    public static void registerAspect(PhysicalAspect aspect) {
        if(aspects.containsKey(aspect.getDisplayID())){
            throw new IllegalStateException("Duplicate aspect ID: " + aspect.getDisplayID());
        }
        aspects.put(aspect.getDisplayID(), aspect);
    }

}
