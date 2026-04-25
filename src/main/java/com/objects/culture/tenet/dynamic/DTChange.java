package com.objects.culture.tenet.dynamic;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.startend.CreatedChange;
import com.base.timeline.change.startend.EndingChange;
import com.objects.CauseOfEnd;

import java.time.LocalDate;

public class DTChange {
    public static class Founding<T extends DynamicTenet<T>> extends CreatedChange<T>{

        protected Founding(DMEReference<? extends T> owner, LocalDate date) {
            super(owner, date);
        }
    }
    public static class Disbanding<T extends DynamicTenet<T>> extends EndingChange<T> {
        protected Disbanding(DMEReference<? extends T> owner, LocalDate date, CauseOfEnd<? super T> causeOfEnd) {
            super(owner, date, causeOfEnd);
        }
    }
}
