package com.objects.title.land.resources;

import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.good.GoodTag;
import com.objects.title.land.resources.good.IGood;
import com.objects.title.land.resources.node.Node;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.Map;

public class GoodManager {
    private static final Multimap<GoodTag, IGood> tags = HashMultimap.create();
    private static final Map<String,IGood> goodMap = new HashMap<>();
    private static final Map<String,GoodTag>  goodTagMap = new HashMap<>();
    private static final Map<String, TriFunction<DMEReference<? extends HabitableLand<?>>,String, Integer, Node>> builders = new HashMap<>();
    public static void registerTag(GoodTag tag) {
        goodTagMap.put(tag.getDisplayID(),tag);
    }
    public static void registerGood(IGood good) {
        goodMap.put(good.getDisplayID(),good);
        for(GoodTag tag : good.getTags()) {
            tags.put(tag,good);
        }
    }
    public static void registerNodeBuilder(String id, TriFunction<DMEReference<? extends HabitableLand<?>>,String, Integer, Node> factory){
        builders.put(id,factory);
    }
    public static Node getNode(String id, DMEReference<? extends HabitableLand<?>> owner, String name, int level) {
        return builders.get(id).apply(owner,name,level);
    }
    public static IGood getGood(String id) {
        return goodMap.get(id);
    }
    public static GoodTag getGoodTag(String id) {
        return goodTagMap.get(id);
    }
}
