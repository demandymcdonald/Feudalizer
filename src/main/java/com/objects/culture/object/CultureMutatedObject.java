package com.objects.culture.object;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.types.Tenet;

public interface CultureMutatedObject {

    PoliticalCompass getBaseCompass();
    Acceptance getAcceptance(Tenet t, CultureObject<?,?,?> c);
}
