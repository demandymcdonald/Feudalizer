package com.base.loaders.global.properties;

import com.ibm.icu.util.ULocale;
import com.utilities.caching.CachingSupplier;
import jdk.jfr.Percentage;

import java.util.Locale;

public class AppConfig extends PropertiesFile<AppConfig>{
    private final CachingSupplier<ULocale> locale = new CachingSupplier<>(() -> {return new ULocale(this.getProperty("language.ibm"));});

    @Override
    public String defaultResourcePath() {
        return "default_data/config.properties";
    }
    public int getWidth(){
        return Integer.parseInt(getProperty("window.width"));
    }
    public int getHeight(){
        return Integer.parseInt(getProperty("window.height"));
    }
    public void setWindowSize(int width, int height){
        setProperty("window.width",String.valueOf(width));
        setProperty("window.height",String.valueOf(height));
        this.setDirty(true);
    }
    public int getNumProcessors(){
        return Runtime.getRuntime().availableProcessors();
    }
    private int maxThreads(){
        int max =  Integer.parseInt(getProperty("threading.max_threads"));
        if (max <= 0){
            return Math.max(getNumProcessors() - 1,1);
        }
        return max;
    }
    @Percentage
    private float maxThreadPercentage(){
        return Math.clamp(Integer.parseInt(getProperty("threading.max_threads_percentage"))/ 100F, .25F, 1F);
    }
    public int getTotalThreads(){
        return (int) Math.min(Math.floor(getNumProcessors() * maxThreadPercentage()),maxThreads());
    }



    public ULocale getIBMLocale(){
        return locale.get();
    }
    public Locale getJavaLocale(){
        return locale.get().toLocale();
    }
    public void setLocale(ULocale locale){
        this.locale.set(locale);
        setProperty("language.locale",locale.toLanguageTag());
        this.setDirty(true);
    }
    @Override
    public void doSave() {

    }

    @Override
    public void doLoad() {
        locale.clear();
    }
}
