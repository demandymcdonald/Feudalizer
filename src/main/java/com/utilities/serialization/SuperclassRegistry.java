package com.utilities.serialization;

import com.google.gson.JsonObject;
import com.utilities.Factory;
import com.utilities.ObjectRegistry;
import com.utilities.ThreadMutable;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Consumer;

public abstract class SuperclassRegistry <R extends SuperclassRegistry<R,T,OK,BA>,T extends SuperclassSerializable,OK, BA>
implements ThreadMutable<R, ObjectRegistry<T,OK>> {
     private final Map<Class<? extends T>, Factory<? extends T,T,OK,BA>> factory_registry = Collections.synchronizedMap(new HashMap<>());
     private final ThreadLocal<ObjectRegistry<T,OK>> object_registry = ThreadLocal.withInitial(ObjectRegistry::new);
     private final String uniqueKey;
     public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SuperclassRegistry.class);
     protected SuperclassRegistry(String uniqueKey){
          this.uniqueKey = uniqueKey;
     }
     @SuppressWarnings("unchecked")
     protected <tT extends T> Factory<tT,T,OK,BA> getFactory(Class<tT> subclass){
          Factory<tT,T,OK,BA> f = (Factory<tT,T,OK,BA>) factory_registry.get(subclass);
          if (f == null){
               throw new RuntimeException("No factory registered for " + subclass.getName());
          }
          return f;
     }
     protected <tT extends T> void registerFactory(Class<? extends T> subclass,Factory<? extends T,T,OK,BA> factory){
          factory_registry.put(subclass,factory);
     }
     protected <tT extends T> tT updateOrLoad(Class<tT> subclass,OK key, JsonObject object) {
         tT tt =  object_registry.get().get(subclass,key);
         if (tt != null){
              tt.deserialize(object);
              return tt;
         } else {
              return loadObject(subclass,key,object);
         }
     }
     protected <tT extends T> tT loadObject(Class<tT> subclass,OK key, JsonObject object){

          tT t = getFactory(subclass).load(key,object);
          afterLoad(t);
          object_registry.get().put(subclass,key,t);
          return t;
     }
     protected <tT extends T> tT createObject(Class<tT> subclass,OK key,BA argumentContainer){
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
          tT t = object_registry.get().get((Class<tT>) subclass,key);
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
     public void receiveShared(ObjectRegistry<T, OK> shared) {
          object_registry.set(shared);
     }

     @Override
     public ObjectRegistry<T, OK> share() {
          return object_registry.get();
     }

     protected <tT extends T> void afterCreate(tT t){

     }
     protected <tT extends T> void afterLoad(tT t){

     }

     @Override
     public String uniqueKey() {
          return uniqueKey;
     }
}
