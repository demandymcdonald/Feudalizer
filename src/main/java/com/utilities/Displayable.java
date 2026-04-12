package com.utilities;

public interface Displayable {
    String getDisplayID();
    String displayName();
    String description();
    //TODO ICONS
    default String getFull(){
        return displayName() + ": " + description();
    }
}
