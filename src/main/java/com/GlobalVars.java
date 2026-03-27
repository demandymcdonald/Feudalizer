package com;

import com.base.DMRegistry;
import com.utilities.LoadingManager;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Supplier;

public class GlobalVars {
    private static final ThreadLocal<LocalDate> CURRENT_DATE =  ThreadLocal.withInitial(new Supplier<LocalDate>() {
        @Override
        public LocalDate get() {
            return DEFAULT_DATE;
        }
    });
    public static final LocalDate MAX_DATE = LocalDate.MAX;
    public static final LocalDate CONFEDERACY_FOUNDED = LocalDate.of(2415,12,24);
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2415,12,24);
    public static Double SCREEN_WIDTH = 1920D;
    public static Double SCREEN_HEIGHT = 1080D;
    public static final boolean SQL_ENABLED = false;
    public static final Path SHAPE_PATH = Path.of("data/shapefiles/");
    private static final LoadingManager LOADING_MANAGER = new LoadingManager();
    public static LocalDate CURRENT_DATE() {
        return CURRENT_DATE.get();
    }

    public static void setCurrentDate(LocalDate currentDate) {
        CURRENT_DATE.set(currentDate);
        DMRegistry.onDateChange();
        //TODO use this as a trigger for Updating EVERY registered state to the proper date?
    }
    public static LoadingManager getLoadingManager() {
        return LOADING_MANAGER;
    }

}
