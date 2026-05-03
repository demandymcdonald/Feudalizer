package com.objects.culture.tenet;

import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.error.StateError;
import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.Culture;
import com.objects.culture.IActivatable;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.change.OpinionChange;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.utilities.IDisplayable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface Tenet extends IDisplayable, ICultureObject {
    TenetGroup getGroup();
    default TenetReference getTenetReference() {
        return TenetReference.of(this);
    }
    default Multimap<CultureCondition.Key,CultureCondition<?,?>> getConditionMap(){
        Multimap<CultureCondition.Key, CultureCondition<?, ?>> result = HashMultimap.create();
        Set<CultureCondition<?, ?>> conditions = new HashSet<>();
        getConditions(conditions);
        for (CultureCondition<?, ?> condition : conditions) {
            for (CultureCondition.Key key : condition.getKeys()) {
                result.put(key,condition);
            }
        }
        return result;
    };
    void getConditions(Set<CultureCondition<?, ?>> conditions);


    @Override
    default AcceptanceContainer getAcceptanceTenet(TenetReference tenet, boolean includeInfluencers){
        return new AcceptanceContainer(getAcceptanceValue(tenet,getCulture(), includeInfluencers));
    };

    @Override
    default Type getType(){
        return Type.Tenet;
    };

    default Acceptance getAcceptance(TenetReference tenet,DMEReference<Culture> culture, boolean includeInfluencers) {
        return Acceptance.get((int) Math.round(getAcceptanceValue(tenet,culture,includeInfluencers)));
    }

    default double getAcceptanceValue(TenetReference tenet, DMEReference<Culture> culture, boolean includeInfluencers) {
        if (tenet.equals(getTenetReference())) {
            return Acceptance.CORE_FANATIC.getValue();
        }
        return getCompass(culture).getCompatibilityValue(tenet.get().getCompass(culture),includeInfluencers);
    }
}
