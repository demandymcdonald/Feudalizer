package com.objects.organization.government;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.AbstractOrganization;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class GoverningEntity<T extends GoverningEntity<T>> extends AbstractOrganization<T> {
    public GoverningEntity(LocalDate created, LocalDate ended,String name, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(GovernmentGroups.GOVERNMENT,name,created, ended, foundingCulture,initialState);
    }
    public GoverningEntity(DMEReference<T> dme) {
        super(GovernmentGroups.GOVERNMENT, dme);
    }
    public GoverningEntity(String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(GovernmentGroups.GOVERNMENT, name, id, created, ended, foundingCulture, initialState);
    }

    public Set<TenetInstance<T>> getRights(){
        return getOpinionByGroup(GovernmentGroups.POPULATION_GROUP_RIGHTS,true,true);
    }

}
