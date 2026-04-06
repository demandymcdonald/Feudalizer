package com.objects.title.land;

import com.base.reference.SimpleReference;
import com.base.reference.StateReference;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.objects.character.HumanCharacter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Manor extends AbstractLandDivision<Manor>{
    public Manor(UUID id, String name, LocalDate created, LocalDate ended, JsonObject additionalData) {
        super(id, name,created, ended, additionalData);
        init();
    }

    public Manor(JsonObject payload) {
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

    public Manor(UUID id, String name, LocalDate created, LocalDate ended) {
        super(id, name, created, ended, new JsonObject());
    }
    public Manor(UUID id, String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID) {
        super(id, name, created, ended, geoType, geoID);
    }
    @Override
    protected JsonObject getPassthroughData() {
        return new JsonObject();
    }

    @Override
    protected void onNewStateLoad(JsonObject passthrough) {

    }

    @Override
    public StateReference getTitleName() {
        return new SimpleReference(this.getTitleName() + "Manor");
    }

    @Override
    protected void onRelink() {

    }
}
