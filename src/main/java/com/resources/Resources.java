package com.resources;

import java.util.HashMap;

public class Resources {
    private static final HashMap<String,ResourceType> registry = new HashMap<>();
    public static final ResourceType TIMBER = add("Timber", "It's goin' down, I'm yelling timber");
    public static final ResourceType IRON = add("Iron", "Skin made of Iron, steel in our bones, to dig...");
    public static final ResourceType SANDSTONE = add("Sandstone", "DUH DUH DUH DUH DUH");



    private static ResourceType add(String id, String description) {
        ResourceType rt = new ResourceType(id, description);
        registry.put(id, rt);
        return rt;
    }
    public static ResourceType get(String id) {
        return registry.get(id);
    }
}
