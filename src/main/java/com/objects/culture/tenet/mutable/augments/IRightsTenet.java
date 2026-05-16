package com.objects.culture.tenet.mutable.augments;

import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.organization.government.rights.Right;

import java.util.Set;

public interface IRightsTenet<R extends Right<R>>{

    Set<InterestGroup> isAffected();
   R getRight();

}
