package com.base.datemutable;

import com.base.StateChangeKey;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.ArrayList;

public record DateState<T>(LocalDate created, LocalDate ended, T state, ArrayList<StateChangeKey> startKey, String EndNotes) {
    public DateState(JsonObject metadata, T sta){
        this(
                LocalDate.ofEpochDay(metadata.get("created").getAsLong()),
                metadata.has("ended") ? LocalDate.ofEpochDay(metadata.get("ended").getAsLong()) : null,
                sta,
                buildStartKey(metadata.get("startKey").getAsJsonArray()),
                metadata.has("endNotes") ? metadata.get("endNotes").getAsString() : null
        );
    }


    public JsonObject serializeMetadata(){
        JsonObject json = new JsonObject();
        json.addProperty("created", created.toEpochDay());
        if (ended != null) json.addProperty("ended", ended.toEpochDay());
        json.addProperty("state", state.toString());
        json.add("startKey", buildStartArray());
        json.addProperty("Endnotes", EndNotes);
        return json;
    }
    private JsonArray buildStartArray(){
        JsonArray array = new JsonArray();
        for (StateChangeKey key : startKey){
            array.add(key.serialize());
        }
        return array;
    }
    private static ArrayList<StateChangeKey> buildStartKey(JsonArray array){
        ArrayList<StateChangeKey> key = new ArrayList<>();
        for (JsonElement element : array){
            key.add(StateChangeKey.deserialize(element.getAsJsonObject()));
        }
        return key;
    }

    @Override
    public String toString() {;
        String pretty = String.join(", ", startKey.stream().map(StateChangeKey::toString).toList());
        return "State Created: " +created + ", Keys: "+ pretty;
    }

    @Override
    public LocalDate created() {
        return created;
    }

    @Override
    public LocalDate ended() {
        return ended;
    }

    @Override
    public String EndNotes() {
        return EndNotes;
    }

    @Override
    public ArrayList<StateChangeKey> startKey() {
        return startKey;
    }

    @Override
    public T state() {
        return state;
    }
}