package com.base.geography;

import com.base.datemutable.utilities.TLSynced;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.utilities.serialization.StringToHex;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.store.ContentFeatureCollection;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WorldMap extends TLSynced {
    ContentFeatureCollection runtime;
    LocalDate last;
    Set<String> excluded = new HashSet<>();
    TreeMultimap<LocalDate, VisibilityChange> dateFeatures = TreeMultimap.create(Ordering.natural(),Ordering.arbitrary());

    public WorldMap() {
        super(UUID.randomUUID());
    }


    @Override
    public void onLoad(LocalDate date) {
        if(date == last) return;
        last = date;
        Set<VisibilityChange> changes = getByDate(date);
        Set<String> exclude = getExcluded(changes);
        if (exclude.equals(excluded)) return;
        excluded = exclude;

    }

    @Override
    public void onLink(LocalDate date) {

    }

    private ContentFeatureCollection build(Set<String> excluded){
        Multimap<GeometryType, SimpleFeatureCollection> features = GeographyManager.;
        Content
    }


    private Set<VisibilityChange> getByDate(LocalDate date){
        Set<VisibilityChange> changes = new HashSet<>(dateFeatures.get(date));
        Set<LocalDate> dates = dateFeatures.keySet().headSet(date,false);
        for(LocalDate d : dates){
            changes.addAll(dateFeatures.get(d));
        }
        for(VisibilityChange c : new HashSet<>(changes)){
            if(c.end().isBefore(date)){
                changes.remove(c);
            }
        }
        return changes;
    }
    private static Set<String> getExcluded(Set<VisibilityChange> changes){
        Set<String> endChanges = new HashSet<>();
        for(VisibilityChange c : changes){
            endChanges.addAll(c.endChanges());
        }
        return endChanges;
    }
    public record VisibilityChange(UUID id, LocalDate start, LocalDate end,Set<String> endChanges){
        public JsonObject toJson(){
            JsonObject obj = new JsonObject();
            String builder = StringToHex.encode(id.toString()) +
                    "::" +
                    StringToHex.encode(start.toString()) +
                    "::" +
                    StringToHex.encode(end.toString());
            obj.add("data", new JsonPrimitive(builder));
            obj.add("end", serializeSet(endChanges));
            return obj;
        }
        public static VisibilityChange fromJson(JsonObject json){
            String[] parts = json.get("data").getAsString().split("::");
            UUID id = UUID.fromString(StringToHex.decode(parts[0]));
            LocalDate start = LocalDate.parse(StringToHex.decode(parts[1]));
            LocalDate end = LocalDate.parse(StringToHex.decode(parts[2]));
            Set<String> endChanges = deserializeSet(json.getAsJsonArray("end"));
            return new VisibilityChange(id, start, end, endChanges);
        }
        private static Set<String> deserializeSet(JsonArray changes){
            Set<String> hiddenChanges = new HashSet<>();
            for(int i = 3; i < changes.size(); i++){
                hiddenChanges.add(StringToHex.decode(changes.get(i).getAsString()));
            }
            return hiddenChanges;
        }
        private static JsonArray serializeSet(Set<String> changes){
            JsonArray array = new JsonArray();
            for(String change : changes){
                array.add(new JsonPrimitive(StringToHex.encode(change)));
            }
            return array;
        }
    }
}
