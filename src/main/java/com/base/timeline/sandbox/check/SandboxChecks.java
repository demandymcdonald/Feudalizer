package com.base.timeline.sandbox.check;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;

import java.util.List;

public class SandboxChecks {

    public static class canNullify<T extends DateMutableEntity<T>> extends SandboxCheck<T>{
        @Override
        public SandboxCode check(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange) {
            if(newChange.canNullify(existingChange)){
                newChange.nullify(entity,state,existingChange);
            };
            return SandboxCode.CONTINUE;
        }
    }
    public static class canAddChange<T extends DateMutableEntity<T>> extends SandboxCheck<T>{
        @Override
        public SandboxCode check(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange) {
            List<StateError> errors = newChange.doesConflict(existingChange);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }
    }
    public static class canDeactivate<T extends DateMutableEntity<T>> extends SandboxCheck<T>{
        @Override
        public SandboxCode check(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<? super T> existingChange) {
            List<StateError> errors = newChange.canBeDeactivated(existingChange);
            if (errors.isEmpty()) {
                return SandboxCode.CONTINUE;
            }
            return resolveStateErrors(sandbox, state, newChange, errors);
        }
    }
    protected static <T extends DateMutableEntity<T>> SandboxCode resolveStateErrors(
            Sandbox<T> sandbox, TimelineState<T> state, TimelineChange<? super T> newChange, List<StateError> errors){
        return sandbox.handleErrors(state,newChange,errors);
    }

}
