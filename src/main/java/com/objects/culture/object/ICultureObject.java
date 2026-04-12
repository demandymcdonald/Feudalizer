package com.objects.culture.object;

import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.types.Tenet;

public interface ICultureObject {
    Acceptance getAcceptance(Tenet tenet, boolean includeInfluencers);
    double getAcceptanceValue(Tenet tenet, boolean includeInfluencers);
    IPoliticalCompass getCompass();
}
