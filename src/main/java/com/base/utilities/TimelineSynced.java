package com.base.utilities;

import com.Global;

import java.time.LocalDate;

public interface TimelineSynced {
    default void registerListener(){
        Global.addListener(this);
    }
    void onLoad(LocalDate date);
    void onLink(LocalDate date);
}
