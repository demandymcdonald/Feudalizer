package com.objects.family;

import com.base.timeline.TimelineContainer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.BookCharacter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record FamilyState(UUID PrimarySpouse, @Nullable UUID SecondarySpouse,
                          List<UUID> Children) implements TimelineContainer<FamilyState> {
    public static FamilyState builder(BookCharacter primary, @Nullable BookCharacter secondary, List<BookCharacter> children) {
        List<UUID> Children = new ArrayList<>();
        for (BookCharacter child : children) {
            Children.add(child.getId());
        }
        UUID cleanSecondary;
        if (secondary != null) {
            cleanSecondary = secondary.getId();
        } else {
            cleanSecondary = null;
        }
        return new FamilyState(primary.getId(), cleanSecondary, Children);
    }

    public JsonObject getSerialized() {
        JsonObject json = new JsonObject();
        json.addProperty("PrimarySpouse", PrimarySpouse.toString());
        if (SecondarySpouse != null) {
            json.addProperty("SecondarySpouse", SecondarySpouse.toString());
        }
        JsonArray children = new JsonArray();
        for (UUID childi : Children) {
            children.add(new JsonPrimitive(childi.toString()));
        }
        json.add("Children", children);
        return json;
    }

    public static FamilyState deserialize(JsonObject json) {
        UUID PrimarySpouse = UUID.fromString(json.get("PrimarySpouse").getAsString());
        UUID SecondarySpouse;
        if (json.has("SecondarySpouse")) {
            SecondarySpouse = UUID.fromString(json.get("SecondarySpouse").getAsString());
        } else {
            SecondarySpouse = null;
        }
        List<UUID> Children = new ArrayList<>();
        JsonArray children = json.get("Children").getAsJsonArray();
        for (JsonElement child : children) {
            Children.add(UUID.fromString(child.getAsString()));
        }
        return new FamilyState(PrimarySpouse, SecondarySpouse, Children);
    }
}
