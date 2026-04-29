package com.base.component.instanced.tri;

import com.base.component.instanced.IComponentInstance;

public abstract class IOITri<T extends IOTri<T,I,A,B,C>,I extends IOITri<T,I,A,B,C>,A,B,C> implements IComponentInstance<T,I> {


}
