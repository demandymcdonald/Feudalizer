package com.base.component.instanced.bi;

import com.base.component.instanced.IComponentInstance;

public abstract class IOIBi<T extends IOBi<T,I,A,B>,I extends IOIBi<T,I,A,B>,A,B> implements IComponentInstance<T,I> {

}
