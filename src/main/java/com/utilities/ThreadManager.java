package com.utilities;

import com.Feudalizer;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadManager {
    //private static final Multimap<Thread,ThreadMutable<?>> thread_map = Multimaps.synchronizedMultimap(HashMultimap.create());
    private static final Map<String,ThreadMutable<?,?>> thread_mutables = Collections.synchronizedMap(new HashMap<>());


    public static Thread BuildThread(ThreadGroup group, String name, Runnable task, @Nullable Thread parent){
        Runnable wrapper;
        String full_name;
        if (parent != null){
            final Map<ThreadMutable<?,?>,Object> to_share = new HashMap<>();
            for (ThreadMutable<?,?> tm : thread_mutables.values()){
                to_share.put(tm,tm.share());
            }
            wrapper= () -> {
                for (Map.Entry<? extends ThreadMutable<?,?>,Object> entry : to_share.entrySet()){
                    ThreadMutable<?,?> tm = entry.getKey();
                    tm.onThreadInit(true);
                    putVariable(tm,entry.getValue());
                    tm.afterThreadInit(true);
                }
                task.run();
                for (ThreadMutable<?,?> tm : thread_mutables.values()){
                    tm.onShutdown(true);
                }
            };
            full_name = "tm_shared_"+ parent.getName() + "::" +  name+ ";" ;
        } else {
            wrapper= () -> {
                for (ThreadMutable<?,?> tm : thread_mutables.values()){
                    tm.onThreadInit(false);
                    tm.afterThreadInit(false);
                }
                task.run();
                for (ThreadMutable<?,?> tm : thread_mutables.values()){
                    tm.onShutdown(false);
                }
            };
            full_name = "tm_isolated_"+ name;
        }
        Thread thread_to_return = new Thread(group,wrapper,full_name);
        thread_to_return.setDaemon(true);
        return thread_to_return;
    }
    public static boolean hasKey(String key){
        return thread_mutables.containsKey(key);
    }

    protected static <TM extends ThreadMutable<TM,S>,S> void putVariable(ThreadMutable<?,?> key ,Object o){
        TM tm = (TM) key;
        tm.receiveShared((S) o);
    }


    public static <t extends ThreadMutable<?,?>> void registerThreadMutable(String key, ThreadMutable<?,?> type){
        thread_mutables.put(key,  type);
    }
    public static <t extends ThreadMutable<?,?>> t getThreadMutable(String key){
        return (t) thread_mutables.get(key);
    }
//    public static Thread createNewThread(String name, Runnable runnable, boolean pullFromParent){
//        Thread thread = new Thread(runnable,name);
//        if(pullFromParent){
//            copyNewInstance(thread,Thread.currentThread());
//        } else {
//            buildNewInstance(thread);
//        }
//        return thread;
//    }
    public static boolean isMainThread(){
        //That global variable is pulled at startup, so it should ALWAYS be the JavaFX thread name
        return Thread.currentThread().equals(Feudalizer.MAIN_THREAD);
    }
    public static Thread getMainThread(){
        return Feudalizer.MAIN_THREAD;
    }

}
