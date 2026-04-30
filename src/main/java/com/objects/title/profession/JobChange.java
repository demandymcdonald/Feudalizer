package com.objects.title.profession;

import com.base.datemutable.timeline.change.startend.CreatedChange;
import com.base.datemutable.timeline.change.startend.EndingChange;
import com.base.reference.DMEReference;
import com.objects.CauseOfEnd;

import java.time.LocalDate;

public class JobChange {
    public static class Created extends CreatedChange<Job> {
        protected Created(DMEReference<? extends Job> owner, LocalDate date) {
            super(owner, date);
        }
    };
    public static class Removed extends EndingChange<Job> {
        protected Removed(DMEReference<? extends Job> owner, LocalDate date, CauseOfEnd<? super Job> causeOfEnd) {
            super(owner, date, causeOfEnd);
        }
    }
    public static final CauseOfEnd<Job> JOB_REMOVED = new CauseOfEnd<Job>("job_remove","Removed Job","");
}
