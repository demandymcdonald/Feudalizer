package com.base.timeline;

import com.Feudalizer;
import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TimelineHelper {
    public static <T extends DateMutableEntity<T>> TimelineChange<? super T> followBreadcrumb(Timeline<T> timeline, long breadcrumb, LocalDate date) {
        return timeline.getStateAt(date).getChange(breadcrumb);
    }

    public static <T extends DateMutableEntity<T>> void BreadcrumbCleanup(Timeline<T> timeline, long breadcrumb, LocalDate startDate, LocalDate newEnd) {
        if (newEnd.isBefore(startDate)) {
            cleanInternal(timeline, breadcrumb, newEnd, startDate);
        } else {
            cleanInternal(timeline, breadcrumb, startDate, newEnd);
        }
    }

    public static <T extends DateMutableEntity<T>> void UpdateBreadcrumbDate(Timeline<T> timeline, LocalDate startDate, LocalDate endDate, long breadcrumb, LocalDate newDate) {
        TimelineState<T> state = timeline.getStateAt(startDate);
        TimelineChange<? super T> change = state.getChange(breadcrumb);
        if (change.isStatic()) {
            return;
        }
        LocalDate currentDate = startDate;
        while ((currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) && state != null) {
            state.removeBreadcrumb(breadcrumb);
            state.insertBreadcrumb(breadcrumb, newDate);
        }
    }

    private static <T extends DateMutableEntity<T>> void cleanInternal(Timeline<T> timeline, long breadcrumb, LocalDate startDate, LocalDate newEnd) {
        TimelineState<T> state = timeline.getStateAt(startDate);
        LocalDate currentDate = startDate;
        TimelineChange<? super T> change = (TimelineChange<? super T>) followBreadcrumb(timeline, breadcrumb, state.getBreadcrumbStart(breadcrumb));
        while ((currentDate.isBefore(newEnd) || currentDate.isEqual(newEnd)) && state != null) {
            state.removeBreadcrumb(breadcrumb);
            state = timeline.getNextState(currentDate);
            currentDate = state.getStart();
        }
        change.addEnd(newEnd);
    }



    public static <T extends DateMutableEntity<T>> void propagateBreadcrumb(Timeline<T> timeline, TimelineChange<T> tc) {
        if (tc.isDeactivated()) {
            Feudalizer.LOGGER.error("Tried to propagate on deactivated change: " + tc);
            return;
        } else if (tc.isStatic()) {
            return;
        }


        final LocalDate startDate = tc.getStart();
        final LocalDate endDate = tc.getEnd();
        final long breadcrumb = tc.getId();

        TimelineState<T> state = timeline.getStateAt(startDate);
        LocalDate currentDate = startDate;
        while ((currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) && state != null) {
            state.forceInsertBC(breadcrumb, startDate);
            state = timeline.getNextState(currentDate);
            currentDate = state.getStart();
        }
    }

    public static <T extends DateMutableEntity<T>> void insertBreadcrumb(TimelineState<T> state, TimelineChange<T> tc) {
        state.forceInsertBC(tc.getId(), tc.getStart());
        if (tc.getEnd() == null || tc.getEnd().isBefore(state.getStart())) {
            tc.addEnd(state.getStart());
        }
    }

    public static <T extends DateMutableEntity<T>> List<TimelineChange<? super T>> getAllChanges(Timeline<T> timeline, TimelineState<T> ts, boolean includeInactive) {
        List<TimelineChange<? super T>> baseChanges;
        HashMap<Long, LocalDate> passiveChanges = ts.getBreadcrumbs();
        if (!includeInactive) {
            baseChanges = ts.getChanges();
        } else {
            baseChanges = ts.getAllChanges();
        }
        for (Map.Entry<Long, LocalDate> change : passiveChanges.entrySet()) {
            TimelineChange<? super T> c = (TimelineChange<? super T>) followBreadcrumb(timeline, change.getKey(), change.getValue());
            if (includeInactive || !c.isDeactivated()) {
                baseChanges.add(c);
            }
        }
        return baseChanges;
    }

    public static <T extends DateMutableEntity<T>> List<TimelineChange<? super T>> getChangesWhere(List<TimelineChange<? super T>> changes, Predicate<TimelineChange<? super T>> predicate) {
        return changes.stream().filter(predicate).toList();
    }

    public static <T extends DateMutableEntity<T>> TimelineChange<? super T> getLastValidChange(Timeline<T> timeline, TimelineState<? super T> current, TimelineChange<? super T> soonToBeGone) {
        final boolean isPositive = soonToBeGone.isPositive();
        List<Class<? super T>> opposites = (List<Class<? super T>>) soonToBeGone.oppositeChanges();
        List<Class<? super T>> siblings = (List<Class<? super T>>) soonToBeGone.siblingChanges();
        Class<? extends TimelineChange<? super T>> cClass = (Class<? extends TimelineChange<? super T>>) soonToBeGone.getClass();
        LocalDate currentDate = current.getStart();
        while (true) {
            TimelineState<T> next = timeline.getStateBefore(currentDate);
            for (TimelineChange<? super T> change : next.getChanges()) {
                Class<? extends TimelineChange<T>> changeClass = (Class<? extends TimelineChange<T>>) change.getClass();
                if ((isPositive && (changeClass.equals(cClass) || siblings.contains(changeClass))) ||
                        !isPositive && opposites.contains(changeClass)) {
                    return change;
                }
            }
            currentDate = next.getStart();
            if (next.isBoundary()) {
                throw new IllegalStateException("Could not find a valid change for " + soonToBeGone);
            }
        }
    }

    public static <T extends DateMutableEntity<T>> HashMap<Long, LocalDate> extendTrail(Timeline<T> timeline, TimelineState<T> lastState, LocalDate newState) {
        HashMap<Long, LocalDate> breadcrumbs = new HashMap<>();
        for (Map.Entry<Long, LocalDate> entry : lastState.getBreadcrumbs().entrySet()) {
            breadcrumbs.put(entry.getKey(), entry.getValue());
            TimelineChange<? super T> change = (TimelineChange<? super T>) followBreadcrumb(timeline, entry.getKey(), entry.getValue());
            change.addEnd(newState);
        }
        for (TimelineChange<? super T> change : lastState.getChanges()) {
            Pair<Long, LocalDate> pair = change.buildStateBreadcrumb();
            breadcrumbs.put(pair.getLeft(), pair.getRight());
            change.addEnd(newState);
        }
        return breadcrumbs;
    }

    public static <T extends DateMutableEntity<T>> HashMap<Long, LocalDate> extendTrail(Timeline<T> timeline, LocalDate date) {
        return extendTrail(timeline, timeline.getStateBefore(date), date);
    }
}

