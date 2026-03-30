package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineHelper;
import com.base.timeline.TimelineState;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public abstract class TimelineMapChange<M extends TimelineMapChange<M,K,V,T>,K,V,T extends DateMutableEntity<T>> extends TimelineChange<T> {
    //Plan:
    //Each relationship change is treated as a single variable from the timeline's perspective, keeping breadcrumb count sane
    //Internally, the keyframe stores: the diff (modifiers added/removed), the calculated total opinion value, the expected diff size, and a pointer to the previous relationship change
    //To reconstruct the full relationship map at any point: jump to the pointer, walk back collecting one entry per UUID, stop when seen-count hits expected-size
    //Full modifier history is reconstructable via scrub-back if needed, but not stored redundantly in every keyframe
    //Forward propagation handles expected-size consistency automatically — adding/removing a relationship mid-timeline just increments/decrements an int during the existing propagation pass
    //The same pattern applies recursively to the diff history within each relationship entry

    private Pair<Long, LocalDate> leapfrog;
    private int leapfrogSize;
    private Map<K,V> changes = new HashMap<>();

    private TimelineMapChange(DMEReference<T> owner, LocalDate date) {
        super(owner, date);
    }

    public final Map<K,V> buildMap(Timeline<T> t){
        Map<K,V> toReturn = new HashMap<>();
        final int finalTotal = leapfrogSize + changes.size();
        final BiConsumer<M,Map<K,V>> consumer = new BiConsumer<>() {
            @Override
            public void accept(M m, Map<K, V> kvMap) {
                Map<K,V> newMap = m.getChanges();
                for (Map.Entry<K,V> e : newMap.entrySet()){
                    if (!kvMap.containsKey(e.getKey())){
                        kvMap.put(e.getKey(),e.getValue());
                    }
                }
            }
        };
        final BiFunction<M,Map<K,V>,Integer> function = (m, r) -> {
            Map<K,V> newMap = m.getChanges();
            int fresh = 0;
            for (Map.Entry<K,V> e : newMap.entrySet()) {
                if (!r.containsKey(e.getKey())){
                    fresh++;
                }
            }
            return fresh;
        };
        return doLeapFrog(t,leapfrog,function,finalTotal,consumer,toReturn,true);
    }
    protected static <M extends TimelineMapChange<M,K,V,T>,K,V,T extends DateMutableEntity<T>,R> R doLeapFrog(
            Timeline<T> t, Pair<Long, LocalDate> firstLF, BiFunction<M,R,Integer> getCurrent,
            final int total, BiConsumer<M,R> makeChange, R result, boolean completeLastCycle){
        int current = 0;
        Pair<Long, LocalDate> currentLeapFrog = firstLF;
        while(current < total){
            TimelineChange<? super T> n = (TimelineChange<? super T>) TimelineHelper.followBreadcrumb(t,currentLeapFrog.getKey(),currentLeapFrog.getValue());
            if (n instanceof TimelineMapChange<?,?,?,?>){
                M m = (M) n;
                current += getCurrent.apply(m,result);
                if (current >= total && !completeLastCycle){
                    break;
                }
                makeChange.accept(m,result);
                currentLeapFrog = m.getLeapFrog();
            } else {
                throw new RuntimeException("Tried to do a leapfrog on a non-map change" + n);
            }
        }
        return result;
    }

    private void addChange(Pair<K,V>... changes){
        int totalNew = 0;
        for (Pair<K,V> p : changes){
            if (!this.changes.containsKey(p.getKey())){
                this.changes.put(p.getKey(),p.getValue());
                totalNew++;
            }
        }
        //TODO finish once I figure out how commits will work XD I think I need a commit method in TLChange.
    }



    public void merge(M combine){

    }
    protected Pair<Long, LocalDate> getLeapFrog(){
        return leapfrog;
    }
    protected Map<K,V> getChanges(){
        return changes;
    }


    @Override
    public final void mainSave(JsonObject o) {
        super.mainSave(o);
    }
    @Override
    public final void mainLoad(JsonObject object) {
        super.mainLoad(object);
    }
    protected abstract JsonObject serializeK(K k);
    protected abstract K deserializeK(JsonObject m);
    protected abstract V deserializeV(JsonObject m);
    protected abstract JsonObject serializeV(V v);
}
