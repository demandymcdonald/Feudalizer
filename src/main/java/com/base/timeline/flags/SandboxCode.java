package com.base.timeline.flags;

public enum SandboxCode {
    CONTINUE(false),
    BRANCHING_OBJECTIVE(false),
    END_SAVE(true),
    END_DISCARD(true),
    CRITICAL_ERROR(true);

    private final boolean isComplete;

    SandboxCode(boolean isFinished){
        this.isComplete = isFinished;
    }

    public boolean isComplete(){
        return isComplete;
    }
}
