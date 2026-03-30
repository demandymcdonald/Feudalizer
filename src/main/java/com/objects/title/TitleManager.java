package com.objects.title;

import com.Feudalizer;
import com.base.AbstractMutableManager;
import com.base.ObjectType;
import com.display.geography.GeographyManager;
import com.display.geography.GeometryType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import com.objects.title.land.AbstractLandDivision;
import com.objects.title.land.Titles;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.GlobalVars.CONFEDERACY_FOUNDED;

public class TitleManager extends AbstractMutableManager<Title<?>, TitleContainer> {
    private Cache<Integer,Title<?>> titleLookupCache = CacheBuilder.newBuilder().expireAfterAccess(10, java.util.concurrent.TimeUnit.MINUTES).maximumSize(256).build();

    public TitleManager() {
        super(new TitleContainer());
    }


    @Override
    public Title<?> deserializer(UUID id, JsonObject json) {
        return TitleFactory.create(json);
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.TITLE;
    }

    public Title<?> getOrCreateTitle(GeometryType geoType, String geometryId) {
        int checksum = simpleChecksum(geoType.toString(),geometryId);
        if (titleLookupCache.getIfPresent(checksum) != null) return titleLookupCache.getIfPresent(checksum);
        List<Title<?>> titles = getItemMap().values().stream().filter(title -> AbstractLandDivision.class.isAssignableFrom(title.getClass())).toList();
        for (Title<?> title : titles) {
            assert AbstractLandDivision.class.isAssignableFrom(title.getClass());
            AbstractLandDivision<?> division = (AbstractLandDivision<?>) title;
            if (division.getGeoID() == checksum){
                titleLookupCache.put(checksum,title);
                return title;
            }
        }
        Feudalizer.LOGGER.warn("No title found for geometry {} {}",geoType,geometryId);
       String nerdShit = (String) GeographyManager.getFeature(geoType,geometryId).getAttribute("NAME");
       Feudalizer.LOGGER.info("The nerds call it: {}",nerdShit);
        //TODO: once I split up CUST into different units, make sure we're calling the right factory method.
        if (geoType == GeometryType.FIPS || geoType == GeometryType.CANA){
            return Titles.createCounty(nerdShit,CONFEDERACY_FOUNDED,null,geoType,geometryId);
        }
        return null;
    }

    public static int simpleChecksum(String a, String b) {
        return Objects.hash( a, b);
    }
}
