package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.objects.culture.object.ICultureObject;

public interface CultureAware<C extends TimelineChange<? super D> & CultureAware<C,S,D>, S extends DateMutableEntity<?> & ICultureObject, D extends DateMutableEntity<?> & ICultureObject> {

    DMEReference<? extends S> getSubject();
    DMEReference<? extends D> getDecider();
}
