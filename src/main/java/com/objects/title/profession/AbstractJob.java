package com.objects.title.profession;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.objects.culture.tenet.interest.InterestGroups;
import com.objects.organization.AbstractOrganization;
import com.objects.organization.IOrganizedEntity;
import com.objects.title.Title;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public abstract InterestGroups.ClassCaste getPrimaryClass();
    public Set<InterestGroups.ClassCaste> getSecondaryClasses() {
        return new HashSet<>();
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
    protected int basePrestige() {
        int base = organization.get().getPrestige();
        double main = base * getPrimaryClass().getPrestigeMultiplier();
        double sub = 0;
        int count = getSecondaryClasses().size();
        if (count > 0) {
            for(InterestGroups.ClassCaste c : getSecondaryClasses()) {
                sub += base * (c.getPrestigeMultiplier()/count);
            }
            sub /= 2;
        } else {
            sub = main/2; //To offset the increase provided by sub groups
        }
        return (int) Math.round((main + sub));
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
