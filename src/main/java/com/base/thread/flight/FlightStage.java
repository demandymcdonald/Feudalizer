package com.base.thread.flight;

import com.base.thread.space.ThreadFlight;

public class FlightStage {
    private final ThreadFlight parent;
    private final Runnable stage;
    public FlightStage(ThreadFlight parent, Runnable stage) {
        this.parent = parent;
        this.stage = stage;
    }
    public void run(){


    }
}
