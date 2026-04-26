package com.objects.culture.tenet.instance;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.TimelineObject;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
import com.base.datemutable.timeline.change.multi.MiddlemanMap;
import com.base.datemutable.timeline.change.multi.TimelineMapChange;
import com.base.datemutable.timeline.change.multi.type.Delta;
import com.base.datemutable.timeline.error.ErrorListResolution;
import com.base.datemutable.timeline.error.SandboxCode;
import com.base.datemutable.timeline.error.StateError;
import com.base.datemutable.timeline.sandbox.core.Sandbox;
import com.base.datemutable.timeline.state.TimelineState;
import com.base.datemutable.timeline.variable.EasingChange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.TenetReference;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
public class TenetInstanceChange<T extends DateMutableEntity<T> & CultureObject<T>>
        extends TimelineMapChange<TenetInstanceChange<T>,TenetReference,TenetInstance<T>, UUID,T>
        implements EasingChange<TenetInstance<T>,TenetInstanceChange<T>,T> {

    protected TenetInstanceChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

    }

    public TenetInstanceChange(DMEReference<? extends T> owner, LocalDate date, Map<TenetReference, TenetInstance<T>> initial) {
        super(owner, date, initial);
    }

    @Override
    public TenetInstanceChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return new TenetInstanceChange<>(owner, date);
    }



    @Override
    protected String getText() {
        return "tenet_instance_change";
    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }


    @Override
    public boolean hasEndingChanges() {
        return true;
    }
    Predicate<Pair<TenetReference, TenetInstance<T>>> getMax = new Predicate<Pair<TenetReference, TenetInstance<T>>>() {

        @Override
        public boolean test(Pair<TenetReference, TenetInstance<T>> tenetReferenceTenetInstancePair) {
            TenetReference reference = tenetReferenceTenetInstancePair.getKey();
            TenetInstance<T> instance = tenetReferenceTenetInstancePair.getValue();





        }
    };
    @Override
    public void conditionsAdd(List<MultiCondition<TenetInstanceChange<T>, TenetReference, TenetInstance<T>, UUID, T>> current) {
        super.conditionsAdd(current);
        current.add()
    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<TenetInstanceChange<T>, TenetReference, TenetInstance<T>, UUID, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<TenetInstanceChange<T>, TenetReference, TenetInstance<T>, UUID, T>> current) {

    }

    @Override
    protected JsonElement kSerialize(TenetReference tenetReference) {
        return tenetReference.serialize();
    }

    @Override
    protected TenetReference kDeserialize(JsonElement o) {
        return TenetReference.deserialize(o.getAsJsonObject());
    }

    @Override
    protected JsonElement vSerialize(TenetInstance<T> tTenetInstance) {
        return tTenetInstance.serialize();
    }

    @Override
    protected TenetInstance<T> vDeserialize(JsonElement o) {
        TenetInstance<T> t = new TenetInstance<>();
        t.deserialize(o.getAsJsonObject());
        return t;
    }

    @Override
    protected JsonElement iSerialize(UUID s) {
        return new JsonPrimitive(s.toString());
    }

    @Override
    protected UUID iDeserialize(JsonElement o) {
        return UUID.fromString(o.getAsString());
    }

    @Override
    public void setRuntimeMap(MiddlemanMap<TenetInstanceChange<T>,TenetReference, TenetInstance<T>,UUID,T> map) {
        getOwner().get().internalSetOpinions(map);
    }

    @Override
    public MiddlemanMap<TenetInstanceChange<T>,TenetReference, TenetInstance<T>,UUID,T> getRuntimeMap() {
        return (MiddlemanMap<TenetInstanceChange<T>,TenetReference, TenetInstance<T>,UUID,T>) getOwner().get().getOpinions();
    }
    @Override
    public TenetInstanceChange<T> getNext(){
        return TimelineObject.getChangeStep(this, Global.TimeDirection.FORWARD,false,1,null);
    }


    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }

    @Override
    public TenetInstance<T> getEasingVariable(Predicate<TenetInstance<T>> matching) {
        for(Map.Entry<TenetReference, TenetInstance<T>> entry : getFullMap().entrySet()) {
            if(matching.test(entry.getValue())) {
                return entry.getValue();
            }
        }
        return null;
    }
    private final MultiCondition<TenetInstanceChange<T>, TenetReference, TenetInstance<T>, UUID, T> activeCondition = new MultiCondition<TenetInstanceChange<T>, TenetReference, TenetInstance<T>, UUID, T>() {
        Condition.ShouldRun run = Condition.ShouldRun.ONCE_PER_STATE;


        @Override
        protected Optional<StateError> doCheck(Delta change, TenetInstanceChange<T> newChange, List<Pair<TenetReference, TenetInstance<T>>> newEntries, TenetInstanceChange<T> curChange, List<Pair<TenetReference, TenetInstance<T>>> curEntries) {
            return Optional.empty();
        }

        @Override
        public Condition.ShouldRun shouldRun() {
            return run;
        }
    }
    private final MultiCondition<TenetInstanceChange<T>, TenetReference, TenetInstance<T>, UUID, T> maxCondition = new MultiCondition<TenetInstanceChange<T>, TenetReference, TenetInstance<T>, UUID, T>() {
        @Override
        public Condition.ShouldRun shouldRun() {
            return Condition.ShouldRun.ONCE_PER_STATE;
        }

        @Override
        protected Optional<StateError> doCheck(Delta change, TenetInstanceChange<T> newChange, List<Pair<TenetReference, TenetInstance<T>>> newEntries, TenetInstanceChange<T> curChange, List<Pair<TenetReference, TenetInstance<T>>> curEntries) {
            Map<TenetGroup, Map<Acceptance, MutableInt>> capacityLeft = new HashMap<>();
            for(Pair<TenetReference, TenetInstance<T>> entry : newEntries) {
                TenetGroup group = entry.getKey().getGroup();
                Map<Acceptance, MutableInt> map = capacityLeft.get(group);
                if(map == null) {
                    map = buildBase(group);
                    Map<TenetReference,TenetInstance<T>> current = curChange.getFullMap().getWhere((tr) -> {
                        return tr.getGroup().equals(group);
                    });
                    getCapacityLeft(group, map, current);
                    capacityLeft.put(group, map);
                }
                Acceptance acceptance = entry.getValue().getAcceptance();
                map.get(acceptance).add(-1);
                for(MutableInt left : map.values()) {
                    if(left.intValue() < 0) {
                        return Optional.of(new StateError("tenet_map_over_cap",new ComplexReference("TenetGroup: {} is over it's maximum capacity of {} by {}",group, group.type().getMaxFor(acceptance), Math.abs(left.intValue())),curChange).addEndCancel())
                    }
                }
            }
            return Optional.empty();
        }


        private void getCapacityLeft(TenetGroup group, Map<Acceptance, MutableInt> capacity, Map<TenetReference, TenetInstance<T>> entries) {
            for (Map.Entry<TenetReference, TenetInstance<T>> entry : entries.entrySet()) {
                TenetInstance<T> instance = entry.getValue();
                Acceptance acceptance = instance.getAcceptance();
                capacity.get(acceptance).add(- 1);
            }
        }
        private static Map<Acceptance, MutableInt> buildBase(TenetGroup group){
            Map<Acceptance, MutableInt> base = new HashMap<>();
            for(Acceptance acceptance : Acceptance.values()) {
                base.put(acceptance, new MutableInt(group.type().getMaxFor(acceptance)));
            }
            return base;
        }
    };
    private static ErrorListResolution<TenetReference,UUID> buildResolution(List<TenetReference> tenets){



    }
    private static class  ReduceLevel extends ErrorListResolution<TenetReference,UUID>{

        public ReduceLevel(String id, String display, String description, SandboxCode expectedCode, List<TenetReference> options) {
            super(id, display, description, expectedCode, options);
        }

        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            return reduceLevel(oldChange);
        }
        @SuppressWarnings("unchecked")
        private <T extends DateMutableEntity<T> & CultureObject<T>> SandboxCode reduceLevel(TimelineChange<?> oc) {
            if(!(oc instanceof TenetInstanceChange)){
                throw new IllegalArgumentException("Expected TenetInstanceChange instead of " + oc.getClass().getSimpleName() + "!");
            }
            TenetInstanceChange<T> oldChange = (TenetInstanceChange<T>) oc;
            Acceptance acceptance = Acceptance.getLower(oldChange.get(getOption(getChosen())).getAcceptance());

            oldChange.get(getOption(getChosen())).getRaw();
            return SandboxCode.RESTART_FROM_STATE;
        }
    }
}
