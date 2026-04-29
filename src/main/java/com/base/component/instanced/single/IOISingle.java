package com.base.component.instanced.single;

import com.base.component.instanced.IComponentInstance;

public abstract class IOISingle <T extends IOSingle<T,I,A>,I extends IOISingle<T,I,A>,A> implements IComponentInstance<T,I> {

}
