package com.display.geography;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum GeometryType {
    FIPS(Paths.get("cb_2018_us_county_5m.shp")),
    CANA(Paths.get("boobs but Canadian")),
    CUST(Paths.get("stuff here"));


    private final Path filePath;

    GeometryType(Path path){
        this.filePath = path;
    }
}
