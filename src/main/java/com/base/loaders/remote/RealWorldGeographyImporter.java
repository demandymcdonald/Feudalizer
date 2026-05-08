package com.base.loaders.remote;

import com.Global;
import com.base.component.InstanceType;
import com.base.geography.GeoContainer;
import com.base.loaders.LoaderVars;
import com.base.loaders.global.HandlerType;
import com.base.loaders.global.json.FileLoaderUtility;
import com.base.loaders.local.utilities.JsonLoader;
import com.base.loaders.local.utilities.SFCLoader;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.ibm.icu.util.ULocale;
import org.apache.commons.lang3.tuple.Triple;
import org.geotools.api.data.DataStore;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class RealWorldGeographyImporter extends GlobalLoader{
    private static final JsonLoader metadataLoader = new JsonLoader();
    private static final SFCLoader payloadLoader = new SFCLoader();
    private static final String GeoBoundsPrefix = "gb_";
    private final String realWorldLocalCache = "imported_geography/gb/";


    public Set<GeoContainer> getFindFeatures(String... codes){
        File dirLoc = localCacheDir();
        FileLoaderUtility.validatePath(dirLoc,true);
        if(!dirLoc.isDirectory()) throw new RuntimeException("Directory does not exist: "+dirLoc.getAbsolutePath());
        final Set<String> ids = buildIds(codes);
        Map<JsonObject, DataStore> features = new HashMap<>();
        Set<File> files = Set.of(Objects.requireNonNull(dirLoc.listFiles()));
        Set<String> needsImport = new HashSet<>();
        checkCache(ids, files, needsImport, features);
        if(!needsImport.isEmpty()){
            importFeatures(dirLoc, needsImport, features);
        }
        Set<GeoContainer> toReturn = new HashSet<>();
        for(Map.Entry<JsonObject, DataStore> entry : features.entrySet()){
            JsonObject metadata = entry.getKey();
            DataStore payload = entry.getValue();
            geoContainers.add(
                    new GeoContainer(
                            InstanceType.EXTERNAL,
                            metadata.get("file_code").getAsString(),
                            metadata.get("Continent").getAsString(),
                            payload,
                            metadata.get("file_name").getAsString()
                            );
        }
    }

    private void checkCache(Set<String> ids, Set<File> files, Set<String> needsImport, Set<GeoContainer> toReturn) {
        for(String s : ids){
            File rawMetadata;
            Set<File> filtered = new HashSet<>(files.stream().filter((file) -> file.getName().contains(s)).toList());
            if(filtered.size() != 4){
                if (!filtered.isEmpty()) {
                    handleImproperlyFormatted(needsImport, s, filtered);
                } else {
                    needsImport.add(s);
                }
                continue;
            } else {
                rawMetadata = filtered.stream().filter((file) -> file.getName().contains(".json")).findFirst().orElse(null);
                Set<File> layers = filtered.stream().filter((file) -> file.getName().contains(".geojson")).collect(Collectors.toSet());
                if (layers.size() != 3 || rawMetadata == null) {
                    handleImproperlyFormatted(needsImport, s, filtered);
                    continue;
                }
                JsonObject metadata = metadataLoader.read(rawMetadata,null,true);
                DataStore country = payloadLoader.read(layers.stream().filter((file) -> file.getName().contains("adm0")).findFirst().orElse(null),null,true);
                DataStore state = payloadLoader.read(layers.stream().filter((file) -> file.getName().contains("adm1")).findFirst().orElse(null),null,true);
                DataStore county = payloadLoader.read(layers.stream().filter((file) -> file.getName().contains("adm2")).findFirst().orElse(null),null,true);

            }
            features.put(metadataLoader.read(rawMetadata,null,true),

            );
        }
    }
    private GeoContainer parse(JsonObject metadata, DataStore country, DataStore state, DataStore county){
        GeoContainer container = new GeoContainer(InstanceType.GEOGRAPHY, metadata.get("id").getAsString(), metadata.get("continent").getAsString(), country, state, county, metadata.get("areaKM").getAsLong(), metadata.get("name").getAsString(), metadata.get("iso").getAsString(), metadata.get("defaultNumAdminUnits").getAsInt());
        return container;
    }
    private void handleImproperlyFormatted(Set<String> needsImport, String code, Set<File> filtered){
        needsImport.add(code);
        logger.warn("Improperly formatted cached geography: {}",code);
        for (File f : filtered){
            if(!f.delete()){
                logger.warn("Failed to delete file: {}",f.getAbsolutePath());
            };
        }
    }
    private static Set<String> buildIds(String... codes){
        Set<String> ids = new HashSet<>();
        for (String code : codes) {
            ids.add(GeoBoundsPrefix + code);
        }
        return ids;
    }
    private void importFeatures(File dirLoc, Set<String> needsImport, Map<JsonObject, DataStore> existing){
       for (String code : needsImport) {
           Map<Integer,JsonObject> metadata = importMetadata(code);
           if (metadata.isEmpty()) {
               continue;
           }
           for (Map.Entry<Integer, JsonObject> entry : metadata.entrySet()) {
               JsonObject meta = entry.getValue();
               meta.addProperty("file_code",GeoBoundsPrefix + code + "_" + entry.getKey());
           }

       }
        for (JsonObject meta : metadata) {

            existing.put(meta, payload);
            metadataLoader.write(new File(dirLoc, code + ".json"), meta, true);
        }
    }
    private DataStore importPayload(File dirLoc, String code, JsonObject metadata) {
        try (HttpClient client = HttpClient.newHttpClient()){
            return remoteGetPayload(client, dirLoc, "", code, new URI(metadata.get("gjDownloadURL").getAsString()));
        } catch (Exception e){
            logger.error("Failed to import geography with code: .. {} Error: {}",code,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private DataStore remoteGetPayload(HttpClient client, File dirLoc, String prefix, String code, URI url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        File finalFile = FileLoaderUtility.validatePath(new File(dirLoc, prefix + code + ".geojson"),null,null,true);
        Files.writeString(finalFile.toPath(), response.body());
        return payloadLoader.read(finalFile,null,true);
    }
    private Map<Integer,JsonObject> importMetadata(String code){
        if (getValidIds().contains(code)){
            Map<Integer,JsonObject> metaData = remoteGetMetadata(code);
            for(JsonObject meta : metaData.values()){
                meta.addProperty("file_code",GeoBoundsPrefix + code);
            }
            return metaData;
        } else {
            logger.warn("Invalid code: {}", code);
            return Collections.emptyMap();
        }
    }


    private Map<Integer,JsonObject> remoteGetMetadata(String code){
        Map<Integer,JsonObject> features = new HashMap<>();
        try (HttpClient client = HttpClient.newHttpClient()){
            for(int i = 0; i < 6; i++) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://www.geoboundaries.org/api/current/gbOpen/" + code + "/ADM"+i+"/"))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 404) {
                    break;
                }
                features.put(i,LoaderVars.GSON.fromJson(response.body(), JsonObject.class));
            }
        } catch (Exception e){
            logger.error("Failed to import geography with code: .. {} Error: {}",code,e.getMessage());
            throw new RuntimeException(e);
        }
        return features;
    }
    public Map<String,String> getValidCodes(){
        Map<String,String> codes = new HashMap<>();

        for (String code : getValidIds()) {
            ULocale locale = ULocale.forLanguageTag(code);
            codes.put(Global.getIBMLocale().getDisplayCountry(locale), code);
        }
        return codes;
    }
    public Set<String> getValidIds(){
        return new HashSet<>(Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3));
    }
    private File localCacheDir(){
        return new File(LoaderVars.getLocalDirectory(), realWorldLocalCache);
    }
    @Override
    public ImmutableSet<HandlerType> getHandled() {
        return ImmutableSet.of(HandlerType.RL_GEOGRAPHY);
    }
}
