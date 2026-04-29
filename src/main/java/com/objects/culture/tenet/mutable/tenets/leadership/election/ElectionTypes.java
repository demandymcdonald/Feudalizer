package com.objects.culture.tenet.mutable.tenets.leadership.election;


import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.object.ideology.Ideology;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.tenets.leadership.Leadership;
import com.objects.organization.AbstractOrganization;
import com.objects.organization.NonGovernmentEntity;
import com.objects.organization.government.GoverningEntity;
import com.objects.shared.PopulationContainer;
import com.objects.title.land.habitable.HabitableLand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ElectionTypes {
    private static final Map<String, ElectionType<?>> types = new HashMap<>();
    public static void registerType(ElectionType<?> type) {
        types.put(type.getID(), type);
    }
    public static ElectionType<?> get(String id) {
        ElectionType<?> et = types.get(id);
        if (et == null) {
            throw new IllegalArgumentException("Unknown Election Type: " + id);
        }
        return et;
    }
    public static final ElectionType<InterestGroup> POPULAR = new ElectionType<InterestGroup>(InstanceType.HARDCODED,"popular", "Popular", "Election based on the population of the land.") {
        @Override
        public ElectionType<?> getNewObject(InstanceType type, String id, JsonObject data) {
            return POPULAR;
        }

        @Override
        public Map<Ideology, Integer> getIdeologyWeights() {
            return Map.of(

            );
        }
        @Override
        public PoliticalCompass getBase() {
            return new PoliticalCompass(-25,25,0,-25,15);
        }
        @Override
        public Map<InterestGroup, Long> fromLand(HabitableLand<?> land) {
            return popularLand(land);
        }
        @Override
        public Map<InterestGroup, Long> fromGovernment(GoverningEntity<?> land) {
            return getFrom(land);
        }
        @Override
        public Map<InterestGroup, Long> fromOrganization(NonGovernmentEntity<?> land) {
            return getFrom(land);
        }
        private Map<InterestGroup,Long> getFrom(AbstractOrganization<?> organization) {
            Map<InterestGroup, Long> map = new HashMap<>();
            for(ICultureObject o : organization.getByType(ICultureObject.Type.Land)){
                if(o instanceof HabitableLand<?> hl){
                    Map<InterestGroup,Long> hlMap = fromLand(hl);
                    for(InterestGroup entry : hlMap.keySet()){
                        if(!map.containsKey(entry) && !isDisenfranchised(organization, entry)){
                            map.computeIfPresent(entry, (k,v)->v+hlMap.get(k));
                            continue;
                        }
                        map.put(entry,hlMap.get(entry));
                    }
                }
            }
            return map;
        }
    };
    public static final ElectionType<InterestGroup> SYNDICALIST_WORKER_ELECTION = new ElectionType<>(InstanceType.HARDCODED,
            "syndicalist_worker", "Syndicalist Worker", "Labor Unions decide the election outcome") {

        @Override
        public ElectionType<?> getNewObject(InstanceType type, String id, JsonObject data) {
            return SYNDICALIST_WORKER_ELECTION;
        }

        @Override
        public Map<Ideology, Integer> getIdeologyWeights() {
            return Map.of();
        }

        @Override
        public PoliticalCompass getBase() {
            return null;
        }

        @Override
        public Map<InterestGroup, Long> fromLand(HabitableLand<?> land) {
            return
        }

        @Override
        public Map<InterestGroup, Long> fromGovernment(GoverningEntity<?> land) {
            return GoverningEntity.
        }

        @Override
        public Map<InterestGroup, Long> fromOrganization(NonGovernmentEntity<?> land) {
            return Map.of();
        }


    };
    public static <T extends AbstractOrganization<T>> boolean isDisenfranchised(T entity, InterestGroup group) {
        Set<TenetInstance<T>> barred = entity.getActiveTenetByClass(Leadership.Barred.class);
        for(Leadership.Barred b : barred){
            if(b.contains(group)){
                return true;
            }
        }
        return false;
    }
    public static Map<InterestGroup, Long> popularLand(HabitableLand<?> land) {
        Map<InterestGroup, Long> map = new HashMap<>();
        PopulationContainer<?> pc = land.getPopulationContainer();
        GoverningEntity<?> ge = land.getGovernment().get();
        for(InterestGroup entry : pc.getPopulationMap().keySet()){
            if (!isDisenfranchised(ge,entry)) {
                map.put(entry,pc.getPopulation(entry));
            }
        };
        return map;
    }
}
