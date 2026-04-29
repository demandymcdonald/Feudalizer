package com.base.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ComponentRegistry implements IComponentLogged {
    private static final Map<Class<? extends IComponent<?>>, ComponentManager<?>> MANAGER_MAP = Collections.synchronizedMap(new HashMap<>());
    protected static Map<Class<? extends IComponent<?>>, ComponentManager<?>> getManagerMap() {
        return MANAGER_MAP;
    }
    public static <T extends IComponent<?>> ComponentManager<T> getManager(Class<? extends T> type){
        ComponentManager<?> man = MANAGER_MAP.get(type);
        if(man == null){
            for(Class<? extends IComponent<?>> c : MANAGER_MAP.keySet()){
                if(c.isAssignableFrom(type)){
                    man = (ComponentManager<? super T>) MANAGER_MAP.get(c);
                    MANAGER_MAP.put(type,man);
                    break;
                }
            }
            LOGGER.warn(type.getName() + " does not have a prebuilt ComponentManager, creating new one");
            man = new ComponentManager<T>(type) {};
            //throw new RuntimeException("Could not find ComponentManager for " + type.getName());
        }
        return (ComponentManager<T>) man;
    }
    public static void registerManager(ComponentManager<?> manager, Class<? extends IComponent<?>>... types){
        for(Class<? extends IComponent<?>> type : types){
            MANAGER_MAP.put(type,manager);
        }
    }
}
