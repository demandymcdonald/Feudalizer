package com.objects.culture.tenet.mutable.tenets.leadership;

import com.Global;
import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.ComplexReference;
import com.base.timeline.change.CultureAware;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.StateError;
import com.google.common.collect.ImmutableMap;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.ICultureOpinionated;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.interest.InterestGroup;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
public abstract class LeadershipConditions {
    public static final CultureCondition.Key BASE = new CultureCondition.Key() {
        @Override
        protected <TC extends TimelineChange<D> & CultureAware<TC, ?, D>, D extends DateMutableEntity<D> & ICultureObject> boolean isValid(TC change) {
            return change instanceof ILeaderChange;
        }
    };
    public static <D extends DateMutableEntity<D> & ILeadered<D>> CultureCondition<SentientCharacter<?>,D> TermLimit(Tenet t, int duration, ChronoUnit unit){
        return new TermLimit<>(t, duration, unit);
    }
    public static <D extends DateMutableEntity<D> & ILeadered<D>> CultureCondition<SentientCharacter<?>,D> Disenfranchised(Tenet t, Set<InterestGroup> groups){
        return new Disenfranchised<>(t, groups);
    }
    
    public static <D extends DateMutableEntity<D> & ILeadered<D>> CultureCondition<SentientCharacter<?>, D> Election(Tenet t, int percentageRequired) {
        return new Election<D>(t, percentageRequired);
    }
    private static class TermLimit<D extends DateMutableEntity<D> & ILeadered<D>> extends CultureCondition<SentientCharacter<?>,D>{
        private final int amount;
        private final ChronoUnit unit;
        public TermLimit(Tenet tenet, int amount, ChronoUnit unit) {
            super(tenet);
            this.amount = amount;
            this.unit = unit;
        }

        @Override
        public Set<Key> getKeys() {
            return Set.of(
                    BASE
            );
        }

        @Override
        public Condition.ShouldRun shouldRun() {
            return Condition.ShouldRun.ONCE_PER_STATE;
        }

        @Override
        protected <TC extends TimelineChange<? super D> & CultureAware<TC, SentientCharacter<?>, D>> Optional<StateError> doCheck(Tenet tenet, TC change, SentientCharacter<?> subject, Culture subjectCulture, D decider, Culture deciderCulture) {
            LocalDate date = change.getStart().plus(amount,unit);
            if (date.isAfter(Global.getDate())){
                return Optional.empty();
            }
            Global.setCurrentDate(date);//basically, end the term at the proper time if the user decides to save and exit.
            return Optional.of(CultureCondition.makeError(tenet,new ComplexReference("Term limit expired for holding: {} and character: {} on date: ",decider,subject,date),change));
        }
    }

    private static class Disenfranchised<D extends DateMutableEntity<D> & ILeadered<D>>  extends CultureCondition<SentientCharacter<?>,D> {
        Set<InterestGroup> groups;
        public Disenfranchised(Tenet tenet, Set<InterestGroup> group) {
            super(tenet);
            groups = new HashSet<>(group);
        }
        @Override
        public Set<Key> getKeys() {
            return Set.of(
                    BASE
            );
        }
        @Override
        public Condition.ShouldRun shouldRun() {
            return Condition.ShouldRun.ONCE_PER_STATE;
        }

        @Override
        protected <TC extends TimelineChange<? super D> & CultureAware<TC, SentientCharacter<?>, D>> Optional<StateError> doCheck(Tenet tenet, TC change, SentientCharacter<?> subject, Culture subjectCulture, D decider, Culture deciderCulture) {
            for(InterestGroup group : groups){
                if(group.isMember(subject.getReference().get())){
                    return Optional.of(CultureCondition.makeError(tenet,new ComplexReference("{} is barred from holding: {} because they're a member of {}",decider,subject,group),change));
                }
            }
            return Optional.empty();
        }
    }
    private static class Election<D extends DateMutableEntity<D> & ILeadered<D>>  extends CultureCondition<SentientCharacter<?>,D>{
        private final BoundInt requirement;
        public Election(Tenet tenet, int percentageRequired) {
            super(tenet);
            requirement = BoundInts.Percent(false, Math.abs(percentageRequired));
        }
        @Override
        public Set<Key> getKeys() {
            return Set.of(
                    BASE
            );
        }
        @Override
        protected <TC extends TimelineChange<? super D> & CultureAware<TC, SentientCharacter<?>, D>> Optional<StateError> doCheck(Tenet tenet, TC change, SentientCharacter<?> subject, Culture subjectCulture, D decider, Culture deciderCulture) {
            final Map<ICultureOpinionated,Integer> groups = ImmutableMap.copyOf(decider.getStakeholders());
            double forVote = 0;
            double againstVote = 0;
            double percentage = requirement.get()/100;
            int maxPos = (Acceptance.MAX_VALUE * 2);
            for (ICultureOpinionated group : groups.keySet()) {
                int weight = groups.get(group);
                AcceptanceContainer ac = group.getAcceptanceTenet(deciderCulture,false);
                double value = ac.value() + Acceptance.MAX_VALUE; //Doing this to make a -512 score 0, and a 512 score 1024
                double thresholdclear = value/maxPos;
                forVote += weight * thresholdclear;
                againstVote += weight * (1 - thresholdclear);
            }
            if (forVote/(forVote + againstVote) >= percentage) {
                return Optional.empty();
            } else {
                return Optional.of(CultureCondition.makeError(tenet,new ComplexReference("{} would lose a democratic election with a vote of: {} for and {} against.",subject,forVote,againstVote),change));
            }
        }
        @Override
        public Condition.ShouldRun shouldRun() {
            return Condition.ShouldRun.ONCE_PER_ENTITY;
        }
    }
}
