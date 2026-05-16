package com.objects.culture.tenet;

import com.base.datemutable.timeline.error.StateError;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;

import com.google.common.collect.ImmutableSet;
import com.objects.culture.IActivatable;
import com.objects.culture.object.change.OpinionChange;
import com.objects.culture.tenet.flag.FlagInstance;
import com.objects.culture.tenet.flag.FlagTenet;
import com.objects.culture.tenet.instance.TenetInstance;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public interface SubTenet extends Tenet{
    default Optional<StateError> canBeActive(DMEReference<? extends IActivatable<?>> holder,TenetReference reference, OpinionChange<? extends IActivatable<?>> existing){
        ImmutableSet<TenetInstance<?>> activeTenets = ImmutableSet.copyOf(new HashSet<>(holder.get().getActiveTenets()));
        Set<TenetInstance<?>> influencers = new HashSet<>(holder.get().getOpinions().asSet());
        final Optional<StateError> reference1 = getRequiredTenet(holder, reference, existing, influencers);
        if (reference1.isPresent()) return reference1;
        final Optional<StateError> reference2 = getIncompatTenet(reference, existing, activeTenets);
        if (reference2.isPresent()) return reference2;
        final Optional<StateError> reference3 = getRequiredFlag(holder, reference, existing);
        if (reference3.isPresent()) return reference3;
        for(TenetInstance<?> ti : activeTenets){
            if(!this.isTenetCompatible(ti)){
                return Optional.of(new StateError("tenet_incompatible", new ComplexReference("Tenet {} is incompatible with {}.", reference, ti.getTenet()), existing).addEndSave().addEndCancel().addIgnore());
            }
        }
        return getIncompatFlags(holder, reference, existing);
    }

    private @NonNull Optional<StateError> getIncompatFlags(DMEReference<? extends IActivatable<?>> holder, TenetReference reference, OpinionChange<? extends IActivatable<?>> existing) {
        Set<FlagTenet> incompatFlags = new HashSet<>();
        incompatibleFlags(incompatFlags);
        if(!incompatFlags.isEmpty()){
            for(FlagTenet f : incompatFlags){
                if(holder.get().getFlags().stream().anyMatch((fi) -> fi.getBase().get().equals(f))){
                    return Optional.of(new StateError("tenet_incompatible_flag", new ComplexReference("Tenet {} is incompatible with flag {} to be added to {}.", reference, f, holder), existing).addEndSave().addEndCancel().addIgnore());
                }
            }
        }
        return Optional.empty();
    }

    private  Optional<StateError> getRequiredFlag(DMEReference<? extends IActivatable<?>> holder, TenetReference reference, OpinionChange<? extends IActivatable<?>> existing) {
        ImmutableSet<FlagTenet> flags = ImmutableSet.copyOf(holder.get().getFlags());
        Set<FlagTenet> reqFlags = new HashSet<>();
        requiredFlags(reqFlags);
        if(!reqFlags.isEmpty()){
            for(FlagTenet f : reqFlags){
                if(flags.stream().noneMatch((fi) -> fi.getBase().get().equals(f))){
                    return Optional.of(new StateError("tenet_missing_flag", new ComplexReference("Tenet {} is missing flag {} to be added to {}.", reference, f, holder), existing).addEndSave().addEndCancel().addIgnore());
                }
            }
        }
        return Optional.empty();
    }

    private Optional<StateError> getIncompatTenet(TenetReference reference, OpinionChange<? extends IActivatable<?>> existing, ImmutableSet<TenetInstance<?>> activeTenets) {
        Set<TenetReference> incompat = new HashSet<>();
        incompatibleTenets(incompat);
        if(!incompat.isEmpty()){
            for(TenetReference t : incompat){
                if(activeTenets.stream().anyMatch(t::equals)){
                    return Optional.of(new StateError("tenet_incompatible", new ComplexReference("Tenet {} is incompatible with {}.", reference, t), existing).addEndSave().addEndCancel().addIgnore());
                }
            }
        }
        return Optional.empty();
    }

    private Optional<StateError> getRequiredTenet(DMEReference<? extends IActivatable<?>> holder, TenetReference reference, OpinionChange<? extends IActivatable<?>> existing, Set<TenetInstance<?>> influencers) {
        Map<TenetReference,Acceptance> prt = new HashMap<>();
        prerequisiteTenets(prt);
        if (!prt.isEmpty()) {
            for (Map.Entry<TenetReference, Acceptance> entry : prt.entrySet()) {
                TenetReference key = entry.getKey();
                Acceptance value = entry.getValue();
                if(influencers.stream().noneMatch(t -> t.getTenet().get().equals(key))){
                    return Optional.of(new StateError("tenet_missing_prereq", new ComplexReference("Tenet {} is missing prerequisite {} to be added to {}.", reference, key, holder), existing).addEndSave().addEndCancel().addIgnore());
                } else if (
                    influencers.stream().anyMatch(t -> t.getTenet().get().equals(key) && !threshold(value,t.getAcceptance()))
                ){
                    return Optional.of(new StateError("tenet_missing_acceptance", new ComplexReference("Tenet {} does not have the acceptance level of {} to be added to {}.", reference, value, holder), existing).addEndSave().addEndCancel().addIgnore());
                }
            }
        }
        return Optional.empty();
    }


    private static boolean threshold(Acceptance base, Acceptance against){
        if(base.getValue() > 0){
            return base.getValue() <= against.getValue();
        } else {
            return base.getValue() >= against.getValue();
        }
    }
    default void prerequisiteTenets(Map<TenetReference,Acceptance> tenets){};
    default void incompatibleTenets(Set<TenetReference> tenets){};
    default void requiredFlags(Set<Class<? extends FlagTenet>> flags){};
    default void incompatibleFlags(Set<Class<? extends FlagTenet>> flags){};
    default boolean isTenetCompatible(TenetInstance<?> t){
        return true;
    }
}
