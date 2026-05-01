package com.base.reference;

import com.utilities.id.Identifiable;

public interface IReference<R extends IReference<R,T,I>,T extends Identifiable<I>,I> {
//    static final Cache<Long, IReference<?,?,?>> CACHE = CacheBuilder.newBuilder()
//            .expireAfterWrite(30, TimeUnit.MINUTES)
//            .maximumSize(4000)
//            .build();
//    static final Cache<String, Class<? extends Identifiable<?>>> CLASS_CACHE = CacheBuilder.newBuilder()
//            .expireAfterWrite(30, TimeUnit.MINUTES)
//            .maximumSize(4000)
//            .build();
    T get();
    Class<T> getType();
    I getID();
//    T getFromRegistry(Class<T> type, I id);

//    String getDelimiter();
//    String serializeI(I i);
//    I deserializeI(String id);
//    default JsonElement serialize(){
//        return new JsonPrimitive(StringToHex.encode(getType().getName())+getDelimiter()+StringToHex.encode(serializeI(getID())));
//    }
//    static <R extends IReference<R,T,I>,T extends Identifiable<I>,I> R deserialize(JsonElement object)  {
//        String[] splits = object.getAsString().split(getDelimiter());
//        String type = StringToHex.decode(splits[0]);
//        I id = deserializeI(StringToHex.decode(splits[1]));
//
//    }
//    private static <T extends Identifiable<I>,I> Class<T> buildClass(String name){
//        try {
//            Class<T> t = (Class<T>) Class.forName(name);
//            CLASS_CACHE.put(name,t);
//        } catch (ClassNotFoundException e) {
//            Feudalizer.LOGGER.error(e.getMessage());
//        }
//        throw new RuntimeException("Could not find class " + name);
//    }
}
