package com.base.timeline;

import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TimelineHelper {
    public static <T extends DateMutableEntity<T>> TimelineChange<T> GetChangeByBreadcrumb(Timeline<T> timeline, long breadcrumb, LocalDate date){
        return timeline.getStateAt(date).getDiff(breadcrumb);
    }
    public static <T extends DateMutableEntity<T>> void BreadcrumbCleanup(Timeline<T> timeline, long breadcrumb, LocalDate startDate, LocalDate newEnd){
        if (newEnd.isBefore(startDate)){
            cleanInternal(timeline, breadcrumb, newEnd, startDate);
        } else {
            cleanInternal(timeline, breadcrumb, startDate, newEnd);
        }
    }
    public static <T extends DateMutableEntity<T>> void UpdateBreadcrumbDate(Timeline<T> timeline, LocalDate startDate, LocalDate endDate, long breadcrumb, LocalDate newDate){
        TimelineState<T> state = timeline.getStateAt(startDate);
        LocalDate currentDate = startDate;
        while ((currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) && state != null){
            state.removeBreadcrumb(breadcrumb);
            state.insertBreadcrumb(breadcrumb,newDate);
        }
    }
    private static <T extends DateMutableEntity<T>> void cleanInternal(Timeline<T> timeline, long breadcrumb, LocalDate startDate, LocalDate newEnd){
        TimelineState<T> state = timeline.getStateAt(startDate);
        LocalDate currentDate = startDate;
        TimelineChange<T> change = GetChangeByBreadcrumb(timeline,breadcrumb,state.getBreadcrumbStart(breadcrumb));
        while ((currentDate.isBefore(newEnd) || currentDate.isEqual(newEnd)) && state != null){
            state.removeBreadcrumb(breadcrumb);
            state = timeline.getNextState(currentDate);
            currentDate = state.getStart();
        }
        change.addEndPoint(newEnd);
    }
    public static <T extends DateMutableEntity<T>> void propagateBreadcrumb(Timeline<T> timeline, TimelineChange<T> tc){
        final LocalDate startDate = tc.getStart();
        final LocalDate endDate = tc.getEnd();
        final long breadcrumb = tc.getId();

        TimelineState<T> state = timeline.getStateAt(startDate);
        LocalDate currentDate = startDate;
        while ((currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) && state != null){
            state.forceInsertBC(breadcrumb,startDate);
            state = timeline.getNextState(currentDate);
            currentDate = state.getStart();
        }
    }
    public static <T extends DateMutableEntity<T>> void insertBreadcrumb(TimelineState<T> state, TimelineChange<T> tc){
        state.forceInsertBC(tc.getId(),tc.getStart());
        if (tc.getEnd() == null || tc.getEnd().isBefore(state.getStart())){
            tc.addEndPoint(state.getStart());
        }
    }
    public static <T extends DateMutableEntity<T>> List<TimelineChange<T>> getAllChanges(Timeline<T> timeline, TimelineState<T> ts, boolean includeInactive){
        List<TimelineChange<T>> baseChanges;
        HashMap<Long,LocalDate> passiveChanges = ts.getBreadcrumbs();
        if (!includeInactive){
            baseChanges = ts.getDiffs();
        } else {
            baseChanges = ts.getAllDiffs();
        }
        for (Map.Entry<Long,LocalDate> change : passiveChanges.entrySet()){
            TimelineChange<T> c = GetChangeByBreadcrumb(timeline,change.getKey(),change.getValue());
            if (includeInactive || !c.isDeactivated()){
                baseChanges.add(c);
            }
        }
        return baseChanges;
    }
    public static <T extends DateMutableEntity<T>> List<TimelineChange<T>> getChangesWhere(List<TimelineChange<T>> changes, Predicate<TimelineChange<T>> predicate){
        return changes.stream().filter(predicate).toList();
    }
}
