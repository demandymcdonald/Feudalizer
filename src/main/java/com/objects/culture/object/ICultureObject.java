package com.objects.culture.object;

import com.objects.culture.Culture;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.reference.TenetReference;

public interface ICultureObject {
    Culture getCulture();
    Acceptance getAcceptance(TenetReference tenet, boolean includeInfluencers);
    double getAcceptanceValue(TenetReference tenet, boolean includeInfluencers);
    IPoliticalCompass getCompass();
}
