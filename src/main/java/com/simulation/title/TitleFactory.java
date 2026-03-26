package com.simulation.title;

import com.simulation.land.*;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.function.Function;

public class TitleFactory {
    private static final HashMap<String,Function<JsonObject,Title<?>>> registry = new HashMap<>();

    public static Title<?> create(JsonObject json) {
        String type = json.get("additionalData").getAsJsonObject().get("TitleType").getAsString();
        Function<JsonObject,Title<?>> factory = registry.get(type);
        if (factory == null) {
            throw new RuntimeException("Could not find title factory for title type " + type);
        }
        return factory.apply(json);
    }

    public static <T extends Title<T>> void register(Class<T> clas, Function<JsonObject,Title<?>> function) {
        registry.put(clas.getSimpleName(),function);
    }

    static {
        register(SpecialEconomicZone.class, SpecialEconomicZone::new);
        register(County.class, County::new);
        register(Province.class, Province::new);
        register(Manor.class, Manor::new);
        register(Town.class, Town::new);

    }
}
