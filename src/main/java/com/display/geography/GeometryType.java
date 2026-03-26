package com.display.geography;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.GlobalVars.SHAPE_PATH;

public enum GeometryType {
    FIPS(Paths.get("fips/cb_2018_us_county_5m.shp"),false),
    CANA(Paths.get("cana/boobs but Canadian"),false), //TODO fix this later
    CUST_C(Paths.get("custom/custom_counties.shp"),true),
    CUST_P(Paths.get("custom/custom_provinces.shp"),true),
    CUST_M(Paths.get("custom/custom_manors.shp"),true),
    CUST_T(Paths.get("custom/custom_towns.shp"),true),
    CUST_O(Paths.get("custom/custom_other.shp"),true);

    private final boolean isCustom;
    private final Path filePath;
    public boolean isCustom(){return isCustom;}
    public Path buildFullPath(){
        return SHAPE_PATH.resolve(filePath);
    }
    GeometryType(Path path, boolean isCustom){
        this.filePath = path;
        this.isCustom = isCustom;
    }
}
