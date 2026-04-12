package com.objects.family;

import com.Feudalizer;
import com.Global;
import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;
import com.objects.character.human.HumanCharacter;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FamilyManager extends AbstractMutableManager<Family, FamilyState> {
    public static List<Family> getNuclear(HumanCharacter humanCharacter) {
        List<Family> families = new ArrayList<>();
        for (Map.Entry<Family,FamilyRelationship> family : humanCharacter.getFamilies().entrySet()) {
            if (family.getValue() != FamilyRelationship.CHILD) {
                families.add(family.getKey());
            }
        }
        families.sort(Comparator.comparing(Family::getCreated));
        Feudalizer.LOGGER.info("Families for " + humanCharacter.getGivenName() + ": " + families.size());
        return families;
    }

    public static Family getBirthFamily(HumanCharacter humanCharacter) {
        for (Map.Entry<Family,FamilyRelationship> family : humanCharacter.getFamilies().entrySet()) {
            if (family.getValue() == FamilyRelationship.CHILD) {
                return family.getKey();
            }
        }
        return null;
    }

    public static Family getCurrentFamily(HumanCharacter humanCharacter) {
        for (Family family : getNuclear(humanCharacter)) {
            if (family.getEnded().isAfter(Global.CURRENT_DATE())){
                return family;
            }
        }
        return null;
    }

    public static Family findOrCreateFamily(HumanCharacter main, HumanCharacter spouse, boolean makePrimary){
        for (Family f : getNuclear(main)){
            if (Arrays.asList(f.getSpouses()).contains(spouse)){
                return f;
            }
        }
        HumanCharacter primary;
        HumanCharacter secondary;
        if (makePrimary){
            primary = main;
            secondary = spouse;
        } else {
            primary = spouse;
            secondary = main;
        }
        Family nf = new Family(UUID.randomUUID(), Global.CURRENT_DATE(), primary, secondary, new ArrayList<>());
        primary.addFamily(nf, FamilyRelationship.PRIMARY_SPOUSE);
        secondary.addFamily(nf, FamilyRelationship.SECONDARY_SPOUSE);
        return nf;
    }

    public static Optional<HumanCharacter[]> getParents(HumanCharacter child){
        Family birthFamily = getBirthFamily(child);
        if (birthFamily == null){
            return Optional.empty();
        }
        HumanCharacter[] parents = birthFamily.getSpouses();
        Feudalizer.LOGGER.info("Parents for " + child.getGivenName() + ": " + parents.length);
        return Optional.of(parents);
    }

    public static Optional<HumanCharacter[]> getSiblings(HumanCharacter child){
        Family birthFamily = getBirthFamily(child);
        if (birthFamily == null){
            return Optional.empty();
        }
        return Optional.of(
                birthFamily.getChildrenOrdered().stream()
                        .filter(c -> !c.equals(child))
                        .toArray(HumanCharacter[]::new)
        );
    }

    public static List<HumanCharacter> getAllSpouses(HumanCharacter c, boolean oldestToYoungest){
        List<Pair<HumanCharacter, LocalDate>> spouses = new ArrayList<>();
        List<Family> families = getNuclear(c);
        for (Family family : families) {
            HumanCharacter spouse = Arrays.stream(family.getSpouses()).filter(sp -> sp != c).findFirst().orElse(null);
            if (spouse != null) {
                spouses.add(new Pair<>(spouse,family.getCreated()));
            }
        }
        if (oldestToYoungest) {
            spouses.sort(Comparator.comparing(Pair::getValue));
        } else {
            spouses.sort(Comparator.<Pair<HumanCharacter,LocalDate>, LocalDate>comparing(Pair::getValue).reversed());
        }
        Feudalizer.LOGGER.info("Spouses for " + c.getGivenName() + ": " + spouses.size());
        return spouses.stream().map(Pair::getKey).collect(Collectors.toList());
    }
    public static Pair<Set<HumanCharacter>,Set<Family>> getDynastyForward(HumanCharacter character){
        Set<HumanCharacter> dynasty = new HashSet<>();
        Set<Family> families = new HashSet<>();
        Deque<HumanCharacter> nextChecks = new ArrayDeque<>();
        HumanCharacter primary = character;
        while (primary != null) {
            for (Family family : getNuclear(primary)) {
                families.add(family);
                HumanCharacter spouse1 = family.getHeadofFamily();
                HumanCharacter spouse2 = family.getSecondarySpouse().orElse(null);
                if (spouse2 != null && !spouse2.getDisplayID().equals(primary.getDisplayID())) {
                    nextChecks.add(spouse2);
                    dynasty.add(spouse2);
                } else {
                    nextChecks.add(spouse1);
                    dynasty.add(spouse1);
                }
                for (HumanCharacter child : family.getChildrenOrdered()) {
                    dynasty.add(child);
                    nextChecks.add(child);
                }
            }
            Family birthFamily = getBirthFamily(primary);
            if (birthFamily != null) {
                for (HumanCharacter child : birthFamily.getChildrenOrdered()) {
                    dynasty.add(child);
                    nextChecks.add(child);
                }
                families.add(birthFamily);
            }
            primary = nextChecks.poll();
        }
        return dynasty;
    }
//    public static HashMap<FamilyGroups,Relationship> RebuildRelationshipMap(HumanCharacter character){
//
//    }
    @Override
    public Family deserializer(UUID id, JsonObject json) {
        return new Family(json);
    }
}
