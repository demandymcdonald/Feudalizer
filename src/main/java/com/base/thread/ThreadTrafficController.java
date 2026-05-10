package com.base.thread;

import com.base.thread.flight.ThreadFlight;

public class ThreadTrafficController {
    //The actual manager of Threadports, Flights, which has the sole authority to grant clearance, control the threadspace, and issue navigation directives.
    private static final ThreadTrafficController instance = new ThreadTrafficController();
    public static ThreadTrafficController connect(){
        return instance;
    }
    public <T extends ILocking<T>> void requestClearance(T object) throws PossibleThreadDeviation{

    }
    public <T extends ILocking<?>>void requestClearance(Class<? super T> object) throws PossibleThreadDeviation{

    }
    public <T extends ILocking<T>> void requestPriorityClearance(T object) throws PossibleThreadDeviation{

    }
    public <T extends ILocking<?>>void requestPriorityClearance(Class<? super T> object)throws PossibleThreadDeviation {

    }
    public <T extends ILocking<T>>void endClearance(T object){

    }
    public <T extends ILocking<T>>void endClearance(Class<? extends T> object){

    }


    public void declareEmergency(ThreadFlight flight, boolean crashed, Exception e){

    }
}
