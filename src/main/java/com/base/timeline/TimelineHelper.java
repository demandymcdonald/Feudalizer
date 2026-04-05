package com.base.timeline;

import com.Feudalizer;
import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TimelineMapChange;
import com.base.timeline.state.TimelineState;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import static com.Global.*;
import static com.Global.TimeDirection.FORWARD;
@SuppressWarnings("unchecked")
public class TimelineHelper {
    @Deprecated(forRemoval = true)
    public static <T extends DateMutableEntity<T>> void changeBreacrumbCleanup(Timeline<? extends T> timeline, long breadcrumb, LocalDate startDate, LocalDate newEnd) {
        BreadcrumbCleanup((Timeline<T>) timeline,breadcrumb,startDate,newEnd);
    }
    @Deprecated(forRemoval = true)
    public static <T extends DateMutableEntity<T>> void BreadcrumbCleanup(Timeline<T> timeline, long breadcrumb, LocalDate startDate, LocalDate newEnd) {
        if (newEnd.isBefore(startDate)) {
            cleanInternal(timeline, breadcrumb, newEnd, startDate);
        } else {
            cleanInternal(timeline, breadcrumb, startDate, newEnd);
        }
    }




    @Deprecated(forRemoval = true)
    public static <T extends DateMutableEntity<T>> void changePropagateBreadcrumb(Timeline<? extends T> timeline, TimelineChange<? super T> tc) {
        PropagateBreadcrumb((Timeline<T>) timeline,tc);
    }
    @Deprecated(forRemoval = true)
    public static <T extends DateMutableEntity<T>> void PropagateBreadcrumb(Timeline<T> timeline, TimelineChange<? super T> tc) {
        if (tc.isDeactivated()) {
            Feudalizer.LOGGER.error("Tried to propagate on deactivated change: " + tc);
            return;
        } else if (tc.isStatic()) {
            return;
        }


        final LocalDate startDate = tc.getStart();
        final LocalDate endDate = tc.getEnd();
        final long breadcrumb = tc.getFullID();

        TimelineState<T> state = timeline.getStateAt(startDate);
        LocalDate currentDate = startDate;
        while ((currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) && state != null) {
            state.forceInsertBC(breadcrumb, startDate);
            state = timeline.getNextState(currentDate);
            currentDate = state.getStart();
        }
    }


    public static <T extends DateMutableEntity<T>> List<TimelineChange<? super T>> getAllChanges(Timeline<T> timeline, TimelineState<T> ts, boolean includeInactive) {
        List<TimelineChange<? super T>> baseChanges;
        HashMap<Long, LocalDate> passiveChanges = ts.getBreadcrumbs();
        if (!includeInactive) {
            baseChanges = ts.getChanges();
        } else {
            baseChanges = ts.getAllCurrentChanges();
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
    public static <T extends DateMutableEntity<T>> TimelineChange<? super T> changeGetLastValidChange(Timeline<? extends T> timeline, TimelineState<? extends T> current, TimelineChange<? super T> soonToBeGone) {
        return (TimelineChange<? super T>) getLastValidChange((Timeline<T>) timeline, (TimelineState<T>) current,soonToBeGone);
    }
    @SuppressWarnings("unchecked")
    public static <T extends DateMutableEntity<T>> TimelineChange<? super T> getLastValidChange(Timeline<T> timeline, TimelineState<T> current, TimelineChange<? super T> soonToBeGone) {
        final boolean isPositive = soonToBeGone.isPositive();
        List<Class<TimelineChange<? super T>>> opposites = (List<Class<TimelineChange<? super T>>>) soonToBeGone.oppositeChanges();
        List<Class<TimelineChange<? super T>>> siblings = (List<Class<TimelineChange<? super T>>>) soonToBeGone.siblingChanges();
        Class<? extends TimelineChange<? super T>> cClass = (Class<? extends TimelineChange<? super T>>) soonToBeGone.getClass();
        LocalDate currentDate = current.getStart();
        while (true) {
            TimelineState<T> next = timeline.getStateBefore(currentDate);
            for (TimelineChange<? super T> change : next.getChanges()) {
                Class<TimelineChange<? super T>> changeClass = (Class<TimelineChange<? super T>>) change.getClass();
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

//    public static <T extends DateMutableEntity<T>> HashMap<Long, LocalDate> extendTrail(Timeline<T> timeline, TimelineState<T> lastState, LocalDate newState) {
//        HashMap<Long, LocalDate> breadcrumbs = new HashMap<>();
//        for (Map.Entry<Long, LocalDate> entry : lastState.getBreadcrumbs().entrySet()) {
//            breadcrumbs.put(entry.getKey(), entry.getValue());
//            TimelineChange<? super T> change = (TimelineChange<? super T>) followBreadcrumb(timeline, entry.getKey(), entry.getValue());
//            change.addEnd(newState);
//        }
//        for (TimelineChange<? super T> change : lastState.getChanges()) {
//            Pair<Long, LocalDate> pair = change.buildStateBreadcrumb();
//            breadcrumbs.put(pair.getLeft(), pair.getRight());
//            change.addEnd(newState);
//        }
//        return breadcrumbs;
//    }
//
//    public static <T extends DateMutableEntity<T>> HashMap<Long, LocalDate> extendTrail(Timeline<T> timeline, LocalDate date) {
//        return extendTrail(timeline, timeline.getStateBefore(date), date);
//    }
//    public static <M extends TimelineMapChange<M,K,V,? super T>,K,V,T extends DateMutableEntity<T>,R> R doMapChangeLeapFrog(
//            Timeline<? extends T> t, Pair<Long, LocalDate> firstLF, BiFunction<M, R, Integer> getCurrent, Class<M> mClass,
//            final int total, BiConsumer<M, R> makeChange, R result, boolean completeLastCycle, TimeDirection direction, boolean throwIfIncomplete, boolean pointless){
//        return doMapChangeLeapFrog((Timeline<T>) t,firstLF,getCurrent,mClass,total,makeChange,result,completeLastCycle,direction,throwIfIncomplete);
//    }
//    public static <M extends TimelineMapChange<M,K,V,? super T>,K,V,T extends DateMutableEntity<T>,R> R doMapChangeLeapFrog(
//            Timeline<? extends T> t, Pair<Long, LocalDate> firstLF, BiFunction<M, R, Integer> getCurrent, Class<M> mClass,
//            final int total, BiConsumer<M, R> makeChange, R result, boolean completeLastCycle, TimeDirection direction){
//        return doMapChangeLeapFrog((Timeline<T>) t,firstLF,getCurrent,mClass,total,makeChange,result,completeLastCycle,direction,true);
//    }

    public static <M extends TimelineMapChange<M,K,V,? super T>,K,V,T extends DateMutableEntity<T>,R> R doMapChangeLeapFrog(
            Timeline<? extends T> t, Pair<Long, LocalDate> firstLF, BiFunction<M, R, Integer> getCurrent, Class<M> mClass,
            final int total, BiConsumer<M, R> makeChange, R result, boolean completeLastCycle, TimeDirection direction, boolean throwIfIncomplete){

        int current = 0;
        Pair<Long, LocalDate> currentLeapFrog = firstLF;
        while(current < total){
            TimelineChange<? super T> n = (TimelineChange<? super T>) changeFollowBreadcrumb(t,currentLeapFrog.getKey(),currentLeapFrog.getValue());
            if (mClass.equals(n.getClass())){
                M m = (M) n;
                current += getCurrent.apply(m,result);
                if (current >= total && !completeLastCycle){
                    break;
                }
                makeChange.accept(m,result);
                if (direction == FORWARD){
                    currentLeapFrog = changeFindBreadcrumb(t,mClass.getName(),n.getEnd(),FORWARD,false);
                } else {
                    currentLeapFrog = m.getLeapfrog();
                }
                if (currentLeapFrog == null){
                    if (throwIfIncomplete){
                        throw new IllegalStateException("Could not find a leapfrog for " + m);
                    }
                    Feudalizer.LOGGER.debug("Leapfrog completed before count was complete for: " + mClass);
                    return result;
                    //throw new IllegalStateException("Could not find a leapfrog for " + m);
                }
            } else {
                throw new RuntimeException("Tried to do a leapfrog on a non-map change" + n);
            }
        }
        return result;
    }
//    public static <T extends DateMutableEntity<T>> TimelineChange<? super T> changeFollowBreadcrumb(Timeline<? extends T> timeline, long breadcrumb, LocalDate date) {
//        return (TimelineChange<? super T>) followBreadcrumb((Timeline<T>) timeline, breadcrumb, date);
//    }
//    public static <T extends DateMutableEntity<T>> TimelineChange<? super T> followBreadcrumb(Timeline<T> timeline, ChangeID breadcrumb, LocalDate date) {
//        return timeline.getStateAt(date).getChange(breadcrumb);
//    }
//    public static <T extends DateMutableEntity<T>> Pair<Long,LocalDate> changeFindBreadcrumb(Timeline<? extends T> timeline, String mClass, LocalDate startingDate, TimeDirection direction, boolean shouldThrow) {
//        return findBreadcrumb((Timeline<T>) timeline,mClass,startingDate,direction,shouldThrow);
//    }
//    public static <T extends DateMutableEntity<T>> Pair<Long,LocalDate> findBreadcrumb(Timeline<T> timeline, String mClass, LocalDate startingDate, TimeDirection direction, boolean shouldThrow) {
//        //TODO think on this. Potentially a 3 state if could get the same result. Though the reasonID check would (I think) be undeniable.
//        while (true) {
//            TimelineState<T> state;
//            if (direction == FORWARD) {
//                state = timeline.getStateAfter(startingDate);
//            } else {
//                state = timeline.getStateBefore(startingDate);
//            }
//
//            startingDate = state.getStart();
//            for (TimelineChange<? super T> change : state.getChanges()) {
//                long id = TimelineChange.generateID(mClass,startingDate,change.additionalIDVars());
//                if (change.getFullID() == id) {
//                    return Pair.of(id,startingDate);
//                }
//            }
//            if (state.isBoundary()) {
//                if (!shouldThrow) {
//                    throw new IllegalStateException("Could not find a valid change for " + mClass + " starting at " + startingDate + " and going " + direction + ".");
//                } else {
//                    return null;
//                }
//
//            }
//        }
//    }






}

