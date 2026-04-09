//package com.base.reference;
//
//import com.Feudalizer;
//
//import com.base.DateMutableEntity;
//import com.google.gson.JsonObject;
//
//import javax.annotation.Nullable;
//import java.util.UUID;
//
//public class CompoundSR extends StateReference{
//    public static final String COMPOUND_SR_TYPE = "CompoundReference";
//    @Nullable String prefix;
//    @Nullable String suffix;
//    String Cached;
//    public CompoundSR(String prefix, String suffix, Class<T> type, UUID uuid) {
//        super(type, uuid);
//    }
//
//    public CompoundSR(String prefix, T entity, String suffix) {
//        super(entity);
//    }
//
//    @Override
//    public String parse() {
//        return Cached == null ? buildString(super.parse()) : Cached;
//    }
//    private String buildString (String superString){
//        StringBuilder builder = new StringBuilder();
//        if (prefix != null) builder.append(prefix);
//        if (superString != null) builder.append(" ").append(superString);
//        if (suffix != null) builder.append(" ").append(suffix);
//        Cached = builder.toString();
//        return Cached;
//    }
//    @Override
//    public JsonObject serialize() {
//        JsonObject object = super.serialize();
//        object.addProperty(TYPE_VARIABLE_NAME,COMPOUND_SR_TYPE);
//        object.addProperty("Prefix", prefix);
//        object.addProperty("Suffix", suffix);
//        return object;
//    }
//
//    public static <T extends DateMutableEntity<T>> DMEReference<T> deserializeCompound(JsonObject object) {
//        Class<T> r = null;
//        try {
//            r = (Class<T>) Class.forName(object.get("type").getAsString());
//        } catch (Exception e){
//            Feudalizer.LOGGER.error(e.getMessage());
//        }
//        String prefix = object.get("Prefix").getAsString();
//        String suffix = object.get("Suffix").getAsString();
//        String uuid = object.get("uuid").getAsString();
//        if (!uuid.equals("") && r != null) {
//            return new CompoundSR<>(prefix,suffix,r,UUID.fromString(uuid));
//        }
//        return null;
//    }
//}
