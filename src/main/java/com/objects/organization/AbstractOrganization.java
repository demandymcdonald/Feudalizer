package com.objects.organization;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.mutable.tenets.leadership.ILeadered;
import com.objects.organization.change.OrgParentChange;
import com.objects.organization.labor.LaborUnion;
import com.objects.title.IPrestiged;
import com.objects.title.profession.AbstractJob;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractOrganization<T extends AbstractOrganization<T>> extends DynamicTenet<T> implements ILeadered<T>, IPrestiged {
    private final Map<DMEReference<? extends AbstractJob<?>>, Optional<DMEReference<? extends SentientCharacter<?>>>> employees = new HashMap<>();
    private DMEReference<? extends AbstractOrganization<?>> parent;
    public AbstractOrganization(TenetGroup group, DMEReference<T> dme) {
        super(group, dme);
    }

    public AbstractOrganization(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, created, ended, foundingCulture, initialState);
    }

    public AbstractOrganization(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, id, created, ended, foundingCulture, initialState);
    }

    public Optional<DMEReference<? extends AbstractOrganization<?>>> getParent(){
        return Optional.ofNullable(parent);
    }
    public void setParent(DMEReference<? extends AbstractOrganization<?>> parent){
        getTimeline().addChange(new OrgParentChange<>(getReference(), LocalDate.now(), parent));
    }
    public void internalSetParent(DMEReference<? extends AbstractOrganization<?>> parent){
        this.parent = parent;
    }

    public abstract Set<LaborUnion> getRelevantUnions();

    public final void linkEmployee(AbstractJob<?> job) {
        employees.put(job.getReference(),job.getHolder());
    }
    public abstract Map<ICultureOpinionated,Integer> getStakeholdersFor(ILeadered<?> object);
    protected <O extends AbstractOrganization<O>> Set<O> getMemberOrganizations(Class<O> clazz, boolean includeParents){
        Set<?> set = new HashSet<>(getByType(Type.Organization).stream().filter(clazz::isInstance).collect(Collectors.toSet()));
        Set<O> orgs = (Set<O>) set;
        if (includeParents && getParent().isPresent()) {
                orgs.addAll(getParent().get().get().getMemberOrganizations(clazz, includeParents));
        }
        return orgs;
    }

}
