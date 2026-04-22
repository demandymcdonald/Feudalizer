package com.base.timeline.change.condition.nullify;

import com.base.condition.IConditionError;

public record NullifyResult(boolean canNullify, boolean isOr) implements IConditionError {
    public static final NullifyResult NULLIFY = new NullifyResult(true, true);
    public static final NullifyResult NULLIFY_ALWAYS = new NullifyResult(true, false);
    public static final NullifyResult NOT_NULLIFY_EXCLUSIVE = new NullifyResult(false, false);
    public static final NullifyResult NOT_NULLIFY_NON_EXCLUSIVE = new NullifyResult(false, true);

}
