package com.objects.organization.religion.tenets.diety;

import com.Global.*;
import com.base.component.ComponentReference;
import com.base.component.IComponent;
import com.base.component.InstanceType;
import com.base.component.immutable.ImmutableComponent;
import com.base.component.instanced.bi.IOBi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.IDisplayable;
import com.utilities.serialization.StringToHex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DivineScope extends ImmutableComponent<DivineScope> implements IDisplayable {
    private String name;
    private String description;
    private final Set<ComponentReference<DivineScope>> subScopes = new HashSet<>();
    private PoliticalCompass compass;
    public DivineScope(InstanceType type, PoliticalCompass adjustments, String id, String name, String description) {
        super(type, id);
        this.name = name;
        this.description = description;
        this.compass = adjustments;
    }

    public DivineScope(InstanceType type, String id) {
        super(type, id);
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("ds:display", StringToHex.encode(name) + "::" + StringToHex.encode(description));
        data.add("ds:compass", compass.toJson());
        //data.addProperty("ds:type", type.name());
        JsonArray array = new JsonArray();
        for(ComponentReference<DivineScope> scope : subScopes){
            array.add(scope.toJson());
        }
        data.add("ds:subScopes", array);
    }

    @Override
    public void additionalLoad(JsonObject data) {
        String[] splits = data.get("ds:display").getAsString().split("::");
        name = StringToHex.decode(splits[0]);
        description = StringToHex.decode(splits[1]);
        compass = PoliticalCompass.build(data.get("ds:compass").getAsJsonObject());
        //type = DivineEntityType.valueOf(data.get("ds:type").getAsString());
        subScopes.clear();
        for(JsonElement element : data.get("ds:subScopes").getAsJsonArray()){
            subScopes.add(IComponent.deserializeRef(element.getAsJsonObject()));
        }
    }
    public PoliticalCompass getCompass() {
        PoliticalCompass pc = compass.clone();
        for(ComponentReference<DivineScope> scope : subScopes){
            pc.merge(scope.get().getCompass());
        }
        return pc;
    }
    @Override
    public DivineScope getNewObject(InstanceType type, String id, JsonObject data) {
        return new DivineScope(type, id);
    }

    @Override
    public String getDisplayID() {
        return getID();
    }
    public Set<ComponentReference<DivineScope>> getSubScopes(){
        Set<ComponentReference<DivineScope>> subScopes = new HashSet<>(this.subScopes);
        subScopes.add(this.getReference());
        return subScopes;
    }
    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
    public static class Builder {
        private final DivineScope scope;
        private Builder(InstanceType type, PoliticalCompass adjustments, String id, String name, String description){
            scope = new DivineScope(type, adjustments, id, name, description);
        }
        public Builder addSubScope(ComponentReference<DivineScope> scope){
            this.scope.subScopes.add(scope);
            return this;
        }
        public DivineScope build(){
            return scope;
        }
        public static Builder ofData(PoliticalCompass adjustments, String id, String name, String description){
            return new Builder(InstanceType.DATA_DRIVEN,adjustments, id, name, description);
        }
        public static Builder ofProcedural(PoliticalCompass adjustments, String id, String name, String description){
            return new Builder(InstanceType.PROCEDURAL, adjustments, id, name, description);
        }
        public static Builder ofHardcoded(PoliticalCompass adjustments, String id, String name, String description){
            return new Builder(InstanceType.HARDCODED, adjustments, id, name, description);
        }
    }

}
