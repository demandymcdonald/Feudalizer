package com.objects.culture.tenet.types.mutable.tenets.general;

import com.Global;
import com.base.condition.Condition;
import com.base.reference.ComplexReference;
import com.base.timeline.error.StateError;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.Tenet;
import com.objects.title.Title;
import com.objects.title.change.TitleSingleChange;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class LeadershipConditions {

    public static <D extends Title<D>> CultureCondition<TitleSingleChange.SetHolder<D>,SentientCharacter<?>,D> buildTermLimit(Tenet t, int duration, ChronoUnit unit){
        return new TermLimit<>(t, duration, unit);
    }


    private static class TermLimit<D extends Title<D>> extends CultureCondition<TitleSingleChange.SetHolder<D>,SentientCharacter<?>,D>{
        private final int amount;
        private final ChronoUnit unit;
        public TermLimit(Tenet tenet, int amount, ChronoUnit unit) {
            super(tenet);
            this.amount = amount;
            this.unit = unit;
        }

        @Override
        public List<Key> getKeys() {
            return List.of();
        }

        @Override
        public Condition.ShouldRun shouldRun() {
            return Condition.ShouldRun.ONCE_PER_STATE;
        }

        @Override
        protected Optional<StateError> doCheck(Tenet tenet, TitleSingleChange.SetHolder<D> change, SentientCharacter<?> subject, Culture subjectCulture, D decider, Culture deciderCulture) {
            LocalDate date = change.getStart().plus(amount,unit);
            if (date.isAfter(Global.getDate())){
                return Optional.empty();
            }
            Global.setCurrentDate(date);//basically, end the term at the proper time if the user decides to save and exit.
            return Optional.of(CultureCondition.makeError(tenet,new ComplexReference("Term limit expired for title: {} and character: {} on date: ",decider,subject,date),change));
        }
    }



}
