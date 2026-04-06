package com.objects.title.land;

import com.base.reference.StateReference;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.objects.character.HumanCharacter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SpecialEconomicZone extends AbstractLandDivision<SpecialEconomicZone> {
    public SpecialEconomicZone(UUID id, String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID) {
        super(id, name, created, ended, geoType, geoID);
        init();
    }

    public SpecialEconomicZone(JsonObject payload) {
        super(payload);
    }

    @Override
    public boolean canInherit(HumanCharacter person) {
        return false;
    }

    @Override
    public List<HumanCharacter> getAllClaimants() {
        return List.of();
    }

    @Override
    protected void onRelink() {

    }

    @Override
    public StateReference getTitleName() {
        return null;
    }

    @Override
    protected JsonObject getPassthroughData() {
        return null;
    }
}
