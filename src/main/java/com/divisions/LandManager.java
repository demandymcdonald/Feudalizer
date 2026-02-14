package com.divisions;

import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LandManager extends AbstractMutableManager<AbstractLandDivision.MutableLandContainer, AbstractLandDivision> {
    //TODO: Make sure to two stage the initial load of any subholding so it's populated, and then fire the DoRelink on Link
    @Override
    public AbstractLandDivision deserializer(UUID id, JsonObject json) {
        String type = json.get("additionalData").getAsJsonObject().get("LandType").getAsString();
        switch (type) {
            case "County" -> {
                return new County(json);
            }
            case "Province" -> {
                return new Province(json);
            }
            case "Special Economic Zone" ->{

            }
            case "Manor" ->{
                return new Manor(json);
            }
            case "Township/District" ->{

            }
            case "District" -> {

            }
            case "Wasteland" -> {

            }
            case "Other" -> {

            }
        }

        return null;
    }
    public List<AbstractLandDivision> getLandByHolder(UUID holder) {
        List<AbstractLandDivision> lands = new ArrayList<>();
        for (AbstractLandDivision a :getItemMap().values() ) {
            if (a.Holder.isPresent() && a.Holder.get().getId().equals(holder)) {
                lands.add(a);
            }
        }
        return lands;
    }
}
