package com.base.timeline.error;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.base.timeline.state.TimelineState;
import com.objects.title.Title;
import com.utilities.Displayable;

import static com.base.timeline.error.SandboxCode.*;

public abstract class ErrorResolution implements Displayable {
    private final int priority;
    private final SandboxCode expectedCode;
    private final String id;
    private final String display;
    private final String description;
    private final boolean isExclusive;
    public ErrorResolution(String id, String display, String description, int priority, boolean isExclusive, SandboxCode expectedCode){
        this.priority = priority;
        this.expectedCode = expectedCode;
        this.id = id;
        this.display = display;
        this.description = description;
        this.isExclusive = isExclusive;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String displayName() {
        return display;
    }

    @Override
    public String getID() {
        return id;
    }
    public boolean isExclusive(){
        return isExclusive;
    }
    public int getPriority(){
        return priority;
    }
    public SandboxCode getExpectedCode(){
        return expectedCode;
    }
    public abstract <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange);


    public static class  GenOverride  extends ErrorResolution {
        public GenOverride() {
            super("gen_override","Override Existing", "Replace the existing change with the new one",5,false,CONTINUE);
        }

        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            newChange.override(state,oldChange,true,false);
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenAccept extends ErrorResolution {
        public GenAccept() {
            super("gen_accept","Accept","Accept the Current Change",12,true,CONTINUE);
        }

        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            newChange.advance(entity,state,oldChange,false);
            return SandboxCode.CONTINUE;
        }
    }
    public static class EndSandbox_Save extends ErrorResolution {
        public EndSandbox_Save() {
            super("gen_end_sandbox_save","End Sandbox", "Stop Propagating before this state",1,true,SandboxCode.END_SAVE);
        }

        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            return SandboxCode.END_SAVE;
        }
    }
    public static class EndSandbox_Cancel extends ErrorResolution {
        public EndSandbox_Cancel() {
            super("gen_end_sandbox_cancel","Cancel Change" ,"Cancel the current change and revert.",0,true,SandboxCode.END_DISCARD);
        }
        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            return SandboxCode.END_DISCARD;
        }
    }
    public static class GenIgnore extends ErrorResolution {
        public GenIgnore() {
            super("gen_ignore","Ignore","Ignore the lore error and apply the change regardless",6,false,CONTINUE);
        }


        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            newChange.advance(entity,state,oldChange,false);
            return CONTINUE;
        }
    }
//    public static class GenContinue extends ErrorResolution {
//        public GenContinue() {
//            super(8);
//        }
//        @Override
//        public String getCode() {
//            return "gen_continue";
//        }
//
//        @Override
//        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, TimelineChange<? super T> change,TimelineChange<?> oldChange, DMEReference<T> entity) {
//            change.onContinue();
//            change.apply(entity,saveToDiff);
//            return SandboxCode.CONTINUE;
//        }
//    }
    public static class SandboxBranching<R extends DateMutableEntity<R>> extends ErrorResolution {
        private final Objective<R> objective;
        public SandboxBranching(String branchingSubID, String displayName, String description, Objective<R> newObjective) {
            super("mut_sandbox_branch:"+branchingSubID,displayName,description,2,true,RESTART_FROM_STATE);
            objective = newObjective;
        }
        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            SandboxHandler<R> handler = SandboxHandler.StartSandbox(objective,null,sandbox.getHandler(),null);
            SandboxCode code = handler.getEndCode().join();
            if (code == SandboxCode.END_SAVE){
                return SandboxCode.RESTART_FROM_STATE;
            }
            return code;
        }
    }
    public static class ReplaceExistingWithNew extends ErrorResolution {
        private final TimelineChange<?> replace;
        public ReplaceExistingWithNew(String replaceSubID, String replaceTitle, String replaceDescription, TimelineChange<?> replace) {
            super("mut_replace:" + replaceSubID,replaceTitle,replaceDescription,3,true,SandboxCode.RESTART_FROM_STATE);
            this.replace = replace;
        }
        @Override
        public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange, TimelineChange<?> oldChange) {
            final TimelineChange<? super T> replacingChange = (TimelineChange<? super T>) replace;
            replacingChange.override(state,oldChange,false,false);
            return SandboxCode.RESTART_FROM_STATE;
        }
    }
    public static class SuccessionPlanning_Title<T extends Title<T>> extends SandboxBranching<T> {
        public SuccessionPlanning_Title(Objective<T> newObjective) {
            super("title_succession", "Run Succession Planner", "Give the title to their heir or a designated person", newObjective);
        }
    }

}
