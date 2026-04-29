package com.objects.title.profession;

import com.base.datemutable.timeline.change.TimelineChange;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.objects.CauseOfEnd;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.interest.groups.ClassCaste;
import com.objects.organization.AbstractOrganization;
import com.objects.organization.IOrganizedEntity;
import com.objects.title.Title;
import com.objects.title.condition.CanHoldCondition;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public class Job extends Title<Job> implements IOrganizedEntity<Job> {
    private DMEReference<? extends AbstractOrganization<?>> organization;
    private JobType type;

    public Job(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Job, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Job(LocalDate created, LocalDate ended, List<ChangeSupplier<Job, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Job(DMEReference<Job> dme) {
        super(dme);
    }

    public ClassCaste getPrimaryClass(){
        return type.getPrimaryClass();
    };
    public Set<ClassCaste> getSecondaryClasses() {
        return new HashSet<>(Arrays.stream(type.getSecondaryClass()).toList());
    }

    @Override
    public final DMEReference<? extends AbstractOrganization<?>> getOrganization() {
        return organization;
    }

    @Override
    public final void internalSetOrg(DMEReference<? extends AbstractOrganization<?>> newOrganization) {
        organization = newOrganization;
    }
    public JobType getJobType() {
        return type;
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
            for(ClassCaste c : getSecondaryClasses()) {
                sub += base * (c.getPrestigeMultiplier()/count);
            }
            sub /= 2;
        } else {
            sub = main/2; //To offset the increase provided by sub groups
        }
        return (int) Math.round((main + sub));
    }
    @Override
    protected void conditionsCanHold(List<CanHoldCondition<? super Job>> list) {
        list.addAll(type.getCanHoldConditions());
    }
    @Override
    protected void conditionsCanInherit(List<CanHoldCondition<? super Job>> list) {
        list.addAll(type.getCanInheritConditions());
    }
    @Override
    public String getTitleName() {
        return "";
    }

    @Override
    public void onLink() {
        super.onLink();
        if (organization != null) {
            organization.get().forceLink();
            organization.get().linkEmployee(this);
        }
    }

    @Override
    public TimelineChange<Job> getBirthChange(DMEReference<Job> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Job> getDeathChange(DMEReference<Job> dme, LocalDate date, CauseOfEnd<? super Job> cOd) {
        return null;
    }

    @Override
    public CauseOfEnd<? super Job> defaultDeathCause() {
        return null;
    }


    @Override
    public DMEReference<Culture> getCulture() {
        return organization.get().getCulture();
    }

    @Override
    public IPoliticalCompass getCompass() {
        return organization.get().getCompass();
    }

    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return null;
    }
}
