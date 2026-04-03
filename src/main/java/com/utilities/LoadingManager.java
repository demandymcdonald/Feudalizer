package com.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;

public class LoadingManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingManager.class);
    private static final String LOADING_COMPLETE = "Loading Complete";
    private static final String STAGE_COMPLETE = "Stage Complete";
    private AtomicInteger loading = new AtomicInteger(0);
    private AtomicInteger maxLoading = new AtomicInteger(0);
    private AtomicReference<String> currentLoading = new AtomicReference<>("Loading Complete");
    private AtomicReference<String> oldState = new AtomicReference<>("");
    private AtomicBoolean isLoading = new AtomicBoolean(false);
    private AtomicInteger debugCounter = new AtomicInteger(0);
    private WeakReference<Thread> subjectThread;
    private Deque<CompletableFuture<Thread>> loadQueue = new ConcurrentLinkedDeque<>();
    public LoadingManager(String currentLoading, int maxLoading) {
        this.currentLoading.set(currentLoading);
        this.maxLoading.set(maxLoading);
        this.isLoading.set(true);
    }
    public LoadingManager() {
        reset();
    }
    public void incrementLoading(){
        if (loading.incrementAndGet() >= maxLoading.get()){
            LOGGER.debug("Loading complete for: " + currentLoading.get() + "... Number of Cycles: " + debugCounter.get());
            reset();
            currentLoading.set(STAGE_COMPLETE);
        } else if (LOGGER.isDebugEnabled() && debugCounter.incrementAndGet() % 10 == 0){
            LOGGER.debug("Loading Status: " + currentLoading.get() + " " + loading + "/" + maxLoading);
        }
    }
    private Thread prepLoad(String currentLoading, AtomicInteger maxLoading, Thread t){
        LOGGER.debug("Starting Load: " + currentLoading + "... Total Items: " + maxLoading + ".");
        this.currentLoading.set(currentLoading);
        this.maxLoading = maxLoading;
        loading.set(0);
        isLoading.set(true);
        subjectThread = new WeakReference<>(t);
        return t;
    }
    private Thread prepLoad(String currentLoading, AtomicInteger maxLoading, CompletableFuture<Thread> t){
        return prepLoad(currentLoading, maxLoading, t.join());
    }
    public Thread newStage(String currentLoading, int maxLoading, Runnable toLoad, boolean pullFromParent){
        return newStage(currentLoading, new AtomicInteger(maxLoading), toLoad, pullFromParent);
    }
    public Thread newStage(String currentLoading, AtomicInteger maxLoading, Runnable toLoad, boolean pullFromParent){
        //final CompletableFuture<Runnable> runnable = new CompletableFuture<>();
        final CompletableFuture<Thread> th = new CompletableFuture<>();
        final Runnable payload;
        if (isLoading.get()) {
            payload = () -> {
                while (loadQueue.contains(th)) {
                    try {
                        sleep(5000);
                    } catch (InterruptedException ignored) {}
                }
                prepLoad(currentLoading, maxLoading,th);
                toLoad.run();
            };
        } else {
            payload = () -> {
                toLoad.run();
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                forceComplete();
            };
        }
        Thread t;
        if (pullFromParent) {
            t = ThreadManager.BuildThread("LoadManager-" + currentLoading, payload, Thread.currentThread());
        } else {
            t = ThreadManager.BuildThread("LoadManager-" + currentLoading, payload,null);
        }
        if (isLoading.get()) {
            th.complete(t);
            loadQueue.add(th);
        }
        return t;
    }
    public void setTemporaryAppend(String toAppend){
        setTemporaryAppend(": ", toAppend);
    }
    public void setTemporaryAppend(String delimiter, String toAppend){
        if (!hasOldState()) {
            oldState.set(currentLoading.get());
            currentLoading.set(currentLoading.get() + delimiter + toAppend);
        }
    }
    public void setTemporary(String newText){
        if (!hasOldState()) {
            oldState.set(currentLoading.get());
            currentLoading.set(newText);
        }
    }
    public void restoreToMainState(){
        if (hasOldState()) {
            currentLoading.set(oldState.get());
            oldState.set("");
        }
    }
    private boolean hasOldState(){
        return !oldState.get().isEmpty();
    }
    public void setStageName(String toAppend){
        currentLoading.set(toAppend);
    }
    private void reset(){
        if (!loadQueue.isEmpty()) {
            CompletableFuture<Thread> next = new CompletableFuture<>();
            final int total = loadQueue.size();
            int skipCount = 0;
            while (true) {
                next = loadQueue.poll();
                if (next == null) break;
                if (!next.join().isAlive()) {
                    if (!loadQueue.isEmpty() && skipCount < total) {
                        loadQueue.add(next);
                        skipCount++;
                        continue;
                    } else {
                        break;
                    }
                }
                //If next is alive, then it'll be unblocked internally and will start the loading process.
            }
        } else {
            currentLoading.set(LOADING_COMPLETE);
            maxLoading.set(0);
            loading.set(0);
            debugCounter.set(0);
            oldState.set("");
            isLoading.set(false);
            cullThread();
        }
    }
    public void forceComplete(){
        if (isLoading.get()){
            LOGGER.info("Load force ended: " + currentLoading + "... " + loading + "/" + maxLoading +" were registered as loaded.");
            reset();
        }
    }

    private void cullThread(){

    }
    public boolean isLoading(){
        return isLoading.get();
    }
    public String getCurrentLoadText(){
        if (!isLoading.get()){
            return currentLoading.get();
        } else {
            return currentLoading.get() + "... " + loading + "/" + maxLoading;
        }
    }
    public String getBaseText(){
        return currentLoading.get();
    }
    public int getLoading(){
        return loading.get();
    }
    public int getLoadPercent(){
        if (!isLoading.get()){
            return 100;
        }
        return (int) (loading.get() / (double) maxLoading.get() * 100);
    }
    @Override
    public String toString() {
        return getCurrentLoadText();
    }
}
