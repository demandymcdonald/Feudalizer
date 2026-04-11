package com.base.utilities;

import com.base.timeline.change.TimelineChange;
import com.google.common.base.Suppliers;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ChangeSupplier<T, C extends TimelineChange<?>> {
    private final Supplier<T> supplier;
    private Supplier<T> usableSupplier;
    private Consumer<C> consumer;
    public ChangeSupplier(Supplier<T> supplier, @Nullable Consumer<C> consumer) {
        this.supplier = supplier;
        this.consumer = consumer;
        resetCache();
    }
    private void resetCache(){
        usableSupplier = Suppliers.memoize(supplier::get);
    }

    public T get(){
        return usableSupplier.get();
    }
    public void clear(){
        resetCache();
    }
    public void clear(C change){
        if(consumer != null) {
            consumer.accept(change);
        }
        resetCache();
    }
    public void setConsumer(Consumer<C> consumer){
        this.consumer = consumer;
    }



}
