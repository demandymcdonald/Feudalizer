package com.base.timeline.state;

import com.base.DateMutableEntity;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineObject;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TLChangeRegistry;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

/**
 * Represents the state of a timeline for an entity of type T, where T extends {@code DateMutableEntity<T>}.
 * This class manages the timeline's start and end dates, breadcrumb mappings,
 * associated changes, and its boundary state.
 *
 * @param <T> The type parameter extending {@code DateMutableEntity<T>}.
 */
public class TimelineState<T extends DateMutableEntity<T>> extends TimelineObject<T> {
    private final Timeline<T> timeline;
    private LocalDate start;
    private LocalDate end;
    private final boolean isBoundary;
    private final List<ChangeID> breadcrumbs;
    private final Map<ChangeID, TimelineChange<? super T>> currentChanges;

    public TimelineState(Timeline<T> timeline, LocalDate start, LocalDate end, boolean isBoundary, List<ChangeID> breadcrumbs, Map<ChangeID,TimelineChange<? super T>> currentChanges) {
        super(timeline.getOwner());
        this.timeline = timeline;
        this.start = start;
        this.end = end;
        this.isBoundary = isBoundary;
        this.breadcrumbs = breadcrumbs;
        this.currentChanges = currentChanges;
    }
    public TimelineState(Timeline<T> timeline,  LocalDate start, LocalDate end, List<ChangeID> breadcrumbs, List<TimelineChange<? super T>> currentChanges) {
        this(timeline,start, end, false, breadcrumbs, buildMap(currentChanges));
    }
    public TimelineState(Timeline<T> timeline, LocalDate start, LocalDate end, boolean isBoundary, List<TimelineChange<? super T>> currentChanges) {
        this(timeline,start, end, isBoundary, new ArrayList<>(), buildMap(currentChanges));
    }

    public TimelineState(Timeline<T> owner,  LocalDate start, LocalDate end, List<ChangeID> breadcrumbs, Map<ChangeID, TimelineChange<? super T>>  currentChanges) {
        this(owner,start, end, false, breadcrumbs, currentChanges);
    }



    public List<ChangeID> getBreadcrumbs() {
        return breadcrumbs;
    }
    public boolean isDuring(LocalDate d){
        return d.isAfter(start) && d.isBefore(end);
    }
    @Override
    public LocalDate getEnd() {
        return end;
    }

    public boolean isBoundary() {
        return isBoundary;
    }
    public Timeline<T> getTimeline() {
        return timeline;
    }
    @Override
    public LocalDate getStart() {
        return start;
    }
    public void setStart(LocalDate start) {
        this.start = start;
        for (TimelineChange<? super T> change : currentChanges.values()) {
            change.moveChange(start,end);
            moveBreadcrumbStart(timeline,change);
        }
    }
    public void setEnd(LocalDate end) {
        this.end = end;
    }
    public List<TimelineChange<? super T>> getChanges() {
        return filterDiffs(currentChanges.values());
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
    public List<TimelineChange<? super T>> getAllCurrentChanges(boolean includeDeactivated) {
        if (includeDeactivated){
            return new ArrayList<>(currentChanges.values());
        } else {
            return getActiveOnly();
        }
    }
    public List<TimelineChange<? super T>> getAllChanges(boolean includeDeactivated) {
        final List<TimelineChange<? super T>> all = new ArrayList<>(currentChanges.values());
        for (ChangeID i : breadcrumbs){
            all.add(followBreadcrumb(timeline,i));
        }
        if (includeDeactivated){
            return all;
        } else {
            List<TimelineChange<? super T>> active = new ArrayList<>();
            for (TimelineChange<? super T> t : all){
                if (!t.isDeactivated()){
                    active.add(t);
                }
            }
            return active;
        }
    }
    public List<TimelineChange<? super T>> getChangesWhere(boolean all, boolean active, Predicate<TimelineChange<? super T>> predicate) {
        List<TimelineChange<? super T>> filtered = new ArrayList<>();
        if (all){
            filtered.addAll(getAllChanges(active));
        } else {
            filtered.addAll(getAllCurrentChanges(active));
        }
        return filtered.stream().filter(predicate).toList();
    }
    private List<TimelineChange<? super T>> getActiveOnly(){
        List<TimelineChange<? super T>> active = new ArrayList<>();
        for (TimelineChange<? super T> t : currentChanges.values()){
            if (!t.isDeactivated()){
                active.add(t);
            }
        }
        return active;
    }


    public TimelineChange<? super T> getChange(long fullID) {
        for (ChangeID t : currentChanges.keySet()) {
            if (t.getFullID() == (fullID)){
                return currentChanges.get(t);
            }
        }
        return null;
    }
    public TimelineChange<? super T> getChange(ChangeID id){
        return currentChanges.get(id);
    }

    public <C extends TimelineChange<? super T>> TimelineChange<? super T> getChange(Class<C> clazz){
        long classID = ChangeID.buildChangeClassID(clazz.getName());
        return getChange(classID);
    }
    public TimelineChange<? super T> getChangeByClassID(long id){
        for(ChangeID t : currentChanges.keySet()){
            if (t.getClassID() == id){
                return currentChanges.get(t);
            }
        }
        return null;
    }

    public void insertBreadcrumb(ChangeID id){
        breadcrumbs.add(id);
    }
//    public void forceInsertBC(long id, LocalDate date){
//        breadcrumbs.put(id, date);
//    }
    public void removeBreadcrumb(ChangeID id){
        breadcrumbs.remove(id);
    }

    public void insertChange(TimelineChange<? super T> change){
        currentChanges.put(change.getID(), change);
        propagateBreadcrumbs(timeline,change);
    }
    public void removeChange(ChangeID id){
        currentChanges.remove(id);
    }

    public void deactivateChange(ChangeID id, boolean sandbox){
        TimelineChange<? super T> c = getChange(id);
        if (c != null){
            c.deactivate(sandbox);
            cleanBreadcrumbs(timeline,c);
        }
    }

    public List<ChangeID> buildFullBreadcrumbList(){
        return buildFullBreadcrumbList(this);
    }

    //==== SERIALIZATION ====
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("start", start.toString());
        metadata.addProperty("end", end.toString());
        metadata.addProperty("immutable", isBoundary);
        json.add("metadata", metadata);
        json.add("breadcrumbs", serializeBreadcrumbs());
        json.add("diffs", serializeDiffs());
        return json;
    }
    private JsonArray serializeBreadcrumbs() {
        JsonArray json = new JsonArray();
        for (ChangeID key : breadcrumbs) {
            json.add(key.toJson());
        }
        return json;
    }
    private JsonArray serializeDiffs() {
        JsonArray json = new JsonArray();
        for (TimelineChange<? super T> t : currentChanges.values()) {
            json.add(t.serialize());
        }
        return json;
    }
    private static List<ChangeID> deserializeBreadcrumbs(JsonArray json) {
        List<ChangeID> map = new ArrayList<>();
        for (int i = 0; i < json.size(); i++) {
            map.add(ChangeID.fronJson(json.get(i).getAsJsonObject()));
        }
        return map;
    }
    private static <T extends DateMutableEntity<T>> List<TimelineChange<? super T>> deserializeCurrentChanges(JsonArray a) {
        final List<TimelineChange<? super T>> list = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            list.add((TimelineChange<? super T>) TLChangeRegistry.ChangeFactory(a.get(i).getAsJsonObject()));
        }
        return list;
    }
    private static <T extends DateMutableEntity<T>> Map<ChangeID, TimelineChange<? super T>> buildMap(List<TimelineChange<? super T>> l) {
        Map<ChangeID, TimelineChange<? super T>> map = new HashMap<>();
        for (TimelineChange<? super T> change : l) {
            map.put(change.getID(), change);
        }
        return map;
    }
    public static <T extends DateMutableEntity<T>> TimelineState<T> deserialize(Timeline<T> timeline, JsonObject json) {
        final JsonObject metadata = json.getAsJsonObject("metadata");
        LocalDate start = LocalDate.parse(metadata.get("start").getAsString());
        LocalDate end = LocalDate.parse(metadata.get("end").getAsString());
        boolean immutable = metadata.get("immutable").getAsBoolean();
        List<ChangeID> breadcrumbs = deserializeBreadcrumbs(json.getAsJsonArray("breadcrumbs"));
        List<TimelineChange<? super T>> diffs = deserializeCurrentChanges(json.getAsJsonArray("diffs"));
        return new TimelineState<T>(timeline,start, end, immutable, breadcrumbs, buildMap(diffs));
    }
}

