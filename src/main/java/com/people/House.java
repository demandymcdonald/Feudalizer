package com.people;

import com.divisions.County;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class House extends DateMutableEntity<House.HouseState> {
    private HashMultimap<House, County> DirectHouses = HashMultimap.create();
    private Character HeadOfHouse;
    private Set<Family> DirectMembers = Sets.newHashSet();
    private Set<Family> IndirectMembers = Sets.newHashSet();


    public record HouseState(UUID HeadofHouse, Set<UUID> Houses, Set<UUID> DirectMembers, Set<UUID> IndirectMembers) {
        public static HouseState builder(HashMultimap<House, County> Counties, Character HeadOfHouse, Set<Family> DirectMembers, Set<Family> IndirectMembers) {
            Set<UUID> Houses = Sets.newHashSet();
            for (House house : Counties.keySet()) {
                Houses.add(house.getId());
            }
            Set<UUID> dFamilies = Sets.newHashSet();
            for (Family family : DirectMembers) {
                dFamilies.add(family.getId());
            }
            Set<UUID> iFamilies = Sets.newHashSet();
            for (Family family : IndirectMembers) {
                iFamilies.add(family.getId());
            }
            return new HouseState(HeadOfHouse.getId(),Houses,dFamilies,iFamilies);
        }
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.addProperty("HoH",HeadofHouse.toString());
            JsonArray counties = new JsonArray();
            JsonArray indirectMembers = new JsonArray();
            JsonArray directMembers = new JsonArray();
            for (UUID uuid : Houses) {
                counties.add(uuid.toString());
            }
            for (UUID uuid : DirectMembers) {
                directMembers.add(uuid.toString());
            }
            for (UUID uuid : IndirectMembers) {
                indirectMembers.add(uuid.toString());
            }
            json.add("counties", counties);
            json.add("indirectMembers", indirectMembers);
            json.add("directMembers", directMembers);
            return json;
        }
        public static HouseState deserialize(JsonObject json) {
            UUID headOfHouse = UUID.fromString(json.get("HoH").getAsString());
            Set<UUID> Houses = Sets.newHashSet();
            Set<UUID> DirectMembers = Sets.newHashSet();
            Set<UUID> IndirectMembers = Sets.newHashSet();
            JsonArray counties = json.get("counties").getAsJsonArray();
            JsonArray indirectMembers = json.get("indirectMembers").getAsJsonArray();
            JsonArray directMembers = json.get("directMembers").getAsJsonArray();
            for (JsonElement jsonElement : counties) {
                Houses.add(UUID.fromString(jsonElement.getAsString()));
            }
            for (JsonElement jsonElement : indirectMembers) {
                IndirectMembers.add(UUID.fromString(jsonElement.getAsString()));
            }
            for (JsonElement jsonElement : directMembers) {
                DirectMembers.add(UUID.fromString(jsonElement.getAsString()));
            }
            return new HouseState(headOfHouse,Houses,DirectMembers,IndirectMembers);
        }
    }



    public House(UUID id, Date foundDate, HouseState... states) {
        super(id, foundDate, null);
        for (HouseState state : states) {

        }
    }

    @Override
    protected HouseState getCurrentState() {
        return HouseState.builder(DirectHouses, HeadOfHouse, DirectMembers, IndirectMembers);
    }

    @Override
    public void relink(HouseState state) {
        DirectHouses.clear();
        IndirectMembers.clear();
        DirectMembers.clear();
        final HouseManager HM = DMRegistry.getHouseManager();
        final FamilyManager FM = DMRegistry.getFamilyManager();
        for (UUID uuid : state.Houses) {
            House house = HM.get(uuid);
            DirectHouses.put(HM.get(uuid),null);
        }
    }

    @Override
    protected JsonObject serializeData(HouseState data) {
        return data.serialize();
    }
    @Override
    protected HouseState buildState(JsonObject o) {
        return HouseState.deserialize(o);
    }
//
//    @Override
//    public JsonObject serialize() {
//        JsonObject json = new JsonObject();
//        JsonArray array = new JsonArray();
//        for (DateState<HouseState> s : this.timeline){
//            JsonObject md = s.serializeMetadata();
//            md.add("state",s.state().serialize());
//            array.add(md);
//        }
//        json.add("payload",array);
//        return json;
//    }



}
