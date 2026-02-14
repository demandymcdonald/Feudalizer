package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class GlobalData {
    private static Logger Feudalogger = LoggerFactory.getLogger(GlobalData.class);
    private static Date CurrentDate = new Date();


    public static Logger logger(){
        return Feudalogger;
    }
    public static Date CurrentDate(){
        return CurrentDate;
    }
}
