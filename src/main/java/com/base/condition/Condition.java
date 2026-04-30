package com.base.condition;

import com.base.component.ComponentManager;
import com.base.component.ComponentRegistry;
import com.base.component.InstanceType;
import com.base.component.immutable.ImmutableComponent;

import java.util.List;
import java.util.Optional;



public abstract class Condition<CON extends Condition<CON,R,A,B,C>,R extends ConditionResult, A,B,C> extends ImmutableComponent<CON> implements iCondition{
    public enum ShouldRun {
        ONCE_PER_STATE,
        ONCE_PER_ENTITY,
        WHOLE_STATE_PER_ENTITY,
        ONCE_PER_CHANGE,
    }
    protected List<ShouldRun> currentRuns;
    public Condition(String id) {
        super(InstanceType.HARDCODED,id);
    }
    @SuppressWarnings("unchecked")
    public final Optional<R> check(A entity, B thisChange, C checkAgainst, List<ShouldRun> shouldRun){
        if (shouldRun.contains(whenToRun())){
            currentRuns = shouldRun;
            return doCheck(entity, thisChange, checkAgainst);
        }
        return Optional.empty();
    };

    protected abstract Optional<R> doCheck(A entity, B thisChange, C checkAgainst);

    @Override
    public String getCode() {
        return getID();
    }
    public ShouldRun whenToRun(){
        return ShouldRun.ONCE_PER_CHANGE;
    }
    public static <C extends Condition<C,?,?,?,?>> ComponentManager<C>  getConditionManager(Class<C> c){
        return ComponentRegistry.getManager(c);
    }

}




