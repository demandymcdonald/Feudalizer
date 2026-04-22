package com.objects.culture.tenet.factory;

import com.base.DateMutableEntity;
import com.base.condition.IConditionError;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.base.timeline.error.StateError;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.dynamic.DynamicTenet;

import java.util.Optional;

public abstract class TenetCondition<T extends DynamicTenet<T>,D extends DateMutableEntity<D> & CultureObject<D>> {
    private final TenetReference tenetReference;
    public TenetCondition(TenetReference tenetReference) {
        this.tenetReference = tenetReference;
    }
    public final Optional<TenetError<T,D>> check(T tenet, DMEReference<? extends D> decider){
        return doCheck(tenetReference,tenet,decider);
    };

    protected abstract Optional<TenetError<T,D>> doCheck(TenetReference tenet, T parentTenet, DMEReference<? extends D> decider);


    public static <T extends DynamicTenet<T>,D extends DateMutableEntity<D> & CultureObject<D>> TenetCondition<T,D> prerequisite(TenetReference thisTenet, TenetReference preRequisite){
        return new TenetCondition<T,D>(thisTenet){
            @Override
            protected Optional<TenetError<T,D>> doCheck(TenetReference tenet, T parentTenet, DMEReference<? extends D> decider) {
                if(parentTenet.hasActiveOpinion(preRequisite)){
                    return Optional.empty();
                }
                return Optional.of(missing(parentTenet.getTenetReference(),preRequisite));
            }
        };
    }
    public static <T extends DynamicTenet<T>,D extends DateMutableEntity<D> & CultureObject<D>> TenetCondition<T,D> hasAcceptance(TenetReference tenet, Acceptance threshold, boolean includeInfluencer){
        return new TenetCondition<T,D>(tenet){
            @Override
            protected Optional<TenetError<T,D>> doCheck(TenetReference tenet, T parentTenet, DMEReference<? extends D> decider) {
                if(parentTenet.getAcceptance(tenet,includeInfluencer).greaterThan(threshold)){
                    return Optional.empty();
                }
                return Optional.of(missing(parentTenet.getTenetReference(),tenet));
            }
        };
    }
    protected static <T extends DynamicTenet<T>,D extends DateMutableEntity<D> & CultureObject<D>> TenetError<T,D> missing(TenetReference misser, TenetReference missing){
        return TenetError.generic("dt_missing_pre_req",new ComplexReference("{} is missing Tenet {}",misser,missing));
    }
}
