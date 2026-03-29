package com.base.timeline.sandbox.core;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.flags.SandboxCode;
import com.base.timeline.flags.StateError;
import com.google.gson.JsonObject;
import com.utilities.ThreadManager;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Deque;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SandboxHandler<T extends DateMutableEntity<T>> {
    private final Thread sandboxThread;
    private final Sandbox<T> sandbox;
    private SandboxHandler<?> child;
    private final CompletableFuture<HashMap<DMEReference<?>,JsonObject>> CompletedData;
    private static final Deque<StateError> CurrentErrors = new ConcurrentLinkedDeque<>();
    private static final Deque<CompletableFuture<Pair<DMEReference<?>, JsonObject>>> CurrentDataRequests = new ConcurrentLinkedDeque<>();

    private SandboxHandler(Thread parentThread, Sandbox<T> sandbox) {
        this.sandboxThread = parentThread;
        this.sandbox = sandbox;
        CompletedData = sandbox.getFuture();
    }
    public void startSandbox(){
        if (ThreadManager.isMainThread()){

        }
    }
    protected boolean isDone(){
        return (sandbox.getStatus().isComplete());
    }
    private void mainThreadListener(){
        while (!isDone()){

        }
    }
    public void addChild(SandboxHandler<?> child){
        this.child = child;
    }
    private SandboxCode startYield(){
        while (!child.isDone()){
            try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
        }
    }
    private HashMap<DMEReference<?>, JsonObject> getCompletedData(){
        return CompletedData.join();
    }
    ///  Main Method To be Utilized
    public static <T extends DateMutableEntity<T>> SandboxCode SandboxApplyChange(Objective<T> objective, @Nullable LocalDate endDate, @Nullable SandboxHandler<?> parent){
        Sandbox<T> sandbox;
        if (endDate == null){
            sandbox = new Sandbox<>(objective);
        } else {
            sandbox = new Sandbox<>(objective, endDate);
        }
        SandboxHandler<T> handler = new SandboxHandler<>(sandbox.getThread(),sandbox);
        boolean parentFlag = parent != null;














            if (parentFlag){
                parent.addChild(handler);
                parent.startYield();
            }
    }
}

