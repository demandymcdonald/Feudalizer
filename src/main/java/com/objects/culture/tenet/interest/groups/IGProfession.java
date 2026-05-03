package com.objects.culture.tenet.interest.groups;

import com.base.component.ComponentReference;
import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.objects.culture.tenet.interest.IGPointer;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.title.profession.JobType;
import com.utilities.number.BoundInt;

import java.util.Map;

public class IGProfession extends InterestGroup {
    private JobType caste;
    public IGProfession(InstanceType type, String id, String displayName, String description, JobType jobType) {
        super(type, Dimension.Profession, id, displayName, description);
        this.caste = jobType;
    }
    public IGProfession(InstanceType type, String id) {
        super(type, id);
        this.caste = null;
    }
    @Override
    public <C extends SentientCharacter<C>> boolean isMember(C character) {
        return false;
    }

    @Override
    public Map<IGPointer, BoundInt> getRelations() {
        return Map.of();
    }

    @Override
    public GovernmentGroups.GovernmentGroup getRightsGroup() {
        return GovernmentGroups.PROFESSION;
    }

    @Override
    public SocietyGroups.SocietyGroup getSocialStatusGroup() {
        return SocietyGroups.PROFESSION;
    }

    @Override
    public PoliticalCompass getCompass(Culture culture) {
        return ;
    }



    @Override
    public void additionalLoad(JsonObject object) {
        super.additionalLoad(object);
        if(caste == null) caste = (JobType) ComponentReference.fromJson(object.getAsJsonPrimitive("pg:jobType")).get();
    }

    @Override
    public void additionalSave(JsonObject object) {
        super.additionalSave(object);
        object.add("pg:jobType",caste.serialize());
    }

    @Override
    public InterestGroup getNewObject(InstanceType type, String id, JsonObject data) {
        return new IGProfession(type, id,"","", (JobType) ComponentReference.fromJson(data.getAsJsonPrimitive("pg:jobType")).get());
    }
}
