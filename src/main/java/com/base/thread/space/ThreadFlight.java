package com.base.thread.space;

import com.base.thread.ILocking;
import com.base.thread.PossibleThreadDeviation;
import com.base.thread.ThreadTower;
import com.base.thread.ThreadTracon;
import com.base.thread.flight.FlightTracker;
import com.base.thread.flight.acars.Acars;
import com.utilities.id.StringIdentifiable;
import com.utilities.id.UUIDIdentifiable;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ThreadFlight implements StringIdentifiable,Runnable,Comparable<Long> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ThreadFlight.class);
    public enum Status{
        ON_GROUND(false,false),
        TAXIING(false,false),
        TAKEOFF(true,false),
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
    public enum FlightType{
        VFR,
        VFR_FOLLOWING,
        IFR,
    }
    private final Triple<String,String,String> callsign;
//    protected final TCCFlightTracker tccEndPoint = new TCCFlightTracker();
//    protected final ThreadcraftFlightTracker threadcraftEndPoint = new ThreadcraftFlightTracker();
    protected final Acars<?> acars;
    protected final AtomicReference<Status> status = new AtomicReference<>(Status.ON_GROUND);
    private final int priority;
    protected final AtomicLong fullPriority = new AtomicLong(5000);
    protected final AtomicLong takeoffTime = new AtomicLong(-1);
    private final long formedTime;
    private final FlightType type;
    private final AtomicInteger numBump = new AtomicInteger(1);
    public ThreadFlight(FlightType type, int priority, Acars<?> acars) {
        this.type = type;
        this.formedTime = System.currentTimeMillis();
        this.acars = acars;
        this.priority = priority > 1000 ? priority : priority * 1000;
        this.callsign = CallsignGen.generateFlightCode(type); //I love this for the memes, but I might cut it later if it's hurting performance.
    }

    public final void startFlight(){
        status.set(Status.TAXIING);
        doPreTakeoffChecks();
    };
    public final void takeOff(){
        try {
            status.set(Status.TAKEOFF);
            takeoffTime.set(System.currentTimeMillis());
            status.set(Status.IN_FLIGHT);
            doFlight();
            status.set(Status.LANDING);
            doLandingChecks();
            status.set(Status.LANDED);

        } catch (Exception e) {
            ThreadTracon.connect().declareEmergency(this,true, e);
            status.set(Status.CRASHED);
            return;
        }
    }
    public final void switchThreadspace(ThreadTower newTower){

    }
    public abstract void doFlight();
    public abstract void doPreTakeoffChecks();
    public abstract void doLandingChecks();

    public int numBump(){
        return numBump.get();
    }
    public void bump(){
        numBump.incrementAndGet();
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
        return callsign.getLeft();
    }
    public final long getFormedTime() {
        return formedTime;
    }
    public final long getFormedTimeSec(){
        return (System.currentTimeMillis() - formedTime)/1000;
    }
    public final long getTakeoffTime() {
        return takeoffTime.get();
    }
    public AtomicReference<Status> getStatus(){
        return status;
    };
    public final String getIcaoCallsign(){
        return callsign.getRight();
    }
    public final String getShortCallsign(){
        return callsign.getMiddle();
    }
    public FlightType getType() {
        return type;
    }
    public final <T extends ILocking<T>> T requestLock(T object) throws PossibleThreadDeviation {
        ThreadTracon.connect().requestClearance(object);
        return object;
    }
    public final <T extends ILocking<?>> void requestClassLock(Class<? super T> object) throws PossibleThreadDeviation {
        ThreadTracon.connect().requestClearance(object);
    }




    @Override
    public final void run() {
        if(status.get() == Status.ON_GROUND){
            startFlight();
        }
        takeOff();
    }

    @Override
    public final int compareTo(Long o) {
        return o.compareTo(fullPriority.get());
    }
//    public class TCCFlightTracker extends FlightTracker {
//        //Will be basically a view object with responses to TFT's calls, and vice versa.
//        public TCCFlightTracker() {
//            super(ThreadFlight.this);
//        }
//
//    }
//    public class ThreadcraftFlightTracker extends FlightTracker {
//        //See above. Will also allow the thread to open a tunnel to grab clearance on resources in another ThreadTower (say if a siloed sandbox needs to deep copy something from the main ThreadPort)
//        public ThreadcraftFlightTracker() {
//            super(ThreadFlight.this);
//        }
//    }


}
