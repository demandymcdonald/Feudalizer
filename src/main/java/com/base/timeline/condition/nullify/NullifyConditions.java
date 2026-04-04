package com.base.timeline.condition.nullify;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.condition.Condition;
import com.base.timeline.condition.ConditionResult;
import static com.base.timeline.condition.nullify.NullifyResult.*;
import java.util.List;
import java.util.Optional;

import static com.base.timeline.condition.nullify.NullifyResult.NULLIFY;

public class NullifyConditions {


    public static final AlwaysNullify<? extends DateMutableEntity<?>> ALWAYS_NULLIFY = new AlwaysNullify<>();
    public static final NeverNullify<? extends DateMutableEntity<?>> NEVER_NULLIFY = new NeverNullify<>();

    public static List<NullifyCondition<DateMutableEntity<?>>> BaseConditions() {
        return List.of(new IsSame<>(), new IsOpposite());
    }

    public static class IsSame<T extends DateMutableEntity<?>> extends NullifyCondition<T> {
        private IsSame() {
            super("null_gen_same");
        }

        @Override
        protected Optional<NullifyResult> doCheck(DMEReference<? extends T> entity, TimelineChange<? super T> thisChange, TimelineChange<?> checkAgainst) {
            if( thisChange.getFullID() == checkAgainst.getFullID() && thisChange.hashCode() != checkAgainst.hashCode()) {
                return Optional.of(NULLIFY);
            } else {
                return Optional.of(NOT_NULLIFY_NON_EXCLUSIVE);
            }
        }

    }
    public static class IsOpposite<T extends DateMutableEntity<?>> extends NullifyCondition<T> {
        //Might be obsolete? since removing changes is now possible through deactivate() or remove()?
        public IsOpposite() {
            super("null_gen_opposite");
        }


        @Override
        protected Optional<NullifyResult> doCheck(DMEReference<? extends T> entity, TimelineChange<? super T> thisChange, TimelineChange<?> checkAgainst) {
            if (thisChange.oppositeChanges().contains(checkAgainst.getClass()) && thisChange.getStart() == checkAgainst.getStart()) {
                return Optional.of(NULLIFY);
            }
            return Optional.empty();
        }
    }
    public static class AlwaysNullify<T extends DateMutableEntity<?>> extends NullifyCondition<T> {
        private AlwaysNullify() {
            super("null_gen_always");
        }

        @Override
        protected Optional<NullifyResult> doCheck(DMEReference<? extends T> entity, TimelineChange<? super T> thisChange, TimelineChange<?> checkAgainst) {
            return Optional.of(NULLIFY);
        }
    }
    public static class NeverNullify<T extends DateMutableEntity<?>> extends NullifyCondition<T> {
        private NeverNullify() {
            super("null_gen_never");
        }

        @Override
        protected Optional<NullifyResult> doCheck(DMEReference<? extends T> entity, TimelineChange<? super T> thisChange, TimelineChange<?> checkAgainst) {
            return Optional.of(NOT_NULLIFY_EXCLUSIVE);
        }
    }
}
