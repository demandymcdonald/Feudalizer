package com.objects.succession.condition;

import com.base.reference.DMEReference;
import com.base.condition.Condition;
import com.base.datemutable.timeline.error.StateError;
import com.objects.character.sentient.SentientCharacter;
import com.objects.title.Title;

import java.time.LocalDate;

public abstract class CanInheritCondition<T extends Title<T>> extends Condition<CanInheritCondition<T>,StateError, DMEReference<? extends T>,DMEReference<?  extends SentientCharacter<?>>, LocalDate> {
    public CanInheritCondition(String id) {
        super(id);
    }

    @Override
    public boolean runOncePerState() {
        return true;
    }
}
