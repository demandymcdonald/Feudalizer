package com.base.flags;

import com.base.DateMutableEntity;
import com.base.timeline.TimelineContainer;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.propagation.core.Sandbox;
import com.simulation.people.BookCharacter;
import com.simulation.people.CharacterState;

public abstract class ErrorResolution {
    public abstract String getCode();
    public abstract <T extends DateMutableEntity<T,?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity);
    public String tooltip(){
        return getCode();
    };

    public static class  GenOverride  extends ErrorResolution {

        @Override
        public String getCode() {
            return "gen_override";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity) {
            change.overwrite(entity);
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenAccept extends ErrorResolution {
        @Override
        public String getCode() {
            return "gen_accept";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity) {
            change.apply(entity);
            return SandboxCode.CONTINUE;
        }

    }
    public static class EndSandbox extends ErrorResolution {
        @Override
        public String getCode() {
            return "end_sandbox";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity) {
            return SandboxCode.END_SAVE;
        }
    }
    public static class EndCancel extends ErrorResolution {
        @Override
        public String getCode() {
            return "end_cancel";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity) {
            return SandboxCode.END_DISCARD;
        }

    }
    public static class GenIgnore extends ErrorResolution {
        @Override
        public String getCode() {
            return "end_ignore";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity) {
            change.apply(entity);
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenNullify extends ErrorResolution {
        @Override
        public String getCode() {
            return "gen_nullify";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity) {
            change.doNullify(entity.getDateStateAt(sandbox.getCurrent()));
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenContinue extends ErrorResolution {
        @Override
        public String getCode() {
            return "gen_continue";
        }

        @Override
        public <T extends DateMutableEntity<T, ?>> SandboxCode resolve(Sandbox sandbox, TimelineChange<T> change, T entity) {
            change.onContinue();
            change.apply(entity);
            return SandboxCode.CONTINUE;
        }
    }
}
