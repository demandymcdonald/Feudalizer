package com.base.datemutable.timeline.error;

public enum SandboxCode {
    CONTINUE(false),
    CONTINUE_NEW_SAVE(false),
    RESTART_FROM_STATE(false),
    END_SAVE(true),
    END_DISCARD(true),
    CRITICAL_ERROR(true),
    END_SUCCESSION_PLANNING(true),
    SUCCESSION_NEXT_HEIR(true);

    private final boolean sandboxEnding;

    SandboxCode(boolean isFinished){
        this.sandboxEnding = isFinished;
    }

    public boolean sandboxComplete(){
        return sandboxEnding;
    }



    //0 End Cancel
    //1 End Save
    //2 MapMergeEnd
    //3 Sandbox Branching
    //4 Replace with New
    //5 Override
    //6 Ignore
    //7 List Option
    //8
    //9 MapMergeContinue
    //10 Accept
}
