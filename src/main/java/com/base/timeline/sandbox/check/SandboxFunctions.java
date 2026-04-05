package com.base.timeline.sandbox.check;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.objects.character.BookCharacter;
import com.objects.title.succession.SuccessionPlanner;

import java.time.LocalDate;
import java.util.List;

public class SandboxFunctions {

    public static class canNullify<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        @Override
        public void onComplete(Sandbox<T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<T> entity, TimelineChange<? super T> newChange) {

        }
        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, boolean seenBefore) {
            if(newChange.canNullify(existingChange)){
                newChange.nullify(entity,state,existingChange);
            };
            return SandboxCode.CONTINUE;
        }

        @Override
        public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {

        }
    }
    public static class canAddChange<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        private TimelineState<T> state;
        private List<TimelineChange<? super T>> changes;

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
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, boolean seenBefore) {
            List<StateError> errors = newChange.doesConflict(existingChange,seenBefore);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }

        @Override
        public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {
            if (isFirstCycle) {
                sandbox.buildDirtyMap();
            }
        }
    }
    public static class canDeactivate<T extends DateMutableEntity<T>> extends SandboxFunction<T> {
        @Override
        public SandboxCode onCycle(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange, boolean seenBefore) {
            List<StateError> errors = newChange.canBeDeactivated(existingChange,seenBefore);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }

        @Override
        public void onStep(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, boolean isFirstCycle) {

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
    public static class SuccessionPlanning extends SandboxFunction<BookCharacter>{
        @Override
        public void onComplete(Sandbox<BookCharacter> sandbox, LocalDate endDate, SandboxCode code, DMEReference<BookCharacter> entity, TimelineChange<? super BookCharacter> newChange) {
            final Timeline<BookCharacter> timeline = sandbox.getSubject().get().getTimeline();
            final TimelineChange<? super BookCharacter> change = sandbox.getObjective().change();
            final TimelineState<BookCharacter> state = timeline.getStateAtExact(change.getStart(),true);
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
        public SandboxCode onCycle(Sandbox<BookCharacter> sandbox, DMEReference<BookCharacter> entity, TimelineState<BookCharacter> state, TimelineChange<? super BookCharacter> newChange, TimelineChange<? super BookCharacter> existingChange, boolean seenBefore) {
            final BookCharacter character = entity.get();
            final LocalDate endDate = character.getEnded();
            return SuccessionPlanner.run(sandbox,character,endDate);
        }

        @Override
        public void onStep(Sandbox<BookCharacter> sandbox, DMEReference<BookCharacter> entity, TimelineState<BookCharacter> state, TimelineChange<? super BookCharacter> newChange, boolean isFirstCycle) {

        }


    }

    protected static <T extends DateMutableEntity<T>> SandboxCode resolveStateErrors(
            Sandbox<T> sandbox, TimelineState<T> state, TimelineChange<? super T> newChange, List<StateError> errors){
        return sandbox.handleErrors(state,newChange,errors);
    }

}
