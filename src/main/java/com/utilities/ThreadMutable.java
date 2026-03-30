package com.utilities;

public interface ThreadMutable<T extends ThreadMutable<T,S>,S>{

    //I highly recommend calling this whenever you
    default void ts_init(){
        if (!ThreadManager.hasKey(uniqueKey())){
            ThreadManager.registerThreadMutable(uniqueKey(),this);
        } else if (!ThreadManager.getThreadMutable(uniqueKey()).equals(this.getClass())) {
            throw new RuntimeException("uniqueKey has been used twice: " + ThreadManager.getThreadMutable(uniqueKey()) + ".");
        }
    }


    String uniqueKey();
    void onThreadInit(boolean shared);
    S share();
    void receiveShared(S shared);
    default void afterThreadInit(boolean shared){}
    default void onShutdown(boolean shared){}
}
