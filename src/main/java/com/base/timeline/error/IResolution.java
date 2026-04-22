package com.base.timeline.error;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;

import java.util.Date;

public interface IResolution<T extends DateMutableEntity<?>,A,B,C,D> extends IDisplayable, StringIdentifiable {
    int getPriority();
    SandboxCode getExpectedCode();
    boolean isExclusive();
    SandboxCode resolve(DMEReference<? extends T> reference, A a, B b, C c, D d);



}
