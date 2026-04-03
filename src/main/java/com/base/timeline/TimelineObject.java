package com.base.timeline;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.changes.TimelineChange;
import com.base.timeline.state.TimelineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
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
    public final TimelineChange<? super T> followBreadcrumb(Timeline<T> timeline, ChangeID breadcrumb) {
        TimelineState<T> ts = timeline.getStateAtExact(breadcrumb.getDate(),false);
        if (ts == null){
            logger.warn("Tried to follow breadcrumb whose state doesn't exist: " + breadcrumb);
            return null;
        }
        TimelineChange<? super T> tc = ts.getChange(breadcrumb);
        if (tc == null){
            logger.warn("Tried to follow breadcrumb whose change doesn't exist: " + breadcrumb);
            return null;
        }
        return tc;
    }
    public final List<TimelineChange<? super T>> findChangeByClassID(LocalDate starting, TimeDirection direction, String classType,
                                                                     final boolean includeDeactivated) {
        return findChangeByClassID(this.owner.get().getTimeline(), starting,direction,includeDeactivated,ChangeID.buildChangeClassID(classType));
    }
    public final List<TimelineChange<? super T>> findChangeByClassID(Timeline<T> timeline, LocalDate starting, TimeDirection direction, Class<TimelineChange<?>> classType,
                                                       final boolean includeDeactivated) {
        return findChangeByClassID(timeline,starting,direction,includeDeactivated,ChangeID.buildChangeClassID(classType.getName()));
    }
    public final List<TimelineChange<? super T>> findChangeByClassID(Timeline<T> timeline, LocalDate starting, TimeDirection direction, final boolean includeDeactivated, Long classID){
        BiFunction<Long,TimelineState<T>, Optional<List<TimelineChange<? super T>>>> change = (l,ts) -> {
            List<TimelineChange<? super T>> tc = ts.getChangesByClassID(l);
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
    public final void removeBreadcrumbs(Timeline<T> timeline, TimelineChange<? super T> changeBeingRemoved, boolean replaceWithLast){
        BiConsumer<ChangeID,TimelineState<T>> consumer;
        if (!replaceWithLast){
            final TimelineChange<?> find = timeline.findChangeByClassID(timeline,changeBeingRemoved.getStart().minusDays(1),
                    TimeDirection.BACKWARD,(Class<TimelineChange<?>>) changeBeingRemoved.getClass(), false).get(0);
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
    public static <T extends DateMutableEntity<T>,TL extends Timeline<T>,S> void iterateAndDo(
            TL t, TimelineChange<? super T> change, TimeDirection direction,
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
    public abstract LocalDate getStart();
    public abstract LocalDate getEnd();

}
