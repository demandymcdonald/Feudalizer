package com.base.thread.flight;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Acars implements Future<ThreadFlight> {
    private final ThreadFlight flight;
    public Acars(ThreadFlight flight){
        this.flight = flight;
    }

    public enum Instruction{
        HOLDING_PATTERN,
        LAND,
        CONTINUE,
    }

    public Instruction getTCCInstruction(){
        return Instruction.CONTINUE;
    }




    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
    if(mayInterruptIfRunning)


        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public FlightTracker get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public FlightTracker get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
