package com.objects.culture.object;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.objects.culture.Culture;
import com.objects.culture.object.change.MinorCultureChange;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.types.TenetReference;
import com.objects.culture.tenet.types.mutable.Tenet;
import org.apache.commons.lang3.tuple.Pair;

public interface MinorCultureObject<T extends DateMutableEntity<T> & MinorCultureObject<T>> extends ICultureObject {
    DMEReference<Culture> getParent();
    void internalSetParent(DMEReference<Culture> parent);
    DMEReference<T> getReference();
    Pair<Double,Integer> getAddedOpinion(Tenet tenet);
    default void changeCulture(DMEReference<Culture> newCulture){
        getReference().get().getTimeline().addChange(new MinorCultureChange<>(getReference(), Global.getDate(), newCulture));
    }

    @Override
    default AcceptanceContainer getAcceptanceContainer(TenetReference tenet, boolean includeInfluencers){
        double val = getParent().get().getAcceptanceValue(tenet, includeInfluencers);
        Pair<Double,Integer> added = getAddedOpinion(tenet.get());
        int localInf = Math.clamp(added.getRight(),0,100);
        double frac = (val * (1- (double) localInf /100)) + (added.getLeft() * localInf/100);
        return new AcceptanceContainer(frac);
    };
    default IPoliticalCompass getCompass(){
        return getParent().get().getCompass();
    }
}
