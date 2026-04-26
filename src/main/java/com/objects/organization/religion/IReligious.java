package com.objects.organization.religion;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.IChanger;
import com.objects.culture.object.ICultureObject;

public interface IReligious<T extends DateMutableEntity<T> & IReligious<T>> extends IChanger<T> {

    DMEReference<Religion> getReligion();
    void internalSetReligion(DMEReference<Religion> religion);
    default void setReligion(DMEReference<Religion> religion){
        getReference().get().getTimeline().addChange(new ReligionChanged<>(getReference(), Global.getDate(),religion));
    };
    default void onLink(){
        DMEReference<Religion> religion = getReligion();
        if(religion != null){
            religion.get().addMember((ICultureObject) getReference().get());
        }
    }
}
