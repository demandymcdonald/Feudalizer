package com.base.component.instanced;

import com.base.component.ComponentReference;
import com.base.component.IComponentLogged;
import com.utilities.id.UUIDIdentifiable;
import com.utilities.serialization.JsonSerializable;
import com.utilities.serialization.SuperclassSerializable;

public interface IComponentInstance<IO extends IInstancedComponent<IO,IN>,IN extends IComponentInstance<IO,IN>> extends UUIDIdentifiable, IComponentLogged, SuperclassSerializable<IN> {
    ComponentReference<IO> getBase();
    default IO getIO(){
        return getBase().get();
    };
}
