package com.utilities;

import com.google.gson.JsonObject;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Consumer;

public abstract class SuperclassRegistry <R extends SuperclassRegistry<R,T,OK,BA>,T extends SuperclassSerializable,OK, BA> implements ThreadMutable<R,
        ObjectRegistry<T,OK>> {
     private final Map<Class<? extends T>,Factory<? extends T>> factory_registry = Collections.synchronizedMap(new HashMap<>());
     private final ThreadLocal<ObjectRegistry<T,OK>> object_registry = ThreadLocal.withInitial(ObjectRegistry::new);
     private final String uniqueKey;
     public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SuperclassRegistry.class);
     protected SuperclassRegistry(String uniqueKey){
          this.uniqueKey = uniqueKey;
          ts_init();
     }
     @SuppressWarnings("unchecked")
     protected <tT extends T> Factory<tT> getFactory(Class<tT> subclass){
          Factory<tT> f = (Factory<tT>) factory_registry.get(subclass);
          if (f == null){
               throw new RuntimeException("No factory registered for " + subclass.getName());
          }
          return f;
     }
     protected <tT extends T> void registerFactory(Class<tT> subclass,Factory<tT> factory){
          factory_registry.put(subclass,factory);
     }
     public <tT extends T> tT loadObject(Class<tT> subclass,OK key, JsonObject object){
          tT t = getFactory(subclass).load(object);
          afterLoad(t);
          object_registry.get().put(subclass,key,t);
          return t;
     }
     public <tT extends T> tT createObject(Class<tT> subclass,OK key,BA argumentContainer){
          tT t = getFactory(subclass).create(argumentContainer);
          afterLoad(t);
          object_registry.get().put(subclass,key,t);
          return t;
     }
     public <tT extends T> void remove(Class<tT> subclass,OK key){
          object_registry.get().remove(subclass,key);
     }
     public <tT extends T> void remove(OK key){
          object_registry.get().remove(key);
     }
     public <tT extends T> void clear(){
          object_registry.get().clear();
     }
     public <tT extends T> void clearSingle(Class<tT> subclass){
          object_registry.get().clearSingle(subclass);
     }
     public <tT extends T> tT get(Class<tT> subclass,OK key){
          tT t = object_registry.get().get(subclass,key);
          if (t == null){
               throw new RuntimeException("No object of type " + subclass.getName() + " with key " + key);
          }
          return t;
     }
     public Collection<? extends T> getAll(){
          return object_registry.get().getAllValues();
     }
     public void doIterate(Consumer<T> consumer){

     }
     public <Tt extends T> void doSpecificIterate(Class<Tt> clas, Consumer<Tt> consumer){

     }
     @Override
     public final ObjectRegistry<T, OK> share() {
          return object_registry.get();
     }

     @Override
     public final void receiveShared(ObjectRegistry<T, OK> shared) {
          object_registry.set(shared);
     }

     @Override
     public final String uniqueKey() {
          return uniqueKey;
     }

     protected abstract class Factory<tT extends T>{
          Class<tT> subclassReference;
          abstract tT create(BA argumentContainer);
          abstract tT load(JsonObject object);
     }
     protected <tT extends T> void afterCreate(tT t){

     }
     protected <tT extends T> void afterLoad(tT t){

     }
}
