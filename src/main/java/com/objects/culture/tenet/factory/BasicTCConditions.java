package com.objects.culture.tenet.factory;

import com.base.DateMutableEntity;
import com.base.timeline.change.CultureAware;
import com.base.timeline.change.TimelineChange;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.object.ICultureObject;
import com.objects.title.Title;
import com.objects.title.change.TitleSingleChange;

public class BasicTCConditions {

    public static final TenetCondition.Key IS_GRANT_TITLE = new TenetCondition.Key(){
        @Override
        protected <TC extends TimelineChange<D> & CultureAware<TC, ?, D>, D extends DateMutableEntity<D> & ICultureObject> boolean isValid(TC change) {
            return change instanceof TitleSingleChange.setHolderGrant<?> || change instanceof TitleSingleChange.setHolderInherit<?>;
        }
    };
    public static final TenetCondition.Key PERSON_FOR_TITLE = new TenetCondition.Key(){
        @Override
        protected <TC extends TimelineChange<D> & CultureAware<TC, ?, D>, D extends DateMutableEntity<D> & ICultureObject> boolean isValid(TC change) {
            return change.getSubject().get() instanceof SentientCharacter<?> && change.getDecider().get() instanceof Title<?>;
        }
    };
    public static final TenetCondition.Key INTERPERSONAL = new TenetCondition.Key(){
        @Override
        protected <TC extends TimelineChange<D> & CultureAware<TC, ?, D>, D extends DateMutableEntity<D> & ICultureObject> boolean isValid(TC change) {
            return change.getSubject().get() instanceof SentientCharacter<?> && change.getDecider().get() instanceof SentientCharacter<?>;
        }
    };
    public static final TenetCondition.Key IS_MARRY = new TenetCondition.Key(){

        @Override
        public boolean isValid(TimelineChange<?> change) {
            return false; //
        }
    };

}
