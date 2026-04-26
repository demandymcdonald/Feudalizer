package com.base.instanced.single;

import com.base.instanced.IOInstance;

public abstract class IOISingle <T extends IOSingle<T,I,A>,I extends IOISingle<T,I,A>,A> implements IOInstance<T,I> {

}
