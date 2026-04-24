package com.objects.title.profession;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.organization.AbstractOrganization;
import com.objects.organization.IOrganizedEntity;
import com.objects.title.Title;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class AbstractJob<T extends AbstractJob<T>> extends Title<T> implements IOrganizedEntity<T> {
    private DMEReference<? extends AbstractOrganization<?>> organization;

    public AbstractJob(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public AbstractJob(DMEReference<T> dme) {
        super(dme);
    }

    public AbstractJob(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    @Override
    public final DMEReference<? extends AbstractOrganization<?>> getOrganization() {
        return organization;
    }

    @Override
    public final void internalSetOrg(DMEReference<? extends AbstractOrganization<?>> newOrganization) {
        organization = newOrganization;
    }

    @Override
    public final void setOrg(DMEReference<? extends AbstractOrganization<?>> newOrganization) {
        IOrganizedEntity.super.setOrg(newOrganization);
    }


    @Override
    public void onLink() {
        super.onLink();
        if (organization != null) {
            organization.get().forceLink();
            organization.get().linkEmployee(this);
        }
    }
}
