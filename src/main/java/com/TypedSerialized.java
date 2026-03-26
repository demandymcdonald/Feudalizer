package com;

import com.simulation.title.succession.rules.CommonLawEntry;
import com.simulation.title.succession.rules.CustomEntry;
import com.simulation.title.succession.rules.SuccessionEntry;

import java.util.HashMap;

public class TypedSerialized {
    private static final HashMap<Class<? extends SuccessionEntry<?>>, SuccessionEntry<?>> REGISTERED_RULES = new HashMap<>();
    static {
        REGISTERED_RULES.put(CustomEntry.class,new CustomEntry());
        REGISTERED_RULES.put(CommonLawEntry.class,new CommonLawEntry());
    }
    public static HashMap<Class<? extends SuccessionEntry<?>>, SuccessionEntry<?>> getRegisteredRules() {
        return REGISTERED_RULES;
    }
    public static void init(){
        Feudalizer.LOGGER.info("Initialized Succession Rules.. Count: " + REGISTERED_RULES.size());
    }
}
