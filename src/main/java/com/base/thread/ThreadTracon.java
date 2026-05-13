package com.base.thread;

import com.Global;
import com.base.thread.space.ThreadFlight;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTracon {
    private final AtomicInteger lastNum = new AtomicInteger(0);
    private final Thread tracon; //This should be the only raw thread, and it's relatively light.
    private final AtomicBoolean isActive = new AtomicBoolean(true);
    private final Map<String,ThreadTower> towers = new ConcurrentHashMap<>();
    private final Map<String,ThreadFlight> flights = Collections.synchronizedMap(new WeakHashMap<>());
    //The actual manager of Threadports, Flights, which has the sole authority to grant clearance, control the threadspace, and issue navigation directives.
    private static final ThreadTracon instance = new ThreadTracon();
    private final int maxPhysicalThreads = Global.CONFIG.getTotalThreads() - 2; //Take away one for main/render, another for this/buffer.
    public ThreadTracon() {
        this.tracon = new Thread(new ThreadGroup("KPDX_1337"),this::tccLifecycle);
        tracon.setName("Tracon_P80");
        tracon.setDaemon(true);
        tracon.setPriority(Thread.MAX_PRIORITY - 2);
        tracon.start();
    }

    public static long getSystemTime(){
        return System.currentTimeMillis();
    }
    public static long getSystemTimeSec(){
        return System.currentTimeMillis()/1000;
    }









    public static ThreadTracon connect(){
        return instance;
    }

    public void registerFlight(ThreadFlight flight){
        flights.put(flight.getCallsign(),flight);
        flight.internalGetFullPriority().set((flight.getBasePriority() * 1000L) + getNextPriorityNumber());
    }


    public <T extends ILocking<T>> void requestClearance(T object) throws PossibleThreadDeviation{

    }
    public <T extends ILocking<?>>void requestClearance(Class<? super T> object) throws PossibleThreadDeviation{

    }
    public <T extends ILocking<T>> void requestPriorityClearance(T object) throws PossibleThreadDeviation{

    }
    public <T extends ILocking<?>> void requestPriorityClearance(Class<? super T> object)throws PossibleThreadDeviation {

    }
    public <T extends ILocking<T>> void endClearance(T object){

    }
    public <T extends ILocking<T>> void endClearance(Class<? extends T> object){

    }
    void holdingPattern(ThreadFlight flight){

    }
    private <T extends ILocking<T>> void onHeldResource(ThreadFlight flight, T resource){

    }
    private <T extends ILocking<?>> void onHeldClass(ThreadFlight flight, Class<? super T> resource){

    }
    public void declareEmergency(ThreadFlight flight, boolean crashed, Exception e){

    }
    public void shutdown(){
        isActive.set(false);

    }
    public void tccLifecycle(){
        while (isActive.get()){

        }
    }





    private void reallocateThreads(){
        Map<ThreadTower,Integer> towerCount = new HashMap<>();
        for(ThreadTower tower : towers.values()){
            towerCount.put(tower,towerCount.getOrDefault(tower,0) + 1);
        }
    }

    public int getNextPriorityNumber(){
        int i = lastNum.getAndIncrement();
        if (i > 999){
            i = 1;
            lastNum.set(i);
        }
        return i;
    }
    public Set<String> getTowers(){
        return Set.copyOf(this.towers.keySet());
    }
}
