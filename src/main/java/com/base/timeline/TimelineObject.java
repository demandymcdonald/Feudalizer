package com.base.timeline;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.state.TimelineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public abstract class TimelineObject<T extends DateMutableEntity<T>>  {

    protected final DMEReference<T> owner;
    protected TimelineObject(DMEReference<T> owner) {
        this.owner = owner;
    }







    protected static final Logger logger = LoggerFactory.getLogger(TimelineObject.class);
    public final <TC extends TimelineChange<? super T>> TC followBreadcrumb(Timeline<T> timeline, ChangeID breadcrumb) {
        TimelineState<T> ts = timeline.getStateAtExact(breadcrumb.getDate(),false);
        if (ts == null){
            logger.warn("Tried to follow breadcrumb whose state doesn't exist: " + breadcrumb);
            return null;
        }
        TC tc = ts.getChange(breadcrumb);
        if (tc == null){
            logger.warn("Tried to follow breadcrumb whose change doesn't exist: " + breadcrumb);
            return null;
        }
        return tc;
    }
    public final <TC extends TimelineChange<? super T>> List<TC> findChangeByClassID(Timeline<T> timeline, LocalDate starting, TimeDirection direction, TC example,
                                                                                     final boolean includeDeactivated) {
        return findChangeByClassID(timeline,starting,direction,includeDeactivated,ChangeID.buildChangeClassID(example.getClass().getName()));
    }
    public final <TC extends TimelineChange<? super T>> List<TC> findChangeByClassID(Timeline<T> timeline, LocalDate starting, TimeDirection direction, Class<TC> classType,
                                                       final boolean includeDeactivated) {
        return findChangeByClassID(timeline,starting,direction,includeDeactivated,ChangeID.buildChangeClassID(classType.getName()));
    }
    public final <TC extends TimelineChange<? super T>> List<TC> findChangeByClassID(Timeline<T> timeline, LocalDate starting, TimeDirection direction, final boolean includeDeactivated, Long classID){
        BiFunction<Long,TimelineState<T>, Optional<List<TC>>> change = (l,ts) -> {
            List<TC> tc = ts.getChangesByClassID(l);
            if (!includeDeactivated){
                tc.removeIf(TimelineChange::isDeactivated);
            }
            if (tc.isEmpty()){
                return Optional.empty();
            }
            return Optional.of(tc);
        };
        return iterateAndFind(timeline,starting,direction,change,classID);
    }
    public final ChangeID[] findBreadcrumbByClassID(Timeline<T> timeline, LocalDate starting, TimeDirection direction, final boolean includeDeactivated, Long classID){
        List<TimelineChange<? super T>> changes = findChangeByClassID(timeline,starting,direction,includeDeactivated,classID);
        if (changes == null){
            return new ChangeID[0];
        } else {
            return changes.stream().map(TimelineChange::getID).toArray(ChangeID[]::new);
        }
    }
    public final ChangeID[] findBreadcrumbByClassID(Timeline<T> timeline, LocalDate starting, TimeDirection direction, Class<TimelineChange<?>> classType,
             final boolean includeDeactivated){
        return findBreadcrumbByClassID(timeline,starting,direction,includeDeactivated,ChangeID.buildChangeClassID(classType.getName()));
    }

    private final BiPredicate<ChangeID,TimelineState<T>> predicate = (id,ts) -> {
        return ts == null || ts.getChange(id) == null;
    };
    public final <TC extends TimelineChange<? super T>> void removeBreadcrumbs(Timeline<T> timeline, TC changeBeingRemoved, boolean replaceWithLast){
        BiConsumer<ChangeID,TimelineState<T>> consumer;
        if (!replaceWithLast){
            final TimelineChange<?> find = timeline.findChangeByClassID(timeline,changeBeingRemoved.getStart().minusDays(1),
                    TimeDirection.BACKWARD, getChangeClass(changeBeingRemoved), false).get(0);
            find.setEnd(changeBeingRemoved.getEnd());
            consumer = (id,ts) -> {
                ts.removeBreadcrumb(id);
                ts.insertBreadcrumb(find.getID());
            };
        } else {
            consumer = (id,ts) -> {
                ts.removeBreadcrumb(id);

            };
        }
        final ChangeID id = changeBeingRemoved.getID();
        iterateAndDo(timeline,changeBeingRemoved,TimeDirection.FORWARD,consumer,predicate,id);
    }
    public final void propagateBreadcrumbs(Timeline<T> timeline, TimelineChange<? super T> change){
        BiConsumer<ChangeID,TimelineState<T>> consumer = (id,ts) -> {
            ts.insertBreadcrumb(id);
        };

        final ChangeID id = change.getID();
        iterateAndDo(timeline,change,TimeDirection.FORWARD,consumer,id);
    }
    public final void moveBreadcrumbStart(Timeline<T> timeline, TimelineChange<? super T> change){
        BiConsumer<ChangeID,TimelineState<T>> consumer = (id,ts) -> {
            TimelineChange<? super T> c = ts.getChange(id);
            if (c != null){
                c.getID().setDate(change.getStart());
            }
        };
        final ChangeID id = change.getID();
        iterateAndDo(timeline,change,TimeDirection.FORWARD,consumer,predicate,id);
    }

    public final DMEReference<T> getOwner() {
        return owner;
    }
    public static <T extends DateMutableEntity<T>,TL extends Timeline<T>,S> void iterateAndDo(
            TL t, TimelineChange<? super T> change, TimeDirection direction,
            BiConsumer<S,TimelineState<T>> consumer,  final S s){
        final BiPredicate<S, TimelineState<T>> predicate = (id,ts) -> {
            return ts == null;
        };
        iterateAndDo(t,change,direction,consumer,predicate,s);
    }
    protected List<ChangeID> buildFullBreadcrumbList(TimelineState<T> t){
        List<ChangeID> toReturn = new java.util.ArrayList<>();
        for (ChangeID id : t.getBreadcrumbs()) {
            toReturn.add(id);
        }
        for (TimelineChange<? super T> c : t.getChanges()){
            toReturn.add(c.getID());
        }
        return toReturn;
    }
    /**
     * Iterates over the timeline states based on a specified {@code TimelineChange}, performs operations using a
     * {@code BiConsumer}, and determines when to stop based on a {@code BiPredicate}.
     *
     * @param <T>        The type of the timeline entity, extending {@code DateMutableEntity<T>}.
     * @param <TL>       The type of the timeline, extending {@code Timeline<T>}.
     * @param <S>        The type of the external state used by the {@code BiConsumer} and {@code BiPredicate}.
     * @param t          The timeline containing the entities to iterate over.
     * @param change     The {@code TimelineChange} defining the interval in the timeline for iteration.
     * @param direction  The direction of iteration, either forward or backward through the timeline.
     * @param consumer   A {@code BiConsumer} that performs operations using the external state and the current timeline state.
     * @param shouldEnd  A {@code BiPredicate} that determines whether the iteration should terminate based on the external
     *                   state and the current timeline state.
     * @param s          The external state used by the {@code BiConsumer} and {@code BiPredicate}.
     */
    public static <T extends DateMutableEntity<T>,TL extends Timeline<T>,TC extends TimelineChange<? super T>,S> void iterateAndDo(
            TL t, TC change, TimeDirection direction,
            BiConsumer<S,TimelineState<T>> consumer, BiPredicate<S, TimelineState<T>> shouldEnd, final S s){
        LocalDate currentDate = change.getStart().minusDays(2);
        final LocalDate endDate = change.getEnd();

        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)){
            TimelineState<T> ts;
            if (direction == TimeDirection.FORWARD){
                ts = t.getStateAfter(currentDate);
            } else {
                ts = t.getStateBefore(currentDate);
            }
            if (shouldEnd.test(s,ts)){
                return;
            }
            currentDate = ts.getStart();
            if (ts == null){
                return;
            }
            consumer.accept(s,ts);
        }
    }
    public static <T extends DateMutableEntity<T>,TL extends Timeline<T>,S,R> R iterateAndFind(
      TL t, LocalDate start, TimeDirection direction, BiFunction<S,TimelineState<T>, Optional<R>> getter, S s){
        return iterateAndFind(t,start,direction,false,true,getter,s);
    }
    public static <T extends DateMutableEntity<T>,TL extends Timeline<T>,S,R> R iterateAndFind(
            TL t, LocalDate start, TimeDirection direction, boolean shouldThrow,boolean includeStart,
            BiFunction<S,TimelineState<T>, Optional<R>> getter, S s){
        LocalDate currentDate;
        if (includeStart){
            if (direction == TimeDirection.FORWARD){
                currentDate = start.minusDays(1);
            } else {
                currentDate = start.plusDays(1);
            }
        } else {
            currentDate = start;
        }
        while (currentDate != null){
            TimelineState<T> ts;
            if (direction == TimeDirection.FORWARD){
                ts = t.getStateAfter(currentDate);
            } else {
                ts = t.getStateBefore(currentDate);
            }
            if (ts == null){
                if (shouldThrow){
                    throw new IllegalStateException("Could not find a state for " + currentDate);
                } else {
                    logger.debug("Could not find a state for {} in iterateAndFind",currentDate);
                    return null;
                }
            }
            currentDate = ts.getStart();
            Optional<R> result = getter.apply(s,ts);
            if (result.isPresent()){
                return result.get();
            }
        }
        if (shouldThrow){
            throw new IllegalStateException("Could not find a result for " + start);
        } else {
            return null;
        }
    }



    public static <T extends DateMutableEntity<T>,TC extends TimelineChange<? super T>,C> void iterateMap(
            BiFunction<Timeline<T>,TC,ChangeID> getNext, BiConsumer<TC,C> toDo, BiPredicate<LocalDate, C> shouldContinue,
            Timeline<T> timeline, ChangeID starting, C map, boolean shouldThrow){
        ChangeID current = starting;
        boolean contin = true;
        while(current != null && contin){
            TC tc = (TC) timeline.followBreadcrumb(current);
            toDo.accept(tc,map);
            current = getNext.apply(timeline,tc);
            try {
                //Literally here in case I forget that shouldContinue probably shouldn't do a timeline check.
                //But anything that uses the change SHOULD check for nulls.
                contin = shouldContinue.test(current.getDate(), map);
            } catch (Exception e){
                if (shouldThrow){
                    throw new IllegalStateException("Could not find a state for " + e);
                } else {
                    logger.error("Error suppressed on iterateMap: ",e);
                    contin = false;
                }
            }
            if (current == null && contin){
                if (shouldThrow){
                    throw new IllegalStateException("Could not find a state for iterateMap: " + map);
                } else {
                    logger.error("Error suppressed on iterateMap: current == null but continue says to continue: " + map);
                }
            }
        }
    }
    public static <T extends DateMutableEntity<T>,TC extends TimelineChange<? super T>> Class<TC> getChangeClass(TC tc){
        return (Class<TC>) tc.getClass();
    }
    public abstract LocalDate getStart();
    public abstract LocalDate getEnd();
    public abstract void setDirty();
}
