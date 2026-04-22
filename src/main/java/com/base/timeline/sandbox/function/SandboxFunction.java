package com.base.timeline.sandbox.function;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.common.collect.ImmutableList;

import java.time.LocalDate;
import java.util.List;

import static com.base.condition.Condition.ShouldRun.*;

public abstract class SandboxFunction<T extends DateMutableEntity<?>>{
    boolean first_entity = true;
    boolean first_state = true;
    private TimelineState<? extends T> state;
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_RUN = ImmutableList.of(ONCE_PER_STATE,ONCE_PER_ENTITY,WHOLE_STATE_PER_ENTITY,ONCE_PER_CHANGE);
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_FIRST_STATE = ImmutableList.of(WHOLE_STATE_PER_ENTITY,ONCE_PER_CHANGE);
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_STATE = ImmutableList.of(ONCE_PER_STATE,ONCE_PER_CHANGE);
    private static final List<Condition.ShouldRun> DEFAULT_SHOULD_ENTITY = ImmutableList.of(ONCE_PER_CHANGE);

    public final SandboxCode startup(Sandbox<? extends T> sandbox, DMEReference<? extends T> entity, TimelineChange<? super T> newChange){
        return onStartup(sandbox,entity,newChange);
    }
    public final SandboxCode cycle(Sandbox<? extends T> sandbox, DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<? super T> newChange, TimelineChange<?> existingChange){
        List<Condition.ShouldRun> shouldRun;
        if(state != this.state){
            this.state = state;
            if (!first_entity && !first_state){
                shouldRun = DEFAULT_SHOULD_STATE;
            } else if(first_entity){
                first_entity = false;
                shouldRun = DEFAULT_SHOULD_RUN;
            } else {
                first_state = false;
                shouldRun = DEFAULT_SHOULD_STATE;
            }
        } else if (first_state){
            shouldRun = DEFAULT_SHOULD_FIRST_STATE;
        }else {
            shouldRun = DEFAULT_SHOULD_ENTITY;
        }
        return onCycle(sandbox,entity,state,newChange, shouldRun, existingChange);
    };
    public final void step(Sandbox<? extends T> sandbox, DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<? super T> newChange, boolean isFirstCycle){
        onStep(sandbox,entity,state,newChange,isFirstCycle);
    }
    public final void complete(Sandbox<? extends T> sandbox, SandboxCode code, DMEReference<? extends T> entity, TimelineChange<? super T> newChange){
        onComplete(sandbox,newChange.getEnd(),code,entity,newChange);
        newChange.complete(sandbox,this,code);
    }
    /**
     * Executes logic when the simulation has initialized on the thread before the full start.
     *
     * @param sandbox   The sandbox instance within which the simulation is executed.
     * @param entity    The reference to the entity being processed during the startup phase.
     * @param newChange The timeline change being proposed.
     * @return A {@link SandboxCode} indicating whether the initialization should continue or take a specific action.
     */
    protected SandboxCode onStartup(Sandbox<? extends T> sandbox, DMEReference<? extends T> entity, TimelineChange<? super T> newChange){
        return SandboxCode.CONTINUE;
    } // Runs when the simulation has initialized on the thread before the full start.
    /**
     * Executes logic on every change in the state. This method is invoked during each cycle of the simulation
     * and can filter conditions using the provided {@code shouldRun} list.
     *
     * @param sandbox        The sandbox instance within which the simulation is executed.
     * @param entity         The entity reference being processed during this cycle.
     * @param state          The current timeline state before applying the new change.
     * @param newChange      The change being newly introduced to the timeline.
     * @param shouldRun      A list of {@link Condition.ShouldRun} instances that indicate conditions
     *                       under which specific operations or checks should be executed.
     * @param existingChange The prior change in the timeline to compare with the new change.
     * @return A {@link SandboxCode} indicating the outcome of this cycle, such as whether
     * the simulation should continue, restart, or terminate.
     */
    protected abstract SandboxCode onCycle(Sandbox<? extends T> sandbox, DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<? super T> newChange, List<Condition.ShouldRun> shouldRun, TimelineChange<?> existingChange); //Runs on every change in the state. Implementations should take advantage of the shouldrun conditions to properly filter conditions
    /**
     * Executes logic at the end of a validation or simulation check. This method is invoked
     * after all errors have been resolved in the current cycle and before the sandbox transitions
     * to the next date in the simulation.
     *
     * @param sandbox      The sandbox instance in which the simulation is being executed.
     * @param entity       The reference to the entity being processed during this step.
     * @param state        The current state of the timeline at the moment this step is executed.
     * @param newChange    The new change being introduced to the timeline during this step.
     * @param isFirstCycle A boolean indicating whether this is the first cycle of the simulation.
     */
    protected abstract void onStep(Sandbox<? extends T> sandbox, DMEReference<? extends T> entity, TimelineState<? extends T> state, TimelineChange<? super T> newChange, boolean isFirstCycle); //Runs at end of a check, after errors have resolved and the Sandbox is preparing to move onto the next date.
    /**
     * Handles the completion of the simulation. This method is invoked once the
     * simulation is finished, providing the final state and associated parameters.
     *
     * @param sandbox   The sandbox instance in which the simulation was executed.
     * @param endDate   The end date of the simulation.
     * @param code      The resultant status code of the simulation.
     * @param entity    The entity reference that was processed in the simulation.
     * @param newChange The final change in the timeline that occurred during the simulation.
     */
    protected abstract void onComplete(Sandbox<? extends T> sandbox, LocalDate endDate, SandboxCode code, DMEReference<? extends T> entity, TimelineChange<? super T> newChange); //Runs once the simulation is complete.
}
