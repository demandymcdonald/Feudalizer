package com.utilities;

import com.base.datemutable.timeline.change.display.DisplayContainer;
import com.google.gson.JsonObject;
import com.utilities.serialization.CompressString;
import com.utilities.serialization.StringToHex;

public interface IDisplayable {
    String getDisplayID();
    String getDisplayName();
    String getDescription();
    //TODO ICONS
    default String getFull(){
        return getDisplayName() + ": " + getDescription();
    }




    static void serialize(JsonObject object, IDisplayable toSerialize){
        String nameId = StringToHex.encode(toSerialize.getDisplayID()) + "::" + StringToHex.encode(toSerialize.getDisplayName());
        String description = CompressString.compress(toSerialize.getDescription());
        object.addProperty("display:nameId", nameId);
        object.addProperty("display:description", description);
    }
    static IDisplayData deserialize(JsonObject object){
        String nameId = object.get("display:nameId").getAsString();
        String[] split = nameId.split("::");
        return new IDisplayData(StringToHex.decode(split[0]),StringToHex.decode(split[1]), CompressString.decompress(object.get("display:description").getAsString()));
    }


    record IDisplayData(String displayID, String displayName, String description){};
}
