package com.objects.culture.tenet.interest;

import com.utilities.IDisplayable;

public interface IPlural extends IDisplayable {
    default String getPlural(){
        return getDisplayID()+"s";
    }
    default String getPossessive(){
        return getDisplayID()+"'s";
    }

}
