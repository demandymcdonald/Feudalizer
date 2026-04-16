package com.objects.shared;

import com.base.DateMutableEntity;

public interface IDemographicDriven<T extends DateMutableEntity<T> & IDemographicDriven<T>> {

    PopulationContainer<T> getPopulationContainer();


}
