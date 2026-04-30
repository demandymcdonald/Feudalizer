package com.objects.organization.government;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.IChanger;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.object.ICultureObject;

import java.util.Optional;

public interface IGoverned<T extends DateMutableEntity<T> & IGoverned<T>> extends IChanger<T> {
    DMEReference<? extends GoverningEntity<?>> getGovernment();
    void internalSetGovernment(DMEReference<? extends GoverningEntity<?>> government);
    default void setGovernment(DMEReference<? extends GoverningEntity<?>> government){
        getReference().get().getTimeline().addChange(new GovernmentChanged<>(getReference(), Global.getDate(),government));
    };
    default Optional<DMEReference<? extends SentientCharacter<?>>> getGovLeader(){
        return getGovernment().get().getHolder();
    }
    default void onLink(){
        DMEReference<? extends GoverningEntity<?>> government = getGovernment();
        if(government != null){
            government.get().addMember((ICultureObject) getReference().get());
        }
    }
}
