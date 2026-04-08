package com.utilities.number;

import java.time.LocalDate;

public class DateUtilities {

    public static boolean isBetween(LocalDate dateToCheck, LocalDate floor, LocalDate ceiling){
        return dateToCheck.isAfter(floor) && dateToCheck.isBefore(ceiling);
    }
    public static LocalDate floor(LocalDate date1, LocalDate date2){
        if (date1 == null){
            return date2;
        }
        if (date2 == null){
            return date1;
        }
        return date1.isBefore(date2) ? date1 : date2;
    }
    public static LocalDate ceiling(LocalDate date1, LocalDate date2){
        if (date1 == null){
            return date2;
        }
        if (date2 == null){
            return date1;
        }
        return date1.isAfter(date2) ? date1 : date2;
    }
}
