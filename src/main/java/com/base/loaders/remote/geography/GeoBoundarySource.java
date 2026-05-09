package com.base.loaders.remote.geography;

import com.base.geography.RawGeoContainer;
import com.base.geography.params.LayerType;
import com.base.geography.params.LayerTypes;
import com.base.loaders.LoaderVars;
import com.base.loaders.global.HandlerType;
import com.base.loaders.global.json.FileLoaderUtility;
import com.base.loaders.local.utilities.JsonLoader;
import com.base.loaders.local.utilities.SFCLoader;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import org.geotools.api.data.DataStore;
import org.geotools.api.feature.simple.SimpleFeature;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.base.loaders.global.HandlerType.GEOGRAPHY;

public class GeoBoundarySource extends RealWorldGeoSource{
    private static final JsonLoader metadataLoader = new JsonLoader();
    private static final SFCLoader payloadLoader = new SFCLoader();


    public GeoBoundarySource() {
        super("geoBoundaries");
    }

    @Override
    public Set<LayerType> canSupplyLayer() {
        return Set.of(
            LayerTypes.ADM0,
            LayerTypes.ADM1,
            LayerTypes.ADM2,
            LayerTypes.ADM3,
            LayerTypes.ADM4,
            LayerTypes.ADM5
        );
    }

    @Override
    public RawGeoContainer getRawGeo(CountryCode countryCode) {
        return null;
    }

    private DataStore importPayload(String code, JsonObject metadata) {
        try (HttpClient client = HttpClient.newHttpClient()){
            return remoteGetPayload(client, code, new URI(metadata.get("gjDownloadURL").getAsString()));
        } catch (Exception e){
            logger.error("Failed to import geography with code: .. {} Error: {}",code,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private DataStore remoteGetPayload(HttpClient client, String prefix, URI url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Path path = Files.createTempFile("tmp_gb_"+prefix, ".geojson");
        path.toFile().deleteOnExit();
        Files.writeString(path, response.body());
        return payloadLoader.read(path,null,true);
    }
    private Map<Integer,JsonObject> remoteGetMetadata(String code){
        Map<Integer,JsonObject> features = new HashMap<>();
        try (HttpClient client = HttpClient.newHttpClient()){
            for(int i = 0; i < 5; i++) {
                URI uri = new URI("https://www.geoboundaries.org/api/current/gbOpen/" + code + "/ADM"+i+"/");
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(uri)
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 404) {
                    break;
                }
                features.put(i, LoaderVars.GSON.fromJson(response.body(), JsonObject.class));
            }
        } catch (Exception e){
            logger.error("GeoBoundary: Failed to import geography with code: .. {} Error: {}",code,e.getMessage());
            throw new RuntimeException(e);
        }
        return features;
    }
    @Override
    public String getFromFeatID(LayerType type, SimpleFeature feature, JsonObject sidecar) {
        return "";
    }

    @Override
    public String getFromFeatName(LayerType type, SimpleFeature feature, JsonObject sidecar) {
        return "";
    }

    @Override
    public String getFromFeatGroupSuffix(LayerType type, SimpleFeature feature, JsonObject sidecar) {
        return "";
    }

    @Override
    public String getFromFeatAreaKM(LayerType type, SimpleFeature feature, JsonObject sidecar) {
        return "";
    }

}
