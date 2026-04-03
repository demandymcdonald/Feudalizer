package com.utilities;

public interface Displayable {
    String getID();
    String displayName();
    String description();
    //TODO ICONS
    default String getFull(){
        return displayName() + ": " + description();
    }
}
