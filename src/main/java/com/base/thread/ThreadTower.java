package com.base.thread;

import com.base.thread.space.ThreadFlight;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.Global.*;

public class ThreadTower {

    private final String tower_code = generateCode();
    private final ThreadGroup group = new ThreadGroup("tower_" + tower_code);
    private final ThreadPoolExecutor service;
    private final Map<ILocking<?>, Deque<ThreadFlight>> separatedAssets = new ConcurrentHashMap<>();
    private final Map<ThreadFlight,Thread> activeFlights = Collections.synchronizedMap(new WeakHashMap<>());
    private final SortedSet<ThreadFlight> activeFlightsQueue = new ConcurrentSkipListSet<>();
            ;
    protected ThreadTower(int coreSize, int poolSize) {
        service = new ThreadPoolExecutor(coreSize, poolSize, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue<>()){
            @Override
            public ThreadFactory getThreadFactory() {
                return new ThreadFactory() {
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        if (r instanceof ThreadFlight tf){
                            Thread t = new Thread(group, r);
                            t.setDaemon(true);
                            t.setName(tower_code + "_" + tf.getCallsign());
                            t.setPriority(Thread.NORM_PRIORITY);
                            activeFlights.put(tf,t);
                            return t;
                        } else {
                            throw new RuntimeException("Unknown Traffic :" + r.toString() + " in the airspace of " + tower_code);
                        }
                    }
                };
            };
        };
    }


    public void openThreadSpace(){

    }
    public void closeThreadSpace(){
        service.shutdown();
    }

    public int getThreadSpaceActivity(){
        return activeFlights.size();
    }
    public int getAllOperations(){
        return activeFlightsQueue.size() + getThreadSpaceActivity();
    }




    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String generateCode(){
        StringBuilder sb = new StringBuilder();
        sb.append("K");
        for (int i = 0; i < 3; i++) {
            int index = RANDOM.nextInt(CHARS.length());
            sb.append(CHARS.charAt(index));
        }
        if (sb.toString().equals("KPDX") || ThreadTracon.connect().getTowers().contains(sb.toString())){
            return generateCode();
        }
        return sb.toString();
    }

    //Acts as a bucket for threads that need to/can access the same shared resources and aren't "in air"










}
