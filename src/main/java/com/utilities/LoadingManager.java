package com.utilities;

import javafx.application.Platform;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static java.lang.Thread.sleep;

public class LoadingManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingManager.class);
    //these string builders will never be mutated
    private static final ThreadGroup LOADER_THREAD = new ThreadGroup("Load_Controlled");
    private static final int CYCLES_TO_KILL = 10000;
    private static final StringBuilder LOADING_COMPLETE = new StringBuilder("Loading Complete");
    private static final StringBuilder STAGE_COMPLETE = new StringBuilder("Stage Complete");
    private AtomicInteger loading = new AtomicInteger(0);
    private AtomicInteger maxLoading = new AtomicInteger(0);
    private AtomicReference<StringBuilder> currentText = new AtomicReference<>(LOADING_COMPLETE);
    private AtomicBoolean lcActive = new AtomicBoolean(false);
    private AtomicInteger isLoading = new AtomicInteger(0);
    private AtomicBoolean isHoldingMain = new AtomicBoolean(false);
    private AtomicInteger debugCounter = new AtomicInteger(0);
    private Map<Type, Deque<LoadTask>> awaiting = buildAwaiting();
    public enum Type{
        WORKER_SHARED(true, true,(t,m)->!m.isHoldingMain.get()),
        WORKER_ISOLATED(false, false,(t,m)->true),
        SANDBOX_INHERIT(false, false,(t,m)->true),
        SANDBOX_ISOLATED(false, false, (t,m)->true),
        MAIN(true, true, (t,m)->!m.isHoldingMain.get());

        private final BiPredicate<LoadTask,LoadingManager> canStartNew;
        private final boolean holdMain;
        private final boolean inheritFromMain;
        Type(boolean holdMain, boolean inheritFromMain, BiPredicate<LoadTask,LoadingManager> canStartNew){
            this.canStartNew = canStartNew;
            this.holdMain = holdMain;
            this.inheritFromMain = inheritFromMain;
        }
        private boolean canStart(LoadTask task, LoadingManager manager){
            return canStartNew.test(task, manager);
        }

    }
    public LoadingManager(){}
    public void addTask(Type type, StringBuilder builder, Consumer<AtomicInteger> updateCount, boolean parallel, Runnable... tasks){
    }

    private static Map<Type, Deque<LoadTask>> buildAwaiting(){
        EnumMap<Type, Deque<LoadTask>> map = new EnumMap<>(Type.class);
        for (Type t : Type.values()){
            map.put(t, new ConcurrentLinkedDeque<>());
        }
        return map;
    }

    @Override
    public String toString() {
        return currentText.get().toString();
    }
    protected record LoadTask(Thread parentThread, String threadname, Type type, AtomicReference<StringBuilder> loadText,
                              Consumer<AtomicInteger> updateProgress, int max, AtomicBoolean forceKill, AtomicInteger progress,
                              AtomicInteger taskCycles, boolean runParallel, Runnable... tasks){

    }
    private class Controller{
        private static Map<Thread,LoadTask> activeTasks = Collections.synchronizedMap(new HashMap<>());
        private void start(){
            Runnable r = () -> {
                int killCounter = 0;
                //can be unsafe since it only is accessible on the controller thread.
                try {
                    lcActive.set(true);
                    while (killCounter < CYCLES_TO_KILL){
                    boolean didSomething = false;
                        didSomething |= handleStarts();
                        didSomething |= handleLifeCycle();
                        if (isLoading.get() <= 0){
                            killCounter += 100;
                            continue;
                        }
                        killCounter = didSomething?0:killCounter + 1;
                    }
                }catch (Exception e){
                    LOGGER.error("Error in load controller: {}",e.getMessage());
                    LOGGER.debug("Stack trace: {}",e.getStackTrace());
                } finally {
                    lcActive.set(false);
                    isLoading.set(0);
                }
            };
            Thread t = new Thread(LOADER_THREAD,r, "Load_Controller");
            t.setDaemon(true);
            t.start();
        }
    }
    private boolean handleStarts(){
        boolean didSomething = false;
        for(Type type : Type.values()){
            Deque<LoadTask> queue = awaiting.get(type);
            if (queue.isEmpty()) continue;
            didSomething = true;
            int current = 0;
            int total = queue.size();
            while (current < total) {
                LoadTask task = queue.poll();
                if(task == null) continue;
                if (type.canStart(task, this)){
                    if(task.runParallel()){
                        runParallel(task);
                    } else {
                        runSequential(task);
                    }
                } else {
                    queue.addLast(task);
                }
                current++;
            }
        }
        return didSomething;
    }
    private void runParallel(LoadTask task){
        for (Runnable r : task.tasks()){
            startTask(task,r);
        }
    }
    private void runSequential(LoadTask task){
        Runnable r = () -> {
            for (Runnable r1 : task.tasks()){
                Thread t = startTask(task,r1);
                try {
                    if (task.type() != Type.MAIN){
                        t.join();
                    } else {
                        while(Controller.activeTasks.containsKey(ThreadManager.getMainThread())){
                            sleep(100);
                        }
                    }
                } catch (InterruptedException ignored) {

                }
            }
        };
        startTask(task,r);
    }
    private Thread startTask(LoadTask task, Runnable r){
       Type type = task.type();
        Runnable t = () -> {
            try {
                isLoading.incrementAndGet();
                if(type.holdMain){
                    triggerHoldMain(task);
                }
                r.run();
                task.updateProgress.accept(task.progress());
            } catch (Exception e) {
                LOGGER.error("Error in load task: {}",e.getMessage());
                LOGGER.debug("Stack trace: {}",e.getStackTrace());
            } finally {
                task.forceKill.set(true);
                cleanup(task);
            }
        };
        if (type == Type.MAIN){
            Platform.runLater(t);
            return ThreadManager.getMainThread();
        }
        Thread parent = null;
        if (type.inheritFromMain){
            parent = task.parentThread();
        }
        Thread rT = ThreadManager.BuildThread(LOADER_THREAD,task.threadname(),t,parent);
        rT.start();
        Controller.activeTasks.put(rT,task);
        return rT;
    }
    private boolean handleLifeCycle(){
        Map<Thread,LoadTask> active = Controller.activeTasks;
        if (active.isEmpty()) return false;
        for (Thread thread :new ArrayList<>(active.keySet())){
            LoadTask task = active.get(thread);
            if (task == null || !active.containsKey(task)) continue;
            if (task.forceKill.get() || task.progress().get() >= task.max){
                thread.interrupt();
                cleanup(task);
                continue;
            }
            if (task.taskCycles.incrementAndGet() >= (CYCLES_TO_KILL * 100)){
                LOGGER.warn("Load task {} has been running for {} cycles. Interrupting.",task.threadname(),task.taskCycles.get());
                thread.interrupt();
                cleanup(task);
            }
        }
        return true;
    }
    private void cleanup(LoadTask task){
        if(task.type().holdMain){
            isHoldingMain.set(false);
            killHoldMain(task);
        }
        isLoading.decrementAndGet();
        Controller.activeTasks.remove(task);
    }
    private void triggerHoldMain(LoadTask task){
        Platform.runLater(() -> {
            try {
                isHoldingMain.set(true);
                //TODO write UI Logic here.
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isHoldingMain.set(false);
            }
        });
    }
    private void killHoldMain(LoadTask task){
        //TODO UI LOGIC HERE
            isHoldingMain.set(false);
    }


}
