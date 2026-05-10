package com.base.thread.flight;

public abstract class FlightTracker {
    //Base object that acts as a socket between the ThreadTrafficController and the Thread itself.
    protected final ThreadFlight realObject;
    public FlightTracker(ThreadFlight realObject) {
        this.realObject = realObject;
    }
    public boolean isInFlight(){
        return realObject.status.get() == ThreadFlight.Status.IN_FLIGHT;
    }

}
