package com.base.timeline.change.display;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.utilities.IDisplayable;

public interface ITLDisplayable<T extends DateMutableEntity<T>  & ITLDisplayable<T>> extends IDisplayable {
    DisplayContainer<T> getDisplayable();
    DMEReference<T> getOwner();
    @Override
    default String getDisplayID(){
        return getDisplayable().getDisplayId();
    };

    @Override
    default String getDisplayName(){
        return getDisplayable().getName();
    };

    @Override
    default String getDescription(){
        return getDisplayable().getDescription();
    };
}
