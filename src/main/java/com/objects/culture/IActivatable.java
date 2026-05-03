package com.objects.culture;

import com.Feudalizer;
import com.base.datemutable.DateMutableEntity;
import com.google.common.cache.Cache;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.flag.FlagInstance;
import com.objects.culture.tenet.flag.FlagTenet;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.instance.TenetInstance;

import java.util.HashSet;
import java.util.Set;

public interface IActivatable<T extends DateMutableEntity<T> & IActivatable<T>> extends CultureObject<T> {
    Cache<Class<? extends Tenet>,Set<TenetInstance<T>>> getActiveLookup();
    TenetInstance<T> getInstance(TenetReference t);
    default Set<TenetInstance<T>> getActiveTenets(){
        return getOpinions().getWhere(TenetInstance::isActive);
    }
    default Set<TenetInstance<T>> getFlagTenets(boolean activeOnly){
        return getOpinions().getWhere(t -> t.getTenet().get() instanceof FlagTenet && (activeOnly ? t.isActive() : true));
    }
    default Set<TenetInstance<T>> getActiveTenetByClass(Class<? extends Tenet> clazz){
        Set<TenetInstance<T>> set = getActiveLookup().getIfPresent(clazz);
        if(set == null){
            Set<TenetInstance<T>> map = getActiveTenets();
            Set<TenetInstance<T>> toReturn = new HashSet<>();
            map.forEach((tr) -> {
                if(tr.getTenet().getTenetClass().isInstance(clazz)){toReturn.add((tr));}
            });
            getActiveLookup().put(clazz, toReturn);
            return toReturn;
        } else {
            return new HashSet<>(set);
        }
    };
    default Set<TenetInstance<T>> getActiveTenetByGroup(TenetGroup group, boolean includeDescendants){
        final Set<TenetInstance<T>> toReturn = new HashSet<>();
        getActiveTenets().forEach((at -> {
            if(CultureObject.matchesGroup(at, group, includeDescendants)){
                toReturn.add(at);
            }
        }));
        return toReturn;
    };
    default void addActiveTenet(Tenet tenet){
        TenetInstance<T> ti = this.getInstance(tenet.getTenetReference());
        if(ti == null){
            getOpinions().add(new TenetInstance<>(tenet.getTenetReference(), this.getReference(), Acceptance.getMid(Acceptance.CORE),true));
        } else if(!ti.isActive()){
            ti.setActive();
        }
    }
    Set<FlagInstance> getFlags();
    Set<FlagInstance> internalGetFlags();
    void internalSetFlags(Set<FlagInstance> flags);
    default boolean hasFlag(FlagInstance flag){
        return getFlags().contains(flag);
    }
}
