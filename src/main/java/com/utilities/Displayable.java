package com.utilities;

public interface Displayable {
    String internalID();
    String displayName();
    String description();
    //TODO ICONS
    default String getFull(){
        return displayName() + ": " + description();
    }
}
