package com.utilities;

import com.google.gson.JsonObject;

public interface SidecarSave{


    JsonObject additionalData();
    JsonObject onLoad(JsonObject data);

}
