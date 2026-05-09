package com.base.geography.params;

import com.Global;
import com.Global.*;
import com.base.component.AbstractComponent;
import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.utilities.serialization.StringToHex;

import java.util.*;

public class LayerType extends AbstractComponent<LayerType> {
    public enum Category{
        Administrative("ADM"),
        Natural("NAT"),
        Cultural("CUL"),
        Political("POL"),
        Diplomatic("DIP"),
        Custom("CUS");
        private final String code;

        Category(String code){
            this.code = code;
        }
        public String getCode(){
            return code;
        }
    }
    private Category category;
    private int position;

    public LayerType(InstanceType type, String id, Category category, int position) {
        super(type, id);
        this.category = category;
        this.position = position;
    }
    private LayerType(InstanceType type, String id) {
        super(type, id);
    }

    public Category getCategory() {
        return category;
    }

    public String getFileCode() {
        return category.getCode() + position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public LayerType getNewObject(InstanceType type, String id, JsonObject data) {
        return new LayerType(type, id);
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("lt:data", StringToHex.encode(category.name()) +"::" + position);
    }

    @Override
    public void additionalLoad(JsonObject data) {
        String[] split = data.get("lt:data").getAsString().split(":: + ");
        category = Category.valueOf(StringToHex.decode(split[0]));
        position = Integer.parseInt(split[1]);

    }
}
