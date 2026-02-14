package com.divisions;

import com.base.DMRegistry;
import com.google.gson.JsonObject;
import com.people.Character;
import com.resources.HabitableLand;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class Town extends HabitableLand {
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
    protected void setPassthroughData(JsonObject passthrough) {
        super.setPassthroughData(passthrough);
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
