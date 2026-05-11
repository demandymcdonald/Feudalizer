package com.base.thread.flight.acars;

import com.base.thread.flight.ThreadFlight;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AcarsCount extends Acars<CountDownLatch> {
    private final CountDownLatch cdl;
    private final long max;
    public AcarsCount(ThreadFlight flight, CountDownLatch cdl) {
        super(flight);
        this.cdl = cdl;
        max = cdl.getCount();
    }
    public AcarsCount(ThreadFlight flight, CountDownLatch cdl, long max) {
        super(flight);
        this.cdl = cdl;
        this.max = Math.max(max, cdl.getCount());
    }

    @Override
    public CountDownLatch get() throws InterruptedException, ExecutionException {
        return cdl;
    }

    @Override
    public CountDownLatch get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return cdl;
    }


}
