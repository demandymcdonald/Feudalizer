package com.objects.culture.tenet.interest;

import com.base.instanced.IOInstance;
import com.base.instanced.IOReference;
import com.base.instanced.single.IOISingle;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.objects.culture.Culture;
import com.utilities.serialization.StringToHex;

import java.util.UUID;

public class IGInstance extends IOISingle<InterestGroup,IGInstance,DMEReference<Culture>> {
    private DMEReference<Culture> reference;
    private IOReference<InterestGroup> group;
    private UUID id;
    public IGInstance(IOReference<InterestGroup> group, DMEReference<Culture> reference) {
        this.group = group;
        this.reference = reference;
        this.id = UUID.randomUUID();
    }
    @Override
    public IOReference<InterestGroup> getBase() {
        return group;
    }
    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(StringToHex.encode(id.toString())+"::"+group.toJson()+"::"+StringToHex.encode(reference.serialize().getAsString()));
    }

    @Override
    public void fromJson(JsonElement json) {
        String[] split = StringToHex.decode(json.getAsString()).split("::");
        if(split.length != 3) return;
        id = UUID.fromString(StringToHex.decode(split[0]));
        group = IOReference.fromJson(JsonParser.parseString(split[1]).getAsJsonPrimitive());
        reference = DMEReference.deserialize(JsonParser.parseString(StringToHex.decode(split[2])).getAsJsonObject());
    }
}
