package com.base.component.instanced;

import com.base.component.IComponent;

public interface IInstancedComponent<T extends IInstancedComponent<T, I>, I extends IComponentInstance<T, I>> extends IComponent<T>{

}
