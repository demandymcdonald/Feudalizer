package com.base.timeline.change.conditions;

import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TitleTLChange;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Optional;

public class NullifyConditions {

    public static final ConditionResult.Nullify NULLIFY = new ConditionResult.Nullify(true, true);
    public static final ConditionResult.Nullify NULLIFY_ALWAYS = new ConditionResult.Nullify(true, false);
    public static final ConditionResult.Nullify NOT_NULLIFY_EXCLUSIVE = new ConditionResult.Nullify(false, false);
    public static final ConditionResult.Nullify NOT_NULLIFY_NON_EXCLUSIVE = new ConditionResult.Nullify(false, true);

    public static final AlwaysNullify<? extends DateMutableEntity<?>> ALWAYS_NULLIFY = new AlwaysNullify<>();
    public static final NeverNullify<? extends DateMutableEntity<?>> NEVER_NULLIFY = new NeverNullify<>();

    public static <T extends DateMutableEntity<T>> List<Condition<ConditionResult.Nullify, ? super T>> BaseConditions() {
        return List.of(new IsSame<>(), new IsOpposite<>());
    }

    public static class IsSame<T extends DateMutableEntity<T>> extends Condition<ConditionResult.Nullify, T> {
        private IsSame() {
            super("null_gen_same", true);
        }

        @Override
        protected Optional<ConditionResult.Nullify> doCheck(T entity, TimelineChange<? super T> thisChange, TimelineChange<? super T> checkAgainst) {
            if( thisChange.getId() == checkAgainst.getId() && thisChange.hashCode() != checkAgainst.hashCode()) {
                return Optional.of(NULLIFY);
            } else {
                return Optional.of(NOT_NULLIFY_NON_EXCLUSIVE);
            }
        }
    }
    public static class IsOpposite<T extends DateMutableEntity<T>> extends Condition<ConditionResult.Nullify, T> {
        //Might be obsolete? since removing changes is now possible through deactivate() or remove()?
        public IsOpposite() {
            super("null_gen_opposite", true);
        }

        @Override
        protected Optional<ConditionResult.Nullify> doCheck(T entity, TimelineChange<? super T> thisChange, TimelineChange<? super T> checkAgainst) {
            if (thisChange.oppositeChanges().contains(checkAgainst.getClass()) && thisChange.getStart() == checkAgainst.getStart()) {
                return Optional.of(NULLIFY);
            }
        }
    }
    public static class AlwaysNullify<T extends DateMutableEntity<T>> extends Condition<ConditionResult.Nullify, T> {
        private AlwaysNullify() {
            super("null_gen_always", true);
        }

        @Override
        protected Optional<ConditionResult.Nullify> doCheck(T entity, TimelineChange<? super T> thisChange, TimelineChange<? super T> checkAgainst) {
            return Optional.of(NULLIFY);
        }
    }
    public static class NeverNullify<T extends DateMutableEntity<T>> extends Condition<ConditionResult.Nullify, T> {
        private NeverNullify() {
            super("null_gen_never", true);
        }

        @Override
        protected Optional<ConditionResult.Nullify> doCheck(T entity, TimelineChange<? super T> thisChange, TimelineChange<? super T> checkAgainst) {
            return Optional.of(NOT_NULLIFY_EXCLUSIVE);
        }
    }
}
