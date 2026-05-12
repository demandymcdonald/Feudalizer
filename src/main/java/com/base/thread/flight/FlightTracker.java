package com.base.thread.flight;

import com.base.thread.space.ThreadFlight;

public abstract class FlightTracker {
    //Base object that acts as a socket between the ThreadTracon and the Thread itself.
    protected final ThreadFlight realObject;
    public FlightTracker(ThreadFlight realObject) {
        this.realObject = realObject;
    }
    public boolean isInFlight(){
        return realObject.status.get() == ThreadFlight.Status.IN_FLIGHT;
    }

}
