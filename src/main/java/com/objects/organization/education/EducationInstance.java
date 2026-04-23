package com.objects.organization.education;

import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;
import com.utilities.serialization.CompressString;

public class EducationInstance implements IDisplayable, StringIdentifiable {
    private final String id;
    private final String lore;
    private final Education education;
    private final DMEReference<EducationInstitution> institution;
    public EducationInstance(Education education, DMEReference<EducationInstitution> institution, String lore) {
        this.id = education.getID().substring(0,10) + "_" + institution.get().getID().toString().substring(0,10) +"_" + Math.round(999999999 * Math.random());
        this.education = education;
        this.institution = institution;
        this.lore = lore;
    }
    public EducationInstance(String id, Education education, DMEReference<EducationInstitution> institution, String lore) {
        this.id = id;
        this.education = education;
        this.institution = institution;
        this.lore = lore;
    }
    @Override
    public String getID() {
        return id;
    }
    @Override
    public String getDisplayID() {
        return "instance_"+education.getDisplayID();
    }

    @Override
    public String getDisplayName() {
        return education.getDisplayName() + " from " + institution.get().getDisplayName();
    }

    @Override
    public String getDescription() {
        return lore;
    }
    public Education getType(){
        return education;
    }
    public JsonElement toJson(){
        StringBuilder builder = new StringBuilder();
        builder.append(CompressString.compress(id)).append("::");
        builder.append(CompressString.compress(education.getID())).append("::");
        builder.append(CompressString.compress(institution.serialize().getAsString())).append("::");
        builder.append(CompressString.compress(lore));
        return new JsonPrimitive(builder.toString());
    }
    public static EducationInstance fromJson(JsonElement json){
        if(json.isJsonNull()) return null;
        String[] parts = json.getAsString().split("::");
        if(parts.length != 4) return null;
        String id = CompressString.decompress(parts[0]);
        Education education = EduManager.get(CompressString.decompress(parts[1]));
        DMEReference<EducationInstitution> institution = DMEReference.deserialize(JsonParser.parseString(CompressString.decompress(parts[2])).getAsJsonObject());
        String lore = CompressString.decompress(parts[3]);
        return new EducationInstance(id, education, institution, lore);
    }


}
