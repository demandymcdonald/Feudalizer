package com.objects.title.land.resources.good.goods;

import com.Global.*;
import com.google.common.collect.Sets;
import com.objects.title.land.resources.good.GoodTags;
import com.objects.title.land.resources.good.GoodType;
import com.objects.title.land.resources.good.IGood;

public class Crops {
    public static final IGood WHEAT = new GoodType.RawResource("wheat", "Wheat", "A staple cereal grain grown widely across temperate climates, used in bread, pasta, and animal feed.", 0, 540, Sets.newHashSet(GoodTags.GRAIN, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood BARLEY = new GoodType.RawResource("barley", "Barley", "A hardy cereal grain used in food, animal feed, and as a key ingredient in brewing.", 0, 500, Sets.newHashSet(GoodTags.GRAIN, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood RICE = new GoodType.RawResource("rice", "Rice", "A staple cereal grain cultivated in flooded paddies, forming the dietary foundation of much of the world.", 0, 560, Sets.newHashSet(GoodTags.GRAIN, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood MAIZE = new GoodType.RawResource("maize", "Maize", "A versatile cereal crop used for food, animal feed, and industrial processing.", 0, 510, Sets.newHashSet(GoodTags.GRAIN, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood RYE = new GoodType.RawResource("rye", "Rye", "A cold-hardy cereal grain used in bread and whiskey production.", 0, 490, Sets.newHashSet(GoodTags.GRAIN, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood OATS = new GoodType.RawResource("oats", "Oats", "A cereal grain used for porridge, animal feed, and processed food products.", 0, 480, Sets.newHashSet(GoodTags.GRAIN, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood COTTON = new GoodType.RawResource("cotton", "Raw Cotton", "Seed fibers harvested from the cotton plant, used as the primary input for textile production.", 0, 0, Sets.newHashSet(GoodTags.FIBER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood FLAX = new GoodType.RawResource("flax", "Flax", "A fiber and oilseed crop used in linen production and as a source of linseed oil.", 0, 0, Sets.newHashSet(GoodTags.FIBER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood SUGARCANE = new GoodType.RawResource("sugarcane", "Sugarcane", "A tall grass crop harvested for its sucrose-rich stalks, the primary source of refined sugar.", 0, 80, Sets.newHashSet(GoodTags.SUGAR, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood TOBACCO_LEAF = new GoodType.RawResource("tobacco_leaf", "Tobacco Leaf", "Harvested and dried leaves of the tobacco plant, used in smoking and chewing products.", 0, 0, Sets.newHashSet(GoodTags.TOBACCO, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood HEMP = new GoodType.RawResource("hemp", "Hemp", "A versatile fiber crop used in rope, textiles, and as a source of oil and seed.", 0, 0, Sets.newHashSet(GoodTags.FIBER, GoodTags.ROPE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
}
