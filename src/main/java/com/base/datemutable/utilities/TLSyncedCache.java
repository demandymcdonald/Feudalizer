package com.base.datemutable.utilities;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class TLSyncedCache<K,V> implements TimelineSynced {
    private final Cache<K,V> cache;

    public TLSyncedCache(@Nullable Long maxSize, @Nullable TimeUnit tu, @Nullable Long expireAfterAccess, @Nullable Long expireAfterWrite) {
        CacheBuilder<K,V> cb = (CacheBuilder<K, V>) CacheBuilder.newBuilder();
        if (tu == null) {
            tu = TimeUnit.SECONDS;
        }
        if (maxSize != null) {
            cb.maximumSize(maxSize);
        }
        if (expireAfterAccess != null) {
            cb.expireAfterAccess(expireAfterAccess, tu);
        }
        if (expireAfterWrite != null) {
            cb.expireAfterWrite(expireAfterWrite, tu);
        }
        cache = cb.build();
        registerListener();
    }
    public void put(K key, V value){
        cache.put(key,value);
    }
    public void put(Pair<K,V>... pair){
        for (Pair<K,V> p : pair){
            cache.put(p.getKey(),p.getValue());
        }
    }
    public V get(K key){
        return cache.getIfPresent(key);
    }
    public void invalidate(K key){
        cache.invalidate(key);
    }
    public void invalidateAll(){
        cache.invalidateAll();
    }
    @Override
    public void onLoad(LocalDate date) {
        cache.invalidateAll();
    }

    @Override
    public void onLink(LocalDate date) {

    }
}
