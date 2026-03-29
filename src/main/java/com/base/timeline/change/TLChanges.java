package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.BiFunction;


public class TLChanges {
    private static final HashMap<Class<? extends TimelineChange<?>>, BiFunction<LocalDate,DMEReference<?>,TimelineChange<?>>> changes = new HashMap<>();
    static {
        registerChangeType(TitleTLChange.Grant.class, TitleTLChange.Grant::fromJson);
        registerChangeType(TitleTLChange.Revoke.class, TitleTLChange.Revoke::fromJson);
        registerChangeType(TitleTLChange.DeJureDrift.class, TitleTLChange.DeJureDrift::fromJson);
        registerChangeType(TitleTLChange.DeJureDriftPassive.class, TitleTLChange.DeJureDriftPassive::fromJson);
    }
    public static <T extends TimelineChange<U>, U extends DateMutableEntity<U>> T getChange(Class<T> json, DMEReference<U> subject, LocalDate date){
        BiFunction<LocalDate,DMEReference<?>, ?> f = changes.get(json);
        return (T) f.apply(date,subject);
    }
    protected static <T extends TimelineChange<R>,R extends DateMutableEntity<R>> void registerChangeType(Class<T> clazz, BiFunction<LocalDate,DMEReference<?>, T> f){
        changes.put(clazz, (BiFunction<LocalDate,DMEReference<?>,TimelineChange<?>>) f);
    }


}
