package com.objects.organization.labor;

import com.Global.*;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.hierarchy.Parented;
import com.utilities.id.UUIDIdentifiable;
import com.utilities.serialization.JsonSerializable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UnionLocal implements JsonSerializable, UUIDIdentifiable {
    UUID id;
    DMEReference<LaborUnion> union;
    int chapterNumber;
    Set<DMEReference<? extends Unionizable<?>>> linked_scope = new HashSet<>();

    public UnionLocal(DMEReference<LaborUnion> union, int chapterNumber) {
        this.id = UUID.randomUUID();
        this.union = union;
        this.chapterNumber = chapterNumber;
    }
    public UnionLocal() {}

    public DMEReference<LaborUnion> getUnion() {
        return union;
    }
    public String getName(){
        return union.get().getDisplayName() + " Local " + chapterNumber;
    }

    public void link(Unionizable<?> unionizable){
        linked_scope.add(unionizable.getReference());
        union.get().chapters.put(chapterNumber, this);
    }
    public void onLoad(){
        linked_scope.clear();
    }

    @Override
    public UUID getID() {
        return id;
    }
    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id.toString());
        json.addProperty("chapterNumber", chapterNumber);
        json.add("union",union.serialize());
        return json;
    }
    @Override
    public void fromJson(JsonElement json) {
        this.id = UUID.fromString(json.getAsJsonObject().get("id").getAsString());
        chapterNumber = json.getAsJsonObject().get("chapterNumber").getAsInt();
        union = DMEReference.deserialize(json.getAsJsonObject().get("union").getAsJsonObject());
    }
}
