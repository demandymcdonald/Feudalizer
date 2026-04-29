package com.objects.title.profession.tenets;

import com.base.component.ComponentManager;
import com.base.component.ComponentReference;
import com.base.component.ComponentRegistry;
import com.base.component.InstanceType;
import com.base.condition.Condition;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.CultureAware;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.error.StateError;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.TenetManager;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.organization.AbstractOrganization;
import com.objects.title.change.TitleSingleChange;
import com.objects.title.profession.Job;
import com.objects.title.profession.JobType;

import java.util.*;
import java.util.function.Supplier;

public abstract class JobTenet<T extends AbstractOrganization<T>> extends MutableTenet {
    private final Set<ComponentReference<JobType>> scope;

    @SafeVarargs
    public JobTenet(InstanceType type, TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description, ComponentReference<JobType>... scope) {
        super(type, parent, group, entry, id, name, description);
        this.scope = new HashSet<>(Arrays.asList(scope));
    }
    public JobTenet(InstanceType type, String id) {
        super(type, id);
        scope = new HashSet<>();
    }
    public Set<ComponentReference<JobType>> getScope() {
        return new HashSet<>(scope);
    }

    @Override
    public Set<TenetGroup> compatibleParents() {
        return Set.of(
                TenetManager.CULTURE
        );
    }
    @Override
    public void additionalLoad(JsonObject object) {
        super.additionalLoad(object);
        JsonArray array = object.getAsJsonArray("jt:scope");
        scope.clear();
        for (JsonElement element : array) {
            scope.add(ComponentReference.fromJson(element.getAsJsonPrimitive()));
        }
    }

    public abstract Set<JobRequirement<T>> getRequirements();
    @Override
    public void additionalSave(JsonObject object) {
        super.additionalSave(object);
        JsonArray array = new JsonArray();
        for (ComponentReference<JobType> jobType : scope) {
            array.add(jobType.toJson());
        }
        object.add("jt:scope", array);
    }


}
