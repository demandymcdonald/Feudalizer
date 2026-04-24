package com.utilities;

public interface IPlural extends IDisplayable {
    default String getPlural(){
        return getDisplayID()+"s";
    }
    default String getPossessive(){
        return getDisplayID()+"'s";
    }

}
