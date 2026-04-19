package com.base;
import com.Global;
import com.base.reference.DMEReference;
import com.objects.character.CharacterManager;
import com.objects.family.FamilyManager;
import com.objects.government.GovernmentManager;
import com.utilities.LoadingManager;
import com.google.gson.JsonObject;
import com.objects.title.TitleManager;

import java.util.*;

public class DMRegistry {
    private static final Map<Class<? extends DateMutableEntity<?>>,AbstractMutableManager<?,? extends DateMutableEntity<?>,? >> MANAGER_MAP = Collections.synchronizedMap(new HashMap<>());
    private static final List<AbstractMutableManager<?,? extends DateMutableEntity<?>,?>> MANAGERS = Collections.synchronizedList(new ArrayList<>());

    public static final CharacterManager CHARACTER_MANAGER = new CharacterManager();
    public static final TitleManager TITLE_MANAGER = new TitleManager();
    public static final FamilyManager FAMILY_MANAGER = new FamilyManager();
    public static final GovernmentManager HOUSE_MANAGER = new GovernmentManager();

//    private static <T extends DateMutableEntity<T>, M extends AbstractMutableManager<M,T,?,?>> M get(Class<T> type){
//
//    }
//    public static <T extends DateMutableEntity<T>,M extends AbstractMutableManager<T>> void addEntry(ObjectType database, M manager) {
//        map().put(database, manager);
//    }
//    public static <T extends DateMutableEntity<T>> void registerDateMutable(DateMutableEntity<T> entity) {
//        AbstractMutableManager<T> manager = getManager(entity.getClass());
//        if (manager == null) {
//            throw new RuntimeException("Could not find entry for " + entity.getClass());
//        }
//        manager.register(entity);
//    }
    public static <T extends DateMutableEntity<?>,M extends AbstractMutableManager<M,T,?>> M getManager(Class<T> dmeclass){
        if (MANAGER_MAP.containsKey(dmeclass)){
            return (M) MANAGER_MAP.get(dmeclass);
        } else {
            for (AbstractMutableManager<?,? extends DateMutableEntity<?>,?> m : MANAGERS) {
                if(m.accepts(dmeclass)){
                    MANAGER_MAP.put(dmeclass,m);
                    return (M) m;
                }
            }
            throw new RuntimeException("Could not find DME manager for " + dmeclass);
        }
    }
    public static void registerManager(AbstractMutableManager<?,? extends DateMutableEntity<?>,?> manager){
        MANAGERS.add(manager);
    }


    public static <T extends DateMutableEntity<?>,M extends AbstractMutableManager<M,T,?>> T getEntity(DMEReference<T> dme) {
        M manager = getManager(dme.getType());
        return manager.get(dme.getType(),dme.getID());
    }



    public static <T extends DateMutableEntity<T>,M extends AbstractMutableManager<M,T,?>> void load(DMEReference<?> header, JsonObject data){
        M manager = getManager(header.getType());
        manager.loadEntity(header,data);
    }
    public static void onDateChange(){
        final LoadingManager lm = Global.getLoadingManager();
        List<Runnable> r = new ArrayList<>();
        //To my future self: they are separate to reflect that it's two different stages of loading :)
        for (AbstractMutableManager<?,?> m : MANAGERS) {
            final Runnable r = m::onDateChange;
        }

            lm.forceComplete();//Only added for the logging and since these actions are chained together.
            lm.newStage("Linking Entities",getTotalRegistered(),r2,true);
        };
        lm.newStage("Updating Entities",getTotalRegistered(),r,true);
    }
    public static int getTotalRegistered(){
        int total = 0;
        for (AbstractMutableManager<?,?,?> m : MANAGERS) {
            total += m.getAll().size();
        }
        return total;
    }
//    public static boolean isMain(){
//        return Thread.currentThread().getName().equals("main");
//    }



//    protected record MutableEntry<R, T extends DateMutableEntity<R>,M extends AbstractMutableManager<R,T>>(M manager, Function<>) {
//
//    }
}
