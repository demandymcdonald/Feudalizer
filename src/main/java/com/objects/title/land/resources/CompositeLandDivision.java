//package com.simulation.land.resources;
//
//import com.google.gson.JsonObject;
//
//import java.util.Date;
//import java.util.UUID;
//
//public abstract class CompositeLandDivision extends HabitableLand {
//    public CompositeLandDivision(UUID reasonID, Date created, Date ended) {
//        super(reasonID, created, ended);
//    }
//
//    public CompositeLandDivision(UUID reasonID, Date created, Date ended, JsonObject additionalData) {
//        super(reasonID, created, ended, additionalData);
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
