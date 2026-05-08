package com.objects.title.land;

import com.base.geography.tools.GeometryType;
import com.objects.title.land.habitable.County;
import com.objects.title.land.habitable.Province;
import com.objects.title.land.habitable.Town;

import java.time.LocalDate;
import java.util.UUID;

public class Titles {

    public static County createCounty(String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID){
        return new County(UUID.randomUUID(),name,created,ended,geoType,geoID);
    }
    public static Province createProvince(String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID){
        return new Province(UUID.randomUUID(),name,created,ended,geoType,geoID);
    }
    public static Manor createManor(String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID){
        return new Manor(UUID.randomUUID(),name,created,ended,geoType,geoID);
    }
    public static Town createTown(String name, LocalDate created, LocalDate ended, GeometryType geoType, String geoID){
        return new Town(UUID.randomUUID(),name,created,ended,geoType,geoID);
    }
}
