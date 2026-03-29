package com.base.timeline.change.conditions;

import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TitleTLChange;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Optional;

public class NullifyConditions {
    public static final ConditionResult.Nullify NULLIFY = new ConditionResult.Nullify(true,false);
    public static final ConditionResult.Nullify NOT_NULLIFY_EXCLUSIVE = new ConditionResult.Nullify(false,false);
    public static final ConditionResult.Nullify NOT_NULLIFY_NON_EXCLUSIVE = new ConditionResult.Nullify(false,true);
    public static final Condition<ConditionResult.Nullify,?> IS_OPPOSITE = new Condition<>("isOpposite", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar, Optional<ConditionResult.Nullify>>() {

        @Override
        public Optional<ConditionResult.Nullify> apply(TimelineChange<?> change, TimelineChange<?> check, Sidecar sidecar) {
            if (change.isOpposite(check)) {
                return Optional.of(NULLIFY);
            }
            return Optional.of(NOT_NULLIFY_NON_EXCLUSIVE);
        }
    });
    public static final Condition<ConditionResult.Nullify,?> IS_SAME_DATE = new Condition<>("isSameDate", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar, Optional<ConditionResult.Nullify>>() {
            @Override
            public Optional<ConditionResult.Nullify> apply(TimelineChange<?> change, TimelineChange<?> check, Sidecar sidecar) {
                if (change.getStart().equals(check.getStart())) {
                    return Optional.of(NULLIFY);
                }
                return Optional.of(NOT_NULLIFY_EXCLUSIVE);
            }
        });
    public static final Condition<ConditionResult.Nullify,?> IS_DUPLICATE = new Condition<>("isDuplicate", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar, Optional<ConditionResult.Nullify>>() {
        @Override
        public Optional<ConditionResult.Nullify> apply(TimelineChange<?> change, TimelineChange<?> check, Sidecar sidecar) {
            if (change.isSame(check)) {
                return Optional.of(NULLIFY);
            }
            return Optional.of(NOT_NULLIFY_NON_EXCLUSIVE);
        }
    });
    //--- Title Conditions ---
    public static final Condition<ConditionResult.Nullify, Sidecar.TitleChange> TEMPLATE = new Condition<>("title_template", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<ConditionResult.Nullify>>() {
        @Override
        public Optional<ConditionResult.Nullify> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            assert thisChange instanceof TitleTLChange<?> ttl;
            return Optional.empty();
        }
    });
    public static final Condition<ConditionResult.Nullify, Sidecar.TitleChange> IS_SAME_TITLE = new Condition<>("title_sameType", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<ConditionResult.Nullify>>() {
        @Override
        public Optional<ConditionResult.Nullify> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            assert thisChange instanceof TitleTLChange<?> ttl;
            if (checkAgainst instanceof TitleTLChange<?>){
                return Optional.of(NULLIFY);
            } else {
                return Optional.of(NOT_NULLIFY_EXCLUSIVE);
            }
        }
    });
    public static final Condition<ConditionResult.Nullify, Sidecar.TitleChange> IS_SAME_HOLDER = new Condition<>("title_sameHolder", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<ConditionResult.Nullify>>() {
        @Override
        public Optional<ConditionResult.Nullify> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            if( thisChange instanceof TitleTLChange<?> ttl && checkAgainst instanceof TitleTLChange<?> ttl2){
                if (ttl.isSameHolder(ttl2)){
                    return Optional.of(NULLIFY);
                } else {
                    return Optional.of(NOT_NULLIFY_EXCLUSIVE);
                }
            };
            return Optional.empty();
        }
    });
    public static List<Condition<ConditionResult.Nullify,?>> BaseConditions(){
        return List.of(IS_SAME_DATE,IS_DUPLICATE,IS_OPPOSITE);
    }
    public static List<Condition<ConditionResult.Nullify, Sidecar.TitleChange>> BaseTitleConditions(){
        return List.of(IS_SAME_TITLE,IS_SAME_HOLDER);
    }

}
