package com.objects.organization;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.mutable.tenets.leadership.ILeadered;
import com.objects.organization.government.IGoverned;
import com.objects.title.profession.AbstractJob;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public abstract class AbstractOrganization<T extends AbstractOrganization<T>> extends DynamicTenet<T> implements ILeadered<T> {
    Map<DMEReference<? extends AbstractJob<?>>, Optional<DMEReference<? extends SentientCharacter<?>>>> employees = new HashMap<>();

    public AbstractOrganization(TenetGroup group, DMEReference<T> dme) {
        super(group, dme);
    }

    public AbstractOrganization(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, created, ended, foundingCulture, initialState);
    }

    public AbstractOrganization(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, id, created, ended, foundingCulture, initialState);
    }
    public final void linkEmployee(AbstractJob<?> job) {
        employees.put(job.getReference(),job.getHolder());
    }
    public abstract Map<ICultureOpinionated,Integer> getStakeholdersFor(ILeadered<?> object);
}
