package com.simulation.people;

import com.GlobalVars;
import com.base.StateChangeKey;
import com.base.reference.DMEReference;
import com.simulation.character.BookCharacter;
import com.simulation.title.land.County;
import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import java.util.*;

public class House extends DateMutableEntity<House> {
    private String Name;
    private final HashMultimap<House, County> DirectHouses = HashMultimap.create();
    private BookCharacter HeadOfHouse;
    private final Set<Family> DirectMembers = Sets.newHashSet();
    private final HashMultimap<RetainerType,BookCharacter> Retainer = HashMultimap.create();


    public House(String name, BookCharacter headOfHouse) {
        super(UUID.randomUUID(), GlobalVars.CURRENT_DATE(),null);
        this.Name = name;
        this.HeadOfHouse = headOfHouse;
        init();
    }
    public House(JsonObject payload) {
        super(payload);
        init();
    }
    @Override
    protected HouseState getCurrentContainer() {
        return HouseState.builder(Name,DirectHouses, HeadOfHouse, DirectMembers, Retainer);
    }

    @Override
    public void relink(HouseState state) {
        DirectHouses.clear();
        Retainer.clear();
        DirectMembers.clear();
        final HouseManager HM = DMRegistry.getHouseManager();
        final FamilyManager FM = DMRegistry.getFamilyManager();
        for (UUID uuid : state.vassalHouses) {
            House house = HM.get(uuid);
            DirectHouses.put(HM.get(uuid),null);
        }
    }
    public enum RetainerType {
        Knight,
        Administrator,
        Chief_of_Staff,
        Deputy_Chief_of_Staff,
        Servant,
        Slave,

    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    @Override
    protected JsonObject serializeData(HouseState data) {
        return data.getSerialized();
    }
    @Override
    protected HouseState buildState(JsonObject o) {
        return HouseState.deserialize(o);
    }

    @Override
    public StateChangeKey defaultKey() {
        return new StateChangeKey(StateChangeKey.StateChangeType.TITLE_CREATED,new DMEReference<>(this));
    }
    public Set<BookCharacter> getAllCharacters(){
        Set<BookCharacter> characters = new HashSet<>();
        characters.addAll(Retainer.values());
        characters.add(HeadOfHouse);
        for (House house : DirectHouses.keySet()) {
            characters.addAll(house.getAllCharacters());
        }
        for (Family family : DirectMembers) {
            characters.addAll(family.getMembers());
            characters.add(family.getHeadofFamily());
            characters.add(family.getSecondarySpouse().orElse(null));
        }
        return characters;
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
