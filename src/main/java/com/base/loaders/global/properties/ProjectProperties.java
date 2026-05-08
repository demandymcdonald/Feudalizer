package com.base.loaders.global.properties;

import com.base.geography.params.ValidCRS;
import com.utilities.caching.CachingSupplier;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.referencing.CRS;

import java.time.LocalDate;
import java.util.Objects;

public class ProjectProperties extends PropertiesFile<ProjectProperties> {
    private final CachingSupplier<CoordinateReferenceSystem> crs = new CachingSupplier<>(() -> {
        return Objects.requireNonNull(ValidCRS.map.get(getCrsID())).getCRS();
    });
    public LocalDate getLastDate(){
        return LocalDate.parse(getProperty("last_date"));
    }
    public void setLastDate(LocalDate date){
        setProperty("last_date",date.toString());
        this.setDirty(true);
    }
    public boolean saveEnabled(){
        return getProperty("save_enabled").equals("true");
    }
    public String getProjectName(){
        return getProperty("display_name");
    }
    public boolean isRemote(){
        return getProperty("is_remote").equals("true");
    }
    public CoordinateReferenceSystem getCrs(){
        return crs.get();
    }
    public String getCrsID(){
        return getProperty("crs");
    }
    @Override
    public String defaultResourcePath() {
        return "project.properties";
    }
    @Override
    public void doSave() {

    }
    @Override
    public void doLoad() {
        crs.clear();
    }
}
