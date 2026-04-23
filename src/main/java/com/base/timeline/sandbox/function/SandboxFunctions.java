package com.base.timeline.sandbox.function;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.objects.character.sentient.HumanCharacter;
import com.objects.title.succession.SuccessionPlanner;

import java.time.LocalDate;
import java.util.*;

public class SandboxFunctions {

    public static class canNullify<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {

        }

        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
            if(newChange.canNullify(existingChange,shouldRun)){
                newChange.nullify(entity,state,existingChange);
            };
            return SandboxCode.CONTINUE;
        }

        @Override
        protected void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {

        }
    }
    public static class CanAddChange<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {
            final Timeline<T> timeline = sandbox.getSubject().get().getTimeline();
            final TimelineChange<? super T> change = sandbox.getObjective().change();
            final TimelineState<T> state = timeline.getStateAtExact(change.getStart(),true);
            switch (code){
                case END_DISCARD,CRITICAL_ERROR -> {
                    sandbox.getWorkingDirty().clear();
                    break;
                }
                case END_SAVE -> {
                    change.setEnd(endDate);
                    state.insertAndPropagateBreadcrumb(change);
                    break;
                }
            }
        }

        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
            List<StateError> errors = newChange.doesConflict(existingChange,shouldRun);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }

        @Override
        protected void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {
            if (isFirstCycle) {
                sandbox.buildDirtyMap();
            }
        }
    }

    public static class canDeactivate<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        private final Boolean isSubPart;
        public canDeactivate(boolean isSubPart) {
            this.isSubPart = isSubPart;
        }


        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, List<Condition.ShouldRun> shouldRun) {
            List<StateError> errors = newChange.canBeDeactivated(state,isSubPart,shouldRun);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }

        @Override
        protected void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {

        }

        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {
            switch (code){
                case END_DISCARD,CRITICAL_ERROR -> {
                    sandbox.getWorkingDirty().clear();
                    break;
                }
                case END_SAVE -> {
                    newChange.deactivate(true);
                    break;
                }
            }
        }

    }
    public static class SuccessionPlanning extends SandboxFunction<HumanCharacter>{
        @Override
        public void onComplete(Sandbox<HumanCharacter> sandbox, LocalDate endDate, SandboxCode code, DMEReference<HumanCharacter> entity, TimelineChange<? super HumanCharacter> newChange) {
            final Timeline<HumanCharacter> timeline = sandbox.getSubject().get().getTimeline();
            final TimelineChange<? super HumanCharacter> change = sandbox.getObjective().change();
            final TimelineState<HumanCharacter> state = timeline.getStateAtExact(change.getStart(),true);
            switch (code) {
                case END_DISCARD, CRITICAL_ERROR -> {
                    sandbox.getWorkingDirty().clear();
                    break;
                }
                case END_SAVE -> {
                    break;
                }
            }
        }

        @Override
        public SandboxCode onCycle(Sandbox<HumanCharacter> sandbox, DMEReference<HumanCharacter> entity, TimelineState<HumanCharacter> state, TimelineChange<? super HumanCharacter> newChange, TimelineChange<? super HumanCharacter> existingChange, List<Condition.ShouldRun> shouldRun) {
            final HumanCharacter character = entity.get();
            final LocalDate endDate = character.getEnded();
            return SuccessionPlanner.run(sandbox,character,endDate);
        }

        @Override
        protected void onStep(Sandbox<HumanCharacter> sandbox, DMEReference<HumanCharacter> entity, TimelineState<HumanCharacter> state, TimelineChange<? super HumanCharacter> newChange, boolean isFirstCycle) {

        }


    }

    public static <T extends DateMutableEntity<T>> SandboxCode resolveStateErrors(
            Sandbox<T> sandbox, TimelineState<T> state, TimelineChange<? super T> newChange, List<StateError> errors){
        return sandbox.handleErrors(state,newChange,errors);
    }

}
