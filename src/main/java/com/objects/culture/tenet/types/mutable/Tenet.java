package com.objects.culture.tenet.types.mutable;

import com.google.common.collect.Multimap;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.factory.TenetCondition;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.Displayable;
import com.utilities.id.UUIDIdentifiable;

import java.util.Map;

public interface Tenet extends Displayable, ICultureObject, UUIDIdentifiable {
    TenetGroup getGroup();
    Multimap<CultureCondition.Key, CultureCondition<?,?,?>> getConditions();
    @Override
    default AcceptanceContainer getAcceptanceContainer(TenetReference tenet, boolean includeInfluencers){
        return new AcceptanceContainer(getAcceptanceValue(tenet, includeInfluencers));
    };


    @Override
    default Acceptance getAcceptance(TenetReference tenet, boolean includeInfluencers) {
        return Acceptance.get((int) Math.round(getAcceptanceValue(tenet,includeInfluencers)));
    }
    default TenetReference getTenetReference() {
        return TenetReference.of(this);
    }
    @Override
    default double getAcceptanceValue(TenetReference tenet, boolean includeInfluencers) {
        if (tenet.equals(getTenetReference())) {
            return Acceptance.CORE_FANATIC.getValue();
        }
        return getCompass().getCompatibilityValue(tenet.get().getCompass());
    }
}
