package com.objects.title.profession.tenets;

import com.base.component.ComponentReference;
import com.base.component.InstanceType;
import com.base.condition.Condition;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.CultureAware;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.google.common.base.Suppliers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.organization.AbstractOrganization;
import com.objects.title.change.TitleSingleChange;
import com.objects.title.profession.Job;
import com.objects.title.profession.JobType;
import org.apache.commons.math3.analysis.function.Abs;

import java.util.*;
import java.util.function.Supplier;

public class RegulatedJob<T extends AbstractOrganization<T>> extends JobTenet<T> {
    private final Set<JobRequirement<T>> requirements;
    @SafeVarargs
    public RegulatedJob(InstanceType type, DMEReference<? extends T> parent, String id, String name, String description, JobRequirement<T>... requirements) {
        super(type, parent.get().getTenetReference(), GovernmentGroups.REGULATED_JOBS, new PoliticalCompass(0,0,0,0,0), id, name, description, strip(requirements));
        this.requirements = new HashSet<>(Arrays.stream(requirements).toList());
    }
    public RegulatedJob(InstanceType type, String id) {
        super(type, id);
        requirements = new HashSet<>();
    }

    @Override
    public void additionalSave(JsonObject object) {
        super.additionalSave(object);
        JsonArray array = new JsonArray();
        for (JobRequirement<T> requirement : requirements) {
            array.add(requirement.serializeRef());
        }
        object.add("rj:requirements", array);
    }

    @Override
    public void additionalLoad(JsonObject object) {
        super.additionalLoad(object);
        requirements.clear();
        JsonArray array = object.getAsJsonArray("rj:requirements");
        for (JsonElement element : array) {
            requirements.add((JobRequirement<T>) ComponentReference.fromJson(element.getAsJsonPrimitive()).get());
        }
    }
    @Override
    public Set<JobRequirement<T>> getRequirements() {
        return requirements;
    }
    private static ComponentReference<JobType>[] strip(JobRequirement<?>... requirements) {
        Set<ComponentReference<JobType>> set = new HashSet<>();
        for (JobRequirement<?> requirement : requirements) {
            set.addAll(requirement.getScope());
        }
        return set.toArray(new ComponentReference[0]);
    }
    @Override
    public Set<CultureCondition<?, ?>> getConditionList() {
        return conditions.get();
    }
    private final Supplier<Set<CultureCondition<?,?>>> conditions = Suppliers.memoize(this::build);
    private Set<CultureCondition<?,?>> build(){
        return Set.of(new JobRequirementCondition<>(this));
    }
    private static class JobRequirementCondition<T extends AbstractOrganization<T>> extends CultureCondition<SentientCharacter<?>, T>{
        private final CultureCondition.Key condition;
        public JobRequirementCondition(JobTenet<T> tenet) {
            super(tenet);
            condition = getOrMake(tenet);
        }
        @Override
        public Set<Key> getKeys() {
            return Set.of(condition);
        }
        @Override
        public Condition.ShouldRun shouldRun() {
            return Condition.ShouldRun.ONCE_PER_ENTITY;
        }
        @Override
        protected <TC extends TimelineChange<? super T> & CultureAware<TC, SentientCharacter<?>, T>> Optional<StateError> doCheck(Tenet tenet, TC change, SentientCharacter<?> subject, Culture subjectCulture, T decider, Culture deciderCulture) {
            JobTenet<T> jt = (JobTenet<T>) tenet;
            for (JobRequirement<T> jobRequirement : jt.getRequirements()) {
                for(CultureCondition<?,?> condition :jobRequirement.getConditionList()){
                    CultureCondition<SentientCharacter<?>,T> c = (CultureCondition<SentientCharacter<?>,T>) condition;
                    Optional<StateError> error = c.check(change,subject,decider);
                    if(error.isPresent()){
                        return error;
                    }
                }
            }
            return Optional.empty();
        }
        private static HashMap<Set<ComponentReference<JobType>>, Key> cache = new HashMap<>();
        private static <T extends AbstractOrganization<T>> CultureCondition.Key getOrMake(JobTenet<T> tenet){
            Set<ComponentReference<JobType>> scope = tenet.getScope();
            if(cache.containsKey(scope)){
                return cache.get(scope);
            }

            CultureCondition.Key key = new CultureCondition.Key() {
                @Override
                protected <TC extends TimelineChange<D> & CultureAware<TC, ?, D>, D extends DateMutableEntity<D> & ICultureObject> boolean isValid(TC change) {
                    return change instanceof TitleSingleChange.SetHolder<?> sh && sh.getOwner().get() instanceof Job job && scope.contains(job.getJobType().getReference());
                }
            };
            cache.put(scope, key);
            return key;
        }
    }
    @Override
    public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
        return new RegulatedJob<>(type, id);
    }
}
