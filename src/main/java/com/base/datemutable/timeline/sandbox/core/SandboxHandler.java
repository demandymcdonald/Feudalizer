package com.base.datemutable.timeline.sandbox.core;

import com.Global;
import com.base.datemutable.AbstractMutableManager;
import com.base.DMRegistry;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.error.SandboxCode;
import com.base.datemutable.timeline.error.StateError;
import com.google.gson.JsonObject;
import com.base.loaders.LoadingManager;
import com.utilities.ThreadManager;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SandboxHandler<T extends DateMutableEntity<T>> {
    private final Thread sandboxThread;
    private final Sandbox<T> sandbox;
    private SandboxHandler<?> child;
    private CompletableFuture<SandboxCode> future = new CompletableFuture<>();
    private Consumer<SandboxCode> doAfter;


    private static final Deque<StateError> CurrentErrors = new ConcurrentLinkedDeque<>();
    private static final Deque<Pair<DMEReference<?>, CompletableFuture<JsonObject>>> CurrentDataRequests = new ConcurrentLinkedDeque<>();
    private static final Logger LOG = LoggerFactory.getLogger(SandboxHandler.class);
    private static final AtomicInteger sandboxCount = new AtomicInteger(0);


    private SandboxHandler(Thread parentThread, Sandbox<T> sandbox, @Nullable  Consumer<SandboxCode> doAfter) {
        this.sandboxThread = parentThread;
        this.sandbox = sandbox;
        if (doAfter != null){
            this.doAfter = doAfter;
        } else {
            this.doAfter = (c) -> {};
        }
    }
    public void startSandbox(){
        sandboxCount.incrementAndGet();
        if (ThreadManager.isMainThread()){
            LoadingManager lm = Global.getLoadingManager();
            Runnable r = () -> {
                while (!isDone()){
                    if(!CurrentDataRequests.isEmpty()){
                        while (!CurrentDataRequests.isEmpty()){
                            Pair<DMEReference<?>, CompletableFuture<JsonObject>> next = CurrentDataRequests.poll();
                            DateMutableEntity<?> e = DMRegistry.getEntity(next.getLeft());
                            if (e != null){
                                next.getRight().complete(e.serialize());
                            }
                        }
                    }
                    //TODO pass errors to UI here
                }

            };
            sandbox.startSimulation();
            lm.newStage("Running Sandbox",sandboxCount,r,true);
        } else {
            sandbox.startSimulation();
        }
        Map<DMEReference<?>,JsonObject> cf = sandbox.getToReturn().join();
        future.complete(sandbox.getStatus());
        for (Map.Entry<DMEReference<?>, JsonObject> entry : cf.entrySet()) {
            AbstractMutableManager<?,?,?> manager = DMRegistry.getManager(entry.getKey().getType());
            manager.updateOrLoadEntity(entry.getKey(),entry.getValue());
        }
        if (!ThreadManager.isMainThread()){
            sandbox.getToSave().addAll(cf.keySet());
        }
        doAfter.accept(sandbox.getStatus());
    }
    protected boolean isDone(){
        return (sandbox.getStatus().sandboxComplete());
    }
    private void mainThreadListener(){
        while (!isDone()){

        }
    }
    public void addChild(SandboxHandler<?> child){
        this.child = child;
    }
    public void handleErrors(List<StateError> error){
        for (StateError e : error) {
            CurrentErrors.add(e);
        }
        int totalCompleted = 0;
        while (totalCompleted < error.size()){
            int subComplete = 0;
            for (StateError e : error) {
                if (e.getResponse().isDone()){
                    subComplete++;
                }
            }
            totalCompleted = subComplete;
        }
    }
    public Logger getSandboxLogger(){
        return LOG;
    }
    public CompletableFuture<JsonObject> requestData(DMEReference<?> type){
        CompletableFuture<JsonObject> future = new CompletableFuture<>();
        CurrentDataRequests.add(Pair.of(type,future));
        return future;
    }
    public CompletableFuture<SandboxCode> getEndCode(){
        return future;
    }

    public void addDirtyObject(DateMutableEntity<?> entity){
        sandbox.getToSave().add(entity.getReference());
    }
    ///  Main Method To be Utilized
    @SuppressWarnings("unchecked")
    public static <T extends DateMutableEntity<T>> SandboxHandler<T> StartSandbox(Objective<T> objective, @Nullable LocalDate endDate, @Nullable SandboxHandler<?> parent, @Nullable Consumer<SandboxCode> doAfter){
        Sandbox<T> sandbox;
        final Thread parentThread = parent != null ? parent.sandboxThread : null;
        if (endDate == null){
            sandbox = new Sandbox<>(objective,parentThread);
        } else {
            sandbox = new Sandbox<>(objective, endDate,parentThread);
        }
        SandboxHandler<T> handler = new SandboxHandler<>(sandbox.getThread(),sandbox,doAfter);
        boolean parentFlag = parent != null;
        if (parentFlag){
            parent.addChild(handler);
            handler.startSandbox();
        }
        return handler;
    }
    public static <T extends DateMutableEntity<T>> void StartSandbox(Objective<T> objective, SandboxHandler<?> parent, Consumer<SandboxCode> doAfter){
        StartSandbox(objective,null,parent,doAfter);
    }
    public static <T extends DateMutableEntity<T>> void StartSandbox(Objective<T> objective, LocalDate endDate){
        StartSandbox(objective,endDate,null,null);
    }
    public static <T extends DateMutableEntity<T>> void StartSandbox(Objective<T> objective){
        StartSandbox(objective,null,null);
    }
}

