package com.objects.culture.object;

import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetReference;

public interface ICultureOpinionated {
    default AcceptanceContainer getAcceptanceTenet(TenetReference tenet, boolean includeInfluencers){
        return getAcceptanceObject(tenet.get(),includeInfluencers,false);
    };
    AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance);
}
