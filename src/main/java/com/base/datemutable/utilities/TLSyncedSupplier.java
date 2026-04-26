package com.base.datemutable.utilities;

import com.google.common.base.Suppliers;

import java.time.LocalDate;
import java.util.function.Supplier;

public class TLSyncedSupplier<T> implements TimelineSynced {
    private final Supplier<T> supplier;
    private Supplier<T> usableSupplier;
    public TLSyncedSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
        resetCache();
        registerListener();
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
    @Override
    public void onLoad(LocalDate date) {
        resetCache();
    }

    @Override
    public void onLink(LocalDate date) {

    }
}
