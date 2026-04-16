package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

import static com.Global.TimeDirection.FORWARD;

public abstract class TimelineMapChange<M extends TimelineMapChange<M,K,V,I,T>,K extends Identifiable<I>,V,I,T extends DateMutableEntity<T>> extends TimelineMultiChange<M,K,V,I,T> {

    public TimelineMapChange(DMEReference<? extends T> owner, LocalDate date, Map<K, V> initial) {
        super(owner, date, initial);
    }

    protected TimelineMapChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    public abstract void setRuntimeMap(TimelineMap<K,V,T> map);
    public abstract TimelineMap<K,V,T> getRuntimeMap();

    @Override
    public final void apply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {
        super.apply(entity, currentState);
        setRuntimeMap(getRuntimeMap());
    }


}
