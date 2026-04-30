package com.base.datemutable.timeline.change;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;

import com.google.gson.JsonObject;
import com.utilities.serialization.SuperclassSerializable;
import org.reactfx.util.TriFunction;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class TLChangeRegistry {
    private static final Map<Class<? extends TimelineChange<?>>, TriFunction<DMEReference<?>,LocalDate,JsonObject, TimelineChange<?>>> changes = Collections.synchronizedMap(new HashMap<>());

    public static <C extends TimelineChange<T>,T extends DateMutableEntity<T>> C deserializeChange(JsonObject json) {
        Class<C> clazz = (Class<C>) SuperclassSerializable.getSSClass(json);
        JsonObject metadata = SuperclassSerializable.getMetadata(json);
        DMEReference<T> owner = DMEReference.deserialize(metadata.get("subject").getAsJsonObject());
        LocalDate date = LocalDate.parse(metadata.get("date").getAsString());
        validateChangeConstructor(clazz);
        return (C) changes.get(clazz).apply(owner, date, json);
    }
    public static <C extends TimelineChange<T>,T extends DateMutableEntity<T>> C deserializeChange(Class<?> clazz, DMEReference<? extends T> reference, LocalDate date) {
        final Class<C> c = (Class<C>) clazz;
        validateChangeConstructor(c);
        return (C) changes.get(clazz).apply(reference, date, null);
    }
    private static <C extends TimelineChange<T>,T extends DateMutableEntity<T>> void validateChangeConstructor(Class<C> clazz){
        synchronized (clazz) {
            if (!changes.containsKey(clazz)) {
                try {
                    Constructor<C> constructor = clazz.getConstructor(JsonObject.class);
                    changes.put(clazz, (o, d, j) -> {
                        try {
                            if (j == null) {
                                throw new RuntimeException("No json provided for saved constructor: " + clazz.getName());
                            }
                            return constructor.newInstance(j);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to instantiate timeline change", e);
                        }
                    });
                } catch (NoSuchMethodException e) {
                    try {
                        Constructor<C> constructor = clazz.getConstructor(DMEReference.class, LocalDate.class);
                        changes.put(clazz, (o, d, j) -> {
                            try {
                                if (o == null) {
                                    throw new RuntimeException("No DMEReference provided for saved constructor: " + clazz.getName());
                                } else if (d == null) {
                                    throw new RuntimeException("No date provided for saved constructor: " + clazz.getName());
                                }
                                C change = constructor.newInstance(o, d);
                                change.deserialize(j);
                                return change;
                            } catch (Exception ee) {
                                throw new RuntimeException("Failed to instantiate timeline change: " + ee.getMessage(), ee);
                            }
                        });
                    } catch (NoSuchMethodException e2) {
                        throw new RuntimeException("No suitable constructor found for timeline change", e2);
                    }
                }
            }
        }
    }

}
