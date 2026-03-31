package com.objects.character.opinion;

import com.google.gson.JsonObject;

public record OpinionReason(String internalID, String displayName, String description, int change)  {


    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", internalID);
        json.addProperty("displayName", displayName);
        json.addProperty("description", description);
        json.addProperty("change", change);
        return json;
    }


    public static OpinionReason fromJson(JsonObject json) {
        return new OpinionReason(json.get("id").getAsString(), json.get("displayName").getAsString(), json.get("description").getAsString(), json.get("change").getAsInt());
    }

}
