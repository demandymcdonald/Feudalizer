package com.base.timeline.change.multi.wrapper;

import com.base.timeline.change.multi.MiddlemanMap;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.change.multi.type.WipeType;
import com.utilities.id.Identifiable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TLSet<E extends Identifiable<?>> {
    private final MiddlemanMap<?, E,Boolean,?,?> middleman;
    public TLSet(MiddlemanMap<?, E,Boolean,?,?> map){
        this.middleman = map;
    }
    public int size(){
        return middleman.size();
    };

    public boolean isEmpty(){
        return middleman.size() == 0;
    };

    public <e extends E> boolean contains(e o){
        return middleman.containsKey(o);
    };
    public void add(E e){
        middleman.put(e,true);
    };
    public void add(boolean wipeForward, E e){
        middleman.put(wipeForward,e,true);
    };
    public void add(boolean doSandbox, boolean wipeForward, E e){
        middleman.put(doSandbox,wipeForward,e,true);
    };
    public <e extends E> void remove(WipeType type, e... o){
        middleman.remove(type,o);
    };
    public <e extends E> void remove(WipeType type, e o){
        middleman.remove(type,o);
    };
    public Set<E> getWhere(Predicate<E> predicate){
        return new HashSet<>(middleman.getWhere(predicate).keySet());
    }
    public boolean containsAll(Collection<? extends E> c){
        return c.stream().allMatch(this::contains);
    };
    public void addAll(boolean doSandbox, boolean wipeForward, Collection<? extends E> c){
        middleman.putAll(doSandbox,wipeForward,makePairs(c));
    };
    public void addAll(boolean wipeForward, Collection<? extends E> c){
        middleman.putAll(wipeForward,makePairs(c));
    };
    public void setChanged(boolean doSandbox, boolean doPropagate, ChangeType type, Map<E, Consumer<E>> changes){
        middleman.setChanged(doPropagate,type,makeChange(changes));
    }
    public void setChanged(boolean doPropagate, ChangeType type, Map<E, Consumer<E>> changes){
        middleman.setChanged(doPropagate,type,makeChange(changes));
    }
    public void setChanged(boolean doPropagate, ChangeType type, E entry, Consumer<E> consumner){
        middleman.setChanged(doPropagate,type,makeChange(Map.of(entry,consumner)));
    }
    private static <E extends Identifiable<?>> Map<E,Boolean> makePairs(Collection<? extends E> collection){
        Map<E,Boolean> toReturn = new HashMap<>();
        for (E e : collection) {
            toReturn.put(e,true);
        }
        return toReturn;
    }
    private static <E extends Identifiable<?>> Map<E,BiConsumer<E,Boolean>> makeChange(Map<E,Consumer<E>> changes){
        Map<E,BiConsumer<E,Boolean>> toReturn = new HashMap<>();
        for(Map.Entry<E,Consumer<E>> entry : changes.entrySet()){
            toReturn.put(entry.getKey(),(e,b)->entry.getValue().accept(e));
        }
        return toReturn;
    }
}
