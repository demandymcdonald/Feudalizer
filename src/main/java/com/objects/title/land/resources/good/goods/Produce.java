package com.objects.title.land.resources.good.goods;

import com.Global.*;
import com.google.common.collect.Sets;
import com.objects.title.land.resources.good.GoodTags;
import com.objects.title.land.resources.good.GoodType;
import com.objects.title.land.resources.good.IGood;

public class Produce {
    // Vegetables
    public static final IGood POTATO = new GoodType.RawResource("potato", "Potato", "A starchy root vegetable and dietary staple grown across temperate climates.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood SWEET_POTATO = new GoodType.RawResource("sweet_potato", "Sweet Potato", "A sweet-fleshed root vegetable grown in warm climates, rich in vitamins.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood CARROT = new GoodType.RawResource("carrot", "Carrot", "An orange root vegetable widely cultivated for food and animal feed.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood ONION = new GoodType.RawResource("onion", "Onion", "A pungent bulb vegetable used extensively in cooking across all cultures.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood GARLIC = new GoodType.RawResource("garlic", "Garlic", "A strongly flavored bulb used as a seasoning and for medicinal purposes.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.SPICES, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood CABBAGE = new GoodType.RawResource("cabbage", "Cabbage", "A leafy vegetable used fresh, cooked, and fermented across many cuisines.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood LETTUCE = new GoodType.RawResource("lettuce", "Lettuce", "A leafy salad green grown for fresh consumption.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood SPINACH = new GoodType.RawResource("spinach", "Spinach", "A nutrient-dense leafy green used in cooking and salads.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood TOMATO = new GoodType.RawResource("tomato", "Tomato", "A fruiting vegetable used extensively in cooking and sauces.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood PEPPER = new GoodType.RawResource("pepper", "Pepper", "Fruiting vegetables ranging from sweet bell peppers to hot chili varieties.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.SPICES, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood CUCUMBER = new GoodType.RawResource("cucumber", "Cucumber", "A cool, watery fruiting vegetable used fresh and in pickling.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood PUMPKIN = new GoodType.RawResource("pumpkin", "Pumpkin & Squash", "Gourd vegetables including pumpkins, zucchini, and winter squash.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood EGGPLANT = new GoodType.RawResource("eggplant", "Eggplant", "A purple-skinned fruiting vegetable used in cooking across Mediterranean and Asian cuisines.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood BROCCOLI = new GoodType.RawResource("broccoli", "Broccoli & Cauliflower", "Cruciferous vegetables including broccoli, cauliflower, and Brussels sprouts.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood LEGUMES = new GoodType.RawResource("legumes", "Legumes", "Pod vegetables including beans, peas, lentils, and chickpeas grown for food and soil enrichment.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood MUSHROOM = new GoodType.RawResource("mushroom", "Mushrooms", "Edible fungi cultivated and foraged for use in cooking.", 0, 350, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));

    // Fruits
    public static final IGood APPLE = new GoodType.RawResource("apple", "Apple", "A widely cultivated temperate fruit eaten fresh, dried, and pressed into cider.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood PEAR = new GoodType.RawResource("pear", "Pear", "A sweet temperate fruit eaten fresh and used in preserves and perry.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood PEACH = new GoodType.RawResource("peach", "Peach & Nectarine", "Stone fruits grown in warm temperate climates, eaten fresh and preserved.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood PLUM = new GoodType.RawResource("plum", "Plum", "A stone fruit eaten fresh, dried as prunes, and used in jams and spirits.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood CHERRY = new GoodType.RawResource("cherry", "Cherry", "A small stone fruit eaten fresh and used in preserves, baking, and liqueurs.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood GRAPE = new GoodType.RawResource("grape", "Grape", "A vine fruit eaten fresh, dried as raisins, and pressed for wine and juice.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood ORANGE = new GoodType.RawResource("orange", "Orange & Citrus", "Citrus fruits including oranges, lemons, limes, and grapefruits grown in warm climates.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood BANANA = new GoodType.RawResource("banana", "Banana", "A tropical fruit grown in humid climates, a dietary staple across much of the tropics.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood MANGO = new GoodType.RawResource("mango", "Mango", "A tropical stone fruit prized for its sweet flesh, widely consumed fresh and in preserves.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood PINEAPPLE = new GoodType.RawResource("pineapple", "Pineapple", "A tropical fruit with a tough exterior and sweet acidic flesh, eaten fresh and canned.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood MELON = new GoodType.RawResource("melon", "Melon", "Large fruiting gourds including watermelon, cantaloupe, and honeydew.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood FIG = new GoodType.RawResource("fig", "Fig", "A sweet fruit eaten fresh and dried, historically important in Mediterranean diets.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood DATE = new GoodType.RawResource("date", "Date", "A sweet palm fruit eaten fresh and dried, a staple food in arid regions.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood POMEGRANATE = new GoodType.RawResource("pomegranate", "Pomegranate", "A seeded fruit with tart juice, used in cooking, drinks, and medicine.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood OLIVE = new GoodType.RawResource("olive", "Olive", "A small oily fruit eaten cured and pressed for oil, central to Mediterranean agriculture.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.OILS_FOOD, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood COCONUT = new GoodType.RawResource("coconut", "Coconut", "A tropical palm fruit providing edible flesh, milk, and oil.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.OILS_FOOD, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood AVOCADO = new GoodType.RawResource("avocado", "Avocado", "A fatty tropical fruit eaten fresh and used as a source of oil.", 0, 450, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));

    // Berries
    public static final IGood STRAWBERRY = new GoodType.RawResource("strawberry", "Strawberry", "A sweet red berry widely cultivated for fresh eating, preserves, and confections.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood BLUEBERRY = new GoodType.RawResource("blueberry", "Blueberry", "A small blue-purple berry eaten fresh and used in baking and preserves.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood RASPBERRY = new GoodType.RawResource("raspberry", "Raspberry", "A delicate red or yellow berry used in jams, desserts, and liqueurs.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood BLACKBERRY = new GoodType.RawResource("blackberry", "Blackberry", "A wild and cultivated dark berry used in preserves, baking, and wine.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood MARIONBERRY = new GoodType.RawResource("marionberry", "Marionberry", "A superior blackberry hybrid developed in Oregon, prized for its rich, complex flavor.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood CRANBERRY = new GoodType.RawResource("cranberry", "Cranberry", "A tart red berry grown in bogs, used in juices, sauces, and dried goods.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood GOOSEBERRY = new GoodType.RawResource("gooseberry", "Gooseberry", "A tart green or red berry used in jams, pies, and wine.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood ELDERBERRY = new GoodType.RawResource("elderberry", "Elderberry", "A small dark berry used in syrups, wines, and traditional medicine.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.HERBS, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood CURRANT = new GoodType.RawResource("currant", "Currant", "Small red, black, or white berries used in jams, juices, and liqueurs.", 0, 250, Sets.newHashSet(GoodTags.PRODUCE, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
}
