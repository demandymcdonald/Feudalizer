package com.objects.title.land;

import com.objects.culture.object.CultureObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.title.Title;
import com.objects.title.land.resources.Resource;
import com.objects.title.land.resources.ResourceType;

import java.util.*;

public abstract class HabitableLand<R extends HabitableLand<R>> extends AbstractLandDivision<R> implements CultureObject<> {
    final Set<Resource> resources = new HashSet<>();
    long population;


    public HabitableLand(JsonObject payload) {
        super(payload);
    }



    @Override
    protected void onNewStateLoad(JsonObject passthrough) {
        fromJson(passthrough);
    }
    @Override
    protected JsonObject getPassthroughData() {
        return toJson();
    }


    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("population", population);
        JsonArray array = new JsonArray();
        if (resources != null && !resources.isEmpty()) {
            for (Resource resource : resources) {
                JsonObject rj = new JsonObject();
                rj.addProperty("type",resource.type().name());
                rj.addProperty("amount",resource.abundance());
                array.add(rj);
            }
        }
        json.add("resources", array);
        return json;
    }
    public void fromJson(JsonObject json) {
        population = json.get("population").getAsLong();
        resources.clear();
        JsonArray array = json.get("resources").getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject rj = element.getAsJsonObject();
            resources.add(new Resource(rj.get("type").getAsString(),rj.get("amount").getAsInt()));
        }
    }
    public Set<Resource> getDirectResources() {
        return resources;
    }
    public Set<Resource> getResources() {
        Map<ResourceType, Integer> maxAbundance = new HashMap<>();

        // Add this land's direct resources
        for (Resource r : resources) {
            maxAbundance.put(r.type(), r.abundance());
        }

        // Aggregate from children, keeping max abundance per type
        for (Title<?> child : getChildren()) {
            if (child instanceof HabitableLand<?> h) {
                for (Resource r : h.getResources()) {
                    maxAbundance.merge(r.type(), r.abundance(), Math::max);
                }
            }
        }

        // Convert back to Resource set
        Set<Resource> result = new HashSet<>();
        for (Map.Entry<ResourceType, Integer> entry : maxAbundance.entrySet()) {
            result.add(new Resource(entry.getKey(), entry.getValue()));
        }
        return result;
    }
    public long getPopulation() {
        long result = population;
        for (Title<?>  child : getChildren()) {
            if (child instanceof HabitableLand h) {
                result += h.getPopulation();
            }
        }
        return result;
    }
}
