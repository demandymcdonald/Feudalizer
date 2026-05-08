package com.utilities.caching;

import com.google.common.base.Suppliers;

import java.util.function.Supplier;

public class CachingSupplier<T> implements Supplier<T> {
    private final Supplier<T> supplier;
    private Supplier<T> usableSupplier;
    private boolean isMemoized = false;
    public CachingSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
        resetCache();
    }
    private void resetCache(){
        isMemoized = false;
        usableSupplier = Suppliers.memoize(supplier::get);
    }

    public T get(){
        isMemoized = true;
        return usableSupplier.get();
    }
    public void clear(){
        resetCache();
    }
    public void set(T result){
        usableSupplier = Suppliers.memoize(() -> result);
    }


    public boolean isMemoized(){
        return isMemoized;
    }

}
