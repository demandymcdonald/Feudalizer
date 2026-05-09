package com.base.thread;

public class ThreadcraftObject {
    //Stores shared values for both sides of the ThreadFlight to access. Will also contain the Flight Plan, and any vars for tracking progress
    public ThreadcraftObject(String s) {}









    public class TCCFlightTracker extends ThreadFlight {
        //Will be basically a view object with responses to TFT's calls, and vice versa.
        public TCCFlightTracker() {
            super(ThreadcraftObject.this);
        }
    }
    public class ThreadcraftFlightTracker extends ThreadFlight {
        //See above. Will also allow the thread to open a tunnel to grab clearance on resources in another Threadport (say if a siloed sandbox needs to deep copy something from the main ThreadPort)
        public ThreadcraftFlightTracker() {
            super(ThreadcraftObject.this);
        }
    }
}
