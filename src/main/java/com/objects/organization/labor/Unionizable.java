package com.objects.organization.labor;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.reference.DMEReference;

public interface Unionizable<T extends DateMutableEntity<T> & Unionizable<T>> {
    TLSet<UnionLocal> getLocals();
    DMEReference<T> getReference();

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
}
