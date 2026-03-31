package com;

import com.base.DMRegistry;
import com.utilities.LoadingManager;
import com.utilities.ThreadMutable;

import java.nio.file.Path;
import java.time.LocalDate;

public class Global implements ThreadMutable {
    public static final ThreadLocal<Global> INSTANCE = new ThreadLocal<>();
    private static Global gv() {
        return INSTANCE.get();
    }
    //---- Static Globals ----
    public static final LocalDate MAX_DATE = LocalDate.MAX;
    public static final LocalDate CONFEDERACY_FOUNDED = LocalDate.of(2415,12,24);
    public static Double SCREEN_WIDTH = 1920D;
    public static Double SCREEN_HEIGHT = 1080D;
    public static final boolean SQL_ENABLED = false;
    public static final Path SHAPE_PATH = Path.of("data/shapefiles/");
    private static final LoadingManager LOADING_MANAGER = new LoadingManager();
    //--- Thread Specific Globals ---
    private LocalDate CURRENT_DATE = LocalDate.now();
    public static void setCurrentDate(LocalDate currentDate) {
        gv().CURRENT_DATE = currentDate;
        DMRegistry.onDateChange();
        //TODO use this as a trigger for Updating EVERY registered state to the proper date?
    }
    @Deprecated(forRemoval = true)
    public static LocalDate CURRENT_DATE() {
        return gv().CURRENT_DATE;
    }
    public static LocalDate getDate() {
        return gv().CURRENT_DATE;
    }
    public static LoadingManager getLoadingManager() {
        return LOADING_MANAGER;
    }
    @Override
    public Type uniqueKey() {
        return Type.GLOBAL_VARIABLE_CONTAINER;
    }

    @Override
    public void onThreadInit(boolean shared) {

    }

    @Override
    public Object share() {
        return null;
    }

    @Override
    public void receiveShared(Object shared) {

    }

    @Override
    public void onThreadInit() {
        INSTANCE.set(this);
    }
}
