package com.objects.culture.object.change;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.condition.Condition;
import com.base.datemutable.timeline.change.multi.TimelineSetChange;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.TimelineObject;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.datemutable.timeline.change.multi.condition.MultiCondition;
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
import com.objects.culture.IActivatable;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.SubTenet;
import com.objects.culture.tenet.flag.FlagInstance;
import com.objects.culture.tenet.flag.FlagTenet;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.instance.TenetInstance;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static com.base.datemutable.timeline.change.multi.type.Delta.*;

public class OpinionChange<T extends DateMutableEntity<T> & CultureObject<T>>
        extends TimelineSetChange<OpinionChange<T>,TenetInstance<T>,UUID,T>
        implements EasingChange<TenetInstance<T>, OpinionChange<T>,T> {

    protected OpinionChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }



    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

    }

    public OpinionChange(DMEReference<? extends T> owner, LocalDate date, Set<TenetInstance<T>> initial) {
        super(owner, date, initial);
    }

    @Override
    public OpinionChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return new OpinionChange<>(owner, date);
    }

    @Override
    protected TLSet<TenetInstance<T>> getRuntimeSet(T owner) {
        return owner.getOpinions();
    }

    @Override
    public void setRuntimeSet(T owner, TLSet<TenetInstance<T>> set) {
        owner.internalSetOpinions(set);
    }


    @Override
    protected String getText() {
        return "tenet_instance_change";
    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }

    @Override
    public void conditionsModifyValue(List<MultiCondition<OpinionChange<T>, TenetInstance<T>, Boolean, UUID, T>> current) {
        super.conditionsModifyValue(current);

    }

    @Override
    public void conditionsModifyKey(List<MultiCondition<OpinionChange<T>, TenetInstance<T>, Boolean, UUID, T>> current) {
        super.conditionsModifyKey(current);
        current.add(new ActiveCondition<>());
    }

    @Override
    public void conditionsAdd(List<MultiCondition<OpinionChange<T>, TenetInstance<T>, Boolean, UUID, T>> current) {
        super.conditionsAdd(current);
        current.add(new ActiveCondition<>());

    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<OpinionChange<T>, TenetInstance<T>, Boolean, UUID, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<OpinionChange<T>, TenetInstance<T>, Boolean, UUID, T>> current) {

    }

    @Override
    protected JsonElement kSerialize(TenetInstance<T> tTenetInstance) {
        return tTenetInstance.serialize();
    }

    @Override
    protected TenetInstance<T> kDeserialize(JsonElement o) {
        TenetInstance<T> tenetI = new TenetInstance<>(getOwner());
        tenetI.deserialize(o.getAsJsonObject());
        return tenetI;
    }

    @Override
    protected void onLink(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.onLink(entity, currentState);
        if(entity.get() instanceof IActivatable<?> iac) {
            Set<TenetInstance<?>> active = new HashSet<>(iac.getFlagTenets(true));
            Set<FlagTenet> covered = new HashSet<>();
            for (FlagInstance flag : iac.getFlags()) {
                if(active.stream().noneMatch(t -> t.getTenet().get().equals(flag.getIO()))) {
                    iac.internalGetFlags().remove(flag);
                } else {
                    covered.add(flag.getIO());
                }
            }
            Set<TenetInstance<?>> remaining = new HashSet<>(active.stream().filter(t -> !covered.contains(t.getTenet().get())).toList());
            for(TenetInstance<?> t : remaining) {
                FlagTenet ft = (FlagTenet) t.getTenet().get();
                iac.internalGetFlags().add(ft.instance());
            }
        }
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
    public OpinionChange<T> getNext(){
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
        for(TenetInstance<T> entry :getRuntimeSet().asSet()) {
            if(matching.test(entry)) {
                return entry;
            }
        }
        return null;
    }



    private static final class ActiveCondition<T extends DateMutableEntity<T> & CultureObject<T>> extends SetCondition<OpinionChange<T>, TenetInstance<T>, UUID, T> {
        @Override
        protected Optional<StateError> doCheck(Delta change, OpinionChange<T> newChange, List<Pair<TenetInstance<T>, Boolean>> newEntries, OpinionChange<T> curChange, List<Pair<TenetInstance<T>, Boolean>> curEntries) {
            if((change == MODIFY_VALUE || ((change == ADD || change == ADD_WIPE)
                    && newEntries.stream().anyMatch(entry ->
                    (entry.getKey().isActive() && entry.getKey().getTenet().get() instanceof SubTenet))))
                    && newChange.getOwner() instanceof IActivatable<?> ia){

                for(Pair<TenetInstance<T>, Boolean> entry : newEntries) {
                    final TenetInstance<T> instance = entry.getKey();
                    if(instance.getTenet().get() instanceof SubTenet st) {
                        DMEReference<? extends IActivatable<?>> active = ia.getReference();
                        OpinionChange<? extends IActivatable<?>> curChangeHelped = (OpinionChange<? extends IActivatable<?>>) curChange;
                        Optional<StateError> error = st.canBeActive(active, instance.getTenet(), curChangeHelped);
                        if (error.isPresent()) {
                            return error;
                        }
                    }
                }
            }
            return Optional.empty();
        }
        @Override
        public Condition.ShouldRun shouldRun() {
            return Condition.ShouldRun.ONCE_PER_STATE;
        }
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
//    private static ErrorListResolution<TenetReference,UUID> buildResolution(List<TenetReference> tenets){
//
//
//
//    }
//    private static class  ReduceLevel extends ErrorListResolution<TenetReference,UUID>{
//
//        public ReduceLevel(String id, String display, String description, SandboxCode expectedCode, List<TenetReference> options) {
//            super(id, display, description, expectedCode, options);
//        }
//
//        @Override
//        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
//            return reduceLevel(oldChange);
//        }
//        @SuppressWarnings("unchecked")
//        private <T extends DateMutableEntity<T> & CultureObject<T>> SandboxCode reduceLevel(TimelineChange<?> oc) {
//            if(!(oc instanceof OpinionChange)){
//                throw new IllegalArgumentException("Expected OpinionChange instead of " + oc.getClass().getSimpleName() + "!");
//            }
//            OpinionChange<T> oldChange = (OpinionChange<T>) oc;
//            Acceptance acceptance = Acceptance.getLower(oldChange.get(getOption(getChosen())).getAcceptance());
//
//            oldChange.get(getOption(getChosen())).getRaw();
//            return SandboxCode.RESTART_FROM_STATE;
//        }
//    }
}
