package com.objects.culture.tenet.mutable.augments;

import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;

import java.util.Set;

public interface IRightsTenet<R extends MutableTenet & IRightsTenet<R>>{

    Set<InterestGroup> isAffected();


}
