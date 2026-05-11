package com.base.thread.flight;

import java.util.*;

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
