package com.objects.title.profession;

import com.base.component.ComponentReference;
import com.base.component.InstanceType;
import com.base.component.mutable.MutableComponent;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.interest.groups.ClassCaste;
import com.objects.succession.condition.CanHoldCondition;
import com.objects.succession.condition.CanInheritCondition;
import com.utilities.IDisplayable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class JobType extends MutableComponent<JobType> implements IDisplayable {
    private ClassCaste primaryClass;
    private ClassCaste[] secondaryClass;
    private String name;
    private String description;
    private final Set<CanHoldCondition<? super Job>>  canHoldConditions = new HashSet<>();
    private final Set<CanInheritCondition<? super Job>>  canInheritConditions = new HashSet<>();
    JobType(InstanceType type, ClassCaste group, String id, String name, String description) {
        super(type, "job_type:"+id);
        this.primaryClass = group;
        this.secondaryClass = new ClassCaste[0];
        this.name = name;
        this.description = description;
    }
    JobType(InstanceType type, ClassCaste group, String id, String name, String description, ClassCaste... secondary_groups) {
        super(type,"job_type:"+id);
        this.primaryClass = group;
        this.secondaryClass = secondary_groups;
        this.name = name;
        this.description = description;
    }
    public JobType(InstanceType type, String id) {
        super(type, id);
    }

    public ClassCaste getPrimaryClass() {
        return primaryClass;
    }

    public ClassCaste[] getSecondaryClass() {
        return secondaryClass;
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
    public Set<CanHoldCondition<? super Job>> getCanHoldConditions() {

    }


    public Set<CanHoldCondition<? super Job>> getCanInheritConditions() {

    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("jt:name", name);
        data.addProperty("jt:description", description);
        data.add("jt:primary", primaryClass.serializeRef());
        JsonArray array = new JsonArray();
        for (ClassCaste c : secondaryClass) {
            array.add(c.serializeRef());
        }
        data.add("jt:secondary",array);
    }

    @Override
    public void additionalLoad(JsonObject data) {
        name = data.get("jt:name").getAsString();
        description = data.get("jt:description").getAsString();
        primaryClass = (ClassCaste) ComponentReference.fromJson(data.getAsJsonPrimitive("jt:primary")).get();
        List<ClassCaste> castes = new ArrayList<>();
        JsonArray array = data.getAsJsonArray("jt:secondary");
        for (int i = 0; i < array.size(); i++) {
            castes.add((ClassCaste) ComponentReference.fromJson(array.get(i).getAsJsonPrimitive()).get());
        }
        secondaryClass = castes.toArray(new ClassCaste[0]);
    }
}
