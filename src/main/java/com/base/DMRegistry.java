package com.base;
import com.Feudalizer;
import com.GlobalVars;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineContainer;
import com.google.gson.JsonObject;
import com.simulation.people.*;
import com.simulation.title.Title;
import com.simulation.title.TitleManager;
import org.apache.commons.lang3.tuple.Triple;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.base.ObjectType.*;

public class DMRegistry {
    private static final ThreadLocal<HashMap<ObjectType,AbstractMutableManager<?,?>>> registry = ThreadLocal.withInitial(HashMap::new);
    private static final ConcurrentLinkedDeque<Triple<ObjectType,UUID, CompletableFuture<JsonObject>>> pendingLoads = new ConcurrentLinkedDeque<>();
    static {
        sandboxReInit();
    }

    public static <T extends DateMutableEntity<T,C>,M extends AbstractMutableManager<T,C>, C extends TimelineContainer<C>> void addEntry(ObjectType database, M manager) {
        registry.get().put(database, manager);
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
        return (M) registry.get().get(database);
    }
    public static <T extends DateMutableEntity<T,C>,M extends AbstractMutableManager<T,C>, C extends TimelineContainer<C>> M getEntry(String database) {
        return (M) registry.get().get(ObjectType.getByRegKey(database));
    }
    public static <T extends DateMutableEntity<T,C>, C extends TimelineContainer<C>> T getEntity(ObjectType type, UUID id){
        return (T) getEntry(type.getRegKey()).get(id);
    }
    public static JsonObject getEntityData(ObjectType type, UUID id){
        return getEntity(type,id).serialize();
    }

    public static void sandboxReInit() {
        addEntry(CHARACTER, new CharacterManager());
        addEntry(FAMILY, new FamilyManager());
        addEntry(HOUSE, new HouseManager());
        addEntry(TITLE, new TitleManager());
    }

    public static FamilyManager getFamilyManager() {
        return (FamilyManager) registry.get().get(FAMILY);
    }
    public static CharacterManager getCharacterManager() {
        return (CharacterManager) registry.get().get(CHARACTER);
    }
    public static HouseManager getHouseManager() {
        return (HouseManager) registry.get().get(HOUSE);
    }
    public static TitleManager getTitleManager() {
        return (TitleManager) registry.get().get(TITLE);
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
        for (AbstractMutableManager<?,?> m : registry.get().values()) {
            m.onGameStateChangeLoad(date);
        }
        //TODO Change GUI to show we're in linking stage if not sandbox.
        for (AbstractMutableManager<?,?> m : registry.get().values()) {
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
//    protected record MutableEntry<R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>>(M manager, Function<>) {
//
//    }
}
