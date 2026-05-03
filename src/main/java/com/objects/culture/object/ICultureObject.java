package com.objects.culture.object;

import com.base.reference.DMEReference;
import com.objects.culture.Culture;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.TenetReference;

public interface ICultureObject extends ICultureOpinionated{
    enum Type{
        Culture,
        Character,
        Organization,
        Government,
        Land,
        Job,
        Tenet,
    }

    Type getType();
    DMEReference<Culture> getCulture();
    IPoliticalCompass getCompass(DMEReference<Culture> culture);
    @Override
    default AcceptanceContainer getAcceptanceTenet(TenetReference other, boolean factorOtherTolerance){
        DMEReference<Culture> culture = getCulture();
        return new AcceptanceContainer(getCompass(culture).getCompatibilityValue(other.get().getCompass(other.get().getCulture()),factorOtherTolerance));
    };
}
