package com.base.timeline.error;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.TLMultiChange;
import com.base.timeline.change.multi.TimelineMapChange;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.base.timeline.state.TimelineState;
import com.objects.character.sentient.HumanCharacter;
import com.objects.character.sentient.SentientCharacter;
import com.objects.title.Title;
import com.utilities.IDisplayable;

import java.util.Date;

import static com.base.timeline.error.SandboxCode.*;

public abstract class ErrorResolution<T extends DateMutableEntity<?>> implements IResolution<T,Sandbox<? extends T>,TimelineState<? extends T>,TimelineChange<T>,TimelineChange<?>> {
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
    public String getDescription() {
        return description;
    }

    @Override
    public String getDisplayName() {
        return display;
    }

    @Override
    public String getDisplayID() {
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

    @Override
    public abstract SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange);

    @Override
    public String getID() {
        return "";
    }

    public static class  GenOverride<T extends DateMutableEntity<?>>  extends ErrorResolution<T> {
        public GenOverride() {
            super("gen_override","Override Existing", "Replace the existing change with the new one",5,false,CONTINUE);
        }


        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> tTimelineState, TimelineChange<T> change, TimelineChange<?> timelineChange) {
            change.override(tTimelineState,timelineChange,true,false);
            return SandboxCode.CONTINUE;
        }
    }
    public static class GenAccept<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        public GenAccept() {
            super("gen_accept","Accept","Accept the Current Change",12,true,CONTINUE);
        }

        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            newChange.advanceStage(reference,state,false);
            return CONTINUE;
        }
    }
    public static class EndSandbox_Save<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        public EndSandbox_Save() {
            super("gen_end_sandbox_save","End Sandbox", "Stop Propagating before this state",1,true,SandboxCode.END_SAVE);
        }

        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            return SandboxCode.END_SAVE;
        }
    }
    public static class EndSandbox_Cancel<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        public EndSandbox_Cancel() {
            super("gen_end_sandbox_cancel","Cancel Change" ,"Cancel the current change and revert.",0,true,SandboxCode.END_DISCARD);
        }


        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            return SandboxCode.END_DISCARD;
        }
    }
    public static class GenIgnore<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        public GenIgnore() {
            super("gen_ignore","Ignore","Ignore the lore error and apply the change regardless",6,false,CONTINUE);
        }


        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            newChange.advanceStage(reference,state,false);
            return CONTINUE;
        }
    }
    public static class MapMergeEnd<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        public MapMergeEnd() {
            super("map_merge_end", "Merge End", "merge the two", 3, false, END_SAVE);
        }

        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            if (newChange instanceof TLMultiChange<?,?,?,?,?> currentTMC && currentChange instanceof TLMultiChange<?,?,?,?,?> oldTMC && currentTMC.getClass().equals(oldTMC.getClass())){
                oldTMC.mergeSafe(currentTMC);
                return END_SAVE;
            }
            return CRITICAL_ERROR;
        }
    }
    public static class MapMergeContinue<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        public MapMergeContinue() {
            super("map_merge_continue", "Merge Continue", "merge the two", 3, false, CONTINUE);
        }

        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            if (newChange instanceof TLMultiChange<?,?,?,?,?> nTLC && currentChange instanceof TLMultiChange<?,?,?,?,?> cTLC && nTLC.getClass().equals(cTLC.getClass())){

                TLMultiChange<?,?,?,?,T> currentTMC = (TLMultiChange<?,?,?,?,T>) nTLC;
                currentTMC.mergeSafe(cTLC);
                currentTMC.override(state,cTLC,true,false);
                return CONTINUE;
            }
            return CONTINUE;
        }
    }
    public static class SandboxBranching<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        private final Objective<T> objective;
        public SandboxBranching(String branchingSubID, String displayName, String description, Objective<T> newObjective) {
            super("mut_sandbox_branch:"+branchingSubID,displayName,description,3,true,RESTART_FROM_STATE);
            objective = newObjective;
        }

        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            SandboxHandler<T> handler = SandboxHandler.StartSandbox(objective,null,sandbox.getHandler(),null);
            SandboxCode code = handler.getEndCode().join();
            if (code == SandboxCode.END_SAVE){
                return SandboxCode.RESTART_FROM_STATE;
            }
            return code;
        }
    }
    public static class ReplaceExistingWithNew<T extends DateMutableEntity<?>> extends ErrorResolution<T> {
        private final TimelineChange<?> replace;
        public ReplaceExistingWithNew(String replaceSubID, String replaceTitle, String replaceDescription, TimelineChange<?> replace) {
            super("mut_replace:" + replaceSubID,replaceTitle,replaceDescription,4,true,SandboxCode.RESTART_FROM_STATE);
            this.replace = replace;
        }


        @Override
        public SandboxCode resolve(DMEReference<? extends T> reference, Sandbox<? extends T> sandbox, TimelineState<? extends T> state, TimelineChange<T> newChange, TimelineChange<?> currentChange) {
            final TimelineChange<T> replacingChange = (TimelineChange<T>) replace;
            replacingChange.override(state,currentChange,false,false);
            return SandboxCode.RESTART_FROM_STATE;
        }
    }

    public static class SuccessionPlanning_Title<T extends SentientCharacter<?>> extends SandboxBranching<T> {
        public SuccessionPlanning_Title(DMEReference<? extends SentientCharacter<?>> newObjective) {
            super("title_succession", "Run Succession Planner", "Give the title to their heir or a designated person", (Objective<HumanCharacter>) Objective.buildSuccession(newObjective));
        }
    }

}
