package com.objects.culture.tenet.dynamic.government;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.IChanger;
import com.objects.culture.object.ICultureObject;

public interface IGoverned<T extends DateMutableEntity<T> & IGoverned<T>> extends IChanger<T> {
    DMEReference<? extends GoverningEntity<?>> getGovernment();
    void internalSetGovernment(DMEReference<? extends GoverningEntity<?>> government);
    default void setGovernment(DMEReference<? extends GoverningEntity<?>> government){
        getReference().get().getTimeline().addChange(new GovernmentChanged<>(getReference(), Global.getDate(),government));
    };

    default void onLink(){
        DMEReference<? extends GoverningEntity<?>> government = getGovernment();
        if(government != null){
            government.get().addActiveFollower((ICultureObject) getReference().get());
        }
    }
}
