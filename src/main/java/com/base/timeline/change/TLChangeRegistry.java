package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.change.changes.TitleTLChange;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@SuppressWarnings("unchecked")
public class TLChangeRegistry {
    private static final Map<Class<? extends TimelineChange<?>>, BiFunction<LocalDate,?,?>> changes = Collections.synchronizedMap(new HashMap<>());
    static {
        registerChangeType(TitleTLChange.Grant.class, TitleTLChange.Grant::fromJson);
        registerChangeType(TitleTLChange.Revoke.class, TitleTLChange.Revoke::fromJson);
        registerChangeType(TitleTLChange.DeJureDrift.class, TitleTLChange.DeJureDrift::fromJson);
        registerChangeType(TitleTLChange.DeJureDriftPassive.class, TitleTLChange.DeJureDriftPassive::fromJson);
    }
    protected static <T extends TimelineChange<R>,R extends DateMutableEntity<R>> void registerChangeType(Class<T> clazz,
        BiFunction<LocalDate,DMEReference<R>, T> f){
        changes.put(clazz,f);
    }
    /**
     * Creates and returns an instance of a subclass of {@code TimelineChange} based on the provided JSON object.
     * The JSON object must have been previously serialized using the appropriate save method, or this method will throw an exception.
     *
     * @param changeSave the JSON object containing the data necessary to reconstruct an instance of a subclass of {@code TimelineChange}.
     *                   It must include metadata fields such as "subject", "date", and "type", which are used for deserialization.
     * @param <R>        the type of {@code DateMutableEntity} associated with the {@code TimelineChange}.
     * @return an instance of type {@code T}, deserialized from the provided JSON object.
     * @throws RuntimeException if the class specified in the metadata cannot be found or if deserialization fails.
     */
    public static <R extends DateMutableEntity<R>> TimelineChange<? super R> ChangeFactory(JsonObject changeSave){
        //NOTE: This WILL throw if you try and feed in anything that wasn't saved by the SuperclassSerializable's main save method (serialize())
        JsonObject metadata = changeSave.getAsJsonObject("metadata");
        DMEReference<R> subject = DMEReference.deserialize(metadata.get("subject").getAsJsonObject());
        LocalDate date = LocalDate.parse(metadata.get("date").getAsString());
        try {
            Class<TimelineChange<? super R>> clazz = (Class<TimelineChange<? super R>>) Class.forName(metadata.get("type").getAsString());
            BiFunction<LocalDate, DMEReference<?>,TimelineChange<? super R>> f = (BiFunction<LocalDate, DMEReference<?>, TimelineChange<? super R>>) changes.get(clazz);
            TimelineChange<? super R> obj = f.apply(date,subject);
            obj.deserialize(changeSave);
            return obj;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Change type not found: " + metadata.get("type").getAsString(), e);
        }
    }
}
