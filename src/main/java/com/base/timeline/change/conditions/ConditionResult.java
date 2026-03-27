package com.base.timeline.change.conditions;

public interface ConditionResult {

     record Nullify(boolean canNullify, boolean isOr) implements ConditionResult {}

}
