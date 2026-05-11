package com.base.thread;

import com.base.thread.flight.ThreadFlight;
import org.geotools.util.WeakHashSet;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ThreadTrafficController {
    private final Thread tower; //This should be the only raw thread, and it's relatively light.
    private final AtomicBoolean isActive = new AtomicBoolean(true);
    private final Set<Threadspace> TRACONS = Collections.synchronizedSet(new WeakHashSet<>(Threadspace.class));
    //The actual manager of Threadports, Flights, which has the sole authority to grant clearance, control the threadspace, and issue navigation directives.
    private static final ThreadTrafficController instance = new ThreadTrafficController();

    public ThreadTrafficController() {
        this.tower = new Thread(new ThreadGroup("TRACON_TCC"),this::tccLifecycle);
        tower.setName("P80");
        tower.setDaemon(true);
        tower.start();
    }

    public static ThreadTrafficController connect(){
        return instance;
    }


    public void

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
}
