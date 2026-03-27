package com.base.flags;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.propagation.core.Objective;
import com.base.timeline.propagation.core.Sandbox;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.CharacterState;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static com.base.flags.SandboxCode.CRITICAL_ERROR;

public abstract class ErrorResolution {
    public abstract String getCode();
    private final int priority;
    public ErrorResolution(int priority){
        this.priority = priority;
    }
    public int getPriority(){
        return priority;
    }
    public abstract <T extends DateMutableEntity<T,?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, TimelineChange<T> oldChange, T entity, boolean saveToDiff);
    public String tooltip(){
        return getCode();
    };

    public static class  GenOverride  extends ErrorResolution {

        public GenOverride() {
            super(5);
        }

        @Override
        public String getCode() {
            return "gen_override";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            change.overwrite(entity,saveToDiff,oldChange);
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenAccept extends ErrorResolution {
        public GenAccept() {
            super(6);
        }
        @Override
        public String getCode() {
            return "gen_accept";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            change.apply(entity,saveToDiff);
            return SandboxCode.CONTINUE;
        }

    }
    public static class EndSandbox extends ErrorResolution {
        public EndSandbox() {
            super(0);
        }
        @Override
        public String getCode() {
            return "end_sandbox";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            return SandboxCode.END_SAVE;
        }
    }
    public static class EndCancel extends ErrorResolution {
        public EndCancel() {
            super(0);
        }
        @Override
        public String getCode() {
            return "end_cancel";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            return SandboxCode.END_DISCARD;
        }

    }
    public static class GenIgnore extends ErrorResolution {
        public GenIgnore() {
            super(7);
        }
        @Override
        public String getCode() {
            return "end_ignore";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            change.apply(entity,saveToDiff);
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenNullify extends ErrorResolution {
        public GenNullify() {
            super(4);
        }
        @Override
        public String getCode() {
            return "gen_nullify";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            change.nullify(entity,oldChange);
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenContinue extends ErrorResolution {
        public GenContinue() {
            super(8);
        }
        @Override
        public String getCode() {
            return "gen_continue";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            change.onContinue();
            change.apply(entity,saveToDiff);
            return SandboxCode.CONTINUE;
        }
    }
    public static class SandboxBranching extends ErrorResolution {
        private final Objective objective;
        private final String branchType;
        public SandboxBranching(String branchType, Objective newObjective) {
            super(2);
            objective = newObjective;
            this.branchType = branchType;
        }

        @Override
        public String getCode() {
            return "branching_"+branchType;
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            Sandbox branch = new Sandbox(objective);
            CompletableFuture<HashMap<DMEReference<?>, JsonObject>> payload = branch.getFuture();
            branch.startSimulation();
            HashMap<DMEReference<?>, JsonObject> yield = payload.join();
            switch(branch.getStatus()){
                case CONTINUE -> {
                    Sandbox.ImplementChanges(yield);
                    return SandboxCode.CONTINUE;
                }
                case END_DISCARD -> {
                    return SandboxCode.END_DISCARD;
                }
                case END_SAVE -> {
                    Sandbox.ImplementChanges(yield);
                    sandbox.setEndDate(branch.getEndDate());
                    return SandboxCode.CONTINUE;
                }
            }
            return CRITICAL_ERROR;
        }
    }
    public static class ReplaceWithNew extends ErrorResolution {
        private final TimelineChange<?> replace;
        public ReplaceWithNew(TimelineChange<?> replace) {
            super(3);
            this.replace = replace;
        }
        @Override
        public String getCode() {
            return "gen_replaceWithNew";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange, T entity, boolean saveToDiff) {
            final TimelineChange<T> newChange = (TimelineChange<T>) replace;
            newChange.overwrite(entity,saveToDiff,change);
            return SandboxCode.CONTINUE;
        }
    }
}
