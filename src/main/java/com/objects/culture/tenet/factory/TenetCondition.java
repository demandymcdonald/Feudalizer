package com.objects.culture.tenet.factory;

import com.base.DateMutableEntity;
import com.base.condition.Condition;
import com.base.reference.DMEReference;
import com.base.timeline.change.CultureAware;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.error.StateError;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.tenet.Tenet;

import java.util.Optional;
import java.util.Set;


public abstract class TenetCondition<S extends DateMutableEntity<S> & ICultureObject, D extends DateMutableEntity<D> & ICultureObject, TC extends TimelineChange<? super D> & CultureAware<TC,S,D>>
        extends Condition<StateError, TC,Tenet,DMEReference<S>> {
    public TenetCondition(String id) {
        super(id);
    }

    @Override
    protected final Optional<StateError> doCheck(TC change, Tenet tenet, DMEReference<S> subject) {
        D d = (D) change.getOwner().get();
        return doCultureCheck(change, tenet,subject,subject.get().getCulture(),d.getReference(),d.getCulture());
    }
    protected abstract Optional<StateError> doCultureCheck(TC change, Tenet tenet, DMEReference<S> subject, Culture subjectCulture, DMEReference<D> decider, Culture deciderCulture);
    protected abstract Set<Key> getTCKey();
    @Override
    public final ShouldRun whenToRun() {
        return ShouldRun.ONCE_PER_STATE;
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
