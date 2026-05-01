package com.objects.organization.religion.scope;

import com.base.component.ComponentReference;
import com.base.reference.DMEReference;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.organization.religion.Religion;
import com.objects.organization.religion.tenets.diety.AbstractDivineEntity;

import java.util.HashSet;
import java.util.Set;

public interface IReligionScopeable {
    AcceptanceContainer getReligionOpinion(DMEReference<Religion> religion);
    default Set<ComponentReference<? extends AbstractDivineEntity<?>>> getRelatedEntities(DMEReference<Religion> religion){
        Set<? extends AbstractDivineEntity<?>> entities = new HashSet<>();

    };
}
