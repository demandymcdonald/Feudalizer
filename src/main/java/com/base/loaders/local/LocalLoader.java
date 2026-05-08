package com.base.loaders.local;

import com.base.loaders.ILoader;
import com.base.loaders.global.HandlerType;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LocalLoader implements ILoader {
    private static final AtomicBoolean isSaving = new AtomicBoolean(false);
    private static final AtomicBoolean isLoading = new AtomicBoolean(false);
    private static final AtomicBoolean isActive = new AtomicBoolean(false);
    private static final Deque<CountDownLatch> listenerQueue = new ConcurrentLinkedDeque<>();
    private static final Deque<Runnable> awaitingQueue = new ConcurrentLinkedDeque<>();
    private static final ConcurrentLinkedQueue<CountDownLatch> awaitingBlockers = new ConcurrentLinkedQueue<>();
    protected static final Logger LOGGER = LoggerFactory.getLogger(LocalLoader.class);
    private ImmutableSet<HandlerType> types;
    public LocalLoader(HandlerType... types){
        for (HandlerType t : types){
            t.setLocalLoader(this);
        }
        this.types = ImmutableSet.copyOf(types);
    }

    public static CountDownLatch isSaving() {
        return isSaving(new CountDownLatch(1));
    }
    private static CountDownLatch isSaving(CountDownLatch latch){
        if (isSaving.get() && !listenerQueue.contains(latch)){
            listenerQueue.add(latch);
        } else {
            latch.countDown();
        }
        return latch;
    }
    public static CountDownLatch isLoading(){
        return isLoading(new CountDownLatch(1));
    }
    private static CountDownLatch isLoading(CountDownLatch latch){
        if (isLoading.get() && !listenerQueue.contains(latch)){
            listenerQueue.add(latch);
        } else {
            latch.countDown();
        }
        return latch;
    }
    public static CountDownLatch fileInUse(){
        CountDownLatch latch = new CountDownLatch(2);
        isSaving(latch);
        isLoading(latch);
        return latch;
    }
    public static CountDownLatch holdSaving(Runnable runnable){
        return handleHold(isSaving,runnable);
    }
    public static CountDownLatch holdLoading(Runnable runnable){
        return handleHold(isLoading,runnable);
    }
    private static synchronized CountDownLatch handleHold(AtomicBoolean action, Runnable runnable){
        CountDownLatch latch = new CountDownLatch(awaitingBlockers.size() + 1);
        awaitingQueue.add(runnable);
        awaitingBlockers.add(latch);
        if(!isActive.get()){
            action.set(true);
            isActive.set(true);
            processQueue();
        }
        return latch;
    }

    public static void processQueue(){
        while (!awaitingQueue.isEmpty()) {
            Runnable runnable = awaitingQueue.poll();
            runnable.run();
            Set<CountDownLatch> toRemove = new HashSet<>();
            for (CountDownLatch latch : awaitingBlockers) {
                latch.countDown();
                if (latch.getCount() <= 0) {
                    toRemove.add(latch);
                }
            }
            awaitingBlockers.removeAll(toRemove);
        }
        if (!awaitingBlockers.isEmpty()) {
            for (CountDownLatch latch : awaitingBlockers) {
                long ct = latch.getCount();
                for (long i = 0; i < ct; i++) {
                    latch.countDown();
                }
            }
            awaitingBlockers.clear();
        }
    }



    protected abstract void doSaveAll();
    protected abstract void doLoadAll();

    public abstract void newProjectActive(File path);
    public void onNewFile(File f){}
}
