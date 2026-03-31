package com.base.timeline;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TLChangeRegistry;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents the state of a timeline for an entity of type T, where T extends {@code DateMutableEntity<T>}.
 * This class manages the timeline's start and end dates, breadcrumb mappings,
 * associated changes, and its boundary state.
 *
 * @param <T> The type parameter extending {@code DateMutableEntity<T>}.
 */
public class TimelineState<T extends DateMutableEntity<T>>{
    private final DMEReference<T> owner;
    private LocalDate start;
    private LocalDate end;
    private final boolean isBoundary;
    private final HashMap<Long, LocalDate> breadcrumbs;
    private final HashMap<Long, TimelineChange<? super T>> diffs;

    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, boolean isBoundary, HashMap<Long,LocalDate> breadcrumbs, HashMap<Long,TimelineChange<? super T>> diffs) {
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.isBoundary = isBoundary;
        this.breadcrumbs = breadcrumbs;
        this.diffs = diffs;
    }
    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, HashMap<Long, LocalDate> breadcrumbs, List<TimelineChange<? super T>> changeLog) {
        this(owner,start, end, false, breadcrumbs, buildMap(changeLog));
    }
    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, boolean isBoundary, List<TimelineChange<? super T>> changeLog) {
        this(owner,start, end, isBoundary, new HashMap<>(), buildMap(changeLog));
    }

    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, HashMap<Long, LocalDate> breadcrumbs, HashMap<Long, TimelineChange<? super T>> changeLog) {
        this(owner,start, end, false, breadcrumbs, changeLog);
    }



    public HashMap<Long, LocalDate> getBreadcrumbs() {
        return breadcrumbs;
    }
    public boolean isDuring(LocalDate d){
        return d.isAfter(start) && d.isBefore(end);
    }
    public LocalDate getEnd() {
        return end;
    }

    public boolean isBoundary() {
        return isBoundary;
    }

    public DMEReference<T> getOwner() {
        return owner;
    }

    public LocalDate getStart() {
        return start;
    }
    public void setStart(LocalDate start) {
        this.start = start;
    }
    public void setEnd(LocalDate end) {
        this.end = end;
    }
    public List<TimelineChange<? super T>> getChanges() {
        return filterDiffs(diffs.values());
    }
    private static <T extends DateMutableEntity<T>> List<TimelineChange<? super T>> filterDiffs(Collection<TimelineChange<? super T>> changes){
        List<TimelineChange<? super T>> filtered = new ArrayList<>();
        for (TimelineChange<? super T> t : changes){
            if (!t.isDeactivated()){
                filtered.add(t);
            }
        }
        return filtered;
    }
    public List<TimelineChange<? super T>> getAllChanges() {
        return new ArrayList<>(diffs.values());
    }
    public TimelineChange<? super T> getChange(long id) {
        TimelineChange<? super T> tlc = diffs.get(id);
        if (tlc == null) {
            throw new IllegalArgumentException("No Change with ID: " + id);
        }
        return tlc;
    }
    public <C extends TimelineChange<T>> C getChange(Class<C> clazz){
        for (TimelineChange<? super T> tlc : diffs.values()){
            if (clazz.equals(tlc.getClass())){
                return (C) tlc;
            }
        }
        return null;
    }
    public List<TimelineChange<? super T>> getChanges(boolean includeDeactivated) {
        return TimelineHelper.getAllChanges(this.owner.get().getTimeline(), this,includeDeactivated);
    }
    public void insertBreadcrumb(long id, LocalDate date){
        TimelineChange<? super T> t = (TimelineChange<? super T>) TimelineHelper.followBreadcrumb(owner.get().getTimeline(),id,date);
        insertBreadcrumb(t);
    }
    public void insertBreadcrumb(TimelineChange<? super T> change){
        TimelineHelper.insertBreadcrumb(this,change);
    }
    public void forceInsertBC(long id, LocalDate date){
        breadcrumbs.put(id, date);
    }
    public void removeBreadcrumb(long id){
        breadcrumbs.remove(id);
    }
    public LocalDate getBreadcrumbStart(long id){
        return breadcrumbs.get(id);
    }
    public void insertChange(TimelineChange<? super T> change){
        diffs.put(change.getId(), change);
        TimelineHelper.propagateBreadcrumb(owner.get().getTimeline(),change);
    }
    public void removeChange(long id){
        TimelineChange<? super T> t = diffs.get(id);
        if (!t.isDeactivated()){
            t.deactivate(false);
        }
        diffs.remove(id);
    }
    //==== SERIALIZATION ====
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.add("owner", owner.serialize());
        metadata.addProperty("start", start.toString());
        metadata.addProperty("end", end.toString());
        metadata.addProperty("immutable", isBoundary);
        json.add("metadata", metadata);
        json.add("breadcrumbs", serializeBreadcrumbs());
        json.add("diffs", serializeDiffs());
        return json;
    }
    private JsonObject serializeBreadcrumbs() {
        JsonObject json = new JsonObject();
        for (Long key : breadcrumbs.keySet()) {
            json.addProperty(key.toString(), breadcrumbs.get(key).toString());
        }
        return json;
    }
    private JsonArray serializeDiffs() {
        JsonArray json = new JsonArray();
        for (TimelineChange<? super T> t : diffs.values()) {
            json.add(t.serialize());
        }
        return json;
    }
    private static HashMap<Long, LocalDate> deserializeBreadcrumbs(JsonObject json) {
        HashMap<Long, LocalDate> map = new HashMap<>();
        for (String key : json.keySet()) {
            map.put(Long.parseLong(key), LocalDate.parse(json.get(key).getAsString()));
        }
        return map;
    }
    private static <T extends DateMutableEntity<T>> List<TimelineChange<? super T>> deserializeDiffs(JsonArray a) {
        final List<TimelineChange<? super T>> list = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            list.add((TimelineChange<? super T>) TLChangeRegistry.ChangeFactory(a.get(i).getAsJsonObject()));
        }
        return list;
    }
    private static <T extends DateMutableEntity<T>> HashMap<Long, TimelineChange<? super T>> buildMap(List<TimelineChange<? super T>> l) {
        HashMap<Long, TimelineChange<? super T>> map = new HashMap<>();
        for (TimelineChange<? super T> change : l) {
            map.put(change.getId(), change);
        }
        return map;
    }
    public static <T extends DateMutableEntity<T>> TimelineState<T> deserialize(JsonObject json) {
        final JsonObject metadata = json.getAsJsonObject("metadata");
        LocalDate start = LocalDate.parse(metadata.get("start").getAsString());
        LocalDate end = LocalDate.parse(metadata.get("end").getAsString());
        DMEReference<T> owner = DMEReference.deserialize(metadata.get("owner").getAsJsonObject());
        boolean immutable = metadata.get("immutable").getAsBoolean();
        HashMap<Long, LocalDate> breadcrumbs = deserializeBreadcrumbs(json.getAsJsonObject("breadcrumbs"));
        List<TimelineChange<? super T>> diffs = deserializeDiffs(json.getAsJsonArray("diffs"));
        return new TimelineState<T>(owner,start, end, immutable, breadcrumbs, buildMap(diffs));
    }

}

