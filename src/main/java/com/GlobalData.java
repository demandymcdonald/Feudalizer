package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class GlobalData {

    private static Date CurrentDate = new Date();

    @Deprecated
    public static Logger logger(){
        return FeudalizerApp.LOGGER;
    }
    public static Date CurrentDate(){
        return CurrentDate;
    }
}
