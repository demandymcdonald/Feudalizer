package com.utilities;

import com.google.gson.JsonObject;

public interface SidecarSave{


    void saveAdditional(JsonObject data);
    JsonObject onLoad(JsonObject data);

}
