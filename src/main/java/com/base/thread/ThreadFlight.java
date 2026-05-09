package com.base.thread;

public abstract class ThreadFlight {
    //Base object that acts as a socket between the ThreadTrafficController and the Thread itself.
    protected final ThreadcraftObject realObject;
    public ThreadFlight(ThreadcraftObject realObject) {
        this.realObject = realObject;
    }


}
