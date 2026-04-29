package com.objects.title.profession.tenets;

import com.base.component.ComponentReference;
import com.base.component.InstanceType;
import com.base.reference.DMEReference;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.EconomicGroups;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.organization.AbstractOrganization;
import com.objects.title.profession.JobType;

import java.util.Set;

public abstract class JobRequirement<T extends AbstractOrganization<T>> extends JobTenet<T> {
    @SafeVarargs
    public JobRequirement(InstanceType type, DMEReference<? extends T> parent, String id, String name, String description, ComponentReference<JobType>... affected) {
        super(type, parent.get().getTenetReference(), EconomicGroups.JOB_REQUIREMENT,
                new PoliticalCompass(5,0,5,15,-2),
                id,
                name,
                description,
                affected
        );
    }
    public JobRequirement(InstanceType type, String id) {
        super(type, id);
    }

    @Override
    public Set<JobRequirement<T>> getRequirements() {
        return Set.of(this);
    }

    @SuppressWarnings("unchecked")
    public T getOrg(){
        return (T) getParentTenet().get();
    }
}
