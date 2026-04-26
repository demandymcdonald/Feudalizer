package com.base.instanced.bi;

import com.base.instanced.IOInstance;

public abstract class IOIBi<T extends IOBi<T,I,A,B>,I extends IOIBi<T,I,A,B>,A,B> implements IOInstance<T,I> {

}
