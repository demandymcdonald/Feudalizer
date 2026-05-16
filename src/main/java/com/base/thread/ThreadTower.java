package com.base.thread;

import com.base.thread.space.ThreadFlight;
import com.base.thread.space.ThreadedAsset;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.base.thread.space.CallsignGen.generateTowerCode;

public class ThreadTower {
    private static final long TL_SECONDS_BEFORE_BUMP = 10;
    private final String tower_code = generateTowerCode();
    private final ThreadGroup group = new ThreadGroup("tower_" + tower_code);
    private final AtomicInteger activeRunways = new AtomicInteger(0);
    private final ThreadPoolExecutor service;

    private final Map<LockInstance, Deque<ThreadFlight>> separatedAssets = new ConcurrentHashMap<>();
    private final Map<ThreadFlight,Thread> activeFlights = Collections.synchronizedMap(new WeakHashMap<>());
    private final Map<ThreadedAsset<?>,Object> groupedObjects = new ConcurrentHashMap<>();
    private final Deque<ThreadFlight> activeFlightsQueue = new ConcurrentLinkedDeque<>();
    protected final Map<ThreadedAsset<?>,Object> registeredAssets = new ConcurrentHashMap<>();

    protected ThreadTower(int activeRunways) {
        this.activeRunways.set(activeRunways);
        service = new ThreadPoolExecutor(activeRunways, activeRunways * 2, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue<>()){
            @Override
            public ThreadFactory getThreadFactory() {
                return new ThreadFactory() {
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        if (r instanceof ThreadFlight tf){
                            Thread t = new Thread(group, r);
                            t.setDaemon(true);
                            t.setName(tower_code + ":" + tf.getIcaoCallsign());
                            t.setPriority(Thread.NORM_PRIORITY);
                            activeFlights.put(tf,t);
                            return t;
                        } else {
                            throw new RuntimeException("Unknown Traffic :" + r.toString() + " in the airspace of " + tower_code);
                        }
                    }
                };
            };
        };
    }
    public void doTowerControl(int updatedActiveRunways){

        manageExecutor(updatedActiveRunways);
    }
    public void flightEntersControl(ThreadFlight tf){
        if(tf.getStatus().get() == ThreadFlight.Status.ON_GROUND){
            tf.startFlight();
            return;
        }
        activeFlightsQueue.add(tf);
    }
    public void downloadFMCData(ThreadFlight tf){
        if (tf.shouldCopyShared()) return;
        for(ThreadedAsset<?> asset : registeredAssets.keySet()){
            asset.set(registeredAssets.get(asset));
        }
    }
    public void flightLeavesThreadspace(ThreadFlight fl){
        activeFlights.remove(fl);
    }
    private void manageExecutor(int updatedActiveRunways) {
        final int maxInExecutor;
        final int numInPool = service.getActiveCount();
        if(updatedActiveRunways != activeRunways.get()){
            activeRunways.set(updatedActiveRunways);
            service.setCorePoolSize(updatedActiveRunways);
            maxInExecutor = updatedActiveRunways * 2;
            service.setMaximumPoolSize(maxInExecutor);
        } else {
            maxInExecutor = service.getMaximumPoolSize();
        }
        int takeoffSlots = maxInExecutor - numInPool;
        while (takeoffSlots > 0){
            takeoffSlots--;
            ThreadFlight flight = activeFlightsQueue.poll();
            if (flight != null){
                service.execute(flight);
            }
        }
        updatePriorities();
    }

    public void updatePriorities(){
        for(ThreadFlight flight : activeFlights.keySet()){
            int numBump = flight.numBump();
            if((ThreadTracon.getSystemTimeSec() - flight.getFormedTimeSec()) > (TL_SECONDS_BEFORE_BUMP * numBump)){
                flight.bump();
                AtomicLong fullPrio = flight.internalGetFullPriority();
                fullPrio.set(Math.max(0,flight.getBasePriority() - (1000L * numBump)) + ThreadTracon.connect().getNextPriorityNumber());
            }
        }
    }




    public void openThreadSpace(){

    }
    public void closeThreadSpace(){
        service.shutdown();
    }

    //public int getThreadSpaceActivity(){
//        return activeFlights.size();
//    }
    public int getNumFlights(){
        return getFlightsAwaitingTakeoff() + activeFlights.size();
    }
    public int getNumRunways(){
        return activeRunways.get();
    }
    public int getActiveRunways(){
        return service.getActiveCount();
    }
    public int getFlightsAwaitingTakeoff(){
        return activeFlightsQueue.size();
    }
    public boolean containsThread(Thread t){
        return activeFlights.containsValue(t);
    }
    public boolean containsFlight(ThreadFlight tf){
        return activeFlights.containsKey(tf);
    }




    public static class LockInstance {


        public LockInstance(ILocking<?> lock, @Nullable ThreadFlight flight, Thread thread) {
        }
        //Acts as a bucket for threads that need to/can access the same shared resources and aren't "in air"

    }








}
