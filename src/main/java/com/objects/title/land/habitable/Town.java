package com.objects.title.land.habitable;

import com.base.DMRegistry;
import com.base.reference.StateReference;
import com.display.geography.GeometryType;
import com.google.gson.JsonObject;
import com.objects.character.sentient.HumanCharacter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Town extends HabitableLand<Town> {
    public Optional<HumanCharacter> Mayor;
    private Optional<UUID> MayorID;
    public Town(UUID id, String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID) {
        super(id, name, created, ended, geoType, geoID);
    }

    public Town(JsonObject payload) {
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
    protected void onNewStateLoad(JsonObject passthrough) {
        super.onNewStateLoad(passthrough);
        Mayor = Optional.empty();
        MayorID = Optional.empty();
        if (passthrough.has("Mayor")){
            MayorID = Optional.of(UUID.fromString(passthrough.get("Mayor").getAsString()));
        }
    }

    @Override
    public StateReference getTitleName() {
        return null;
    }

    @Override
    protected void onRelink() {
        if (MayorID.isPresent()) {
            Mayor = Optional.of(DMRegistry.getCharacterManager().get(MayorID.get()));
        }
    }
    @Override
    protected JsonObject getPassthroughData() {
        JsonObject obj = super.getPassthroughData();
        if (Mayor.isPresent()) {
            obj.addProperty("Mayor", Mayor.get().getDisplayID().toString());
        } else if (MayorID.isPresent()) {
            obj.addProperty("Mayor", MayorID.get().toString());
        }
        return obj;
    }
}
