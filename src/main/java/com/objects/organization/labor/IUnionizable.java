package com.objects.organization.labor;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.reference.DMEReference;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.government.IGoverned;

public interface IUnionizable<T extends DateMutableEntity<T> & IUnionizable<T> & IGoverned<T>> {
    TLSet<UnionLocal> getLocals();
    DMEReference<T> getReference();
    void internalSetLocals(TLSet<UnionLocal> locals);
    default void addLocal(UnionLocal local){
        getLocals().add(local);
    };
    default void doDateChange(){
        for(UnionLocal local : getLocals().asSet()){
            local.onLoad();
        }
    }
    default void onLink(){
        for(UnionLocal local : getLocals().asSet()){
            local.link(this);
        }
    }
    default boolean isUnionized(InterestGroup group){}

}
