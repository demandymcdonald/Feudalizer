package com.objects.culture.tenet.factory;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.ComplexReference;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.CultureAware;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.StateError;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.Tenet;

import java.util.List;
import java.util.Optional;

public abstract class CultureCondition<TC extends TimelineChange<? super D> & CultureAware<TC,S,D>, S extends DateMutableEntity<?> & ICultureObject, D extends DateMutableEntity<?> & ICultureObject> {
    private final Tenet tenet;
    public CultureCondition(Tenet tenet) {
        this.tenet = tenet;
    }
    public final Optional<StateError> check(TC change, DMEReference<? extends S> subject, DMEReference<? extends D> decider) {
        return check(change, subject.get(),decider.get());
    }
    public final Optional<StateError> check(TC change, S subject, D decider){
        return doCheck(tenet, change,subject,subject.getCulture(),decider,decider.getCulture());
    }
    public abstract List<Key> getKeys();
    public abstract Condition.ShouldRun shouldRun();
    protected abstract Optional<StateError> doCheck(Tenet tenet, TC change, S subject, Culture subjectCulture, D decider, Culture deciderCulture);

    protected final AcceptanceContainer getCulturalAcceptance(S subject, D decider, boolean factorSubjectTolerance) {
        return decider.getCompass().getAcceptanceContainer(subject.getCompass(),factorSubjectTolerance);
    }
    protected final <E extends DateMutableEntity<?> & ICultureObject> AcceptanceContainer getTenetAcceptance(E either){
        return either.getAcceptanceContainer(tenet.getTenetReference(),true);
    }
    protected static <S extends DateMutableEntity<?> & ICultureObject, D extends DateMutableEntity<?> & ICultureObject, TC extends TimelineChange<? super D> & CultureAware<TC,S,D>>
    StateError makeError(Tenet tenet, StateReference message, TC change){
        return new StateError("culture_condition/"+tenet.getDisplayID(),message,change).addIgnore().addEndCancel().addEndSave();
    }
    protected static <S extends DateMutableEntity<?> & ICultureObject, D extends DateMutableEntity<?> & ICultureObject,TC extends TimelineChange<? super D> & CultureAware<TC,S,D>> Optional<StateError>
    AcceptanceTooLow(Tenet tenet, TC change, S subject, D decider){
        return AcceptanceTooLow(tenet,change,subject,decider,false,false);
    }
    protected static <S extends DateMutableEntity<?> & ICultureObject, D extends DateMutableEntity<?> & ICultureObject,TC extends TimelineChange<? super D> & CultureAware<TC,S,D>> Optional<StateError>
    AcceptanceTooLow(Tenet tenet, TC change, S subject, D decider, boolean acceptedAllowed, boolean neutralAllowed){
        final Acceptance accept = decider.getAcceptanceContainer(subject,false).getAcceptance();
        final StateReference sr = ComplexReference.of("{} (decider) and {} (subject) are too incompatible culturally. The decider sees the subject as {}",decider.getDisplayID(),subject.getDisplayID(),accept.getDisplayName());
        switch(accept){
            case CORE,CORE_FANATIC,INTEGRATED -> {
                return Optional.empty();
            }
            case ACCEPTED -> {
                return acceptedAllowed ? Optional.empty() : Optional.of(makeError(tenet,sr,change));
            }
            case  TOLERATED,Acceptance.NEUTRAL,Acceptance.BARELY_TOLERATED -> {
                return neutralAllowed ? Optional.empty() : Optional.of(makeError(tenet,sr,change));
            }
            default -> {
                return Optional.of(makeError(tenet,sr,change));
            }
        }
    }
    public static abstract class Key {
        public <TC extends TimelineChange<D> & CultureAware<TC,?,D>,D extends DateMutableEntity<D> & ICultureObject> boolean isValidChange(TimelineChange<?> change){
            if (!(change instanceof CultureAware)) {
                return false;
            }
            return isValid((TC) change);
        }
        protected abstract <TC extends TimelineChange<D> & CultureAware<TC,?,D>,D extends DateMutableEntity<D> & ICultureObject> boolean isValid(TC change);
    }

}
