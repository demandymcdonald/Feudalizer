package com.base.timeline.change.multi;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.utilities.id.Identifiable;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class TimelineCollectionChange<M extends TimelineCollectionChange<M,T,C, K,I>,T extends DateMutableEntity<T>,C extends Collection<K>, K extends Identifiable<I>,I> extends TimelineMultiChange<M, K,Integer,I,T> {
    private final boolean isOrdered;
    protected TimelineCollectionChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
        C temp = newBaseContainer();
        isOrdered = temp instanceof SequencedCollection<?>;
    }
    public abstract C newSafeContainer();
    public abstract C newBaseContainer();
    protected abstract C getRuntimeContainer();
    public final C getActiveChanges() {
        C c = newBaseContainer();
        c.addAll(super.getActive().keySet());
        return c;
    }
    private void buildContainer(){
        C c = getRuntimeContainer();
        c.clear();
        Map<K,Integer> full = getFullMap();
        this.pauseCacheChecks.set(true);
        try {
            if (!isOrdered) {
                c.addAll(full.keySet());
            } else {
                populateOrdered(c, full);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.pauseCacheChecks.set(false);
        }
    }
    public final void add(K... key){
        if (pauseCacheChecks.get()){
            return;
        }
        addChange(buildList(getActiveChanges().size(),key));
        buildContainer();
    }
    public final void set(int index, K key){
        if (pauseCacheChecks.get()){
            return;
        }
        addChange(buildList(index-1,key));
        buildContainer();
    }
    public final void addFirst(K key){
        if (pauseCacheChecks.get()){
            return;
        }
        addChange(buildList(-1,key));
        buildContainer();
    }
    public final void addLast(K key){
        if (pauseCacheChecks.get()){
            return;
        }
        addChange(buildList(getActiveChanges().size(),key));
        buildContainer();
    }
    public final void addAll(Collection<? extends K> key){
        if (pauseCacheChecks.get()){
            return;
        }
        addChange(buildList(getActiveChanges().size(),key.toArray((K[]) new Identifiable[key.size()])));
        buildContainer();
    }
    public final void remove(WipeType type, K... key){
        if (pauseCacheChecks.get()){
            return;
        }
        this.remove(type,key);
        buildContainer();
    }
    public final void removeAll(WipeType type, Collection<? extends K> key){
        if (pauseCacheChecks.get()){
            return;
        }
        this.remove(type,key.toArray((K[]) new Identifiable[key.size()]));
        buildContainer();
    }
    private static <C extends Collection<K>, K extends Identifiable<I>,I> void populateOrdered(C orderedCollection, Map<K,Integer> full){
        orderedCollection.addAll(full.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()));
    }
    private static <C extends Collection<K>, K extends Identifiable<I>,I> Pair<K,Integer>[] buildList(int size,K... keys){
        List<Pair<K,Integer>> toReturn = new ArrayList<>();
        for(K k : keys){
            toReturn.add(Pair.of(k,size++));
        }
        return toReturn.toArray(new Pair[0]);
    }

    //==================================================================================================================


    @Override
    protected final Integer vDeserialize(JsonElement o) {
        return o.getAsInt();
    }

    @Override
    protected final JsonElement vSerialize(Integer integer) {
        return new JsonPrimitive(integer);
    }
}
