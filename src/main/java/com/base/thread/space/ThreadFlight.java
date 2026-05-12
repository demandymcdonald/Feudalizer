package com.base.thread.space;

import com.base.thread.ThreadTracon;
import com.base.thread.flight.FlightTracker;
import com.base.thread.flight.acars.Acars;
import com.utilities.id.StringIdentifiable;
import com.utilities.id.UUIDIdentifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ThreadFlight implements StringIdentifiable,Runnable,Comparable<Float> {
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
    private final String callsign;
    protected final TCCFlightTracker tccEndPoint = new TCCFlightTracker();
    protected final ThreadcraftFlightTracker threadcraftEndPoint = new ThreadcraftFlightTracker();
    protected final Acars<?> acars;
    protected final AtomicReference<Status> status = new AtomicReference<>(Status.ON_GROUND);
    private final int priority;
    protected final AtomicLong fullPriority = new AtomicLong(5000);
    protected final AtomicLong takeoffTime = new AtomicLong(-1);
    //Stores shared values for both sides of the FlightTracker to access. Will also contain the Flight Plan, and any vars for tracking progress

    public ThreadFlight(int priority, Acars<?> acars) {
        this.acars = acars;
        this.priority = priority;
        this.callsign = UUID.randomUUID().toString();
    }
    public int getBasePriority() {
        return priority;
    }
    public Long getFullPriority() {
        return fullPriority.get();
    }
    public AtomicLong internalGetFullPriority() {
        return fullPriority;
    }
    @Override
    public final String getID() {
        return callsign;
    }

    public final void takeoffClearance(){
        status.set(Status.TAXIING);

        try {
            status.set(Status.IN_FLIGHT);
            takeoffTime.set(System.currentTimeMillis());
            doFlight();
        } catch (Exception e) {
            ThreadTracon.connect().declareEmergency(this,true, e);
            status.set(Status.CRASHED);
            return;
        }
    };
    public AtomicReference<Status> getStatus(){
        return status;
    };
    public abstract void doFlight();


    public String getCallsign(){

    }


    public class TCCFlightTracker extends FlightTracker {
        //Will be basically a view object with responses to TFT's calls, and vice versa.
        public TCCFlightTracker() {
            super(ThreadFlight.this);
        }

    }
    public class ThreadcraftFlightTracker extends FlightTracker {
        //See above. Will also allow the thread to open a tunnel to grab clearance on resources in another ThreadTower (say if a siloed sandbox needs to deep copy something from the main ThreadPort)
        public ThreadcraftFlightTracker() {
            super(ThreadFlight.this);
        }
    }




}
