package com.objects.character.opinion;

import com.base.reference.ComplexReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.utilities.IDisplayable;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;
import com.utilities.serialization.CompressString;

import javax.annotation.Nullable;
import java.time.Duration;

public record OpinionReason(String internalID, String displayName, OpinionReference description, double reciprocalMod, @Nullable Duration duration, BoundInt value) implements IDisplayable {
    public OpinionReason(String internalID, String displayName, OpinionReference description, double reciprocalMod,  @Nullable Duration duration, int value) {
       this(internalID,displayName,description,reciprocalMod,duration,BoundInts.Int256(true,value));
    }

    public JsonPrimitive toJson() {
        StringBuilder zipped = new StringBuilder();
        zipped.append(CompressString.compress(internalID)).append("::");
        zipped.append(CompressString.compress(displayName)).append("::");
        zipped.append(description.toSerializedString()).append("::");
        zipped.append(reciprocalMod).append("::");
        if(duration != null){
            zipped.append(duration.getSeconds()).append("::");
        } else {
            zipped.append("Inf").append("::");
        }
        zipped.append(value.get());
        return new JsonPrimitive(zipped.toString());
    }
    public static OpinionReason fromJson(JsonPrimitive json) {
        String[] data = json.getAsString().split("::");
        String internalID = CompressString.decompress(data[0]);
        String displayName = CompressString.decompress(data[1]);
        OpinionReference description = OpinionReference.fromSerializedString(data[2]);
        double reciprocalMod = Double.parseDouble(data[3]);
        String durationString = data[4];
        Duration dura;
        if(durationString.equals("Inf")){
            dura = null;
        } else {
            dura = Duration.ofSeconds(Long.parseLong(durationString));
        }
        int value = Integer.parseInt(data[5]);
        return OpinionReason.of(internalID,displayName,description,reciprocalMod,dura,value);
    }
    public static OpinionReason of(String id, String name, OpinionReference description, double reciprocalMod, @Nullable Duration duration, int value) {
        return new OpinionReason(id,name,description,reciprocalMod,duration,BoundInts.Int256(true,value));
    }

    @Override
    public String getDisplayID() {
        return internalID;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description.toString();
    }
}
