package com.base;
import com.GlobalVars;
import com.base.reference.DMEReference;
import com.simulation.character.CharacterManager;
import com.utilities.LoadingManager;
import com.google.gson.JsonObject;
import com.simulation.people.*;
import com.simulation.title.TitleManager;
import com.utilities.ThreadManager;
import com.utilities.ThreadSpecific;

import java.util.HashMap;
import java.util.UUID;

import static com.base.ObjectType.*;

public class DMRegistry extends ThreadSpecific {
    private final HashMap<ObjectType,AbstractMutableManager<?>> registry = new HashMap<>();
    private static ThreadLocal<DMRegistry> registryThreadLocal = new ThreadLocal<>();
    private static DMRegistry reg() {
        return registryThreadLocal.get();
    }
    private static HashMap<ObjectType,AbstractMutableManager<?>> map() {
        if (reg() == null) {
            throw new RuntimeException("DMRegistry not initialized for current thread!");
        }
        return reg().registry;
    }

    private static <T extends DateMutableEntity<T>, M extends AbstractMutableManager<T>> M get(ObjectType type){
        return (M) map().get(type);
    }
    public static <T extends DateMutableEntity<T>,M extends AbstractMutableManager<T>> void addEntry(ObjectType database, M manager) {
        map().put(database, manager);
    }
    public static <T extends DateMutableEntity<T>> void registerDateMutable(DateMutableEntity<T> entity) {
        AbstractMutableManager<T> manager = getManager(entity.getClass());
        if (manager == null) {
            throw new RuntimeException("Could not find entry for " + entity.getClass());
        }
        manager.register(entity);
    }
    public static <T extends DateMutableEntity<T>,M extends AbstractMutableManager<T>> M getManager(Class<T> dmeclass){
        ObjectType t = ObjectType.getByClass(dmeclass);
        return get(t);
    }
    public static <T extends DateMutableEntity<T>,M extends AbstractMutableManager<T>> M getManager(ObjectType type) {
        return get(type);
    }
    @Deprecated
    public static <T extends DateMutableEntity<T>,M extends AbstractMutableManager<T>> M getManager(String dmeTypeName) {
        return get(ObjectType.getByRegKey(dmeTypeName));
    }
    public static <T extends DateMutableEntity<T>> T getEntity(ObjectType type, UUID id){
        return (T) getManager(type).get(id);
    }
    public static JsonObject getEntityData(ObjectType type, UUID id){
        return getEntity(type,id).serialize();
    }

    public static void sandboxReInit() {
        map().put(CHARACTER, new CharacterManager());
        map().put(FAMILY, new FamilyManager());
        map().put(HOUSE, new HouseManager());
        map().put(TITLE, new TitleManager());
    }

    public static FamilyManager getFamilyManager() {
        return (FamilyManager) map().get(FAMILY);
    }
    public static CharacterManager getCharacterManager() {
        return (CharacterManager) map().get(CHARACTER);
    }
    public static HouseManager getHouseManager() {
        return (HouseManager) map().get(HOUSE);
    }
    public static TitleManager getTitleManager() {
        return (TitleManager) map().get(TITLE);
    }

    public static ObjectType getObjectType(DateMutableEntity<?> entity){
        for (ObjectType type : ObjectType.values()) {
            if (type.getBaseClass().isAssignableFrom(entity.getClass())){
                return type;
            }
        }
        return null;
    }
    public static <T extends DateMutableEntity<T>> void load(DMEReference<T> header, JsonObject data){
        AbstractMutableManager<T> manager = getManager(header.getType());
        manager.deserializeEntity(header.getID(),data);
    }
    public static void onDateChange(){
        final LoadingManager lm = GlobalVars.getLoadingManager();
        //To my future self: they are separate to reflect that it's two different stages of loading :)
        final Runnable r2 = () -> {
            for (AbstractMutableManager<?> m : map().values()) {
                m.onGameStateChangeLink();
            }
        };
        final Runnable r = () -> {
            for (AbstractMutableManager<?> m : map().values()) {
                m.onGameStateChangeLoad(GlobalVars.getDate());
            }
            lm.forceComplete();//Only added for the logging and since these actions are chained together.
            lm.newStage("Linking Entities",getTotalRegistered(),r2,true);
        };
        lm.newStage("Updating Entities",getTotalRegistered(),r,true);
    }
    public static int getTotalRegistered(){
        int total = 0;
        for (AbstractMutableManager<?> m : map().values()) {
            total += m.getSize();
        }
        return total;
    }
//    public static boolean isMain(){
//        return Thread.currentThread().getName().equals("main");
//    }


    @Override
    public Type specificTypeName() {
        return Type.DM_REGISTRY;
    }
    @Override
    public void onThreadInit() {
        registryThreadLocal.set(ThreadManager.getThreadSpecific(Type.DM_REGISTRY));
        sandboxReInit();
    }

//    protected record MutableEntry<R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>>(M manager, Function<>) {
//
//    }
}
