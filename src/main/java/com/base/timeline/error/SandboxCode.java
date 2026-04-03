package com.base.timeline.error;

public enum SandboxCode {
    CONTINUE(false),
    CONTINUE_NEW_SAVE(false),
    RESTART_FROM_STATE(false),
    END_SAVE(true),
    END_DISCARD(true),
    CRITICAL_ERROR(true);

    private final boolean sandboxEnding;

    SandboxCode(boolean isFinished){
        this.sandboxEnding = isFinished;
    }

    public boolean sandboxComplete(){
        return sandboxEnding;
    }



    //0 End Cancel
    //1 End Save
    //2 Sandbox Branching
    //3 Replace with New
    //4
    //5 Override
    //6 Ignore
    //7
    //8
    //9
    //10 Accept
}
