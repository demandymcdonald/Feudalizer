package com.objects.organization.government;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.objects.culture.Culture;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.augments.IRightsTenet;
import com.objects.organization.AbstractOrganization;
import com.objects.organization.labor.LaborUnion;
import com.objects.succession.held.ICharacterHeld;
import com.objects.succession.rule.RuleEntry;
import com.objects.succession.rule.SuccessionRule;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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
    public Set<TenetInstance<T>> getRightsFor(InterestGroup group){
        return new HashSet<>(getOpinionByGroup(GovernmentGroups.POPULATION_GROUP_RIGHTS,true,true).stream().filter(t -> {
            return t.getTenet() instanceof IRightsTenet<?> irt && irt.isAffected().contains(group);
        }).toList());
    }

    public <SR extends SuccessionRule<SR>,TIT extends DateMutableEntity<TIT> & ICharacterHeld<TIT>> boolean isAllowedSuccessionType(DMEReference<TIT> title, SR rule){

    }
    public <SR extends SuccessionRule<SR>,TIT extends DateMutableEntity<TIT> & ICharacterHeld<TIT>>  RuleEntry<SR> getDefaultRuleFor(DMEReference<TIT> title){

    }
    @Override
    public Set<LaborUnion> getRelevantUnions(){
        return getMemberOrganizations(LaborUnion.class, true);
    }

    @Override
    public final DMEReference<? extends GoverningEntity<?>> getGovernment() {
        return getReference();
    }
    @Override
    public final void setGovernment(DMEReference<? extends GoverningEntity<?>> government) {}

    @Override
    public final void internalSetGovernment(DMEReference<? extends GoverningEntity<?>> government) {}

    @Override
    public final Type getType() {
        return Type.Government;
    }
}
