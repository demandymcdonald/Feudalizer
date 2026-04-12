package com.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

public interface IDateMutableEntity {
    static <R extends DateMutableEntity<R>,B extends Collection<R>> List<UUID> convert(B b){
        List<UUID> result = new ArrayList<>();
        for (R r : b){
            result.add(r.getDisplayID());
        }
        return result;
    }

    static JsonArray buildJson(List<UUID> ids){
        JsonArray json = new JsonArray();
        for (UUID id : ids){
            json.add(id.toString());
        }
        return json;
    }

    @SafeVarargs
    static <R extends DateMutableEntity<R>> JsonArray buildJson(R... ent){
        JsonArray json = new JsonArray();
        for (R r : ent){
            JsonObject obj = new JsonObject();
            obj.addProperty("type",r.getClass().getSimpleName());
            obj.addProperty("id", r.getDisplayID().toString());
            json.add(obj);
        }
        return buildJson(convert(List.of(ent)));
    }

    static List<UUID> buildUUID(JsonArray json){
        List<UUID> result = new ArrayList<>();
        for (int i = 0; i < json.size(); i++){
            result.add(UUID.fromString(json.get(i).getAsString()));
        }
        return result;
    }

    //TODO: Get proper manager from class type.
    static Map<UUID,ObjectType> quickBuildID(DateMutableEntity<?>... entities){
        Map<UUID,ObjectType> result = new HashMap<>();
        for (DateMutableEntity<?> e : entities){
            result.put(e.getDisplayID(),DMRegistry.getObjectType(e));
        }
        return result;
    }
}
