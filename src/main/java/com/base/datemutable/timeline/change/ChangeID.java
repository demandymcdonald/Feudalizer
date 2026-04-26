package com.base.datemutable.timeline.change;

import com.google.common.base.Suppliers;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.function.Supplier;

public class ChangeID {
    private final long classID;
    private LocalDate state;
    private Supplier<Long> fullID = Suppliers.memoize(this::buildFullID);
    public ChangeID(long changeId, LocalDate state) {
        this.classID = changeId;
        this.state = state;
    }
    public ChangeID(TimelineChange<?> change, LocalDate state){
        this.classID = buildChangeClassID(change);
        this.state = state;
    }
    public long getFullID(){
        return fullID.get();
    }
    public LocalDate getDate(){
        return state;
    }
    public void setDate(LocalDate date){
        this.state = date;
        fullID = Suppliers.memoize(this::buildFullID);
    }
    public long getClassID(){
        return classID;
    }
    private long buildFullID(){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putLong(classID);
        hasher.putLong(state.toEpochDay());
        return hasher.hash().asLong();
    }
    @SuppressWarnings("UnstableApiUsage")
    public static long buildChangeClassID(TimelineChange<?> change){
        return buildChangeClassID(change.getClass().getName());
    }
    @SuppressWarnings("UnstableApiUsage")
    public static long buildChangeClassID(String changeClassName){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putString(changeClassName, StandardCharsets.UTF_8);
        return hasher.hash().asLong();
    }
    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("classID",classID);
        json.addProperty("state",state.toEpochDay());
        return json;
    }
    public static ChangeID fromJson(JsonObject json){
        return new ChangeID(json.get("classID").getAsLong(), LocalDate.ofEpochDay(json.get("state").getAsLong()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChangeID cid) {
            return cid.getFullID() == this.getFullID();
        }
        return false;
    }
}
