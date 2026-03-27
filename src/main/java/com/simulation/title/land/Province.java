package com.simulation.title.land;

import com.base.reference.StateReference;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.title.land.resources.HabitableLand;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Province extends HabitableLand<Province> {
    public Province(UUID id, String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID) {
        super(id, name, created, ended, geoType, geoID);
        init();
    }
    @Override
    protected void onRelink() {

    }

    @Override
    public StateReference getTitleName() {
        return null;
    }

    public Province(JsonObject payload) {
        super(payload);
    }

    @Override
    public boolean canInherit(BookCharacter person) {
        return false;
    }

    @Override
    public List<BookCharacter> getAllClaimants() {
        return List.of();
    }
}
