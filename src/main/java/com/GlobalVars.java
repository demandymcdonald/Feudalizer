package com;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.function.Supplier;

public class GlobalVars {
    private static final ThreadLocal<LocalDate> CURRENT_DATE =  ThreadLocal.withInitial(new Supplier<LocalDate>() {
        @Override
        public LocalDate get() {
            return LocalDate.of(2415,12,24);
        }
    });
    public static final LocalDate MAX_DATE = LocalDate.MAX;
    public static final LocalDate CONFEDERACY_FOUNDED = LocalDate.of(2415,12,24);
    public static Double SCREEN_WIDTH = 1920D;
    public static Double SCREEN_HEIGHT = 1080D;
    public static final boolean SQL_ENABLED = false;
    public static final Path SHAPE_PATH = Path.of("data/shapefiles/");

    public static LocalDate CURRENT_DATE() {
        return CURRENT_DATE.get();
    }

    public static void setCurrentDate(LocalDate currentDate) {
        CURRENT_DATE.set(currentDate);
        //TODO use this as a trigger for Updating EVERY registered state to the proper date?
    }

}
