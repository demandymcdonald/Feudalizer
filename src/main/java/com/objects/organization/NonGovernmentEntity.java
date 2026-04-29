package com.objects.organization;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.mutable.tenets.leadership.ILeadered;
import com.objects.organization.government.IGoverned;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class NonGovernmentEntity<T extends NonGovernmentEntity<T>> extends AbstractOrganization<T> implements IGoverned<T> {
    public NonGovernmentEntity(TenetGroup group, DMEReference<T> dme) {
        super(group, dme);
    }
    public NonGovernmentEntity(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, created, ended, foundingCulture, initialState);
    }
    public NonGovernmentEntity(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, id, created, ended, foundingCulture, initialState);
    }
    @Override
    public final Map<ICultureOpinionated, Integer> getStakeholdersFor(ILeadered<?> object) {
        if(getGovernment().get().hasStakeholdersFor(this, object)) {
            return getGovernment().get().getStakeholdersFor(object);
        } else {
            return getStakeholders(object);
        }
    }
    @Override
    public final Type getType() {
        return Type.Organization;
    }
    public abstract Map<ICultureOpinionated,Integer> getStakeholders(ILeadered<?> object);
}
