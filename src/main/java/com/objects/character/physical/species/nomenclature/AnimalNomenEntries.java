package com.objects.character.physical.species.nomenclature;

import com.Global.*;

public class AnimalNomenEntries {

    // =========================================================================
    // KINGDOM
    // =========================================================================

    public static final NomenEntry ANIMALIA = NomenEntry.of(
            Nomenclature.Type.KINGDOM, null,
            "animalia", "Animalia",
            "The animal kingdom; multicellular eukaryotic organisms that are generally motile and obtain energy by consuming other organisms."
    );

    // =========================================================================
    // PHYLUM
    // =========================================================================

    public static final NomenEntry CHORDATA = NomenEntry.of(
            Nomenclature.Type.PHYLUM, ANIMALIA,
            "chordata", "Chordata",
            "Animals possessing a notochord at some point in development; includes all vertebrates and their closest invertebrate relatives."
    );

    // =========================================================================
    // CLASS
    // =========================================================================

    public static final NomenEntry MAMMALIA = NomenEntry.of(
            Nomenclature.Type.CLASS, CHORDATA,
            "mammalia", "Mammalia",
            "Warm-blooded vertebrates characterized by hair or fur, mammary glands, and live birth in most species."
    );

    public static final NomenEntry AVES = NomenEntry.of(
            Nomenclature.Type.CLASS, CHORDATA,
            "aves", "Aves",
            "Warm-blooded vertebrates characterized by feathers, toothless beaked jaws, and the laying of hard-shelled eggs."
    );

    public static final NomenEntry REPTILIA = NomenEntry.of(
            Nomenclature.Type.CLASS, CHORDATA,
            "reptilia", "Reptilia",
            "Cold-blooded, air-breathing vertebrates with scales or scutes; includes snakes, lizards, turtles, crocodilians, and tuataras."
    );

    public static final NomenEntry ACTINOPTERYGII = NomenEntry.of(
            Nomenclature.Type.CLASS, CHORDATA,
            "actinopterygii", "Actinopterygii",
            "Ray-finned fishes; the largest class of vertebrates, characterized by fins supported by bony spines and rays."
    );

    // =========================================================================
    // ORDER
    // =========================================================================

    public static final NomenEntry PRIMATES = NomenEntry.of(
            Nomenclature.Type.ORDER, MAMMALIA,
            "primates", "Primates",
            "Mammals characterized by large brains relative to body size, forward-facing eyes, and grasping hands; includes humans, apes, and monkeys."
    );

    public static final NomenEntry CARNIVORA = NomenEntry.of(
            Nomenclature.Type.ORDER, MAMMALIA,
            "carnivora", "Carnivora",
            "Mammals with specialized shearing teeth for eating flesh; includes dogs, cats, bears, weasels, and seals."
    );

    public static final NomenEntry RODENTIA = NomenEntry.of(
            Nomenclature.Type.ORDER, MAMMALIA,
            "rodentia", "Rodentia",
            "The largest order of mammals, characterized by a single pair of continuously growing incisors; includes mice, squirrels, beavers, and prairie dogs."
    );

    public static final NomenEntry ARTIODACTYLA = NomenEntry.of(
            Nomenclature.Type.ORDER, MAMMALIA,
            "artiodactyla", "Artiodactyla",
            "Even-toed ungulates; hoofed mammals that bear weight equally on the third and fourth toes; includes cattle, deer, pigs, and bison."
    );

    public static final NomenEntry CETACEA = NomenEntry.of(
            Nomenclature.Type.ORDER, MAMMALIA,
            "cetacea", "Cetacea",
            "Fully aquatic, hairless mammals descended from land-dwelling artiodactyls; includes whales, dolphins, and porpoises."
    );

    public static final NomenEntry ACCIPITRIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, AVES,
            "accipitriformes", "Accipitriformes",
            "Diurnal birds of prey; includes hawks, eagles, kites, harriers, and Old World vultures."
    );

    public static final NomenEntry STRIGIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, AVES,
            "strigiformes", "Strigiformes",
            "Owls; nocturnal birds of prey characterized by an upright posture, large forward-facing eyes, a facial disc, and near-silent flight."
    );

    public static final NomenEntry CROCODILIA = NomenEntry.of(
            Nomenclature.Type.ORDER, REPTILIA,
            "crocodilia", "Crocodilia",
            "Large, predatory semi-aquatic reptiles; includes crocodiles, alligators, caimans, and gharials, representing one of the oldest lineages of living reptiles."
    );

    public static final NomenEntry SQUAMATA = NomenEntry.of(
            Nomenclature.Type.ORDER, REPTILIA,
            "squamata", "Squamata",
            "The largest order of reptiles; includes all lizards, snakes, and worm lizards, united by a unique jaw structure enabling wide gape."
    );

    public static final NomenEntry SALMONIFORMES = NomenEntry.of(
            Nomenclature.Type.ORDER, ACTINOPTERYGII,
            "salmoniformes", "Salmoniformes",
            "An order of ray-finned fishes including salmon, trout, and their relatives; typically cold-water fish with an adipose fin."
    );

    // =========================================================================
    // FAMILY
    // =========================================================================

    public static final NomenEntry HOMINIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, PRIMATES,
            "hominidae", "Hominidae",
            "The great apes; the family containing humans, chimpanzees, bonobos, gorillas, and orangutans."
    );

    public static final NomenEntry CANIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, CARNIVORA,
            "canidae", "Canidae",
            "The dog family; digitigrade carnivorans with long muzzles, bushy tails, erect ears, and non-retractile claws."
    );

    public static final NomenEntry FELIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, CARNIVORA,
            "felidae", "Felidae",
            "The cat family; highly specialized obligate carnivores with retractile claws, binocular vision, and lithe, muscular bodies."
    );

    public static final NomenEntry URSIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, CARNIVORA,
            "ursidae", "Ursidae",
            "Bears; large, omnivorous carnivorans with heavy builds, large heads, small rounded ears, and plantigrade feet."
    );

    public static final NomenEntry MUSTELIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, CARNIVORA,
            "mustelidae", "Mustelidae",
            "The weasel family; small to large carnivorans characterized by long bodies, short legs, and musk-secreting anal scent glands."
    );

    public static final NomenEntry DELPHINIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, CETACEA,
            "delphinidae", "Delphinidae",
            "Oceanic dolphins; the largest family of cetaceans, characterized by a pronounced beak, conical teeth, and highly developed social intelligence."
    );

    public static final NomenEntry SCIURIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, RODENTIA,
            "sciuridae", "Sciuridae",
            "The squirrel family; includes tree squirrels, ground squirrels, chipmunks, marmots, and prairie dogs."
    );

    public static final NomenEntry CASTORIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, RODENTIA,
            "castoridae", "Castoridae",
            "Beavers; large, semi-aquatic rodents recognized by their broad flat tail and remarkable ability to engineer aquatic habitats through dam construction."
    );

    public static final NomenEntry BOVIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, ARTIODACTYLA,
            "bovidae", "Bovidae",
            "Hollow-horned ungulates; includes cattle, bison, sheep, goats, and antelope — the most species-rich family of hoofed mammals."
    );

    public static final NomenEntry SUIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, ARTIODACTYLA,
            "suidae", "Suidae",
            "Pigs and their relatives; omnivorous, even-toed ungulates distinguished by a cartilaginous snout adapted for rooting."
    );

    public static final NomenEntry CERVIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, ARTIODACTYLA,
            "cervidae", "Cervidae",
            "Deer; even-toed ungulates in which males typically bear bony antlers that are shed and regrown annually."
    );

    public static final NomenEntry ACCIPITRIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, ACCIPITRIFORMES,
            "accipitridae", "Accipitridae",
            "Hawks, eagles, and kites; diurnal raptors with strongly hooked beaks, keen eyesight, and powerful talons."
    );

    public static final NomenEntry STRIGIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, STRIGIFORMES,
            "strigidae", "Strigidae",
            "True owls; the larger owl family, distinguished by their round facial disc and asymmetrical ear placement for precise sound localization."
    );

    public static final NomenEntry ALLIGATORIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, CROCODILIA,
            "alligatoridae", "Alligatoridae",
            "Alligators and caimans; broad-snouted crocodilians in which the lower teeth are concealed when the mouth is closed."
    );

    public static final NomenEntry VIPERIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, SQUAMATA,
            "viperidae", "Viperidae",
            "Vipers; highly venomous snakes characterized by a pair of long, hollow, hinged fangs that fold flat when not in use."
    );

    public static final NomenEntry SALMONIDAE = NomenEntry.of(
            Nomenclature.Type.FAMILY, SALMONIFORMES,
            "salmonidae", "Salmonidae",
            "Salmon, trout, and whitefish; cold-water fishes known for anadromous migration and the distinctive adipose fin."
    );

    // =========================================================================
    // GENUS
    // =========================================================================

    public static final NomenEntry HOMO = NomenEntry.of(
            Nomenclature.Type.GENUS, HOMINIDAE,
            "homo", "Homo",
            "The genus containing modern humans and their closest extinct relatives, characterized by bipedal locomotion and large cranial capacity."
    );

    public static final NomenEntry CANIS = NomenEntry.of(
            Nomenclature.Type.GENUS, CANIDAE,
            "canis", "Canis",
            "The genus containing wolves, dogs, coyotes, and jackals; medium to large canids with a wide distribution across the globe."
    );

    public static final NomenEntry FELIS = NomenEntry.of(
            Nomenclature.Type.GENUS, FELIDAE,
            "felis", "Felis",
            "Small cats; the genus containing the domestic cat and its closest wild relatives found across Africa, Europe, and Asia."
    );

    public static final NomenEntry URSUS = NomenEntry.of(
            Nomenclature.Type.GENUS, URSIDAE,
            "ursus", "Ursus",
            "The genus containing brown bears, black bears, and polar bears; the most familiar and widely distributed bears in the Northern Hemisphere."
    );

    public static final NomenEntry LONTRA = NomenEntry.of(
            Nomenclature.Type.GENUS, MUSTELIDAE,
            "lontra", "Lontra",
            "New World river otters; semi-aquatic mustelids native to North and South America, adept swimmers that hunt fish and amphibians."
    );

    public static final NomenEntry ENHYDRA = NomenEntry.of(
            Nomenclature.Type.GENUS, MUSTELIDAE,
            "enhydra", "Enhydra",
            "Sea otters; the only fully marine mustelid genus, famous for floating on their backs and using stones as anvils to crack open shellfish."
    );

    public static final NomenEntry TURSIOPS = NomenEntry.of(
            Nomenclature.Type.GENUS, DELPHINIDAE,
            "tursiops", "Tursiops",
            "Bottlenose dolphins; highly intelligent, social oceanic dolphins recognized by their short, robust, bottle-shaped rostrum."
    );

    public static final NomenEntry CYNOMYS = NomenEntry.of(
            Nomenclature.Type.GENUS, SCIURIDAE,
            "cynomys", "Cynomys",
            "Prairie dogs; burrowing ground squirrels of the North American grasslands, known for complex vocal communication and vast social colonies called towns."
    );

    public static final NomenEntry CASTOR = NomenEntry.of(
            Nomenclature.Type.GENUS, CASTORIDAE,
            "castor", "Castor",
            "Beavers; the sole extant genus of Castoridae, comprising the North American and Eurasian beaver."
    );

    public static final NomenEntry BOS = NomenEntry.of(
            Nomenclature.Type.GENUS, BOVIDAE,
            "bos", "Bos",
            "Cattle and their wild relatives; large bovid ungulates including domestic cattle, yaks, and gaur."
    );

    public static final NomenEntry OVIS = NomenEntry.of(
            Nomenclature.Type.GENUS, BOVIDAE,
            "ovis", "Ovis",
            "Sheep; horned bovids of mountainous and open terrain, domesticated primarily for wool, meat, and milk."
    );

    public static final NomenEntry CAPRA = NomenEntry.of(
            Nomenclature.Type.GENUS, BOVIDAE,
            "capra", "Capra",
            "Goats; sure-footed bovids native to mountainous regions of the Middle East and Central Asia, domesticated for milk, meat, and fiber."
    );

    public static final NomenEntry BISON = NomenEntry.of(
            Nomenclature.Type.GENUS, BOVIDAE,
            "bison", "Bison",
            "Bison; the largest terrestrial animals in North America and Europe, characterized by a massive head, humped shoulders, and shaggy forequarters."
    );

    public static final NomenEntry SUS = NomenEntry.of(
            Nomenclature.Type.GENUS, SUIDAE,
            "sus", "Sus",
            "Pigs; omnivorous suids native to Eurasia and North Africa, among the earliest animals domesticated by humans."
    );

    public static final NomenEntry ALCES = NomenEntry.of(
            Nomenclature.Type.GENUS, CERVIDAE,
            "alces", "Alces",
            "Moose; the largest extant deer, characterized by palmate antlers in males, a pronounced dewlap, and elongated, flexible snout."
    );

    public static final NomenEntry ODOCOILEUS = NomenEntry.of(
            Nomenclature.Type.GENUS, CERVIDAE,
            "odocoileus", "Odocoileus",
            "New World deer; includes the white-tailed deer and mule deer, the most widespread deer of North and Central America."
    );

    public static final NomenEntry HALIAEETUS = NomenEntry.of(
            Nomenclature.Type.GENUS, ACCIPITRIDAE,
            "haliaeetus", "Haliaeetus",
            "Sea eagles and fish eagles; large raptors of coastlines and major waterways, known for plunging dives to catch fish."
    );

    public static final NomenEntry BUBO = NomenEntry.of(
            Nomenclature.Type.GENUS, STRIGIDAE,
            "bubo", "Bubo",
            "Horned owls and eagle-owls; the largest owls, characterized by prominent feathered ear tufts and deep, resonant vocalizations."
    );

    public static final NomenEntry ALLIGATOR_GENUS = NomenEntry.of(
            Nomenclature.Type.GENUS, ALLIGATORIDAE,
            "alligator_genus", "Alligator",
            "Alligators; broad-snouted crocodilians native to the southeastern United States and eastern China."
    );

    public static final NomenEntry CROTALUS = NomenEntry.of(
            Nomenclature.Type.GENUS, VIPERIDAE,
            "crotalus", "Crotalus",
            "Rattlesnakes; pit vipers of the Americas named for the segmented rattle on their tail, which produces a warning sound when vibrated."
    );

    public static final NomenEntry ONCORHYNCHUS = NomenEntry.of(
            Nomenclature.Type.GENUS, SALMONIDAE,
            "oncorhynchus", "Oncorhynchus",
            "Pacific salmon and Pacific trout; anadromous fish of the northern Pacific that die after spawning in their natal freshwater streams."
    );

    // =========================================================================
    // SPECIES
    // =========================================================================

    public static final NomenEntry HOMO_SAPIENS = NomenEntry.of(
            Nomenclature.Type.SPECIES, HOMO,
            "homo_sapiens", "Homo sapiens",
            "Modern humans; the only extant species of Homo, defined by bipedalism, a uniquely large brain, abstract language, and tool culture."
    );

    public static final NomenEntry CANIS_LUPUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, CANIS,
            "canis_lupus", "Canis lupus",
            "The grey wolf; the largest extant wild member of the family Canidae and the direct ancestor of the domestic dog."
    );

    public static final NomenEntry FELIS_CATUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, FELIS,
            "felis_catus", "Felis catus",
            "The domestic cat; a small, obligate carnivore domesticated from Near Eastern wildcats roughly 10,000 years ago."
    );

    public static final NomenEntry CYNOMYS_LUDOVICIANUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, CYNOMYS,
            "cynomys_ludovicianus", "Cynomys ludovicianus",
            "The black-tailed prairie dog; a highly social burrowing rodent of the Great Plains, distinguished by a black-tipped tail and alarm-call communication."
    );

    public static final NomenEntry CASTOR_CANADENSIS = NomenEntry.of(
            Nomenclature.Type.SPECIES, CASTOR,
            "castor_canadensis", "Castor canadensis",
            "The North American beaver; a large semi-aquatic rodent that reshapes riparian landscapes through dam and lodge construction."
    );

    public static final NomenEntry LONTRA_CANADENSIS = NomenEntry.of(
            Nomenclature.Type.SPECIES, LONTRA,
            "lontra_canadensis", "Lontra canadensis",
            "The North American river otter; a playful, semi-aquatic mustelid found along rivers, lakes, and coastlines across North America."
    );

    public static final NomenEntry ENHYDRA_LUTRIS = NomenEntry.of(
            Nomenclature.Type.SPECIES, ENHYDRA,
            "enhydra_lutris", "Enhydra lutris",
            "The sea otter; a fully marine mustelid of the north Pacific coast, notable for its extremely dense fur and tool use while foraging."
    );

    public static final NomenEntry HALIAEETUS_LEUCOCEPHALUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, HALIAEETUS,
            "haliaeetus_leucocephalus", "Haliaeetus leucocephalus",
            "The bald eagle; a large fish eagle native to North America and the national symbol of the United States, recognized by its white head and tail in adulthood."
    );

    public static final NomenEntry BUBO_VIRGINIANUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, BUBO,
            "bubo_virginianus", "Bubo virginianus",
            "The great horned owl; the most widespread owl in the Americas, an apex nocturnal predator with distinctive ear tufts and a powerful taloned grip."
    );

    public static final NomenEntry ALLIGATOR_MISSISSIPPIENSIS = NomenEntry.of(
            Nomenclature.Type.SPECIES, ALLIGATOR_GENUS,
            "alligator_mississippiensis", "Alligator mississippiensis",
            "The American alligator; a large freshwater crocodilian endemic to the southeastern United States, found in swamps, marshes, rivers, and lakes."
    );

    public static final NomenEntry CROTALUS_VIRIDIS = NomenEntry.of(
            Nomenclature.Type.SPECIES, CROTALUS,
            "crotalus_viridis", "Crotalus viridis",
            "The prairie rattlesnake; a venomous pit viper of the Great Plains and Rocky Mountain foothills, one of the most commonly encountered rattlesnakes in North America."
    );

    public static final NomenEntry TURSIOPS_TRUNCATUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, TURSIOPS,
            "tursiops_truncatus", "Tursiops truncatus",
            "The common bottlenose dolphin; one of the best-studied cetaceans, found in warm and temperate seas worldwide and known for high intelligence and complex social bonds."
    );

    public static final NomenEntry ONCORHYNCHUS_TSHAWYTSCHA = NomenEntry.of(
            Nomenclature.Type.SPECIES, ONCORHYNCHUS,
            "oncorhynchus_tshawytscha", "Oncorhynchus tshawytscha",
            "The Chinook salmon; the largest Pacific salmon species, a keystone species of Pacific Northwest rivers and an iconic fish of indigenous and commercial culture."
    );

    public static final NomenEntry BOS_TAURUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, BOS,
            "bos_taurus", "Bos taurus",
            "Domestic cattle; descended from the now-extinct aurochs, one of humanity's most important livestock species kept worldwide for beef, dairy, and labor."
    );

    public static final NomenEntry SUS_SCROFA = NomenEntry.of(
            Nomenclature.Type.SPECIES, SUS,
            "sus_scrofa", "Sus scrofa",
            "The wild boar and domestic pig; the most widespread suid, native to Eurasia and North Africa and introduced to every continent except Antarctica."
    );

    public static final NomenEntry OVIS_ARIES = NomenEntry.of(
            Nomenclature.Type.SPECIES, OVIS,
            "ovis_aries", "Ovis aries",
            "The domestic sheep; one of the earliest animals domesticated by humans, derived from wild mouflon and raised globally for wool, meat, and milk."
    );

    public static final NomenEntry CAPRA_HIRCUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, CAPRA,
            "capra_hircus", "Capra hircus",
            "The domestic goat; domesticated from the wild bezoar ibex approximately 10,000 years ago, widely kept for milk, meat, fiber, and hide."
    );

    public static final NomenEntry BISON_BISON = NomenEntry.of(
            Nomenclature.Type.SPECIES, BISON,
            "bison_bison", "Bison bison",
            "The American bison; the heaviest land animal in North America, once numbering in the tens of millions before near-extirpation by commercial hunting in the 19th century."
    );

    public static final NomenEntry URSUS_ARCTOS = NomenEntry.of(
            Nomenclature.Type.SPECIES, URSUS,
            "ursus_arctos", "Ursus arctos",
            "The brown bear; among the most widely distributed bear species, found across North America, Europe, and Asia in a variety of habitats."
    );

    public static final NomenEntry URSUS_AMERICANUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, URSUS,
            "ursus_americanus", "Ursus americanus",
            "The American black bear; the most common and widespread bear species in North America, highly adaptable to forest and mountainous environments."
    );

    public static final NomenEntry URSUS_MARITIMUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, URSUS,
            "ursus_maritimus", "Ursus maritimus",
            "The polar bear; a hypercarnivorous bear of the Arctic Ocean and surrounding seas, uniquely adapted for life on sea ice and the hunting of ringed seals."
    );

    public static final NomenEntry ALCES_ALCES = NomenEntry.of(
            Nomenclature.Type.SPECIES, ALCES,
            "alces_alces", "Alces alces",
            "The moose; the largest and tallest extant deer, distinguished by the broad palmate antlers of males and a highly elongated, prehensile snout."
    );

    public static final NomenEntry ODOCOILEUS_VIRGINIANUS = NomenEntry.of(
            Nomenclature.Type.SPECIES, ODOCOILEUS,
            "odocoileus_virginianus", "Odocoileus virginianus",
            "The white-tailed deer; the most broadly distributed deer in the Americas, named for the bright white underside of its tail raised as a flight signal."
    );

    // =========================================================================
    // SUBSPECIES
    // =========================================================================

    public static final NomenEntry HOMO_SAPIENS_SAPIENS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, HOMO_SAPIENS,
            "homo_sapiens_sapiens", "Homo sapiens sapiens",
            "Anatomically modern humans; the sole surviving subspecies of Homo sapiens, present on every continent and characterized by gracile skeletal morphology."
    );

    public static final NomenEntry CANIS_LUPUS_FAMILIARIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CANIS_LUPUS,
            "canis_lupus_familiaris", "Canis lupus familiaris",
            "The domestic dog; a subspecies of the grey wolf selectively bred by humans over at least 15,000 years into an extraordinary variety of forms and temperaments."
    );

    public static final NomenEntry CANIS_LUPUS_OCCIDENTALIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CANIS_LUPUS,
            "canis_lupus_occidentalis", "Canis lupus occidentalis",
            "The grey wolf (Northwestern wolf); a large subspecies native to the Rocky Mountains and western Canada, reintroduced to Yellowstone in 1995."
    );

    public static final NomenEntry CANIS_LUPUS_LYCAON = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CANIS_LUPUS,
            "canis_lupus_lycaon", "Canis lupus lycaon",
            "The timber wolf (Eastern wolf); a medium-sized subspecies inhabiting the Great Lakes region and eastern Canadian boreal forests."
    );

    public static final NomenEntry CANIS_LUPUS_LUPUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CANIS_LUPUS,
            "canis_lupus_lupus", "Canis lupus lupus",
            "The Eurasian wolf; the nominate and most geographically widespread subspecies, distributed across Europe and Asia."
    );

    public static final NomenEntry CANIS_LUPUS_ARCTOS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CANIS_LUPUS,
            "canis_lupus_arctos", "Canis lupus arctos",
            "The Arctic wolf; a white-coated subspecies of the grey wolf adapted for the extreme cold of the Canadian Arctic Archipelago and northern Greenland."
    );

    public static final NomenEntry FELIS_CATUS_CATUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, FELIS_CATUS,
            "felis_catus_catus", "Felis catus catus",
            "The domestic cat (nominotypical subspecies); the ubiquitous companion animal kept in households across every inhabited continent."
    );

    public static final NomenEntry CYNOMYS_LUDOVICIANUS_LUDOVICIANUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CYNOMYS_LUDOVICIANUS,
            "cynomys_ludovicianus_ludovicianus", "Cynomys ludovicianus ludovicianus",
            "The black-tailed prairie dog (nominotypical subspecies); the primary form inhabiting shortgrass and mixed-grass prairies of the central Great Plains."
    );

    public static final NomenEntry CASTOR_CANADENSIS_CANADENSIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CASTOR_CANADENSIS,
            "castor_canadensis_canadensis", "Castor canadensis canadensis",
            "The North American beaver (nominotypical subspecies); the primary form across central and eastern North America and the largest rodent on the continent."
    );

    public static final NomenEntry LONTRA_CANADENSIS_CANADENSIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, LONTRA_CANADENSIS,
            "lontra_canadensis_canadensis", "Lontra canadensis canadensis",
            "The North American river otter (nominotypical subspecies); found throughout inland waterways from Canada to the eastern United States."
    );

    public static final NomenEntry ENHYDRA_LUTRIS_KENYONI = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ENHYDRA_LUTRIS,
            "enhydra_lutris_kenyoni", "Enhydra lutris kenyoni",
            "The northern sea otter; the subspecies inhabiting the coast from Alaska to Washington State, the most numerous of the three recognized sea otter subspecies."
    );

    public static final NomenEntry HALIAEETUS_LEUCOCEPHALUS_WASHINGTONIENSIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, HALIAEETUS_LEUCOCEPHALUS,
            "haliaeetus_leucocephalus_washingtoniensis", "Haliaeetus leucocephalus washingtoniensis",
            "The northern bald eagle; the larger subspecies found in Canada and the northern United States, with a wingspan reaching up to 2.4 meters."
    );

    public static final NomenEntry BUBO_VIRGINIANUS_VIRGINIANUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, BUBO_VIRGINIANUS,
            "bubo_virginianus_virginianus", "Bubo virginianus virginianus",
            "The great horned owl (nominotypical subspecies); the reference form inhabiting eastern North America from the boreal forests of Canada to the Gulf Coast."
    );

    public static final NomenEntry ALLIGATOR_MISSISSIPPIENSIS_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ALLIGATOR_MISSISSIPPIENSIS,
            "alligator_mississippiensis_nominotypical", "Alligator mississippiensis",
            "The American alligator; a monotypic species with no recognized subspecies, inhabiting freshwater wetlands of the southeastern United States."
    );

    public static final NomenEntry CROTALUS_VIRIDIS_VIRIDIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CROTALUS_VIRIDIS,
            "crotalus_viridis_viridis", "Crotalus viridis viridis",
            "The prairie rattlesnake (nominotypical subspecies); the most common form found across the Great Plains from southern Canada to northern Mexico."
    );

    public static final NomenEntry TURSIOPS_TRUNCATUS_TRUNCATUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, TURSIOPS_TRUNCATUS,
            "tursiops_truncatus_truncatus", "Tursiops truncatus truncatus",
            "The common bottlenose dolphin (nominotypical subspecies); the widespread coastal and offshore form found in temperate and tropical seas worldwide."
    );

    public static final NomenEntry ONCORHYNCHUS_TSHAWYTSCHA_NOMINOTYPICAL = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ONCORHYNCHUS_TSHAWYTSCHA,
            "oncorhynchus_tshawytscha_nominotypical", "Oncorhynchus tshawytscha",
            "The Chinook salmon; a monotypic species with no formally recognized subspecies, though stream-type and ocean-type ecotypes are distinguished by ecology."
    );

    public static final NomenEntry BOS_TAURUS_TAURUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, BOS_TAURUS,
            "bos_taurus_taurus", "Bos taurus taurus",
            "Taurine domestic cattle (nominotypical subspecies); the European-derived lineage of domestic cattle, the most widely kept livestock form worldwide."
    );

    public static final NomenEntry SUS_SCROFA_DOMESTICUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, SUS_SCROFA,
            "sus_scrofa_domesticus", "Sus scrofa domesticus",
            "The domestic pig; the fully domesticated subspecies of the wild boar, bred for thousands of years for meat, lard, hide, and bristle."
    );

    public static final NomenEntry OVIS_ARIES_ARIES = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, OVIS_ARIES,
            "ovis_aries_aries", "Ovis aries aries",
            "The domestic sheep (nominotypical subspecies); the primary woolled form of domesticated sheep kept across temperate regions worldwide."
    );

    public static final NomenEntry CAPRA_HIRCUS_HIRCUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, CAPRA_HIRCUS,
            "capra_hircus_hircus", "Capra hircus hircus",
            "The domestic goat (nominotypical subspecies); among the earliest and most geographically widespread of all domesticated animals."
    );

    public static final NomenEntry BISON_BISON_BISON = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, BISON_BISON,
            "bison_bison_bison", "Bison bison bison",
            "The plains bison; the more numerous subspecies of American bison, historically numbering in the tens of millions across the shortgrass and tallgrass Great Plains."
    );

    public static final NomenEntry URSUS_ARCTOS_HORRIBILIS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, URSUS_ARCTOS,
            "ursus_arctos_horribilis", "Ursus arctos horribilis",
            "The grizzly bear; a large, inland brown bear subspecies of North America named for its grizzled fur, distinguished by a prominent shoulder hump of muscle."
    );

    public static final NomenEntry URSUS_ARCTOS_CALIFORNICUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, URSUS_ARCTOS,
            "ursus_arctos_californicus", "Ursus arctos californicus",
            "The California grizzly bear; an extinct subspecies of brown bear once emblematic of California, driven to extinction by the early 20th century through habitat loss and hunting."
    );

    public static final NomenEntry URSUS_AMERICANUS_AMERICANUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, URSUS_AMERICANUS,
            "ursus_americanus_americanus", "Ursus americanus americanus",
            "The eastern black bear (nominotypical subspecies); the most common form of black bear, inhabiting the forests of eastern North America."
    );

    public static final NomenEntry URSUS_MARITIMUS_MARITIMUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, URSUS_MARITIMUS,
            "ursus_maritimus_maritimus", "Ursus maritimus maritimus",
            "The polar bear; a monotypic species with no recognized subspecies, uniquely specialized for hunting on Arctic sea ice."
    );

    public static final NomenEntry ALCES_ALCES_AMERICANUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ALCES_ALCES,
            "alces_alces_americanus", "Alces alces americanus",
            "The eastern moose; a large North American subspecies distributed from the boreal forests of Canada through the northeastern United States and the Great Lakes region."
    );

    public static final NomenEntry ODOCOILEUS_VIRGINIANUS_VIRGINIANUS = NomenEntry.of(
            Nomenclature.Type.SUBSPECIES, ODOCOILEUS_VIRGINIANUS,
            "odocoileus_virginianus_virginianus", "Odocoileus virginianus virginianus",
            "The Virginia white-tailed deer (nominotypical subspecies); the reference form of the most widespread deer in the Americas, native to the eastern United States."
    );
}