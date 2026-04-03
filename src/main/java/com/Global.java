package com;

import com.base.DMRegistry;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.utilities.LoadingManager;
import com.utilities.ThreadMutable;

import java.nio.file.Path;
import java.time.LocalDate;

public class Global implements ThreadMutable<Global, Global.DateWrapper> {



    //---- Static Globals ----
    public static final LocalDate MAX_DATE = LocalDate.MAX;
    public static final LocalDate CONFEDERACY_FOUNDED = LocalDate.of(2415,12,24);
    public static Double SCREEN_WIDTH = 1920D;
    public static Double SCREEN_HEIGHT = 1080D;
    public static final boolean SQL_ENABLED = false;
    public static final Path SHAPE_PATH = Path.of("data/shapefiles/");
    private static final LoadingManager LOADING_MANAGER = new LoadingManager();
    //--- Thread Specific Globals ---
    private static final ThreadLocal<DateWrapper> CurrentDate = ThreadLocal.withInitial(() -> DateWrapper.of(CONFEDERACY_FOUNDED));
    private static ThreadLocal<SandboxHandler<?>> SANDBOX_HANDLER = new ThreadLocal<>();

    public static void setCurrentDate(LocalDate newDate) {
        CurrentDate.get().set(newDate);
        DMRegistry.onDateChange();
        //TODO use this as a trigger for Updating EVERY registered state to the proper date?
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
        return "";
    }

    @Override
    public void onThreadInit(boolean shared) {

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
