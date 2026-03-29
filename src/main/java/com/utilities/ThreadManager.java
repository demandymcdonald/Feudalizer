package com.utilities;

import com.Feudalizer;
import com.GlobalVars;
import com.base.DMRegistry;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.lang.reflect.Type;
import java.util.List;

public class ThreadManager {
    private static final Multimap<Thread,ThreadSpecific> thread_map = Multimaps.synchronizedMultimap(HashMultimap.create());


    public static <T extends ThreadSpecific> T getThreadSpecific(ThreadSpecific.Type type){
        Thread thread = Thread.currentThread();
        return (T) thread_map.get(thread).stream().filter(ts -> ts.specificTypeName().equals(type)).findFirst().orElseThrow();
    }
    public static Thread buildThread(String name, Runnable runnable){
        Thread t = formatThread(name,runnable);
        buildNewInstance(t);
        return t;
    }
    public static Thread buildThreadFromParent(String name, Runnable runnable){
        Thread t = formatThread(name,runnable);
        copyNewInstance(t,Thread.currentThread());
        return t;
    }
    private static Thread formatThread(String name, Runnable runnable){
        Runnable r = () -> {
            doBuild(Thread.currentThread());
            runnable.run();
        };
        Thread t = new Thread(r,name);
        t.setDaemon(true);
        return t;
    }
    private static void doBuild(Thread thread){
        for (ThreadSpecific ts : thread_map.get(thread)) {
            ts.onThreadInit();
        }
    }
    public static void buildNewInstance(Thread thread){
        buildNewInstance(thread,false);
    }
    public static void buildNewInstance(Thread thread, boolean init){
        thread_map.putAll(thread,getFreshTS());
        if(init) doBuild(thread);
    }
    public static void copyNewInstance(Thread thread, Thread copyFrom){
        copyNewInstance(thread,copyFrom,false);
    }
    public static void copyNewInstance(Thread thread, Thread copyFrom, boolean init){
        thread_map.putAll(thread,thread_map.get(copyFrom));
        if(init) doBuild(thread);
    }
    public static void cullInstance(Thread thread){
        thread_map.removeAll(thread);
    }
    private static List<ThreadSpecific> getFreshTS(){
        return List.of(
                new GlobalVars(),
                new DMRegistry()
        );
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

}
