package com.objects.succession.change;

import com.Global;
import com.base.component.InstanceType;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.condition.apply.ApplyCondition;
import com.base.datemutable.timeline.error.ErrorResolution;
import com.base.datemutable.timeline.error.StateError;
import com.base.datemutable.timeline.sandbox.core.Objective;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.succession.held.ICharacterHeld;
import org.jgrapht.Graphs;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.Optional;
import java.util.Set;

public class HolderConditions {
    public static class ParentLoop<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends ApplyCondition<T> {
        public ParentLoop() {
            super("held_parent_loop");
        }
        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if ((checkAgainst instanceof HolderChanges.NewParent<?> ca && ca.getNew() != null)
                && (thisChange instanceof HolderChanges.NewParent<? extends T> tc && tc.getNew() != null)){
                ICharacterHeld<?> newParent = tc.getNew().get();
                DirectedPseudograph<ICharacterHeld<?>, DefaultEdge> graph = new DirectedPseudograph<>(DefaultEdge.class);
                Graphs.addGraph(graph,entity.get().getTitleGraph());
                graph.addVertex(newParent);
                Optional<DMEReference<? extends ICharacterHeld<?>>> old = tc.getOld();
                if(old.isPresent() && graph.containsVertex(old.get().get())){
                    graph.removeVertex(old.get().get());
                }
                graph.addEdge(newParent,entity.get());
                CycleDetector<ICharacterHeld<?>,DefaultEdge> detector = new CycleDetector<>(graph);
                if(detector.detectCyclesContainingVertex(newParent)){
                    Set<ICharacterHeld<?>> held = detector.findCyclesContainingVertex(newParent);
                    Objective<?> parentChange = Objective.buildInChange(entity, Global.TimeDirection.FORWARD,new HolderChanges.NewParent<>(entity, Global.getDate(),null));
                    Optional.of(new StateError("held_looping", ComplexReference.of("{} is offspring of {}", ca.getOwner(),newParent),tc)
                            .addEndSave().addEndCancel().addSandbox(
                                    "held_looping_fix",
                                    "Remove Existing Parent",
                                    "Remove the parent of " + entity.get().toString(),parentChange));
                }
            }
            return Optional.empty();
        }

        @Override
        public ApplyCondition<T> getNewObject(InstanceType type, String id, JsonObject data) {
            return new ParentLoop<>();
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
    protected static class HolderDead<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends ApplyCondition<T>{
    public HolderDead() {
        super("held_holder_dead");
    }
    @Override
    protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
        if (thisChange instanceof HolderChanges.Change<?> ch && !(ch.getNew().get().isAlive())){
            HolderChanges.Change<T> holderChange = (HolderChanges.Change<T>) thisChange;
            DMEReference<? extends T> o = holderChange.getOwner();
            return Optional.of(new StateError("held_holder_dead", ComplexReference.of("{} is now dead.", entity),checkAgainst)
                    .addEndSave().addEndCancel().addOption(new ErrorResolution.SuccessionPlanning_Title(holderChange.getNew())));
        }
        return Optional.empty();
    }

        @Override
        public ApplyCondition<T> getNewObject(InstanceType type, String id, JsonObject data) {
            return new HolderDead<>();
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
    protected static class CanHold<T extends DateMutableEntity<T> & ICharacterHeld<T>> extends ApplyCondition<T>{
        public CanHold() {
            super("title_can_still_hold");
        }
        @Override
        protected Optional<StateError> doCheck(DMEReference<? extends T> entity, TimelineChange<? extends T> thisChange, TimelineChange<?> checkAgainst) {
            if (thisChange instanceof HolderChanges.Change<?> ch){
                HolderChanges.Change<T> holderChange = (HolderChanges.Change<T>) thisChange;
                return ch.getOwner().get().canHold(holderChange.getNew(),thisChange.getStart(),currentRuns);
            }
            return Optional.empty();
        }

        @Override
        public ApplyCondition<T> getNewObject(InstanceType type, String id, JsonObject data) {
            return new CanHold<>();
        }

        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
}
