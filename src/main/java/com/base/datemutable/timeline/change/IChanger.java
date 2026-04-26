package com.base.datemutable.timeline.change;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;

public interface IChanger<T extends DateMutableEntity<T>> {
    DMEReference<T> getReference();
}
