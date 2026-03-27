package com.base;
import com.Feudalizer;
import com.GlobalVars;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.utilities.LoadingManager;
import com.google.gson.JsonObject;
import com.simulation.people.*;
import com.simulation.title.Title;
import com.simulation.title.TitleManager;
import com.utilities.ThreadManager;
import com.utilities.ThreadSpecific;
import org.apache.commons.lang3.tuple.Triple;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.base.ObjectType.*;

public class DMRegistry extends ThreadSpecific {
    private final HashMap<ObjectType,AbstractMutableManager<?,?>> registry = new HashMap<>();
    private static final ConcurrentLinkedDeque<Triple<ObjectType,UUID, CompletableFuture<JsonObject>>> pendingLoads = new ConcurrentLinkedDeque<>();
    private static ThreadLocal<DMRegistry> registryThreadLocal = new ThreadLocal<>();
    private static DMRegistry reg() {
        return registryThreadLocal.get();
    }
    private static HashMap<ObjectType,AbstractMutableManager<?,?>> map() {
        if (reg() == null) {
            throw new RuntimeException("DMRegistry not initialized for current thread!");
        }
        return reg().registry;
    }
//    static {
//        sandboxReInit();
//    }

    public static <T extends DateMutableEntity<T,C>,M extends AbstractMutableManager<T,C>, C extends TimelineContainer<C>> void addEntry(ObjectType database, M manager) {
        map().put(database, manager);
    }
    public static void registerDateMutable(DateMutableEntity<?,?> entity) {
        AbstractMutableManager<?,?> manager = getEntry(entity.getClass());
        if (manager == null) {
            throw new RuntimeException("Could not find entry for " + entity.getClass());
        }
        manager.register(entity);
    }
    public static <T extends DateMutableEntity<T,?>,M extends AbstractMutableManager<T,?>> M getEntry(Class<T> database){
        if(Title.class.isAssignableFrom(database)){
            return (M) getTitleManager();
        }
        if(Family.class.isAssignableFrom(database)){
            return (M) getFamilyManager();
        }
        if (House.class.isAssignableFrom(database)){
            return (M) getHouseManager();
        }
        if (BookCharacter.class.isAssignableFrom(database)){
            return (M) getCharacterManager();
        }
        Feudalizer.LOGGER.error("Unsupported database class type: " + database);
        return null;
    }
    public static <T extends DateMutableEntity<T,C>,M extends AbstractMutableManager<T,C>, C extends TimelineContainer<C>> M getEntry(ObjectType database) {
        return (M) map().get(database);
    }
    @Deprecated
    public static <T extends DateMutableEntity<T,C>,M extends AbstractMutableManager<T,C>, C extends TimelineContainer<C>> M getEntry(String database) {
        return (M) map().get(ObjectType.getByRegKey(database));
    }
    public static <T extends DateMutableEntity<T,C>, C extends TimelineContainer<C>> T getEntity(ObjectType type, UUID id){
        return (T) getEntry(type).get(id);
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

    public static ObjectType getObjectType(DateMutableEntity<?,?> entity){
        for (ObjectType type : ObjectType.values()) {
            if (type.getBaseClass().isAssignableFrom(entity.getClass())){
                return type;
            }
        }
        return null;
    }
    public static void load(DMEReference<?> header, JsonObject data){
        AbstractMutableManager<?,?> manager = getEntry(header.getType());
        manager.deserializeEntity(header.getUuid(),data);
    }
    public static void onDateChange(){
        final LoadingManager lm = GlobalVars.getLoadingManager();
        //To my future self: they are separate to reflect that it's two different stages of loading :)
        final Runnable r2 = () -> {
            for (AbstractMutableManager<?,?> m : map().values()) {
                m.onGameStateChangeLink();
            }
            lm.forceComplete();
        };
        final Runnable r = () -> {
            for (AbstractMutableManager<?,?> m : map().values()) {
                m.onGameStateChangeLoad(GlobalVars.CURRENT_DATE());
            }
            lm.forceComplete();
            lm.newStage("Linking Entities",getTotalRegistered(),r2,true);
        };
        lm.newStage("Updating Entities",getTotalRegistered(),r,true);
    }
    public static int getTotalRegistered(){
        int total = 0;
        for (AbstractMutableManager<?,?> m : map().values()) {
            total += m.getSize();
        }
        return total;
    }
    public static boolean isMain(){
        return Thread.currentThread().getName().equals("main");
    }
    /**
     * Updates the current date across the application and triggers corresponding actions
     * for all registered managers. Depending on whether the sandbox mode is enabled,
     * additional GUI or load-state changes may be applied.
     *
     * @param date The new date to set as the current application state.
     * @param sandbox Specifies whether the operation is performed in sandbox mode. If true,
     *                certain visual or blocking actions (like loading screens) are skipped.
     */
    public static void updateCurrentDate(LocalDate date, boolean sandbox){
        //TODO trigger loading screen that freezes the main display if not sandbox
        GlobalVars.setCurrentDate(date);
        for (AbstractMutableManager<?,?> m : map().values()) {
            m.onGameStateChangeLoad(date);
        }
        //TODO Change GUI to show we're in linking stage if not sandbox.
        for (AbstractMutableManager<?,?> m : map().values()) {
            m.onGameStateChangeLink();
        }
        //TODO end load state if not sandbox
    }

    public static synchronized CompletableFuture<JsonObject> addPendingLoad(ObjectType type, UUID id){
        CompletableFuture<JsonObject> cf = new CompletableFuture<>();
        pendingLoads.add(Triple.of(type,id,cf));
        return cf;
    }
    public static void onTick(){
        processHooks();
    }
    public static void processHooks(){
        for (Triple<ObjectType,UUID,CompletableFuture<JsonObject>> t : pendingLoads) {
            t.getRight().complete(getEntity(t.getLeft(),t.getMiddle()).serialize());
        }
        pendingLoads.clear();
    }

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
