package com.objects.shared;

import com.base.datemutable.DateMutableEntity;
import com.objects.culture.tenet.interest.InterestGroup;

public interface IDemographicDriven<T extends DateMutableEntity<T> & IDemographicDriven<T>> {

    PopulationContainer<T> getPopulationContainer();
    default boolean isInMajority(InterestGroup group) {
        return getPopulationContainer().isInMajority(group);
    }


}
