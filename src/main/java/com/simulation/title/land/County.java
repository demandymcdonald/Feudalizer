package com.simulation.title.land;

import com.base.reference.SimpleReference;
import com.base.reference.StateReference;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.title.land.resources.HabitableLand;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class County extends HabitableLand<County> {
    public County(UUID id, LocalDate created, LocalDate ended, String name, GeometryType geoType, String geoID) {
        super(id, name, created, ended, geoType,geoID);
        init();
    }
    public County(UUID id,String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID) {
        super(id, name, created, ended, geoType,geoID);
        init();
    }

    public County(JsonObject payload) {
        super(payload);
    }

    @Override
    public boolean canInherit(BookCharacter person) {
        return false;
    }

    @Override
    public boolean isInheritable() {
        return false;
    }

    @Override
    public boolean isSubPropagating() {
        return false;
    }

    @Override
    public List<BookCharacter> getAllClaimants() {
        return List.of();
    }


    @Override
    protected JsonObject getPassthroughData() {
        return super.getPassthroughData();
    }

    @Override
    protected void onNewStateLoad(JsonObject passthrough) {
        super.onNewStateLoad(passthrough);
    }

    @Override
    public StateReference getTitleName() {
        return new SimpleReference("County of " + getName());
    }

    @Override
    protected void onRelink() {

    }
}
