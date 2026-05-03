package com.base.datemutable.utilities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class TLSFunction<I,O> extends TLSynced {
    private final Function<I,O> usableSupplier;
    private final Map<I,O> cache = new HashMap<>();
    public TLSFunction(Function<I,O> supplier) {
        super(UUID.randomUUID());
        this.usableSupplier = supplier;
    }
    public O apply(I i){
        if (!cache.containsKey(i)){
            cache.put(i,usableSupplier.apply(i));
        }
        return cache.get(i);
    }
    @Override
    public void onLoad(LocalDate date) {
        cache.clear();
    }

    @Override
    public void onLink(LocalDate date) {

    }
}
