package com.objects.culture.object;

import com.base.reference.DMEReference;
import com.objects.culture.Culture;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetReference;

public interface ICultureObject extends ICultureOpinionated{
    enum Type{
        Culture,
        Character,
        Land,
        Job,
        Tenet,
    }

    Type getType();
    DMEReference<Culture> getCulture();
    IPoliticalCompass getCompass();
    @Override
    default AcceptanceContainer getAcceptanceTenet(ICultureObject other, boolean factorOtherTolerance){
        return new AcceptanceContainer(getCompass().getCompatibilityValue(other.getCompass(),factorOtherTolerance));
    };
}
