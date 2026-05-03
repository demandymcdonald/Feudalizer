package com.objects.title.profession;

import com.base.component.InstanceType;
import com.base.component.mutable.MutableComponent;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.tenet.interest.groups.ClassCaste;
import com.objects.culture.tenet.interest.groups.IGProfession;
import com.objects.succession.condition.CanHoldCondition;
import com.objects.succession.condition.CanInheritCondition;
import com.utilities.IDisplayable;

import java.util.HashSet;
import java.util.Set;

public abstract class JobType extends MutableComponent<JobType> implements IDisplayable {

    private String name;
    private String description;
    private IGProfession professionalGroup;
    private final Set<CanHoldCondition<? super Job>>  canHoldConditions = new HashSet<>();
    private final Set<CanInheritCondition<? super Job>>  canInheritConditions = new HashSet<>();
    JobType(InstanceType type, String id, String name, String description) {
        super(type, "job_type:"+id);
        this.name = name;
        this.description = description;
        this.professionalGroup = new IGProfession(InstanceType.PROCEDURAL, id, name, description, this);
    }
    JobType(InstanceType type, String id, String name, String description, IGProfession professionalGroup) {
        super(type, "job_type:"+id);
        this.name = name;
        this.description = description;
        this.professionalGroup = professionalGroup;
    }

    public JobType(InstanceType type, String id) {
        super(type, id);
    }

    @Override
    public String getDisplayID() {
        return getID();
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public IGProfession getInterestGroup(){
        return professionalGroup;
    }
    public Set<CanHoldCondition<? super Job>> getCanHoldConditions() {

    }
    public ClassCaste getClassMembership(DMEReference<Culture> culture){

    }

    public Set<CanHoldCondition<? super Job>> getCanInheritConditions() {

    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("jt:name", name);
        data.addProperty("jt:description", description);
    }

    @Override
    public void additionalLoad(JsonObject data) {
        name = data.get("jt:name").getAsString();
        description = data.get("jt:description").getAsString();
        this.professionalGroup = new IGProfession(InstanceType.PROCEDURAL, getID(), name, description, this);
    }
}
