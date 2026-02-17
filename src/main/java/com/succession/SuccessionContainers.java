package com.succession;


import com.google.gson.JsonObject;
import com.people.Character;
import com.people.CharacterManager;

import java.util.*;



public class SuccessionContainers {


    public record PackedSC(UUID primaryHeir, ArrayList<UUID> successionQueue) {
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            return json;
        }
        public static PackedSC deserialize(JsonObject json) {
            return null;
                    //TODO Fix
        }
        public SuccessionContainer convert(){
            CharacterManager CM = new CharacterManager();
            ArrayList<Character> sq = new ArrayList<>();
            for (UUID uuid : successionQueue) {
                CM.get(uuid);
            }
            return new SuccessionContainer(CM.get(primaryHeir), sq);
        };
    }
    public record SuccessionContainer(Character primaryHeir, ArrayList<Character> successionQueue) {
        public List<Character> getChain(){
            List<Character> chain = new ArrayList<>(successionQueue);
            chain.addFirst(primaryHeir);
            return chain;
        }
        public PackedSC convert(){
            UUID main = primaryHeir.getId();
            ArrayList<UUID> sq = new ArrayList<>();
            for (Character c : successionQueue){
                sq.add(c.getId());
            }
            return new PackedSC(main, sq);
        }
    }
}
