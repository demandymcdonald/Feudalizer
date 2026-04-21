package com.base.timeline.change;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;

public interface IChanger<T extends DateMutableEntity<T>> {
    DMEReference<T> getReference();
}
