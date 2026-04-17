package com.utilities;

public interface IDisplayable {
    String getDisplayID();
    String getDisplayName();
    String getDescription();
    //TODO ICONS
    default String getFull(){
        return getDisplayName() + ": " + getDescription();
    }
}
