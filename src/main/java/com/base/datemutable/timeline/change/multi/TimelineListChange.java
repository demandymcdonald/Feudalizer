package com.base.datemutable.timeline.change.multi;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.utilities.id.Identifiable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public abstract class TimelineListChange<M extends TimelineListChange<M,T,K,I>,T extends DateMutableEntity<T>,K extends Identifiable<I>,I> extends TimelineCollectionChange<M,T,ArrayList<K>,K,I>{
    protected TimelineListChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }
    public ArrayList<K> newBaseContainer(){
        return new ArrayList<>();
    }
    @Override
    public ArrayList<K> newSafeContainer() {
        return new ArrayList<K>(){
            @Override
            public boolean add(K k) {
                boolean b =  super.add(k);
                TimelineListChange.super.add(k);
                return b;
            }

            @Override
            public K set(int index, K element) {
                K set = super.set(index, element);
                TimelineListChange.super.set(index, element);
                return set;
            }

            @Override
            public boolean addAll(Collection<? extends K> c) {
                boolean b = super.addAll(c);
                TimelineListChange.super.addAll(c);
                return b;
            }

            @Override
            public boolean addAll(int index, Collection<? extends K> c) {
                boolean b = super.addAll(index, c);
                TimelineListChange.super.addAll(c);
                return b;
            }

            @Override
            public void addFirst(K element) {
                TimelineListChange.super.addFirst(element);
                super.addFirst(element);
            }

            @Override
            public void addLast(K element) {
                TimelineListChange.super.addLast(element);
                super.addLast(element);
            }

            @Override
            public K remove(int index) {
                K o = get(index);
                K tr = super.remove(index);
                TimelineListChange.super.remove(WipeType.NO_WIPE,o);
                return tr;
            }

            @Override
            public boolean remove(Object o) {
                K k = getFirst();
                boolean b = super.remove(o);
                if(o.getClass().isAssignableFrom(k.getClass()) || k.getClass().isAssignableFrom(o.getClass())){
                    TimelineListChange.super.remove(WipeType.NO_WIPE,(K) o);
                }
                return b;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                K k = getFirst();
                List<K> toRemove = new ArrayList<>();
                for (Object o : c) {
                    if(o.getClass().isAssignableFrom(k.getClass()) || k.getClass().isAssignableFrom(o.getClass())){
                        toRemove.add((K) o);
                    }
                }
                if (!toRemove.isEmpty()){
                    boolean b = super.removeAll(c);
                    TimelineListChange.super.removeAll(WipeType.NO_WIPE,toRemove);
                    return b;
                }
                return false;
            }

            @Override
            public boolean removeIf(Predicate<? super K> filter) {
                List<K> toRemove = new ArrayList<>(this.stream().filter(filter).toList());
                boolean b = super.removeIf(filter);
                TimelineListChange.super.removeAll(WipeType.NO_WIPE,toRemove);
                return b;
            }

            @Override
            public K removeFirst() {
                K k = super.removeFirst();
                TimelineListChange.super.remove(WipeType.NO_WIPE,k);
                return k;
            }

            @Override
            public K removeLast() {
                K k = super.removeLast();
                TimelineListChange.super.remove(WipeType.NO_WIPE,k);
                return k;
            }
        };
    }
}
