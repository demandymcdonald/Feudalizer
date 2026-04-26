package com.base.instanced;

import com.utilities.id.UUIDIdentifiable;
import com.utilities.serialization.JsonSerializable;

public interface IOInstance<IO extends IInstancedObject<IO,IN>,IN extends IOInstance<IO,IN>> extends UUIDIdentifiable,IIO, JsonSerializable {
    IOReference<IO> getBase();
    default IO getIO(){
        return getBase().get();
    };
}
