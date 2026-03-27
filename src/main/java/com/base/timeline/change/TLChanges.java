package com.base.timeline.change;

import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.BiFunction;


public class TLChanges {
    private static final HashMap<Class<? extends TimelineChange<?>>, BiFunction<LocalDate,JsonObject,TimelineChange<?>>> changes = new HashMap<>();
    static {
        registerChangeType(TitleTLChange.Grant.class, TitleTLChange.Grant::fromJson);
        registerChangeType(TitleTLChange.Revoke.class, TitleTLChange.Revoke::fromJson);
        registerChangeType(TitleTLChange.DeJureDrift.class, TitleTLChange.DeJureDrift::fromJson);
        registerChangeType(TitleTLChange.DeJureDriftPassive.class, TitleTLChange.DeJureDriftPassive::fromJson);
    }
    public static <T extends TimelineChange<?>> T getChange(Class<T> clazz, LocalDate date, JsonObject json){
        BiFunction<LocalDate,JsonObject, T> f = (BiFunction<LocalDate,JsonObject, T>) changes.get(clazz);
        return f.apply(date,json);
    }
    protected static <T extends TimelineChange<?>> void registerChangeType(Class<T> clazz, BiFunction<LocalDate,JsonObject, T> f){
        changes.put(clazz, (BiFunction<LocalDate,JsonObject, TimelineChange<?>>) f);
    }


}
