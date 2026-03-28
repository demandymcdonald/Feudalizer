package com.simulation.people;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simulation.title.Title;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record CharacterState(String forename, String surname, List<UUID> Families, @Nullable UUID House,
                             List<UUID> Titles) implements TimelineContainer<CharacterState,BookCharacter> {
    public static CharacterState builder(List<Family> families, List<Title<?>> title, Optional<House> house) {
        List<UUID> familyID = DateMutableEntity.convert(families);
        List<UUID> titleID = DateMutableEntity.convert(title);
        House ho = house.orElse(null);
        UUID houseID = ho == null ? null : ho.getId();
        return new CharacterState(familyID, houseID, titleID);
    }

    public JsonObject getSerialized() {
        JsonObject json = new JsonObject();
        json.addProperty("forename", forename);
        json.addProperty("surname", surname);
        JsonArray familyArray = DateMutableEntity.buildJson(Families);
        JsonArray titleArray = DateMutableEntity.buildJson(Titles);
        if (House != null) {
            json.addProperty("house", House.toString());
        }
        json.add("families", familyArray);
        json.add("titles", titleArray);
        return json;
    }

    @Override
    public CharacterState getDeserialized(JsonObject o) {
        List<UUID> familyID = DateMutableEntity.buildUUID(o.get("families").getAsJsonArray());
        List<UUID> titleID = DateMutableEntity.buildUUID(o.get("titles").getAsJsonArray());
        if (!o.has("house")) {
            return new CharacterState(familyID, null, titleID);
        }
        return new CharacterState(familyID, UUID.fromString(o.get("house").getAsString()), titleID);
    }


    @Override
    public String Header() {
        return "CharacterContainer";
    }

    @Override
    public TimelineChange<BookCharacter> getBirthChange(DMEReference<BookCharacter> dme, LocalDate date) {
        return new TimelineChange<>(dme, date, null);
    }

    @Override
    public TimelineChange<BookCharacter> getDeathChange(DMEReference<BookCharacter> dme, LocalDate date) {
        return null;
    }
}
