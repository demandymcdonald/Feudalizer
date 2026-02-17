package com.divisions;

import com.base.DMRegistry;
import com.google.gson.JsonObject;
import com.people.Character;
import com.resources.HabitableLand;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Town extends HabitableLand<Town> {
    public Optional<Character> Mayor;
    private Optional<UUID> MayorID;
    public Town(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    public Town(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    public Town(JsonObject payload) {
        super(payload);
    }

    @Override
    public boolean canInherit(Character person) {
        return false;
    }

    @Override
    public List<Character> getAllClaimants() {
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
    protected void onRelink() {
        if (MayorID.isPresent()) {
            Mayor = Optional.of(DMRegistry.getCharacterManager().get(MayorID.get()));
        }
    }
    @Override
    protected JsonObject getPassthroughData() {
        JsonObject obj = super.getPassthroughData();
        if (Mayor.isPresent()) {
            obj.addProperty("Mayor", Mayor.get().getId().toString());
        } else if (MayorID.isPresent()) {
            obj.addProperty("Mayor", MayorID.get().toString());
        }
        return obj;
    }
}
