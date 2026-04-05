package com.objects.title.condition;

import com.base.reference.DMEReference;
import com.base.condition.Condition;
import com.base.timeline.error.StateError;
import com.objects.character.LivingCreature;
import com.objects.title.Title;

import java.time.LocalDate;

public abstract class CanHoldCondition<T extends Title<T>> extends Condition<StateError, DMEReference<? extends T>,DMEReference<?  extends LivingCreature<?>>, LocalDate> {
    public CanHoldCondition(String id) {
        super(id);
    }

    @Override
    public boolean singleRun() {
        return true;
    }
}
