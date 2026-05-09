package com.base.thread;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
//Yes, I know the whimsy levels are off the charts with the light aviation theming, I try and be a #serious programmer sometimes,
//but this is making a genuinely dense daemon-running monster less scary so I'm going with it. Sue me.

//One more note: if you check TCC, you'll see that main thread is the only thread that generally doesn't have to ask for a lock before
//reading, since TCC will physically override the UI/User/Render thread if something has a lock, and Managers (which are the only non-UI driven entites that can
//perform non-threaded mutations to objects will always get a lock). Also fun fact, Certain Reference wrappers (Like DMEReference) will do a VFR request for traffic and will
//wait for all locks to clear before returning the object instance.
public interface ILocking<T extends ILocking<T>> extends AutoCloseable{
    AtomicBoolean getClearanceLock();




    default Optional<Class<? super T>> getSuperclass(){
        return Optional.empty();
    };
    //Releases your hold on the lock, ending your clearance and preventing a possible pilot deviation (yes, TCC will catch you if you
    // forget to close a resource/breaking your request into stages by nesting requests/explicitly filing a "Flight Plan" and taking longer than 30 seconds
    // with a hold.
    @Override
    default void close(){
        ThreadTrafficController.connect().endClearance((T)this);
    }

    //Note: requesting clearance will hold the thread (or pull a loading screen if it's on the render thread) until you
    //are granted the lock. Clearance should be used for all non-threadsafe writes where there is a possibility of
    //concurrency issues. 9.8/10 it will be granted instantly. Technically does not ensure unsafe access by a rogue actor.
    //But neither does following ATC and the FAA requires pilots to do that too sooooo.. ¯\_(ツ)_/¯
    // I recommend doing this in a try with resources.
    //Also: nesting clearance requests won't throw, since the TCC tracks the number of clearance requests per thread, and de-increments
    // them accordingly on each clearance end call.
    default T requestClearance() throws PossibleThreadDeviation {
        ThreadTrafficController.connect().requestClearance((T)this);
        return (T)this;
    }
    default T requestSuperclassClearance()throws PossibleThreadDeviation {
        Optional<Class<? super T>> superclass = getSuperclass();
        if(superclass.isPresent()){
            ThreadTrafficController.connect().requestClearance(superclass.get());
            return (T)this;
        }
        return requestClassClearance();
    }
    default T requestClassClearance()throws PossibleThreadDeviation {
        ThreadTrafficController.connect().requestClearance(this.getClass());
        return (T)this;
    }
    //Note: if your thread does not have the permissions to cut ahead (generally restricted to the main/render/some sandbox threads
    //you will be patterned in with the rest of the threads awaiting lock. There may still be a momentary wait while the current lock-holder
    //reaches a safe pause point.
    default T requestPriorityClearance()throws PossibleThreadDeviation {
        ThreadTrafficController.connect().requestPriorityClearance((T)this);
        return (T)this;
    }



}
