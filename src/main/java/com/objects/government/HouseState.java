package com.objects.government;

import com.base.timeline.TimelineContainer;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.human.HumanCharacter;
import com.objects.family.Family;
import com.objects.title.land.County;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record HouseState(String name, UUID headOfHouse, Set<UUID> vassalHouses, Set<UUID> directHouseMembers,
                         HashMap<UUID, House.RetainerType> retainers) implements TimelineContainer<HouseState> {

    public static HouseState builder(String name, HashMultimap<House, County> counties, HumanCharacter headOfHouse, Set<Family> directMembers, HashMultimap<House.RetainerType, HumanCharacter> retainer) {
        Set<UUID> Houses = Sets.newHashSet();
        for (House house : counties.keySet()) {
            Houses.add(house.getId());
        }
        Set<UUID> dFamilies = Sets.newHashSet();
        for (Family family : directMembers) {
            dFamilies.add(family.getId());
        }
        HashMap<UUID, House.RetainerType> iRetainers = new HashMap<>();
        for (Map.Entry<House.RetainerType, HumanCharacter> family : retainer.entries()) {
            iRetainers.put(family.getValue().getId(), family.getKey());
        }
        return new HouseState(name, headOfHouse.getId(), Houses, dFamilies, iRetainers);
    }

    public JsonObject getSerialized() {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("HoH", headOfHouse.toString());
        JsonArray vassals = new JsonArray();
        JsonArray retainers = new JsonArray();
        JsonArray directMembers = new JsonArray();
        for (UUID uuid : vassalHouses) {
            vassals.add(uuid.toString());
        }
        for (UUID uuid : directHouseMembers) {
            directMembers.add(uuid.toString());
        }
        for (Map.Entry<UUID, House.RetainerType> entry : this.retainers.entrySet()) {
            JsonObject o = new JsonObject();
            o.addProperty("id", entry.getKey().toString());
            o.addProperty("type", entry.getValue().toString());
            retainers.add(o);
        }
        json.add("vassalHouses", vassals);
        json.add("retainers", retainers);
        json.add("directMembers", directMembers);
        return json;
    }

    public static HouseState deserialize(JsonObject json) {
        UUID headOfHouse = UUID.fromString(json.get("HoH").getAsString());
        String name = json.get("name").getAsString();
        Set<UUID> Houses = Sets.newHashSet();
        Set<UUID> DirectMembers = Sets.newHashSet();
        HashMap<UUID, House.RetainerType> iRetainers = new HashMap<>();
        JsonArray counties = json.get("vassalHouses").getAsJsonArray();
        JsonArray retainers_ = json.get("retainers").getAsJsonArray();
        JsonArray directMembers = json.get("directMembers").getAsJsonArray();
        for (JsonElement jsonElement : counties) {
            Houses.add(UUID.fromString(jsonElement.getAsString()));
        }
        for (JsonElement jsonElement : retainers_) {
            JsonObject o = jsonElement.getAsJsonObject();
            iRetainers.put(UUID.fromString(o.get("id").getAsString()), House.RetainerType.valueOf(o.get("type").getAsString()));
        }
        for (JsonElement jsonElement : directMembers) {
            DirectMembers.add(UUID.fromString(jsonElement.getAsString()));
        }
        return new HouseState(name, headOfHouse, Houses, DirectMembers, iRetainers);
    }
}
