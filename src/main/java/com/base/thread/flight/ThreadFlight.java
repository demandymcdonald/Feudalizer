package com.base.thread.flight;

import com.base.thread.ThreadTrafficController;
import com.base.thread.flight.acars.Acars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ThreadFlight {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ThreadFlight.class);
    public enum Status{
        ON_GROUND(false,false),
        TAXIING(false,false),
        TAKE_OFF(true,false),
        IN_FLIGHT(true,false),
        IN_PATTERN(true,false),
        HOLDING(true,false),
        LANDING(true,false),
        LANDED(false,true),
        CRASHED(false,true),;

        private final boolean inAir;
        private final boolean flightFinished;
        Status(boolean inAir, boolean flightFinished) {
            this.inAir = inAir;
            this.flightFinished = flightFinished;
        }
        public boolean isInAir(){
            return inAir;
        }
        public boolean isFlightFinished(){
            return flightFinished;
        }
    }
    protected final TCCFlightTracker tccEndPoint = new TCCFlightTracker();
    protected final ThreadcraftFlightTracker threadcraftEndPoint = new ThreadcraftFlightTracker();
    protected final Acars acars = new Acars(this);
    protected final AtomicReference<Status> status = new AtomicReference<>(Status.ON_GROUND);
    protected final AtomicLong takeoffTime = new AtomicLong(-1);
    //Stores shared values for both sides of the FlightTracker to access. Will also contain the Flight Plan, and any vars for tracking progress

    public final void takeoffClearance(){
        status.set(Status.TAKE_OFF);
        try {
            status.set(Status.IN_FLIGHT);
            takeoffTime.set(System.currentTimeMillis());
            doFlight();
        } catch (Exception e) {
            ThreadTrafficController.connect().declareEmergency(this,true, e);
            status.set(Status.CRASHED);
            return;
        }
    };
    public AtomicReference<Status> getStatus(){
        return status;
    };
    public abstract void doFlight();





    public class TCCFlightTracker extends FlightTracker {
        //Will be basically a view object with responses to TFT's calls, and vice versa.
        public TCCFlightTracker() {
            super(ThreadFlight.this);
        }

    }
    public class ThreadcraftFlightTracker extends FlightTracker {
        //See above. Will also allow the thread to open a tunnel to grab clearance on resources in another Threadspace (say if a siloed sandbox needs to deep copy something from the main ThreadPort)
        public ThreadcraftFlightTracker() {
            super(ThreadFlight.this);
        }
    }




}
