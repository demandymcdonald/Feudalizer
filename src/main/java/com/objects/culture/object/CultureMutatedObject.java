package com.objects.culture.object;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.types.MutableTenet;

public interface CultureMutatedObject {

    PoliticalCompass getBaseCompass();
    Acceptance getAcceptance(MutableTenet t, CultureObject<?,?,?> c);
}
