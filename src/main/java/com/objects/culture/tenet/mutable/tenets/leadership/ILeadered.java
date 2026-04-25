package com.objects.culture.tenet.mutable.tenets.leadership;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.government.IGoverned;

import java.util.Map;
import java.util.Optional;

public interface ILeadered<T extends DateMutableEntity<T> & ILeadered<T>> extends ICultureObject {

    Optional<DMEReference<? extends SentientCharacter<?>>> getLeader();
    Map<ICultureOpinionated,Integer> getStakeholders();
    Acceptance getAcceptanceOf(SentientCharacter<?> character);
}
