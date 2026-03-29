package com.base.timeline;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.*;

public class TimelineState<T extends DateMutableEntity<T>>{
    private final DMEReference<T> owner;
    private LocalDate start;
    private LocalDate end;
    private final boolean isBoundary;
    private final HashMap<Long, LocalDate> breadcrumbs;
    private final HashMap<Long, TimelineChange<T>> diffs;

    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, boolean isBoundary, HashMap<Long,LocalDate> breadcrumbs, HashMap<Long,TimelineChange<T>> diffs) {
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.isBoundary = isBoundary;
        this.breadcrumbs = breadcrumbs;
        this.diffs = diffs;
    }
    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, HashMap<Long, LocalDate> breadcrumbs, List<TimelineChange<T>> changeLog) {
        this(owner,start, end, false, breadcrumbs, buildMap(changeLog));
    }
    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, boolean isBoundary, List<TimelineChange<T>> changeLog) {
        this(owner,start, end, isBoundary, new HashMap<>(), buildMap(changeLog));
    }

    public TimelineState(DMEReference<T> owner, LocalDate start, LocalDate end, HashMap<Long, LocalDate> breadcrumbs, HashMap<Long, TimelineChange<T>> changeLog) {
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
    public List<TimelineChange<T>> getDiffs() {
        return filterDiffs(diffs.values());
    }
    private static <T extends DateMutableEntity<T>> List<TimelineChange<T>> filterDiffs(Collection<TimelineChange<T>> changes){
        List<TimelineChange<T>> filtered = new ArrayList<>();
        for (TimelineChange<T> t : changes){
            if (!t.isDeactivated()){
                filtered.add(t);
            }
        }
        return filtered;
    }
    public List<TimelineChange<T>> getAllDiffs() {
        return new ArrayList<>(diffs.values());
    }
    public TimelineChange<T> getDiff(long id) {
        TimelineChange<T> tlc = diffs.get(id);
        if (tlc == null) {
            throw new IllegalArgumentException("No Change with ID: " + id);
        }
        return tlc;
    }
    public List<TimelineChange<T>> getChanges(boolean includeDeactivated) {
        return TimelineHelper.getAllChanges(this.owner.link().getTimeline(), this,includeDeactivated);
    }
    public void insertBreadcrumb(long id, LocalDate date){
        TimelineChange<T> t = TimelineHelper.GetChangeByBreadcrumb(owner.link().getTimeline(),id,date);
        insertBreadcrumb(t);
    }
    public void insertBreadcrumb(TimelineChange<T> change){
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
    public void insertChange(TimelineChange<T> change){
        diffs.put(change.getId(), change);
    }
    public void removeChange(long id){
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
        for (TimelineChange<T> t : diffs.values()) {
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
    private static <T extends DateMutableEntity<T>> List<TimelineChange<T>> deserializeDiffs(JsonArray a) {
        final List<TimelineChange<T>> list = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            list.add(TimelineChange.deserialize(a.get(i).getAsJsonObject()));
        }
        return list;
    }
    private static <T extends DateMutableEntity<T>> HashMap<Long, TimelineChange<T>> buildMap(List<TimelineChange<T>> l) {
        HashMap<Long, TimelineChange<T>> map = new HashMap<>();
        for (TimelineChange<T> change : l) {
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
        List<TimelineChange<T>> diffs = deserializeDiffs(json.getAsJsonArray("diffs"));
        return new TimelineState<T>(owner,start, end, immutable, breadcrumbs, buildMap(diffs));
    }

}

