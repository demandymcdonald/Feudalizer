package com.objects.culture.object;

import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetReference;

public interface ICultureOpinionated {
    default AcceptanceContainer getAcceptanceContainer(TenetReference tenet, boolean includeInfluencers){
        return new AcceptanceContainer(getAcceptanceValue(tenet,includeInfluencers));
    };
//    default Acceptance getAcceptance(TenetReference tenet, boolean includeInfluencers){
//        return getAcceptanceContainer(tenet,includeInfluencers).getAcceptance();
//    };
//    default double getAcceptanceValue(TenetReference tenet, boolean includeInfluencers){
//        return getAcceptanceContainer(tenet,includeInfluencers).value();
//    };
    AcceptanceContainer getAcceptanceContainer(ICultureObject other, boolean factorOtherTolerance);
}
