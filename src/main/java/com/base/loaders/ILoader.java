package com.base.loaders;

import com.Global;
import com.base.loaders.global.HandlerType;
import com.google.common.collect.ImmutableSet;

import java.util.UUID;

public interface ILoader {
    default UUID InstanceID(){
        return Global.INSTANCE_ID;
    }
    ImmutableSet<HandlerType> getHandled();
}
