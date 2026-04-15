package com.objects.culture.object;

import com.objects.culture.Culture;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.reference.TenetReference;

public interface ICultureObject {
    Culture getCulture();
    AcceptanceContainer getAcceptanceContainer(TenetReference tenet, boolean includeInfluencers);
    default Acceptance getAcceptance(TenetReference tenet, boolean includeInfluencers){
        return getAcceptanceContainer(tenet,includeInfluencers).getAcceptance();
    };
    default double getAcceptanceValue(TenetReference tenet, boolean includeInfluencers){
        return getAcceptanceContainer(tenet,includeInfluencers).value();
    };
    default AcceptanceContainer getAcceptanceContainer(ICultureObject other, boolean factorOtherTolerance){
        return new AcceptanceContainer(getCompass().getCompatibilityValue(other.getCompass(),factorOtherTolerance));
    }
    IPoliticalCompass getCompass();
}
