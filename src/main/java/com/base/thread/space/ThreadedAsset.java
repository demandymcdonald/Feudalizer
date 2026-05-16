package com.base.thread.space;

import com.Global;
import com.base.DMRegistry;
import com.base.component.ComponentManager;
import com.base.component.IComponent;
import com.base.datemutable.AbstractMutableManager;
import com.base.datemutable.DateMutableEntity;
import com.base.thread.ILocking;
import com.base.thread.PossibleThreadDeviation;
import com.base.thread.ThreadTower;
import com.base.thread.ThreadTracon;
import com.google.common.base.Suppliers;
import com.google.gson.JsonElement;
import com.utilities.caching.CachingSupplier;
import com.utilities.serialization.JsonSerializable;
import com.utilities.serialization.SuperclassSerializable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public abstract class ThreadedAsset<T> implements ILocking<ThreadedAsset<T>> {
    private final ThreadLocal<T> supplier = new ThreadLocal<>();

    public ThreadedAsset(T starting){
        supplier.set(starting);
        ThreadTracon.connect().registerAsset(this);
    }
    private final AtomicBoolean clearanceLock = new AtomicBoolean(false);

    @Override
    public AtomicBoolean getClearanceLock() {
        return clearanceLock;
    }

    public T get() {
        return supplier.get();
    }
    public void set(Object item){
        supplier.set((T) item);
    }
    public void onNewThread(T item){
        supplier.set(item);
    }
    protected abstract T newInstance(boolean copyCurrentValue);
    public synchronized T getNewThreadInstance(boolean copyCurrentValue){
        try (ThreadedAsset<T> ignored = requestClearance()) {
            return newInstance(copyCurrentValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
