package com.base.thread;

import com.base.thread.flight.ThreadFlight;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.TreeMultimap;

import java.util.Collections;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threadspace {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final Map<ILocking<?>, Deque<ThreadFlight>>
    public void openThreadSpace(){

    }
    public void closeThreadSpace(){
        service.shutdown();
    }
    //Acts as a bucket for threads that need to/can access the same shared resources and aren't "in air"
}
