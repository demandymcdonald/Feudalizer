package com.objects.character.physical.species.fish;

import com.Global.*;
import com.objects.character.physical.species.Species;
import com.objects.character.physical.species.nomenclature.NameContainer;
import com.objects.character.physical.species.nomenclature.NomenEntry;
import com.objects.character.physical.species.nomenclature.Nomenclature;

import static com.objects.character.physical.species.nomenclature.AnimalNomenEntries.ANIMALIA;
import static com.objects.character.physical.species.shellfish.Crustacean.*;
import static com.objects.character.physical.species.shellfish.Bivalvia.*;

public class ShellfishSpecies {

    // =========================================================================
    // NOMENCLATURE — PHYLA (new; mammals/fish use CHORDATA under ANIMALIA)
    // =========================================================================

    public static final NomenEntry ARTHROPODA = NomenEntry.of(
            Nomenclature.Type.PHYLUM, ANIMALIA,
            "arthropoda", "Arthropoda",
            "The largest animal phylum; invertebrates with a chitinous exoskeleton, segmented body, and jointed appendages, including insects, arachnids, and crustaceans."
    );

    public static final NomenEntry MOLLUSCA = NomenEntry.of(
            Nomenclature.Type.PHYLUM, ANIMALIA,
            "mollusca", "Mollusca",
            "Soft-bodied invertebrates, many with a hard external shell; the second largest animal phylum, including snails, clams, squid, and octopuses."
    );

    // =========================================================================
    // NOMENCLATURE — CLASSES
    // =========================================================================

    public static final NomenEntry MALACOSTRACA = NomenEntry.of(
            Nomenclature.Type.CLASS, ARTHROPODA,
            "malacostraca", "Malacostraca",
            "The largest class of crustaceans, containing crabs, lobsters, shrimp, and krill; characterized by a 19-segmented body with specialized paired appendages."
    );

    public static final NomenEntry BIVALVIA_CLASS = NomenEntry.of(
            Nomenclature.Type.CLASS, MOLLUSCA,
            "bivalvia", "Bivalvia",
            "Mollusks enclosed in two hinged shell valves; filter feeders including clams, oysters, mussels, and scallops, found in marine and freshwater habitats."
    );

    // =========================================================================
    // NOMENCLATURE — ORDERS
    // =========================================================================

    public static final NomenEntry DECAPODA = NomenEntry.of(
            Nomenclature.Type.ORDER, MALACOSTRACA,
            "decapoda", "Decapoda",
            "Ten-footed crustaceans; the largest crustacean order, including crabs, lobsters, crayfish, and shrimp, characterized by five pairs of thoracic limbs."
    );

    public static final NomenEntry VENERIDA = NomenEntry.of(
            Nomenclature.Type.ORDER, BIVALVIA_CLASS,
            "venerida", "Venerida",
            "Venus clams and their allies; one of the largest and most diverse bivalve orders, including many commercially important clams."
    );

    public static final NomenEntry MYIDA = NomenEntry.of(
            Nomenclature.Type.ORDER, BIVALVIA_CLASS,
            "myida", "Myida",
            "Soft-shell clams and their relatives; burrowing bivalves with elongated siphons adapted for life in intertidal and subtidal sediments."
    );

    public static final NomenEntry OSTREOIDA = NomenEntry.of(
            Nomenclature.Type.ORDER, BIVALVIA_CLASS,
            "ostreoida", "Ostreoida",
            "True oysters and their relatives; sessile, filter-feeding bivalves that cement one valve to hard substrates in intertidal and shallow subtidal zones."
    );

    // =========================================================================
    // NOMENCLATURE — FAMILIES
    // =========================================================================

    public static final NomenEntry LITHODIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, DECAPODA,
            "lithodidae", "Lithodidae",
            "King crabs; large, spiny-legged crustaceans of the North Pacific and Southern Ocean with an asymmetric, calcified carapace and five pairs of walking legs."
    );

    public static final NomenEntry CANCRIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, DECAPODA,
            "cancridae", "Cancridae",
            "Cancer crabs; medium to large oval crabs of the Pacific and Atlantic coasts, including the commercially important Dungeness crab."
    );

    public static final NomenEntry OREGONIIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, DECAPODA,
            "oregoniidae", "Oregoniidae",
            "Spider crabs of the northern oceans; long-legged, slow-growing crabs of Arctic and subarctic waters, including the snow crab."
    );

    public static final NomenEntry NEPHROPIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, DECAPODA,
            "nephropidae", "Nephropidae",
            "Clawed lobsters; large crustaceans of temperate ocean floors with massive chelipeds, including the commercially prized American lobster."
    );

    public static final NomenEntry PALINURIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, DECAPODA,
            "palinuridae", "Palinuridae",
            "Spiny lobsters; warm-water lobsters lacking true claws but bearing long, spiny antennae, widely fished across tropical and subtropical coasts."
    );

    public static final NomenEntry PANDALIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, DECAPODA,
            "pandalidae", "Pandalidae",
            "Pandalid shrimp; cold-water shrimp of the northern Pacific and Atlantic, including the spot prawn and pink shrimp, many of commercial importance."
    );

    public static final NomenEntry VENERIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, VENERIDA,
            "veneridae", "Veneridae",
            "Venus clams; hard-shelled, often ribbed marine bivalves of worldwide distribution, including the butter clam of the Pacific Northwest."
    );

    public static final NomenEntry PHARIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, VENERIDA,
            "pharidae", "Pharidae",
            "Razor clams; elongated, thin-shelled bivalves with a blade-like profile, adapted for extremely rapid burrowing in sandy substrates."
    );

    public static final NomenEntry HIATELLIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, MYIDA,
            "hiatellidae", "Hiatellidae",
            "Geoducks and their allies; large, deep-burrowing bivalves with siphons far too large to retract into the shell, found in subtidal sediments."
    );

    public static final NomenEntry OSTREIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, OSTREOIDA,
            "ostreidae", "Ostreidae",
            "True oysters; the family containing commercially farmed Pacific oysters and the native Olympia oyster, sessile filter feeders of estuaries and rocky coasts."
    );

    // =========================================================================
    // NOMENCLATURE — GENERA
    // =========================================================================

    public static final NomenEntry PARALITHODES = NomenEntry.of(
            Nomenclature.Type.GENUS, LITHODIDAE,
            "paralithodes", "Paralithodes",
            "King crabs; includes the red king crab, among the largest crustaceans and one of the most economically valuable seafoods in the world."
    );

    public static final NomenEntry METACARCINUS = NomenEntry.of(
            Nomenclature.Type.GENUS, CANCRIDAE,
            "metacarcinus", "Metacarcinus",
            "Pacific cancrid crabs; includes the Dungeness crab, a large, commercially important crab of the Pacific coast of North America."
    );

    public static final NomenEntry CHIONOECETES = NomenEntry.of(
            Nomenclature.Type.GENUS, OREGONIIDAE,
            "chionoecetes", "Chionoecetes",
            "Snow crabs and tanner crabs; commercially important spider crabs of cold Arctic and subarctic waters."
    );

    public static final NomenEntry HOMARUS = NomenEntry.of(
            Nomenclature.Type.GENUS, NEPHROPIDAE,
            "homarus", "Homarus",
            "True lobsters; the genus containing the American and European lobsters, among the largest crustaceans and the most commercially valuable."
    );

    public static final NomenEntry PANULIRUS = NomenEntry.of(
            Nomenclature.Type.GENUS, PALINURIDAE,
            "panulirus", "Panulirus",
            "Spiny lobsters; a large genus of warm-water clawless lobsters found in tropical and subtropical marine environments worldwide."
    );

    public static final NomenEntry PANDALUS = NomenEntry.of(
            Nomenclature.Type.GENUS, PANDALIDAE,
            "pandalus", "Pandalus",
            "Pandalid shrimps and prawns; cold-water protandrous hermaphrodites beginning life as males, including the spot prawn and pink shrimp."
    );

    public static final NomenEntry SAXIDOMUS = NomenEntry.of(
            Nomenclature.Type.GENUS, VENERIDAE,
            "saxidomus", "Saxidomus",
            "Butter clams; large, robust Venus clams of the Pacific coast of North America, an important food source for Indigenous peoples and commercial harvesters."
    );

    public static final NomenEntry SILIQUA = NomenEntry.of(
            Nomenclature.Type.GENUS, PHARIDAE,
            "siliqua", "Siliqua",
            "Razor clams; elongated bivalves of Pacific and Atlantic sandy beaches, harvested recreationally and commercially for their sweet, tender meat."
    );

    public static final NomenEntry PANOPEA = NomenEntry.of(
            Nomenclature.Type.GENUS, HIATELLIDAE,
            "panopea", "Panopea",
            "Geoducks; enormous, long-lived, deep-burrowing clams of the Pacific coast, the largest burrowing bivalves in the world."
    );

    public static final NomenEntry MAGALLANA = NomenEntry.of(
            Nomenclature.Type.GENUS, OSTREIDAE,
            "magallana", "Magallana",
            "Pacific oysters; rapidly growing, commercially dominant oysters introduced from Japan and now farmed extensively worldwide."
    );

    public static final NomenEntry OSTREA = NomenEntry.of(
            Nomenclature.Type.GENUS, OSTREIDAE,
            "ostrea", "Ostrea",
            "Flat oysters; round, flat-shelled oysters with complex ecological roles as reef builders and water filterers, including the native Olympia oyster."
    );

    // =========================================================================
    // NOMENCLATURE — SPECIES
    // =========================================================================

    public static final NomenEntry PARALITHODES_CAMTSCHATICUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, PARALITHODES,
            "paralithodes_camtschaticus", "Paralithodes camtschaticus",
            "The red king crab; the largest of the king crabs, inhabiting the shallow, cold waters of the Bering Sea and North Pacific, a prized commercial species."
    );

    public static final NomenEntry METACARCINUS_MAGISTER = NomenEntry.of(
            Nomenclature.Type.SPECIES, METACARCINUS,
            "metacarcinus_magister", "Metacarcinus magister",
            "The Dungeness crab; a large, commercially valuable crab of the Pacific coast of North America, favored for its sweet, delicate meat."
    );

    public static final NomenEntry CHIONOECETES_OPILIO = NomenEntry.of(
            Nomenclature.Type.SPECIES, CHIONOECETES,
            "chionoecetes_opilio", "Chionoecetes opilio",
            "The snow crab; a long-legged, slow-growing crab of Arctic and subarctic waters, with males growing dramatically larger than females."
    );

    public static final NomenEntry HOMARUS_AMERICANUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, HOMARUS,
            "homarus_americanus", "Homarus americanus",
            "The American lobster; a large, clawed crustacean of the Atlantic coast of North America, among the most commercially valuable seafood species in the world."
    );

    public static final NomenEntry PANULIRUS_ARGUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, PANULIRUS,
            "panulirus_argus", "Panulirus argus",
            "The Caribbean spiny lobster; a warm-water, clawless lobster of the western Atlantic and Gulf of Mexico, distinguished by spiny antennae and a banded abdomen."
    );

    public static final NomenEntry PANDALUS_JORDANI = NomenEntry.of(
            Nomenclature.Type.SPECIES, PANDALUS,
            "pandalus_jordani", "Pandalus jordani",
            "The Pacific pink shrimp; a small, cold-water shrimp of the eastern Pacific, one of the most commercially harvested shrimp on the Pacific coast of North America."
    );

    public static final NomenEntry PANDALUS_PLATYCEROS = NomenEntry.of(
            Nomenclature.Type.SPECIES, PANDALUS,
            "pandalus_platyceros", "Pandalus platyceros",
            "The spot prawn; the largest shrimp on the Pacific coast of North America, prized for its sweet flavor and firm texture."
    );

    public static final NomenEntry SAXIDOMUS_GIGANTEA = NomenEntry.of(
            Nomenclature.Type.SPECIES, SAXIDOMUS,
            "saxidomus_gigantea", "Saxidomus gigantea",
            "The butter clam; a large, thick-shelled clam of the Pacific Northwest, an important traditional food source and the most common intertidal clam in the region."
    );

    public static final NomenEntry SILIQUA_PATULA = NomenEntry.of(
            Nomenclature.Type.SPECIES, SILIQUA,
            "siliqua_patula", "Siliqua patula",
            "The Pacific razor clam; a large, elongated clam of Pacific sandy beaches known for its burrowing speed, a popular target for recreational harvesters."
    );

    public static final NomenEntry PANOPEA_GENEROSA = NomenEntry.of(
            Nomenclature.Type.SPECIES, PANOPEA,
            "panopea_generosa", "Panopea generosa",
            "The geoduck; an enormous, extremely long-lived burrowing clam of the Pacific Northwest, with a siphon far too large to retract into its shell."
    );

    public static final NomenEntry MAGALLANA_GIGAS = NomenEntry.of(
            Nomenclature.Type.SPECIES, MAGALLANA,
            "magallana_gigas", "Magallana gigas",
            "The Pacific oyster; the world's most widely farmed oyster, introduced from Japan and now dominant in aquaculture operations across temperate coasts."
    );

    public static final NomenEntry OSTREA_LURIDA = NomenEntry.of(
            Nomenclature.Type.SPECIES, OSTREA,
            "ostrea_lurida", "Ostrea lurida",
            "The Olympia oyster; the only oyster native to the Pacific coast of North America, smaller and slower-growing than the Pacific oyster but ecologically irreplaceable."
    );

    // =========================================================================
    // NOMENCLATURE — SUBSPECIES (nominotypical for all monotypic species)
    // =========================================================================

    public static final NomenEntry PARALITHODES_CAMTSCHATICUS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, PARALITHODES_CAMTSCHATICUS,
            "paralithodes_camtschaticus_nominotypical", "Paralithodes camtschaticus",
            "The red king crab; a monotypic species distributed across the North Pacific from Alaska to northern Japan."
    );

    public static final NomenEntry METACARCINUS_MAGISTER_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, METACARCINUS_MAGISTER,
            "metacarcinus_magister_nominotypical", "Metacarcinus magister",
            "The Dungeness crab; a monotypic species ranging from Alaska to Baja California along the Pacific coast of North America."
    );

    public static final NomenEntry CHIONOECETES_OPILIO_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CHIONOECETES_OPILIO,
            "chionoecetes_opilio_nominotypical", "Chionoecetes opilio",
            "The snow crab; a monotypic species distributed across Arctic and subarctic waters of the North Pacific and North Atlantic."
    );

    public static final NomenEntry HOMARUS_AMERICANUS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, HOMARUS_AMERICANUS,
            "homarus_americanus_nominotypical", "Homarus americanus",
            "The American lobster; a monotypic species ranging from Labrador to North Carolina along the Atlantic coast of North America."
    );

    public static final NomenEntry PANULIRUS_ARGUS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, PANULIRUS_ARGUS,
            "panulirus_argus_nominotypical", "Panulirus argus",
            "The Caribbean spiny lobster; a monotypic species distributed throughout the western Atlantic, Caribbean Sea, and Gulf of Mexico."
    );

    public static final NomenEntry PANDALUS_JORDANI_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, PANDALUS_JORDANI,
            "pandalus_jordani_nominotypical", "Pandalus jordani",
            "The Pacific pink shrimp; a monotypic species ranging from Baja California to British Columbia in cold, deep Pacific waters."
    );

    public static final NomenEntry PANDALUS_PLATYCEROS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, PANDALUS_PLATYCEROS,
            "pandalus_platyceros_nominotypical", "Pandalus platyceros",
            "The spot prawn; a monotypic species distributed from Alaska to San Diego in the cold, deep waters of the Pacific coast."
    );

    public static final NomenEntry SAXIDOMUS_GIGANTEA_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, SAXIDOMUS_GIGANTEA,
            "saxidomus_gigantea_nominotypical", "Saxidomus gigantea",
            "The butter clam; a monotypic species distributed from Alaska to California in intertidal and subtidal gravelly and sandy substrates."
    );

    public static final NomenEntry SILIQUA_PATULA_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, SILIQUA_PATULA,
            "siliqua_patula_nominotypical", "Siliqua patula",
            "The Pacific razor clam; a monotypic species inhabiting exposed sandy beaches from Alaska to California."
    );

    public static final NomenEntry PANOPEA_GENEROSA_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, PANOPEA_GENEROSA,
            "panopea_generosa_nominotypical", "Panopea generosa",
            "The geoduck; a monotypic species found in subtidal soft sediments from Alaska to Baja California."
    );

    public static final NomenEntry MAGALLANA_GIGAS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, MAGALLANA_GIGAS,
            "magallana_gigas_nominotypical", "Magallana gigas",
            "The Pacific oyster; a monotypic species native to the Pacific coast of Asia, now globally farmed."
    );

    public static final NomenEntry OSTREA_LURIDA_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, OSTREA_LURIDA,
            "ostrea_lurida_nominotypical", "Ostrea lurida",
            "The Olympia oyster; a monotypic species native to the Pacific coast of North America from Alaska to Baja California."
    );

    // =========================================================================
    // NAME CONTAINERS
    // =========================================================================

    public static final NameContainer KING_CRAB = new NameContainer(
            ANIMALIA, ARTHROPODA, MALACOSTRACA, DECAPODA, LITHODIDAE,
            PARALITHODES, PARALITHODES_CAMTSCHATICUS, PARALITHODES_CAMTSCHATICUS_NOMINOTYPICAL
    );

    public static final NameContainer DUNGENESS_CRAB = new NameContainer(
            ANIMALIA, ARTHROPODA, MALACOSTRACA, DECAPODA, CANCRIDAE,
            METACARCINUS, METACARCINUS_MAGISTER, METACARCINUS_MAGISTER_NOMINOTYPICAL
    );

    public static final NameContainer SNOW_CRAB = new NameContainer(
            ANIMALIA, ARTHROPODA, MALACOSTRACA, DECAPODA, OREGONIIDAE,
            CHIONOECETES, CHIONOECETES_OPILIO, CHIONOECETES_OPILIO_NOMINOTYPICAL
    );

    public static final NameContainer MAINE_LOBSTER = new NameContainer(
            ANIMALIA, ARTHROPODA, MALACOSTRACA, DECAPODA, NEPHROPIDAE,
            HOMARUS, HOMARUS_AMERICANUS, HOMARUS_AMERICANUS_NOMINOTYPICAL
    );

    public static final NameContainer SPINY_LOBSTER = new NameContainer(
            ANIMALIA, ARTHROPODA, MALACOSTRACA, DECAPODA, PALINURIDAE,
            PANULIRUS, PANULIRUS_ARGUS, PANULIRUS_ARGUS_NOMINOTYPICAL
    );

    public static final NameContainer PINK_SHRIMP = new NameContainer(
            ANIMALIA, ARTHROPODA, MALACOSTRACA, DECAPODA, PANDALIDAE,
            PANDALUS, PANDALUS_JORDANI, PANDALUS_JORDANI_NOMINOTYPICAL
    );

    public static final NameContainer SPOT_PRAWN = new NameContainer(
            ANIMALIA, ARTHROPODA, MALACOSTRACA, DECAPODA, PANDALIDAE,
            PANDALUS, PANDALUS_PLATYCEROS, PANDALUS_PLATYCEROS_NOMINOTYPICAL
    );

    public static final NameContainer BUTTER_CLAM = new NameContainer(
            ANIMALIA, MOLLUSCA, BIVALVIA_CLASS, VENERIDA, VENERIDAE,
            SAXIDOMUS, SAXIDOMUS_GIGANTEA, SAXIDOMUS_GIGANTEA_NOMINOTYPICAL
    );

    public static final NameContainer RAZOR_CLAM = new NameContainer(
            ANIMALIA, MOLLUSCA, BIVALVIA_CLASS, VENERIDA, PHARIDAE,
            SILIQUA, SILIQUA_PATULA, SILIQUA_PATULA_NOMINOTYPICAL
    );

    public static final NameContainer GEODUCK = new NameContainer(
            ANIMALIA, MOLLUSCA, BIVALVIA_CLASS, MYIDA, HIATELLIDAE,
            PANOPEA, PANOPEA_GENEROSA, PANOPEA_GENEROSA_NOMINOTYPICAL
    );

    public static final NameContainer PACIFIC_OYSTER = new NameContainer(
            ANIMALIA, MOLLUSCA, BIVALVIA_CLASS, OSTREOIDA, OSTREIDAE,
            MAGALLANA, MAGALLANA_GIGAS, MAGALLANA_GIGAS_NOMINOTYPICAL
    );

    public static final NameContainer OLYMPIA_OYSTER = new NameContainer(
            ANIMALIA, MOLLUSCA, BIVALVIA_CLASS, OSTREOIDA, OSTREIDAE,
            OSTREA, OSTREA_LURIDA, OSTREA_LURIDA_NOMINOTYPICAL
    );

    // =========================================================================
    // SPECIES — CRABS
    // =========================================================================

    public static final Species KING_CRAB_SPECIES = Species.builder("king_crab", "Paralithodes Camtschaticus", "Red King Crab")
            .addValidProperty(CRUSTACEAN_BODY)
            .addValidProperty(CRUSTACEAN_CARAPACE)
            .addValidProperty(CRUSTACEAN_HEAD)
            .addValidProperty(CRUSTACEAN_EYES)
            .addValidProperty(CRUSTACEAN_MOUTH)
            .addValidProperty(CRUSTACEAN_TORSO)
            .addValidProperty(CRUSTACEAN_CLAWS)
            .addValidProperty(CRUSTACEAN_WALKING_LEGS)
            .addValidProperty(CRUSTACEAN_TAIL)
            .addValidProperty(CRUSTACEAN_BRAIN)
            .addValidProperty(CRUSTACEAN_VITAL_ORGANS)
            .addValidProperty(CRUSTACEAN_IMMUNE_SYSTEM)
            .addValidProperty(CRUSTACEAN_GILLS)
            .addValidProperty(CRUSTACEAN_DIGESTIVE)
            .addValidProperty(CRUSTACEAN_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(5)
            .setNomenclature(KING_CRAB)
            .setLifeExpectancy(30)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(5)
            .setAgeOfElderly(22)
            .setAgeOfInfertilityMale(27)
            .setAgeOfInfertilityFemale(27)
            .addGenderRatio(CRUSTACEAN_BODY_SIZE, 1.5f)
            .build();

    public static final Species DUNGENESS_CRAB_SPECIES = Species.builder("dungeness_crab", "Metacarcinus Magister", "Dungeness Crab")
            .addValidProperty(CRUSTACEAN_BODY)
            .addValidProperty(CRUSTACEAN_CARAPACE)
            .addValidProperty(CRUSTACEAN_HEAD)
            .addValidProperty(CRUSTACEAN_EYES)
            .addValidProperty(CRUSTACEAN_MOUTH)
            .addValidProperty(CRUSTACEAN_TORSO)
            .addValidProperty(CRUSTACEAN_CLAWS)
            .addValidProperty(CRUSTACEAN_WALKING_LEGS)
            .addValidProperty(CRUSTACEAN_TAIL)
            .addValidProperty(CRUSTACEAN_BRAIN)
            .addValidProperty(CRUSTACEAN_VITAL_ORGANS)
            .addValidProperty(CRUSTACEAN_IMMUNE_SYSTEM)
            .addValidProperty(CRUSTACEAN_GILLS)
            .addValidProperty(CRUSTACEAN_DIGESTIVE)
            .addValidProperty(CRUSTACEAN_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(5)
            .setNomenclature(DUNGENESS_CRAB)
            .setLifeExpectancy(13)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(10)
            .setAgeOfInfertilityMale(11)
            .setAgeOfInfertilityFemale(11)
            .addGenderRatio(CRUSTACEAN_BODY_SIZE, 1.3f)
            .build();

    // Notable: males are dramatically larger than females
    public static final Species SNOW_CRAB_SPECIES = Species.builder("snow_crab", "Chionoecetes Opilio", "Snow Crab")
            .addValidProperty(CRUSTACEAN_BODY)
            .addValidProperty(CRUSTACEAN_CARAPACE)
            .addValidProperty(CRUSTACEAN_HEAD)
            .addValidProperty(CRUSTACEAN_EYES)
            .addValidProperty(CRUSTACEAN_MOUTH)
            .addValidProperty(CRUSTACEAN_TORSO)
            .addValidProperty(CRUSTACEAN_CLAWS)
            .addValidProperty(CRUSTACEAN_WALKING_LEGS)
            .addValidProperty(CRUSTACEAN_TAIL)
            .addValidProperty(CRUSTACEAN_BRAIN)
            .addValidProperty(CRUSTACEAN_VITAL_ORGANS)
            .addValidProperty(CRUSTACEAN_IMMUNE_SYSTEM)
            .addValidProperty(CRUSTACEAN_GILLS)
            .addValidProperty(CRUSTACEAN_DIGESTIVE)
            .addValidProperty(CRUSTACEAN_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(5)
            .setNomenclature(SNOW_CRAB)
            .setLifeExpectancy(20)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(4)
            .setAgeOfElderly(15)
            .setAgeOfInfertilityMale(18)
            .setAgeOfInfertilityFemale(18)
            .addGenderRatio(CRUSTACEAN_BODY_SIZE, 1.8f)
            .build();

    // =========================================================================
    // SPECIES — LOBSTERS
    // =========================================================================

    public static final Species MAINE_LOBSTER_SPECIES = Species.builder("maine_lobster", "Homarus Americanus", "American Lobster")
            .addValidProperty(CRUSTACEAN_BODY)
            .addValidProperty(CRUSTACEAN_CARAPACE)
            .addValidProperty(CRUSTACEAN_HEAD)
            .addValidProperty(CRUSTACEAN_EYES)
            .addValidProperty(CRUSTACEAN_MOUTH)
            .addValidProperty(CRUSTACEAN_TORSO)
            .addValidProperty(CRUSTACEAN_CLAWS)
            .addValidProperty(CRUSTACEAN_WALKING_LEGS)
            .addValidProperty(CRUSTACEAN_TAIL)
            .addValidProperty(CRUSTACEAN_BRAIN)
            .addValidProperty(CRUSTACEAN_VITAL_ORGANS)
            .addValidProperty(CRUSTACEAN_IMMUNE_SYSTEM)
            .addValidProperty(CRUSTACEAN_GILLS)
            .addValidProperty(CRUSTACEAN_DIGESTIVE)
            .addValidProperty(CRUSTACEAN_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(8)
            .setNomenclature(MAINE_LOBSTER)
            .setLifeExpectancy(50)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(7)
            .setAgeOfElderly(35)
            .setAgeOfInfertilityMale(45)
            .setAgeOfInfertilityFemale(40)
            .addGenderRatio(CRUSTACEAN_BODY_SIZE, 1.2f)
            .build();

    // No claws — spiny lobster has walking legs and antennae only
    public static final Species SPINY_LOBSTER_SPECIES = Species.builder("spiny_lobster", "Panulirus Argus", "Caribbean Spiny Lobster")
            .addValidProperty(CRUSTACEAN_BODY)
            .addValidProperty(CRUSTACEAN_CARAPACE)
            .addValidProperty(CRUSTACEAN_HEAD)
            .addValidProperty(CRUSTACEAN_EYES)
            .addValidProperty(CRUSTACEAN_MOUTH)
            .addValidProperty(CRUSTACEAN_TORSO)
            .addValidProperty(CRUSTACEAN_WALKING_LEGS)
            .addValidProperty(CRUSTACEAN_TAIL)
            .addValidProperty(CRUSTACEAN_BRAIN)
            .addValidProperty(CRUSTACEAN_VITAL_ORGANS)
            .addValidProperty(CRUSTACEAN_IMMUNE_SYSTEM)
            .addValidProperty(CRUSTACEAN_GILLS)
            .addValidProperty(CRUSTACEAN_DIGESTIVE)
            .addValidProperty(CRUSTACEAN_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(8)
            .setNomenclature(SPINY_LOBSTER)
            .setLifeExpectancy(20)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(5)
            .setAgeOfElderly(15)
            .setAgeOfInfertilityMale(18)
            .setAgeOfInfertilityFemale(18)
            .addGenderRatio(CRUSTACEAN_BODY_SIZE, 1.1f)
            .build();

    // =========================================================================
    // SPECIES — SHRIMP & PRAWNS
    // Note: both are protandrous hermaphrodites — born male, become female.
    // Females are larger; body size ratio reflects male-to-female (males smaller).
    // =========================================================================

    public static final Species PINK_SHRIMP_SPECIES = Species.builder("pink_shrimp", "Pandalus Jordani", "Pacific Pink Shrimp")
            .addValidProperty(CRUSTACEAN_BODY)
            .addValidProperty(CRUSTACEAN_CARAPACE)
            .addValidProperty(CRUSTACEAN_HEAD)
            .addValidProperty(CRUSTACEAN_EYES)
            .addValidProperty(CRUSTACEAN_MOUTH)
            .addValidProperty(CRUSTACEAN_TORSO)
            .addValidProperty(CRUSTACEAN_WALKING_LEGS)
            .addValidProperty(CRUSTACEAN_TAIL)
            .addValidProperty(CRUSTACEAN_BRAIN)
            .addValidProperty(CRUSTACEAN_VITAL_ORGANS)
            .addValidProperty(CRUSTACEAN_IMMUNE_SYSTEM)
            .addValidProperty(CRUSTACEAN_GILLS)
            .addValidProperty(CRUSTACEAN_DIGESTIVE)
            .addValidProperty(CRUSTACEAN_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(3)
            .setNomenclature(PINK_SHRIMP)
            .setLifeExpectancy(4)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(1)
            .setAgeOfElderly(3)
            .setAgeOfInfertilityMale(3)
            .setAgeOfInfertilityFemale(3)
            .addGenderRatio(CRUSTACEAN_BODY_SIZE, 0.7f)
            .build();

    public static final Species SPOT_PRAWN_SPECIES = Species.builder("spot_prawn", "Pandalus Platyceros", "Spot Prawn")
            .addValidProperty(CRUSTACEAN_BODY)
            .addValidProperty(CRUSTACEAN_CARAPACE)
            .addValidProperty(CRUSTACEAN_HEAD)
            .addValidProperty(CRUSTACEAN_EYES)
            .addValidProperty(CRUSTACEAN_MOUTH)
            .addValidProperty(CRUSTACEAN_TORSO)
            .addValidProperty(CRUSTACEAN_WALKING_LEGS)
            .addValidProperty(CRUSTACEAN_TAIL)
            .addValidProperty(CRUSTACEAN_BRAIN)
            .addValidProperty(CRUSTACEAN_VITAL_ORGANS)
            .addValidProperty(CRUSTACEAN_IMMUNE_SYSTEM)
            .addValidProperty(CRUSTACEAN_GILLS)
            .addValidProperty(CRUSTACEAN_DIGESTIVE)
            .addValidProperty(CRUSTACEAN_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(3)
            .setNomenclature(SPOT_PRAWN)
            .setLifeExpectancy(5)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(1)
            .setAgeOfElderly(4)
            .setAgeOfInfertilityMale(4)
            .setAgeOfInfertilityFemale(4)
            .addGenderRatio(CRUSTACEAN_BODY_SIZE, 0.7f)
            .build();

    // =========================================================================
    // SPECIES — CLAMS
    // =========================================================================

    public static final Species BUTTER_CLAM_SPECIES = Species.builder("butter_clam", "Saxidomus Gigantea", "Butter Clam")
            .addValidProperty(BIVALVE_BODY)
            .addValidProperty(BIVALVE_SHELL)
            .addValidProperty(BIVALVE_MANTLE)
            .addValidProperty(BIVALVE_FOOT)
            .addValidProperty(BIVALVE_VITAL_ORGANS)
            .addValidProperty(BIVALVE_IMMUNE_SYSTEM)
            .addValidProperty(BIVALVE_GILLS)
            .addValidProperty(BIVALVE_DIGESTIVE)
            .addValidProperty(BIVALVE_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(2)
            .setNomenclature(BUTTER_CLAM)
            .setLifeExpectancy(25)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(20)
            .setAgeOfInfertilityMale(23)
            .setAgeOfInfertilityFemale(23)
            .build();

    public static final Species RAZOR_CLAM_SPECIES = Species.builder("razor_clam", "Siliqua Patula", "Pacific Razor Clam")
            .addValidProperty(BIVALVE_BODY)
            .addValidProperty(BIVALVE_SHELL)
            .addValidProperty(BIVALVE_MANTLE)
            .addValidProperty(BIVALVE_FOOT)
            .addValidProperty(BIVALVE_VITAL_ORGANS)
            .addValidProperty(BIVALVE_IMMUNE_SYSTEM)
            .addValidProperty(BIVALVE_GILLS)
            .addValidProperty(BIVALVE_DIGESTIVE)
            .addValidProperty(BIVALVE_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(2)
            .setNomenclature(RAZOR_CLAM)
            .setLifeExpectancy(15)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(3)
            .setAgeOfElderly(12)
            .setAgeOfInfertilityMale(13)
            .setAgeOfInfertilityFemale(13)
            .build();

    // Notable: among the longest-lived animals on Earth; can exceed 140 years
    public static final Species GEODUCK_SPECIES = Species.builder("geoduck", "Panopea Generosa", "Geoduck")
            .addValidProperty(BIVALVE_BODY)
            .addValidProperty(BIVALVE_SHELL)
            .addValidProperty(BIVALVE_MANTLE)
            .addValidProperty(BIVALVE_FOOT)
            .addValidProperty(BIVALVE_VITAL_ORGANS)
            .addValidProperty(BIVALVE_IMMUNE_SYSTEM)
            .addValidProperty(BIVALVE_GILLS)
            .addValidProperty(BIVALVE_DIGESTIVE)
            .addValidProperty(BIVALVE_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(2)
            .setNomenclature(GEODUCK)
            .setLifeExpectancy(140)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(7)
            .setAgeOfElderly(100)
            .setAgeOfInfertilityMale(130)
            .setAgeOfInfertilityFemale(130)
            .build();

    // =========================================================================
    // SPECIES — OYSTERS
    // No BIVALVE_FOOT — oysters are sessile and cement to substrate
    // =========================================================================

    public static final Species PACIFIC_OYSTER_SPECIES = Species.builder("pacific_oyster", "Magallana Gigas", "Pacific Oyster")
            .addValidProperty(BIVALVE_BODY)
            .addValidProperty(BIVALVE_SHELL)
            .addValidProperty(BIVALVE_MANTLE)
            .addValidProperty(BIVALVE_VITAL_ORGANS)
            .addValidProperty(BIVALVE_IMMUNE_SYSTEM)
            .addValidProperty(BIVALVE_GILLS)
            .addValidProperty(BIVALVE_DIGESTIVE)
            .addValidProperty(BIVALVE_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(2)
            .setNomenclature(PACIFIC_OYSTER)
            .setLifeExpectancy(30)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(20)
            .setAgeOfInfertilityMale(27)
            .setAgeOfInfertilityFemale(27)
            .build();

    public static final Species OLYMPIA_OYSTER_SPECIES = Species.builder("olympia_oyster", "Ostrea Lurida", "Olympia Oyster")
            .addValidProperty(BIVALVE_BODY)
            .addValidProperty(BIVALVE_SHELL)
            .addValidProperty(BIVALVE_MANTLE)
            .addValidProperty(BIVALVE_VITAL_ORGANS)
            .addValidProperty(BIVALVE_IMMUNE_SYSTEM)
            .addValidProperty(BIVALVE_GILLS)
            .addValidProperty(BIVALVE_DIGESTIVE)
            .addValidProperty(BIVALVE_REPRODUCTIVE)
            .setMagicCapacity(0)
            .setSentience(2)
            .setNomenclature(OLYMPIA_OYSTER)
            .setLifeExpectancy(7)
            .setGenderLifeExpectancyRatio(1.0f)
            .setAgeOfMaturity(2)
            .setAgeOfElderly(5)
            .setAgeOfInfertilityMale(6)
            .setAgeOfInfertilityFemale(6)
            .build();
}