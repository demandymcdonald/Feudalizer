package com.base;

import com.Global;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.utilities.Factory;
import com.utilities.serialization.SuperclassRegistry;
import com.utilities.ThreadManager;

import java.util.Map;
import java.util.UUID;

public abstract class AbstractMutableManager<M extends AbstractMutableManager<M,T>,T extends DateMutableEntity<?>>
        extends SuperclassRegistry<M,T,UUID,JsonObject> {
    protected AbstractMutableManager(String uniqueKey) {
        super(uniqueKey);
        DMRegistry.registerManager(this);
        for (Map.Entry<Class<? extends T>, Factory<? extends T,T,UUID,JsonObject>> e : getFactories().entrySet()){
            registerFactory(e.getKey(),e.getValue());
        }
        ts_init();
    }
    public boolean accepts(DMEReference<?> dme){
        return accepts(dme.getClass());
    }
    public boolean accepts(Class<?> clazz){
        return instanceClass().isAssignableFrom(clazz);
    }
    public void onLink(){
        doIterate(DateMutableEntity::onLink);
    }
    public void onDateChange(){
        doIterate(DateMutableEntity::onDateChange);
        doIterate(DateMutableEntity::link);
    }
    public <R extends DateMutableEntity<R>> R loadEntity(DMEReference<R> dme, JsonObject object) {
        if (!accepts(dme)){
            //This checks if this is the correct manager. It SHOULD always be by this phase.
            throw new IllegalArgumentException("Cannot load object of type " + dme.getType() + " into " + this.getClass());
        }
        DMEReference<? extends T> td = (DMEReference<? extends T>) dme;
        return  (R) super.loadObject(td.getType(), td.getID(), object);
    }
    public <R extends DateMutableEntity<R>> R updateOrLoadEntity(DMEReference<R> dme, JsonObject object) {
        if (!accepts(dme)){
            //This checks if this is the correct manager. It SHOULD always be by this phase.
            throw new IllegalArgumentException("Cannot load object of type " + dme.getType() + " into " + this.getClass());
        }
        DMEReference<? extends T> td = (DMEReference<? extends T>) dme;
        return  (R) super.updateOrLoad(td.getType(), td.getID(), object);

    }
    public <R extends DateMutableEntity<R>> R getEntity(DMEReference<R>  entity) {
        if (!accepts(entity)){
            //This checks if this is the correct manager. It SHOULD always be by this phase.
            throw new IllegalArgumentException("Cannot load object of type " + entity.getType() + " into " + this.getClass());
        }
        DMEReference<? extends T> td = (DMEReference<? extends T>) entity;
        T r = get(td.getType(),entity.getID());
        if (r == null && !ThreadManager.isMainThread()){
            JsonObject o = Global.getSandboxHandler().requestData(entity).join();
            return loadEntity(entity,o);
        }
        return (R) r;
    }
    public abstract Map<Class<? extends T>, Factory<? extends T,T,UUID,JsonObject>> getFactories();

    public abstract Class<?> instanceClass();




}
