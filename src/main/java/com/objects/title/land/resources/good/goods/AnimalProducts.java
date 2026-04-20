package com.objects.title.land.resources.good.goods;

import com.Global.*;
import com.google.common.collect.Sets;
import com.objects.character.physical.species.fish.FishSpecies;
import com.objects.title.land.resources.good.GoodTags;
import com.objects.title.land.resources.good.GoodType;
import com.objects.title.land.resources.good.IGood;

import static com.objects.character.physical.species.artiodactyla.ArtiodactylaSpecies.*;
import static com.objects.character.physical.species.fish.ShellfishSpecies.*;
import static com.objects.character.physical.species.fish.FishSpecies.*;
public class AnimalProducts {
    // =========================================================================
// MEAT
// =========================================================================
    public static final IGood BEEF = new GoodType.Animal_Product("beef", "Beef", "Raw beef harvested from domestic cattle, a widely consumed red meat used in countless culinary traditions.", 0, 250, DOMESTIC_COW, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood BISON_MEAT = new GoodType.Animal_Product("bison_meat", "Bison Meat", "Lean red meat harvested from American bison, historically a dietary staple of the Great Plains.", 0, 180, AMERICAN_BISON, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood PORK = new GoodType.Animal_Product("pork", "Pork", "Rich fatty meat harvested from domestic pigs, one of the most widely consumed meats in the world.", 0, 300, DOMESTIC_PIG, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood MUTTON = new GoodType.Animal_Product("mutton", "Mutton", "Meat harvested from domestic sheep, a staple protein across much of the world.", 0, 240, DOMESTIC_SHEEP, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood GOAT_MEAT = new GoodType.Animal_Product("goat_meat", "Goat Meat", "Lean meat harvested from domestic goats, the most widely consumed meat globally by population.", 0, 180, DOMESTIC_GOAT, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood VENISON = new GoodType.Animal_Product("venison", "Venison", "Lean game meat harvested from white-tailed deer, used in hunting cultures and rural diets.", 0, 120, WHITE_TAILED_DEER, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood MOOSE_MEAT = new GoodType.Animal_Product("moose_meat", "Moose Meat", "Very lean game meat harvested from moose, a staple of northern and subarctic diets.", 0, 110, MOOSE, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));

    // =========================================================================
// MILK
// =========================================================================
    public static final IGood COW_MILK = new GoodType.Animal_Product("cow_milk", "Cow's Milk", "Fresh milk produced by domestic cattle, the most widely consumed dairy liquid in the world.", 0, 60, DOMESTIC_COW, Sets.newHashSet(GoodTags.DAIRY, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood GOAT_MILK = new GoodType.Animal_Product("goat_milk", "Goat's Milk", "Fresh milk produced by domestic goats, widely consumed in Mediterranean and Middle Eastern diets.", 0, 61, DOMESTIC_GOAT, Sets.newHashSet(GoodTags.DAIRY, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood SHEEP_MILK = new GoodType.Animal_Product("sheep_milk", "Sheep's Milk", "Rich, high-fat milk produced by domestic sheep, used primarily in cheese and yogurt production.", 0, 100, DOMESTIC_SHEEP, Sets.newHashSet(GoodTags.DAIRY, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));

    // =========================================================================
// LEATHER
// =========================================================================
    public static final IGood CATTLE_HIDE = new GoodType.Animal_Product("cattle_hide", "Cattle Hide", "Raw unprocessed hide from domestic cattle, the primary source of commercial leather.", 0, 0, DOMESTIC_COW, Sets.newHashSet(GoodTags.LEATHER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood BISON_HIDE = new GoodType.Animal_Product("bison_hide", "Bison Hide", "Thick raw hide from American bison, historically used for robes, shields, and shelter.", 0, 0, AMERICAN_BISON, Sets.newHashSet(GoodTags.LEATHER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood PIGSKIN = new GoodType.Animal_Product("pigskin", "Pigskin", "Raw hide from domestic pigs, used in leather goods and as a byproduct of pork production.", 0, 0, DOMESTIC_PIG, Sets.newHashSet(GoodTags.LEATHER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood SHEEPSKIN = new GoodType.Animal_Product("sheepskin", "Sheepskin", "Raw hide from domestic sheep, used in leather and as a wool-bearing pelt.", 0, 0, DOMESTIC_SHEEP, Sets.newHashSet(GoodTags.LEATHER, GoodTags.FURS, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood GOAT_HIDE = new GoodType.Animal_Product("goat_hide", "Goat Hide", "Raw hide from domestic goats, used in fine leather goods including gloves and bookbinding.", 0, 0, DOMESTIC_GOAT, Sets.newHashSet(GoodTags.LEATHER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood DEER_HIDE = new GoodType.Animal_Product("deer_hide", "Deer Hide", "Soft raw hide from white-tailed deer, used in buckskin clothing and traditional goods.", 0, 0, WHITE_TAILED_DEER, Sets.newHashSet(GoodTags.LEATHER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood MOOSE_HIDE = new GoodType.Animal_Product("moose_hide", "Moose Hide", "Thick durable raw hide from moose, used in heavy-duty leather goods and traditional northern crafts.", 0, 0, MOOSE, Sets.newHashSet(GoodTags.LEATHER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));

    // =========================================================================
// WOOL
// =========================================================================
    public static final IGood SHEEP_WOOL = new GoodType.Animal_Product("sheep_wool", "Sheep's Wool", "Raw shorn fleece from domestic sheep, the primary natural fiber used in textile production.", 0, 0, DOMESTIC_SHEEP, Sets.newHashSet(GoodTags.FIBER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood GOAT_WOOL = new GoodType.Animal_Product("goat_wool", "Goat Hair & Mohair", "Raw fiber shorn from domestic goats, including mohair from Angora breeds.", 0, 0, DOMESTIC_GOAT, Sets.newHashSet(GoodTags.FIBER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood BISON_WOOL = new GoodType.Animal_Product("bison_wool", "Bison Fiber", "Soft underfur combed from American bison, a rare fiber used in high-quality textiles.", 0, 0, AMERICAN_BISON, Sets.newHashSet(GoodTags.FIBER, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));

    // =========================================================================
// TALLOW
// =========================================================================
    public static final IGood BEEF_TALLOW = new GoodType.Animal_Product("beef_tallow", "Beef Tallow", "Rendered fat from domestic cattle, used in cooking, candle-making, and soap production.", 0, 0, DOMESTIC_COW, Sets.newHashSet(GoodTags.OILS_FOOD, GoodTags.CANDLES, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood PORK_LARD = new GoodType.Animal_Product("pork_lard", "Pork Lard", "Rendered fat from domestic pigs, widely used in cooking and pastry production.", 0, 0, DOMESTIC_PIG, Sets.newHashSet(GoodTags.OILS_FOOD, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));
    public static final IGood MUTTON_TALLOW = new GoodType.Animal_Product("mutton_tallow", "Mutton Tallow", "Rendered fat from domestic sheep, used in candles, soap, and traditional cooking.", 0, 0, DOMESTIC_SHEEP, Sets.newHashSet(GoodTags.OILS_FOOD, GoodTags.CANDLES, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_INDUSTRIAL));

    // =========================================================================
// FISH — SALMON
// =========================================================================
    public static final IGood SOCKEYE_SALMON = new GoodType.Animal_Product("sockeye_salmon", "Sockeye Salmon", "Rich, deep-red Pacific salmon prized for its full flavor and high oil content, central to Indigenous and commercial fisheries.", 0, 200, SOCKEYE_SALMON_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood COHO_SALMON = new GoodType.Animal_Product("coho_salmon", "Coho Salmon", "A medium-sized Pacific salmon with bright red flesh and moderate fat content, important to coastal and river fisheries.", 0, 200, COHO_SALMON_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood RAINBOW_TROUT = new GoodType.Animal_Product("rainbow_trout", "Rainbow Trout", "A versatile salmonid fished and farmed across temperate waters, valued for its mild flavor and firm flesh.", 0, 150, STEELHEAD_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));

    // =========================================================================
// FISH — TUNA
// =========================================================================
    public static final IGood YELLOWFIN_TUNA = new GoodType.Animal_Product("yellowfin_tuna", "Yellowfin Tuna", "A large pelagic tuna of tropical oceans, widely fished for fresh and canned consumption.", 0, 110, YELLOWFIN_TUNA_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood ATLANTIC_BLUEFIN_TUNA = new GoodType.Animal_Product("atlantic_bluefin_tuna", "Atlantic Bluefin Tuna", "One of the largest and most valuable food fish in the world, prized in high-end sashimi markets and subject to severe overfishing pressure.", 0, 150, ATLANTIC_BLUEFIN_TUNA_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_LUXURY, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_LUXURY));
    public static final IGood ALBACORE_TUNA = new GoodType.Animal_Product("albacore_tuna", "Albacore Tuna", "A migratory deep-water tuna with white flesh, the primary source of canned white-meat tuna.", 0, 120, ALBACORE_TUNA_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));

    // =========================================================================
// FISH — BASS
// =========================================================================
    public static final IGood LARGEMOUTH_BASS = new GoodType.Animal_Product("largemouth_bass", "Largemouth Bass", "A lean freshwater sport fish of North America, consumed regionally and widely pursued by recreational anglers.", 0, 90, LARGEMOUTH_BASS_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood SMALLMOUTH_BASS = new GoodType.Animal_Product("smallmouth_bass", "Smallmouth Bass", "A lean freshwater bass of clear rivers and lakes, prized as a sport fish and consumed regionally.", 0, 90, SMALLMOUTH_BASS_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood STRIPED_BASS = new GoodType.Animal_Product("striped_bass", "Striped Bass", "A large anadromous bass of the Atlantic coast, commercially and recreationally significant with firm, mild white flesh.", 0, 100, STRIPED_BASS_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood BLACK_SEA_BASS = new GoodType.Animal_Product("black_sea_bass", "Black Sea Bass", "A small inshore marine bass of the Atlantic coast with delicate white flesh, popular in fresh seafood markets.", 0, 90, BLACK_SEA_BASS_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));

    // =========================================================================
// FISH — HALIBUT, COD, STURGEON
// =========================================================================
    public static final IGood PACIFIC_HALIBUT = new GoodType.Animal_Product("pacific_halibut", "Pacific Halibut", "The largest flatfish in the world, yielding dense, mild white flesh highly valued in commercial and sport fisheries.", 0, 90, PACIFIC_HALIBUT_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood PACIFIC_COD = new GoodType.Animal_Product("pacific_cod", "Pacific Cod", "A large demersal fish of the North Pacific with very lean, flaky white flesh, a staple of commercial fisheries.", 0, 80, PACIFIC_COD_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood WHITE_STURGEON = new GoodType.Animal_Product("white_sturgeon", "White Sturgeon", "The largest freshwater fish in North America, yielding firm, rich flesh and the highly prized roe used for caviar.", 0, 100, WHITE_STURGEON_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood WHITE_STURGEON_CAVIAR = new GoodType.Animal_Product("white_sturgeon_caviar", "White Sturgeon Caviar", "Salt-cured roe harvested from white sturgeon, among the most prized luxury foods in the world.", 0, 280, WHITE_STURGEON_SPECIES, Sets.newHashSet(GoodTags.FOOD_LUXURY, GoodTags.MARKET_LUXURY, GoodTags.MARKET_AGRICULTURAL));

    // =========================================================================
// SHELLFISH — CRAB
// =========================================================================
    public static final IGood RED_KING_CRAB = new GoodType.Animal_Product("red_king_crab", "Red King Crab", "A massive spiny-legged crustacean of the North Pacific, yielding sweet, dense leg meat and among the most commercially valuable seafoods.", 0, 80, KING_CRAB_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood DUNGENESS_CRAB = new GoodType.Animal_Product("dungeness_crab", "Dungeness Crab", "A large Pacific coast crab with sweet, tender meat, the cornerstone of West Coast commercial crab fisheries.", 0, 85, DUNGENESS_CRAB_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood SNOW_CRAB = new GoodType.Animal_Product("snow_crab", "Snow Crab", "A cold-water spider crab of Arctic and subarctic seas, prized for its long, slender legs and sweet delicate meat.", 0, 80, SNOW_CRAB_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));

    // =========================================================================
// SHELLFISH — LOBSTER
// =========================================================================
    public static final IGood AMERICAN_LOBSTER = new GoodType.Animal_Product("american_lobster", "American Lobster", "A large clawed lobster of the North Atlantic, one of the most commercially valuable crustaceans in the world.", 0, 90, MAINE_LOBSTER_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL, GoodTags.MARKET_LUXURY));
    public static final IGood SPINY_LOBSTER = new GoodType.Animal_Product("spiny_lobster", "Spiny Lobster", "A clawless warm-water lobster fished across tropical and subtropical coasts, valued for its firm tail meat.", 0, 90, SPINY_LOBSTER_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));

    // =========================================================================
// SHELLFISH — SHRIMP
// =========================================================================
    public static final IGood PINK_SHRIMP = new GoodType.Animal_Product("pink_shrimp", "Pink Shrimp", "A small cold-water shrimp of the North Pacific, harvested in large volumes for fresh and processed seafood markets.", 0, 85, PINK_SHRIMP_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood SPOT_PRAWN = new GoodType.Animal_Product("spot_prawn", "Spot Prawn", "The largest shrimp of the North Pacific, prized for its sweet, firm flesh and considered a delicacy on the West Coast.", 0, 85, SPOT_PRAWN_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_LUXURY));

    // =========================================================================
// SHELLFISH — CLAMS
// =========================================================================
    public static final IGood BUTTER_CLAM = new GoodType.Animal_Product("butter_clam", "Butter Clam", "A large, hard-shelled clam of the Pacific Northwest coast, eaten fresh, steamed, and in chowders.", 0, 60, BUTTER_CLAM_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood RAZOR_CLAM = new GoodType.Animal_Product("razor_clam", "Pacific Razor Clam", "A fast-burrowing intertidal clam of Pacific beaches, highly regarded for its tender, sweet flesh.", 0, 60, RAZOR_CLAM_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));
    public static final IGood GEODUCK = new GoodType.Animal_Product("geoduck", "Geoduck", "A massive, long-lived burrowing clam of the Pacific Northwest, prized in Asian seafood markets for its sweet, crunchy siphon meat.", 0, 70, GEODUCK_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_LUXURY));

    // =========================================================================
// SHELLFISH — OYSTERS
// =========================================================================
    public static final IGood PACIFIC_OYSTER = new GoodType.Animal_Product("pacific_oyster", "Pacific Oyster", "The most widely farmed oyster in the world, with a briny, creamy flavor and a deep, irregular shell.", 0, 70, PACIFIC_OYSTER_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL, GoodTags.MARKET_COMMERCIAL));
    public static final IGood OLYMPIA_OYSTER = new GoodType.Animal_Product("olympia_oyster", "Olympia Oyster", "The only oyster native to the Pacific coast of North America, small and intensely flavored, historically important to Indigenous peoples of the Pacific Northwest.", 0, 70, OLYMPIA_OYSTER_SPECIES, Sets.newHashSet(GoodTags.MEAT, GoodTags.FOOD_STAPLE, GoodTags.MARKET_AGRICULTURAL));

}
