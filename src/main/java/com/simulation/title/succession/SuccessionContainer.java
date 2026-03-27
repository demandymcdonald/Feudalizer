package com.simulation.title.succession;

import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.title.succession.rules.SuccessionEntry;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public class SuccessionContainer implements TimelineContainer<SuccessionContainer> {
    TreeMap<LocalDate, SuccessionEntry<?>> entries = new TreeMap<>();

    @Override
    public JsonObject getSerialized() {
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();

        for (LocalDate date : entries.keySet()) {
            SuccessionEntry<?> entry = entries.get(date);
            JsonObject o = new JsonObject();
            o.addProperty("date",date.toEpochDay());
            o.addProperty("class",entry.getClass().getName());
            o.add("container",entry.toJson());
            array.add(o);
        }
        json.add("entries",array);
        return json;
    }

    @Override
    public SuccessionContainer getDeserialized(JsonObject json) {
        JsonArray array = json.get("entries").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject o = array.get(i).getAsJsonObject();
            LocalDate date = LocalDate.ofEpochDay(o.get("date").getAsLong());
            try {
                Class<? extends SuccessionEntry<?>> clazz = (Class<? extends SuccessionEntry<?>>) Class.forName(o.get("class").getAsString());
                SuccessionEntry.getEmptyEntry(clazz).fromJson(o.get("container").getAsJsonObject());
                entries.put(date,SuccessionEntry.getEmptyEntry(clazz));
            } catch (Exception e){
                throw new RuntimeException("Invalid class get: " + e.getMessage());
            }
        }
        return this;
    }

    @Override
    public String Header() {
        return "SuccessionContainer";
    }
    public List<DMEReference<BookCharacter>> getAllHolders(){
        List<DMEReference<BookCharacter>> holders = new ArrayList<>();
        for (SuccessionEntry<?> entry : entries.values()){
            holders.add(entry.getSubject());
        }
        return holders;
    }
    public List<SuccessionEntry<?>> getAllEntries(){
        return new ArrayList<>(entries.values());
    }
    public SuccessionEntry<?> getEntry(LocalDate date){
        return entries.get(date);
    }
    public SuccessionEntry<?> getNext(LocalDate date){
        return entries.higherEntry(date).getValue();
    }
    public void addEntry(LocalDate date, SuccessionEntry<?> entry){
        entries.put(date,entry);
    }
    public void removeEntry(LocalDate date){
        entries.remove(date);
    }
}
