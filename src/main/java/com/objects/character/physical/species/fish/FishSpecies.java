package com.objects.character.physical.species.fish;

import com.Global.*;
import com.objects.character.physical.species.Species;
import com.objects.character.physical.species.nomenclature.NameContainer;
import com.objects.character.physical.species.nomenclature.NomenEntry;
import com.objects.character.physical.species.nomenclature.Nomenclature;

import static com.objects.character.physical.species.nomenclature.AnimalNomenEntries.*;
import static com.objects.character.physical.species.nomenclature.AnimalNameContainers.PNW_SALMON;
import static com.objects.character.physical.species.fish.Fish.*;

public class FishSpecies {

    // =========================================================================
    // NOMENCLATURE — ORDERS (under existing ACTINOPTERYGII)
    // =========================================================================

    public static final NomenEntry SCOMBRIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, ACTINOPTERYGII,
            "scombriformes", "Scombriformes",
            "Mackerel-like fishes; includes tunas, mackerels, and billfishes — streamlined, fast-swimming pelagic predators of the open ocean."
    );

    public static final NomenEntry PERCIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, ACTINOPTERYGII,
            "perciformes", "Perciformes",
            "Perch-like fishes; a large assemblage of spiny-rayed fishes including basses and their allies found in marine and freshwater environments."
    );

    public static final NomenEntry CENTRARCHIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, ACTINOPTERYGII,
            "centrarchiformes", "Centrarchiformes",
            "Freshwater sunfishes and basses; an order of North American freshwater fishes characterized by spiny dorsal fins and laterally compressed bodies."
    );

    public static final NomenEntry PLEURONECTIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, ACTINOPTERYGII,
            "pleuronectiformes", "Pleuronectiformes",
            "Flatfishes; bottom-dwelling fishes with an asymmetric body plan in which both eyes migrate to one side of the head during larval development."
    );

    public static final NomenEntry GADIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, ACTINOPTERYGII,
            "gadiformes", "Gadiformes",
            "Cod-like fishes; cold-water demersal fishes of the Northern Hemisphere, including cods, hakes, and pollock, many of great commercial importance."
    );

    public static final NomenEntry ACIPENSERIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, ACTINOPTERYGII,
            "acipenseriformes", "Acipenseriformes",
            "Sturgeons and paddlefishes; an ancient order of large, partly cartilaginous fishes with bony scutes, considered living fossils of the vertebrate world."
    );

    // =========================================================================
    // NOMENCLATURE — FAMILIES
    // =========================================================================

    public static final NomenEntry SCOMBRIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, SCOMBRIFORMES,
            "scombridae", "Scombridae",
            "Mackerels and tunas; streamlined, pelagic fishes with a forked or lunate tail, including some of the fastest and largest bony fishes in existence."
    );

    public static final NomenEntry CENTRARCHIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, CENTRARCHIFORMES,
            "centrarchidae", "Centrarchidae",
            "Sunfishes and freshwater basses; a family of North American freshwater fishes including the largemouth and smallmouth bass."
    );

    public static final NomenEntry MORONIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, PERCIFORMES,
            "moronidae", "Moronidae",
            "Temperate basses; semi-anadromous or freshwater basses of North American and European waters, including the commercially important striped bass."
    );

    public static final NomenEntry SERRANIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, PERCIFORMES,
            "serranidae", "Serranidae",
            "Sea basses and groupers; a large marine family of carnivorous fishes, many of which are protogynous hermaphrodites beginning life female."
    );

    public static final NomenEntry PLEURONECTIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, PLEURONECTIFORMES,
            "pleuronectidae", "Pleuronectidae",
            "Right-eye flounders; flatfishes in which both eyes are located on the right side of the body, including the Pacific and Atlantic halibut."
    );

    public static final NomenEntry GADIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, GADIFORMES,
            "gadidae", "Gadidae",
            "Cods; cold-water marine fishes including Atlantic and Pacific cod, historically among the most commercially significant food fishes in the world."
    );

    public static final NomenEntry ACIPENSERIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, ACIPENSERIFORMES,
            "acipenseridae", "Acipenseridae",
            "Sturgeons; large, long-lived fishes with rows of bony scutes and a heterocercal tail, prized for their roe and among the most ancient living fish lineages."
    );

    // =========================================================================
    // NOMENCLATURE — GENERA
    // =========================================================================

    public static final NomenEntry THUNNUS = NomenEntry.of(
            Nomenclature.Type.GENUS, SCOMBRIDAE,
            "thunnus", "Thunnus",
            "Tunas; large, warm-bodied, pelagic fishes capable of partial endothermy, enabling high-speed sustained swimming across ocean basins."
    );

    public static final NomenEntry MICROPTERUS = NomenEntry.of(
            Nomenclature.Type.GENUS, CENTRARCHIDAE,
            "micropterus", "Micropterus",
            "Black basses; predatory freshwater basses of North America and among the most sought-after sport fish on the continent."
    );

    public static final NomenEntry MORONE = NomenEntry.of(
            Nomenclature.Type.GENUS, MORONIDAE,
            "morone", "Morone",
            "Temperate basses; includes the striped bass, a large, anadromous fish of major commercial and recreational importance along North American coasts."
    );

    public static final NomenEntry CENTROPRISTIS = NomenEntry.of(
            Nomenclature.Type.GENUS, SERRANIDAE,
            "centropristis", "Centropristis",
            "Sea basses of the western Atlantic; carnivorous inshore fishes that are protogynous hermaphrodites, beginning life female before transitioning to male."
    );

    public static final NomenEntry HIPPOGLOSSUS = NomenEntry.of(
            Nomenclature.Type.GENUS, PLEURONECTIDAE,
            "hippoglossus", "Hippoglossus",
            "Halibut; the largest flatfishes, found in cold deep waters of the North Atlantic and North Pacific, among the largest bony fishes."
    );

    public static final NomenEntry GADUS = NomenEntry.of(
            Nomenclature.Type.GENUS, GADIDAE,
            "gadus", "Gadus",
            "True cods; the type genus of family Gadidae, containing the commercially vital Atlantic and Pacific cods."
    );

    public static final NomenEntry ACIPENSER = NomenEntry.of(
            Nomenclature.Type.GENUS, ACIPENSERIDAE,
            "acipenser", "Acipenser",
            "Sturgeons; the largest genus of Acipenseridae, including some of the largest and longest-lived freshwater fish on Earth."
    );

    // =========================================================================
    // NOMENCLATURE — SPECIES
    // =========================================================================

    // Salmon — new species under existing genus ONCORHYNCHUS
    public static final NomenEntry ONCORHYNCHUS_NERKA = NomenEntry.of(
            Nomenclature.Type.SPECIES, ONCORHYNCHUS,
            "oncorhynchus_nerka", "Oncorhynchus nerka",
            "The sockeye salmon; a Pacific salmon known for its brilliant red spawning coloration, critically important to Indigenous cultures and commercial fisheries."
    );

    public static final NomenEntry ONCORHYNCHUS_KISUTCH = NomenEntry.of(
            Nomenclature.Type.SPECIES, ONCORHYNCHUS,
            "oncorhynchus_kisutch", "Oncorhynchus kisutch",
            "The coho salmon; a medium-sized Pacific salmon of coastal rivers and streams, known for aggressive behavior and a two-year ocean phase."
    );

    public static final NomenEntry ONCORHYNCHUS_MYKISS = NomenEntry.of(
            Nomenclature.Type.SPECIES, ONCORHYNCHUS,
            "oncorhynchus_mykiss", "Oncorhynchus mykiss",
            "The rainbow trout and steelhead; a remarkably plastic species ranging from non-migratory stream residents to fully anadromous ocean-going steelhead."
    );

    // Tuna
    public static final NomenEntry THUNNUS_ALBACARES = NomenEntry.of(
            Nomenclature.Type.SPECIES, THUNNUS,
            "thunnus_albacares", "Thunnus albacares",
            "The yellowfin tuna; a large, fast-swimming tuna of tropical and subtropical oceans, recognized by its vivid yellow dorsal fin and finlets."
    );

    public static final NomenEntry THUNNUS_THYNNUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, THUNNUS,
            "thunnus_thynnus", "Thunnus thynnus",
            "The Atlantic bluefin tuna; one of the largest bony fishes and critically endangered due to overfishing, commanding extraordinary prices at market."
    );

    public static final NomenEntry THUNNUS_ALALUNGA = NomenEntry.of(
            Nomenclature.Type.SPECIES, THUNNUS,
            "thunnus_alalunga", "Thunnus alalunga",
            "The albacore tuna; a migratory, deep-water tuna with the longest pectoral fins of any tuna species and the source of canned white meat tuna."
    );

    // Bass
    public static final NomenEntry MICROPTERUS_SALMOIDES = NomenEntry.of(
            Nomenclature.Type.SPECIES, MICROPTERUS,
            "micropterus_salmoides", "Micropterus salmoides",
            "The largemouth bass; the most widely distributed freshwater sport fish in North America, characterized by a jaw extending past the rear edge of the eye."
    );

    public static final NomenEntry MICROPTERUS_DOLOMIEU = NomenEntry.of(
            Nomenclature.Type.SPECIES, MICROPTERUS,
            "micropterus_dolomieu", "Micropterus dolomieu",
            "The smallmouth bass; a popular sport fish of clear, cool rivers and lakes, renowned among anglers for its aggressive fight when hooked."
    );

    public static final NomenEntry MORONE_SAXATILIS = NomenEntry.of(
            Nomenclature.Type.SPECIES, MORONE,
            "morone_saxatilis", "Morone saxatilis",
            "The striped bass; a large anadromous fish of the Atlantic coast and its estuaries, the most commercially and recreationally significant temperate bass in North America."
    );

    public static final NomenEntry CENTROPRISTIS_STRIATA = NomenEntry.of(
            Nomenclature.Type.SPECIES, CENTROPRISTIS,
            "centropristis_striata", "Centropristis striata",
            "The black sea bass; a protogynous hermaphrodite of the Atlantic coast that begins life female, with dominant individuals transitioning to male."
    );

    // Other fish
    public static final NomenEntry HIPPOGLOSSUS_STENOLEPIS = NomenEntry.of(
            Nomenclature.Type.SPECIES, HIPPOGLOSSUS,
            "hippoglossus_stenolepis", "Hippoglossus stenolepis",
            "The Pacific halibut; the largest flatfish, with females dramatically exceeding males in size, inhabiting cold deep waters from California to Alaska."
    );

    public static final NomenEntry GADUS_MACROCEPHALUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, GADUS,
            "gadus_macrocephalus", "Gadus macrocephalus",
            "The Pacific cod; a large, demersal fish of the North Pacific with significant commercial importance, closely related to but distinct from the Atlantic cod."
    );

    public static final NomenEntry ACIPENSER_TRANSMONTANUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, ACIPENSER,
            "acipenser_transmontanus", "Acipenser transmontanus",
            "The white sturgeon; the largest freshwater fish in North America, capable of exceeding 6 meters in length and living well over a century."
    );

    // =========================================================================
    // NOMENCLATURE — SUBSPECIES (nominotypical for all monotypic species)
    // =========================================================================

    public static final NomenEntry ONCORHYNCHUS_NERKA_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ONCORHYNCHUS_NERKA,
            "oncorhynchus_nerka_nominotypical", "Oncorhynchus nerka",
            "The sockeye salmon; a monotypic species with no recognized subspecies, though landlocked kokanee populations are ecologically distinct."
    );

    public static final NomenEntry ONCORHYNCHUS_KISUTCH_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ONCORHYNCHUS_KISUTCH,
            "oncorhynchus_kisutch_nominotypical", "Oncorhynchus kisutch",
            "The coho salmon; a monotypic species distributed from California to Alaska and across the North Pacific to northeastern Asia."
    );

    public static final NomenEntry ONCORHYNCHUS_MYKISS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ONCORHYNCHUS_MYKISS,
            "oncorhynchus_mykiss_nominotypical", "Oncorhynchus mykiss",
            "The steelhead; the anadromous form of the rainbow trout, widely treated as the primary representative of the species."
    );

    public static final NomenEntry THUNNUS_ALBACARES_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, THUNNUS_ALBACARES,
            "thunnus_albacares_nominotypical", "Thunnus albacares",
            "The yellowfin tuna; a monotypic species distributed throughout tropical and subtropical oceans worldwide."
    );

    public static final NomenEntry THUNNUS_THYNNUS_THYNNUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, THUNNUS_THYNNUS,
            "thunnus_thynnus_thynnus", "Thunnus thynnus thynnus",
            "The Atlantic bluefin tuna (nominotypical subspecies); the reference form of the western and eastern Atlantic."
    );

    public static final NomenEntry THUNNUS_ALALUNGA_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, THUNNUS_ALALUNGA,
            "thunnus_alalunga_nominotypical", "Thunnus alalunga",
            "The albacore tuna; a monotypic species forming distinct North Pacific, South Pacific, Atlantic, and Indian Ocean populations."
    );

    public static final NomenEntry MICROPTERUS_SALMOIDES_SALMOIDES = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, MICROPTERUS_SALMOIDES,
            "micropterus_salmoides_salmoides", "Micropterus salmoides salmoides",
            "The northern largemouth bass (nominotypical subspecies); the most widespread form, native to the Great Lakes and Mississippi River basin."
    );

    public static final NomenEntry MICROPTERUS_DOLOMIEU_DOLOMIEU = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, MICROPTERUS_DOLOMIEU,
            "micropterus_dolomieu_dolomieu", "Micropterus dolomieu dolomieu",
            "The northern smallmouth bass (nominotypical subspecies); native to clear, cool rivers and lakes from the Great Lakes south to northern Alabama."
    );

    public static final NomenEntry MORONE_SAXATILIS_SAXATILIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, MORONE_SAXATILIS,
            "morone_saxatilis_saxatilis", "Morone saxatilis saxatilis",
            "The striped bass (nominotypical subspecies); the primary Atlantic coastal form, historically ranging from the Gulf of St. Lawrence to the Gulf of Mexico."
    );

    public static final NomenEntry CENTROPRISTIS_STRIATA_STRIATA = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CENTROPRISTIS_STRIATA,
            "centropristis_striata_striata", "Centropristis striata striata",
            "The black sea bass (nominotypical subspecies); found along the Atlantic coast of North America from Maine to the Gulf of Mexico."
    );

    public static final NomenEntry HIPPOGLOSSUS_STENOLEPIS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, HIPPOGLOSSUS_STENOLEPIS,
            "hippoglossus_stenolepis_nominotypical", "Hippoglossus stenolepis",
            "The Pacific halibut; a monotypic species ranging from California to the Bering Sea and across to northern Japan."
    );

    public static final NomenEntry GADUS_MACROCEPHALUS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, GADUS_MACROCEPHALUS,
            "gadus_macrocephalus_nominotypical", "Gadus macrocephalus",
            "The Pacific cod; a monotypic species distributed across the North Pacific from California and Korea north through the Bering Sea."
    );

    public static final NomenEntry ACIPENSER_TRANSMONTANUS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ACIPENSER_TRANSMONTANUS,
            "acipenser_transmontanus_nominotypical", "Acipenser transmontanus",
            "The white sturgeon; a monotypic species endemic to Pacific North America, inhabiting major river systems from California to British Columbia."
    );

    // =========================================================================
    // NAME CONTAINERS
    // =========================================================================

    // PNW_SALMON (Chinook) already defined in AnimalNameContainers — imported directly

    public static final NameContainer SOCKEYE_SALMON = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, SALMONIFORMES, SALMONIDAE,
            ONCORHYNCHUS, ONCORHYNCHUS_NERKA, ONCORHYNCHUS_NERKA_NOMINOTYPICAL
    );

    public static final NameContainer COHO_SALMON = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, SALMONIFORMES, SALMONIDAE,
            ONCORHYNCHUS, ONCORHYNCHUS_KISUTCH, ONCORHYNCHUS_KISUTCH_NOMINOTYPICAL
    );

    public static final NameContainer STEELHEAD = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, SALMONIFORMES, SALMONIDAE,
            ONCORHYNCHUS, ONCORHYNCHUS_MYKISS, ONCORHYNCHUS_MYKISS_NOMINOTYPICAL
    );

    public static final NameContainer YELLOWFIN_TUNA = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, SCOMBRIFORMES, SCOMBRIDAE,
            THUNNUS, THUNNUS_ALBACARES, THUNNUS_ALBACARES_NOMINOTYPICAL
    );

    public static final NameContainer ATLANTIC_BLUEFIN_TUNA = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, SCOMBRIFORMES, SCOMBRIDAE,
            THUNNUS, THUNNUS_THYNNUS, THUNNUS_THYNNUS_THYNNUS
    );

    public static final NameContainer ALBACORE_TUNA = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, SCOMBRIFORMES, SCOMBRIDAE,
            THUNNUS, THUNNUS_ALALUNGA, THUNNUS_ALALUNGA_NOMINOTYPICAL
    );

    public static final NameContainer LARGEMOUTH_BASS = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, CENTRARCHIFORMES, CENTRARCHIDAE,
            MICROPTERUS, MICROPTERUS_SALMOIDES, MICROPTERUS_SALMOIDES_SALMOIDES
    );

    public static final NameContainer SMALLMOUTH_BASS = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, CENTRARCHIFORMES, CENTRARCHIDAE,
            MICROPTERUS, MICROPTERUS_DOLOMIEU, MICROPTERUS_DOLOMIEU_DOLOMIEU
    );

    public static final NameContainer STRIPED_BASS = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, PERCIFORMES, MORONIDAE,
            MORONE, MORONE_SAXATILIS, MORONE_SAXATILIS_SAXATILIS
    );

    public static final NameContainer BLACK_SEA_BASS = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, PERCIFORMES, SERRANIDAE,
            CENTROPRISTIS, CENTROPRISTIS_STRIATA, CENTROPRISTIS_STRIATA_STRIATA
    );

    public static final NameContainer PACIFIC_HALIBUT = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, PLEURONECTIFORMES, PLEURONECTIDAE,
            HIPPOGLOSSUS, HIPPOGLOSSUS_STENOLEPIS, HIPPOGLOSSUS_STENOLEPIS_NOMINOTYPICAL
    );

    public static final NameContainer PACIFIC_COD = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, GADIFORMES, GADIDAE,
            GADUS, GADUS_MACROCEPHALUS, GADUS_MACROCEPHALUS_NOMINOTYPICAL
    );

    public static final NameContainer WHITE_STURGEON = new NameContainer(
            ANIMALIA, CHORDATA, ACTINOPTERYGII, ACIPENSERIFORMES, ACIPENSERIDAE,
            ACIPENSER, ACIPENSER_TRANSMONTANUS, ACIPENSER_TRANSMONTANUS_NOMINOTYPICAL
    );

    // =========================================================================
    // SPECIES — SALMON
    // =========================================================================

    public static final Species CHINOOK_SALMON = Species.builder("chinook_salmon", "Oncorhynchus Tshawytscha", "Chinook Salmon")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(12)
            .setNomenclature(PNW_SALMON)
            .setLifeExpectancy(7)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(5)
            .setAgeOfInfertilityMale(6)
            .setAgeOfInfertilityFemale(6)
            .addGenderRatio(FISH_BODY_SIZE, 1.1f)
            .build();

    public static final Species SOCKEYE_SALMON_SPECIES = Species.builder("sockeye_salmon", "Oncorhynchus Nerka", "Sockeye Salmon")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(12)
            .setNomenclature(SOCKEYE_SALMON)
            .setLifeExpectancy(5)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(4)
            .setAgeOfInfertilityMale(4)
            .setAgeOfInfertilityFemale(4)
            .addGenderRatio(FISH_BODY_SIZE, 1.1f)
            .build();

    public static final Species COHO_SALMON_SPECIES = Species.builder("coho_salmon", "Oncorhynchus Kisutch", "Coho Salmon")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(12)
            .setNomenclature(COHO_SALMON)
            .setLifeExpectancy(4)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(3)
            .setAgeOfInfertilityMale(3)
            .setAgeOfInfertilityFemale(3)
            .addGenderRatio(FISH_BODY_SIZE, 1.1f)
            .build();

    public static final Species STEELHEAD_SPECIES = Species.builder("steelhead", "Oncorhynchus Mykiss", "Steelhead")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(12)
            .setNomenclature(STEELHEAD)
            .setLifeExpectancy(11)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(8)
            .setAgeOfInfertilityMale(9)
            .setAgeOfInfertilityFemale(9)
            .addGenderRatio(FISH_BODY_SIZE, 1.1f)
            .build();

    // =========================================================================
    // SPECIES — TUNA
    // =========================================================================

    public static final Species YELLOWFIN_TUNA_SPECIES = Species.builder("yellowfin_tuna", "Thunnus Albacares", "Yellowfin Tuna")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(18)
            .setNomenclature(YELLOWFIN_TUNA)
            .setLifeExpectancy(9)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(7)
            .setAgeOfInfertilityMale(8)
            .setAgeOfInfertilityFemale(8)
            .addGenderRatio(FISH_BODY_SIZE, 1.1f)
            .build();

    public static final Species ATLANTIC_BLUEFIN_TUNA_SPECIES = Species.builder("atlantic_bluefin_tuna", "Thunnus Thynnus", "Atlantic Bluefin Tuna")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(18)
            .setNomenclature(ATLANTIC_BLUEFIN_TUNA)
            .setLifeExpectancy(40)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(4)
            .setAgeOfElderly(30)
            .setAgeOfInfertilityMale(35)
            .setAgeOfInfertilityFemale(35)
            .addGenderRatio(FISH_BODY_SIZE, 1.1f)
            .build();

    public static final Species ALBACORE_TUNA_SPECIES = Species.builder("albacore_tuna", "Thunnus Alalunga", "Albacore Tuna")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(15)
            .setNomenclature(ALBACORE_TUNA)
            .setLifeExpectancy(12)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(9)
            .setAgeOfInfertilityMale(11)
            .setAgeOfInfertilityFemale(11)
            .addGenderRatio(FISH_BODY_SIZE, 1.1f)
            .build();

    // =========================================================================
    // SPECIES — BASS
    // =========================================================================

    public static final Species LARGEMOUTH_BASS_SPECIES = Species.builder("largemouth_bass", "Micropterus Salmoides", "Largemouth Bass")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(15)
            .setNomenclature(LARGEMOUTH_BASS)
            .setLifeExpectancy(16)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(12)
            .setAgeOfInfertilityMale(14)
            .setAgeOfInfertilityFemale(14)
            .addGenderRatio(FISH_BODY_SIZE, 0.9f)
            .build();

    public static final Species SMALLMOUTH_BASS_SPECIES = Species.builder("smallmouth_bass", "Micropterus Dolomieu", "Smallmouth Bass")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(15)
            .setNomenclature(SMALLMOUTH_BASS)
            .setLifeExpectancy(20)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(15)
            .setAgeOfInfertilityMale(18)
            .setAgeOfInfertilityFemale(18)
            .addGenderRatio(FISH_BODY_SIZE, 0.9f)
            .build();

    public static final Species STRIPED_BASS_SPECIES = Species.builder("striped_bass", "Morone Saxatilis", "Striped Bass")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(15)
            .setNomenclature(STRIPED_BASS)
            .setLifeExpectancy(30)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(22)
            .setAgeOfInfertilityMale(26)
            .setAgeOfInfertilityFemale(26)
            .addGenderRatio(FISH_BODY_SIZE, 0.8f)
            .build();

    public static final Species BLACK_SEA_BASS_SPECIES = Species.builder("black_sea_bass", "Centropristis Striata", "Black Sea Bass")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(10)
            .setNomenclature(BLACK_SEA_BASS)
            .setLifeExpectancy(10)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(8)
            .setAgeOfInfertilityMale(9)
            .setAgeOfInfertilityFemale(9)
            .addGenderRatio(FISH_BODY_SIZE, 1.0f)
            .build();

    // =========================================================================
    // SPECIES — HALIBUT, COD, STURGEON
    // =========================================================================

    // Notable: females live ~55 years and grow dramatically larger; males ~30 years and much smaller
    public static final Species PACIFIC_HALIBUT_SPECIES = Species.builder("pacific_halibut", "Hippoglossus Stenolepis", "Pacific Halibut")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(10)
            .setNomenclature(PACIFIC_HALIBUT)
            .setLifeExpectancy(55)
            .setGenderLifeExpectancyRatio(0.55f)
            .setAgeOfMaturity(8)
            .setAgeOfElderly(40)
            .setAgeOfInfertilityMale(28)
            .setAgeOfInfertilityFemale(50)
            .addGenderRatio(FISH_BODY_SIZE, 0.2f)
            .build();

    public static final Species PACIFIC_COD_SPECIES = Species.builder("pacific_cod", "Gadus Macrocephalus", "Pacific Cod")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(10)
            .setNomenclature(PACIFIC_COD)
            .setLifeExpectancy(25)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(18)
            .setAgeOfInfertilityMale(22)
            .setAgeOfInfertilityFemale(22)
            .addGenderRatio(FISH_BODY_SIZE, 0.9f)
            .build();

    // Notable: can live 100+ years; females outlive males and grow significantly larger
    public static final Species WHITE_STURGEON_SPECIES = Species.builder("white_sturgeon", "Acipenser Transmontanus", "White Sturgeon")
            .addValidProperty(FISH_BODY)
            .addValidProperty(FISH_SCALES)
            .addValidProperty(FISH_HEAD)
            .addValidProperty(FISH_EYES)
            .addValidProperty(FISH_MOUTH)
            .addValidProperty(FISH_NOSE)
            .addValidProperty(FISH_TORSO)
            .addValidProperty(FISH_BACK)
            .addValidProperty(FISH_TAIL_FIN)
            .addValidProperty(FISH_PECTORAL_FINS)
            .addValidProperty(FISH_FINS)
            .addValidProperty(FISH_BRAIN)
            .addValidProperty(FISH_VITAL_ORGANS)
            .addValidProperty(FISH_IMMUNE_SYSTEM)
            .addValidProperty(FISH_GILLS)
            .addValidProperty(FISH_DIGESTIVE)
            .addValidProperty(FISH_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(15)
            .setNomenclature(WHITE_STURGEON)
            .setLifeExpectancy(100)
            .setGenderLifeExpectancyRatio(0.85f)
            .setAgeOfMaturity(15)
            .setAgeOfElderly(70)
            .setAgeOfInfertilityMale(80)
            .setAgeOfInfertilityFemale(90)
            .addGenderRatio(FISH_BODY_SIZE, 0.75f)
            .build();
}