package com.base.loaders.remote.geography;

import com.Global;
import com.base.geography.GeographyManager;
import com.base.geography.MapLayer;
import com.base.geography.RawGeoContainer;
import com.base.geography.params.LayerType;
import com.base.geography.params.LayerTypes;
import com.base.loaders.remote.GlobalLoader;
import com.google.gson.JsonObject;
import com.ibm.icu.util.ULocale;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geotools.api.data.DataStore;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.api.referencing.operation.TransformException;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public abstract class RealWorldGeoSource{
    protected static final Logger logger = LoggerFactory.getLogger(RealWorldGeoSource.class);
    private static final Set<CountryCode> COUNTRY_CODES = buildCountryCodes();
    private final String sourceID;
    public RealWorldGeoSource(String sourceID) {
        this.sourceID = sourceID;
    }
    public abstract Set<LayerType> canSupplyLayer();
    public abstract RawGeoContainer getRawGeo(CountryCode countryCode);
    //TODO implement proper error handling across the board
    public Map<LayerType, SimpleFeatureCollection> importData(CountryCode countryCode){
        RawGeoContainer rawGeo = getRawGeo(countryCode);
        Map<LayerType, DataStore> rawFeatures = new ConcurrentHashMap<>(rawGeo.getStores());
        int admSize = 0;
        int totSize = 0;
        for(LayerType type : rawFeatures.keySet()){
            if(type.getCategory() == LayerType.Category.Administrative){
                admSize++;
            }
            totSize++;
        }
        final Map<LayerType, SimpleFeatureCollection> features = new ConcurrentHashMap<>(totSize);
        final CountDownLatch latch = new CountDownLatch(rawFeatures.size());
        for(Map.Entry<LayerType, DataStore> entry : rawFeatures.entrySet()){
            final int admFinal = admSize;
            final JsonObject json = rawGeo.getSidecar().deepCopy();
            final Runnable r = () ->doThread(countryCode, entry, admFinal, json, features, latch);
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(DataStore store : rawFeatures.values()){
            store.dispose();
        }
        return features;
    }

    private void doThread(CountryCode countryCode, Map.Entry<LayerType, DataStore> entry, int admFinal, JsonObject json, Map<LayerType, SimpleFeatureCollection> features, CountDownLatch latch) {
        try {
            DataStore store = entry.getValue();
            LayerType type = entry.getKey();
            if(type.getCategory() == LayerType.Category.Administrative){
                type = LayerTypes.getADMLayer(type.getPosition(), admFinal);
            }
            final SimpleFeatureType schema = GeographyManager.getOurSchema(type);
            final DefaultFeatureCollection collection = new DefaultFeatureCollection(type.getFileCode(),schema);
                for (String typeName : store.getTypeNames()){
                    try(SimpleFeatureIterator iterator  = store.getFeatureSource(typeName).getFeatures().features()){
                        while (iterator.hasNext()){
                            SimpleFeature feature = iterator.next();
                            SimpleFeatureBuilder builder = new SimpleFeatureBuilder(schema);
                            builder.set("name",getFromFeatName(type,feature, json));
                            builder.set("group", countryCode.code().toLowerCase(Locale.ROOT) +"||" + getFromFeatGroupSuffix(type,feature, json));
                            builder.set("source",sourceID);
                            builder.set("geom",reProjectGeometry(feature, schema.getCoordinateReferenceSystem()));
                            if(isKmType(type)){
                                builder.set("areaKM",getFromFeatAreaKM(type,feature, json));
                            }
                            collection.add(builder.buildFeature(getFromFeatID(type,feature, json)));
                        }
                    } catch (FactoryException e) {
                        throw new RuntimeException(e);
                    } catch (TransformException e) {
                        throw new RuntimeException(e);
                    };
                }
                features.put(type,collection);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }  finally {
                latch.countDown();
            }
    };

    public static Geometry reProjectGeometry(SimpleFeature feature, CoordinateReferenceSystem targetCRS) throws FactoryException, TransformException {
        Geometry sourceGeom = (Geometry) feature.getDefaultGeometry();
        CoordinateReferenceSystem sourceCRS = feature.getType().getCoordinateReferenceSystem();   // WGS84
        if(targetCRS.equals(sourceCRS)){
            return sourceGeom;
        }
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
        return JTS.transform(sourceGeom, transform);
    }

    //While there is nothing stopping you (and by you I mean me, me) from doing network/disk I/O in these, you should try and keep all IO to the getRawGeo method.
    public abstract String getFromFeatID(LayerType type, SimpleFeature feature, JsonObject sidecar);
    public abstract String getFromFeatName(LayerType type, SimpleFeature feature, JsonObject sidecar);
    public abstract String getFromFeatGroupSuffix(LayerType type, SimpleFeature feature, JsonObject sidecar);
    public abstract String getFromFeatAreaKM(LayerType type, SimpleFeature feature, JsonObject sidecar);
    public final String getSourceID(){
        return sourceID;
    }


    public boolean isKmType(LayerType type){
        return type.getCategory() == LayerType.Category.Administrative || type.getCategory() == LayerType.Category.Natural;
    }

    public Set<CountryCode> getValidCodes(){
        return new HashSet<>(COUNTRY_CODES);
    }
    public static Set<String> getValidIds(){
        return new HashSet<>(Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3));
    }
    private static Set<CountryCode> buildCountryCodes(){
        Set<CountryCode> codes = new HashSet<>();
        for (String code : getValidIds()) {
            ULocale locale = ULocale.forLanguageTag(code);

            codes.add(new CountryCode(code,Global.getIBMLocale().getDisplayCountry(locale)));
        }
      return codes;
    }


    public record CountryCode(String code, String name){}


}
