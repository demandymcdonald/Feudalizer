package com.geography;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum GeometryType {
    FIPS(Paths.get("data/shape/fips/cb_2018_us_county_5m.shp")),
    CANC(Paths.get("boobs but Canadian")),
    CUST(Paths.get("stuff here"));


    private final Path filePath;

    GeometryType(Path path){
        this.filePath = path;
    }
}
