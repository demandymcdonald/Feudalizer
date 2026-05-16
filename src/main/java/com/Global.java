package com;

import com.base.DMRegistry;
import com.base.datemutable.timeline.sandbox.core.SandboxHandler;
import com.base.datemutable.utilities.TimelineSynced;
import com.base.loaders.LoadingManager;
import com.base.loaders.global.properties.AppConfig;
import com.base.loaders.global.properties.ProjectProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.icu.util.ULocale;
import com.utilities.ThreadMutable;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class Global implements ThreadMutable<Global, Global.DateWrapper> {
    public static final AppConfig CONFIG = new AppConfig();
    public static final AtomicReference<ProjectProperties> CURRENT_PROPERTIES = new AtomicReference<>(new ProjectProperties());
    //---- Static Globals ----
    public static final UUID INSTANCE_ID = UUID.randomUUID();
    public static final LocalDate MAX_DATE = LocalDate.MAX;
    public static final LocalDate CONFEDERACY_FOUNDED = LocalDate.of(2415,12,24);
    public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    public static final boolean SQL_ENABLED = false;
    public static final Path SHAPE_PATH = Path.of("data/shapefiles/");
    private static final LoadingManager LOADING_MANAGER = new LoadingManager();
    private static final ThreadLocal<Map<TimelineSynced,Boolean>> listeners = ThreadLocal.withInitial(WeakHashMap::new);
    //--- Thread Specific Globals ---
    private static final ThreadLocal<DateWrapper> CurrentDate = ThreadLocal.withInitial(() -> DateWrapper.of(CONFEDERACY_FOUNDED));
    private static ThreadLocal<SandboxHandler<?>> SANDBOX_HANDLER = new ThreadLocal<>();

    public static final Gson GSON = buildGson();


    public static void setCurrentDate(LocalDate newDate) {
        if (newDate == null || newDate.equals(CurrentDate.get().get())){return;}
        CurrentDate.get().set(newDate);
        alertListenersPre(newDate);
        DMRegistry.onDateChange();
        alertListenersPost(newDate);
        CURRENT_PROPERTIES.get().setLastDate(newDate);
    }
    private static void alertListenersPre(LocalDate date){
        listeners.get().keySet().forEach((ts) -> {ts.onLoad(date);});
    }
    private static void alertListenersPost(LocalDate date){
        listeners.get().keySet().forEach((ts) -> {ts.onLink(date);});
    }
    @Deprecated(forRemoval = true)
    public static LocalDate CURRENT_DATE() {
        return CurrentDate.get().get();
    }
    public static LocalDate getDate() {
        return CurrentDate.get().get();
    }
    public static LoadingManager getLoadingManager() {
        return LOADING_MANAGER;
    }

    @Override
    public String uniqueKey() {
        return "global_controls";
    }

    @Override
    public void onThreadInit(boolean shared) {

    }
    public static void addListener(TimelineSynced listener){
        listeners.get().put(listener,false);
    }

    public static ULocale getIBMLocale(){
        return CONFIG.getIBMLocale();
    }
    public static Locale getJavaLocale(){
        return CONFIG.getJavaLocale();
    }

    @Override
    public DateWrapper share() {
        return CurrentDate.get();
    }

    @Override
    public void receiveShared(DateWrapper shared) {
        CurrentDate.set(shared);
    }
    public static SandboxHandler<?> getSandboxHandler() {
        return SANDBOX_HANDLER.get();
    }
    public static void setSandboxHandler(SandboxHandler<?> handler) {
        SANDBOX_HANDLER.set(handler);
    }


    private static Gson buildGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        builder.create();
    }









    public enum TimeDirection {
        FORWARD, BACKWARD
    }
    public static class DateWrapper{
        private volatile LocalDate date;
        DateWrapper(LocalDate date){
            this.date = date;
        }
        public synchronized LocalDate get(){
            return date;
        }
        public synchronized void set(LocalDate date){
            this.date = date;
        }
        public static DateWrapper of(LocalDate date){
            return new DateWrapper(date);
        }
    }
}
