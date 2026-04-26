package com.objects.culture.tenet.factory;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.condition.Condition;
import com.base.datemutable.timeline.Timeline;
import com.base.datemutable.timeline.change.CultureAware;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.error.StateError;
import com.base.datemutable.timeline.state.TimelineState;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.tenet.Tenet;
import com.utilities.number.DateUtilities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CultureChronoCondition<TC extends TimelineChange<? super D> & CultureAware<TC,S,D>, S extends DateMutableEntity<?> & ICultureObject, D extends DateMutableEntity<?> & ICultureObject> extends CultureCondition<TC,S,D> {
    private final int time;
    private final TemporalUnit unit;
    public CultureChronoCondition(Tenet tenet, int time, TemporalUnit unit) {
        super(tenet);
        this.time = time;
        this.unit = unit;
    }
    @Override
    protected final Optional<StateError> doCheck(Tenet tenet, TC change, S subject, Culture subjectCulture, D decider, Culture deciderCulture) {
        AtomicBoolean doGlobalStep = new AtomicBoolean(false);
        LocalDate date = Global.getDate();
        final Timeline<? extends D> timeline = (Timeline<? extends D>) decider.getTimeline();
        final LocalDate endDate = date.minus(time, unit);
        while (date.isAfter(endDate)) {
            Optional<StateError> error = chronoCheck(tenet,subject,subject.getCulture(),decider,decider.getCulture());
            if (error.isPresent()) {
                return error;
            }
            date = moveDate(timeline,date,endDate,doGlobalStep);
        }
        return Optional.empty();
    }
    private final LocalDate moveDate(Timeline<? extends D> timeline, LocalDate current, LocalDate end, AtomicBoolean doGlobalStep) {
        LocalDate date;
        if(doGlobalStep.get()) {
            date = DateUtilities.ceiling(current.minus(7, ChronoUnit.DAYS),end);
        } else {
            TimelineState<? extends D> state = timeline.getStateBefore(current);
            if (state == null){
                doGlobalStep.set(true);
                return moveDate(timeline, current, end, doGlobalStep);
            }
            date = DateUtilities.ceiling(state.getStart(),end);
        }
        Global.setCurrentDate(date);
        return date;
    }
    protected abstract Optional<StateError> chronoCheck(Tenet tenet, S subject, Culture subjectCulture, D decider, Culture decuderCulture);
    @Override
    public Condition.ShouldRun shouldRun() {
        return Condition.ShouldRun.ONCE_PER_ENTITY;
    }
}
