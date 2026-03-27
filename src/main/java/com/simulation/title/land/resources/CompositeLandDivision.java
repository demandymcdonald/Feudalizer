//package com.simulation.land.resources;
//
//import com.google.gson.JsonObject;
//
//import java.util.Date;
//import java.util.UUID;
//
//public abstract class CompositeLandDivision extends HabitableLand {
//    public CompositeLandDivision(UUID id, Date created, Date ended) {
//        super(id, created, ended);
//    }
//
//    public CompositeLandDivision(UUID id, Date created, Date ended, JsonObject additionalData) {
//        super(id, created, ended, additionalData);
//    }
//
//    public CompositeLandDivision(JsonObject payload) {
//        super(payload);
//    }
//
//    @Override
//    protected JsonObject getPassthroughData() {
//        return null;
//    }
//
//    @Override
//    protected void onNewStateLoad(JsonObject passthrough) {
//
//    }
//}
