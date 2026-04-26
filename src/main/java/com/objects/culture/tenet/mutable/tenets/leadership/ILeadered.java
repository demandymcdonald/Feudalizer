package com.objects.culture.tenet.mutable.tenets.leadership;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.tenet.Acceptance;

import java.util.Optional;

public interface ILeadered<T extends DateMutableEntity<T> & ILeadered<T>> extends ICultureObject {

    Optional<DMEReference<? extends SentientCharacter<?>>> getLeader();
    Acceptance getAcceptanceOf(SentientCharacter<?> character);
}
