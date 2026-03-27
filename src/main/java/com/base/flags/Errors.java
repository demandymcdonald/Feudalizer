package com.base.flags;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.SimpleReference;
import com.base.timeline.TimelineChangeState;
import com.base.timeline.change.TitleTLChange;
import com.base.timeline.propagation.core.Objective;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;

import java.awt.print.Book;

public class Errors {
    public static StateError duplicateError() {
        return new StateError(SimpleReference.of("Duplicate Entry")).addOverride();
    }

    public static StateError newInvalidatesOldError() {
        return new StateError(SimpleReference.of("New invalidates old")).addOverride().addIgnore().addEndState().addEndCancel();
    }

    public static StateError oldInvalidatesNewError() {
        return new StateError(SimpleReference.of("Old invalidates new")).addOverride().addIgnore().addEndState().addEndCancel();
    }

    public static StateError newStateNullifiedbyOldError() {
        return new StateError(SimpleReference.of("New state nullified by old")).addOverride().addEndState().addEndCancel();
    }

    public static StateError newHolderDead(TitleTLChange<?> title) {
        //TODO write error message which includes DMR for each thing.
        return new StateError(SimpleReference.of("New holder dead"));
    }
    public static StateError loopError() {
        return new StateError(SimpleReference.of("Loop Detected")).addOverride().addEndState().addEndCancel();
    }
    public static StateError nullifyError() {
        return new StateError(SimpleReference.of("Nullify Entry")).addNullify().addEndState().addEndCancel();
    }
    public static StateError characterDead(BookCharacter character) {

    }
    public static StateError inheritanceFirstTime(TimelineChangeState<?> tcs) {
        if (tcs.change() instanceof TitleTLChange.Inherit<?> ic) {
            return new StateError(SimpleReference.of("Inheritance first time")).addBranchingSuccessionPlanning(new Objective(ic.getTitle(),tcs));
        }
        return new StateError(SimpleReference.of("Inheritance first time")).addContinue();
    }
    public static StateError alreadyHasAParent(DateMutableEntity<?, ?> child, DateMutableEntity<?, ?> newParent, DateMutableEntity<?, ?> oldParent) {
        //TODO write error message which includes DMR for each thing.
        return new StateError(SimpleReference.of("Already has parent")).addOverride().addContinue().addEndState().addEndCancel();
    }
    public static StateError wrongFaction(BookCharacter ch, Title<?> title, String existingText) {
        StateError error = new StateError(SimpleReference.of(existingText));
        error.addEndState().addEndCancel().addCharacterLeaveFaction(ch,title);
        return error;
    }
    public static StateError aboveMaxTitle(String existingText) {
        StateError error = new StateError(SimpleReference.of(existingText));
        error.addIgnore().addEndState().addEndCancel();
        return error;
    }
    public static StateError EMPTY() {
        return new StateError(SimpleReference.of("Passes Condition")).addContinue();
    }
}
//
//    /**
//     * Represents a specific error state where the same state change occurs twice in the timeline.
//     * This error provides resolution options, such as overwriting the conflicting state
//     * or canceling the operation that leads to the conflict.
//     * <p>
//     * Resolutions:
//     * 1. Overwrite conflict - Allows the operation to proceed by overwriting the existing state with the new one.
//     * 2. Cancel conflict - Aborts the operation to prevent the conflict from occurring.
//     */
//    DUPLICATE_STATE(StateError.buildOverride(), StateError.buildEndCancel(), StateError.buildNullify(2), StateError.buildAutoResolve(3)),
//    /**
//     * Represents an error where a new state invalidates a pre-existing non-title state.
//     * This error requires resolution because the state transition causes the existing state
//     * to become obsolete or irrelevant, potentially leading to conflicts.
//     * <p>
//     * Resolutions provided:
//     * 1. Overwrite conflict - Replaces the old non-title state with the new state.
//     * 2. Ignore (potentially dangerous) - Keeps the old state and ignores the new state.
//     * 3. End before conflict - Concludes the previous state before introducing the new state.
//     * 4. Cancel conflict - Aborts the operation to prevent the conflict from occurring.
//     */
//    NEW_STATE_INVALIDATES_OLD_STATE(StateError.buildOverwrite(0), StateError.buildIgnore(1), StateError.buildEndState(2), StateError.buildCancel(3)),
//    /**
//     * Represents an error state where a pre-existing non-title state invalidates a newly introduced state.
//     * This error arises when the introduction of a new state is deemed incompatible or redundant
//     * due to the influence or persistence of an older state, potentially causing transition conflicts.
//     * <p>
//     * Resolutions provided:
//     * 1. Overwrite conflict - Replaces the new state with the existing (old) state.
//     * 2. Ignore (potentially dangerous) - Keeps the new state while ignoring the old state's persistence.
//     * 3. End before conflict - Concludes the old state before introducing the new state.
//     * 4. Cancel conflict - Aborts the operation to prevent the conflict from occurring.
//     */
//    OLD_STATE_INVALIDATES_NEW_STATE(StateError.buildOverwrite(0), StateError.buildIgnore(1), StateError.buildEndState(2), StateError.buildCancel(3)),
//
//    /**
//     * Represents an error state where an existing title invalidates a newly introduced title.
//     * This error occurs when a pre-existing title renders the new title redundant, incompatible,
//     * or inappropriate, potentially leading to transition conflicts in the system.
//     * <p>
//     * Resolutions provided:
//     * 1. Overwrite conflict - Replaces the newly introduced title with the existing (old) title.
//     * 2. Ignore (potentially dangerous) - Keeps the new title and disregards the old title.
//     * 3. End before conflict - Concludes the old title before adding the new title.
//     * 4. Cancel conflict - Aborts the operation to prevent the conflict from occurring.
//     */
//    OLD_TITLE_INVALIDATES_NEW_TITLE(StateError.buildOverwrite(0), StateError.buildIgnore(1), StateError.buildEndState(2), StateError.buildCancel(3)),
//
//
//    /**
//     * Represents a set of predefined actions or behaviors triggered when a new title invalidates
//     * an old title. Each action is associated with a specific build behavior:
//     * <p>
//     * - buildOverwrite(0): Indicates that the new title explicitly overwrites the old title.
//     * - buildIgnore(1): Specifies that the new title is ignored and the old title remains unchanged.
//     * - buildEndState(2): Indicates that the previous state involving the old title is terminated
//     * upon the application of the new title.
//     * - buildCancel(3): Denotes that the process involving the old title is canceled when
//     * a new title is introduced.
//     */
//    NEW_TITLE_INVALIDATES_OLD_TITLE(StateError.buildOverwrite(0), StateError.buildIgnore(1), StateError.buildEndState(2), StateError.buildCancel(3)),
//
//    /**
//     * Represents the state or condition that a title holder already exists.
//     * This variable is used to manage and define specific system behavior
//     * when a conflict arises due to the presence of an existing title holder.
//     * The state is built using predefined configurations or parameters.
//     */
//    TITLE_HOLDER_EXISTS(StateError.buildOverwrite(0), StateError.buildEndState(1), StateError.buildCancel(2)),
//    /**
//     * A constant that represents a semantic error related to the title.
//     * This variable is typically used to indicate issues or inconsistencies
//     * specific to the semantics of a title in a given context.
//     * The value is initialized using the buildAutoResolve method with
//     * a parameter of 0.
//     */
//    TITLE_SEMANTIC_ERROR(StateError.buildAutoResolve(0)), //Use for semantic issues like needing to change references or clean up something. Autoresolve should just do this without informing end user
//
//    /**
//     * A variable indicating that the current title holder is deceased.
//     * This triggers the autoresolve mechanism, which runs a succession planner
//     * and proceeds with the next heir if operating within the propagation sandbox.
//     * The autoresolve is built with a configuration parameter set to 0.
//     */
//    TITLE_HOLDER_DEAD(StateError.buildAutoResolve(0)) // Autoresolve should run succession planner with change, and continue with heir if running in the propagation sandbox.
//    ;
//    private final StateError.Resolution[] options;
//
//    public StateError.Resolution[] options() {
//        return options;
//    }
//
//    Errors(StateError.Resolution... options) {
//        this.options = options;
//    }

