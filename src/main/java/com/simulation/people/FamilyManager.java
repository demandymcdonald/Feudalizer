package com.simulation.people;

import com.Feudalizer;
import com.GlobalVars;
import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;
import com.simulation.character.BookCharacter;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FamilyManager extends AbstractMutableManager<Family,FamilyState> {
    public static List<Family> getNuclear(BookCharacter bookCharacter) {
        List<Family> families = new ArrayList<>();
        for (Map.Entry<Family,FamilyRelationship> family : bookCharacter.getFamilies().entrySet()) {
            if (family.getValue() != FamilyRelationship.CHILD) {
                families.add(family.getKey());
            }
        }
        families.sort(Comparator.comparing(Family::getCreated));
        Feudalizer.LOGGER.info("Families for " + bookCharacter.getGivenName() + ": " + families.size());
        return families;
    }

    public static Family getBirthFamily(BookCharacter bookCharacter) {
        for (Map.Entry<Family,FamilyRelationship> family : bookCharacter.getFamilies().entrySet()) {
            if (family.getValue() == FamilyRelationship.CHILD) {
                return family.getKey();
            }
        }
        return null;
    }

    public static Family getCurrentFamily(BookCharacter bookCharacter) {
        for (Family family : getNuclear(bookCharacter)) {
            if (family.getEnded().isAfter(GlobalVars.CURRENT_DATE())){
                return family;
            }
        }
        return null;
    }

    public static Family findOrCreateFamily(BookCharacter main, BookCharacter spouse, boolean makePrimary){
        for (Family f : getNuclear(main)){
            if (Arrays.asList(f.getSpouses()).contains(spouse)){
                return f;
            }
        }
        BookCharacter primary;
        BookCharacter secondary;
        if (makePrimary){
            primary = main;
            secondary = spouse;
        } else {
            primary = spouse;
            secondary = main;
        }
        Family nf = new Family(UUID.randomUUID(), GlobalVars.CURRENT_DATE(), primary, secondary, new ArrayList<>());
        primary.addFamily(nf, FamilyRelationship.PRIMARY_SPOUSE);
        secondary.addFamily(nf, FamilyRelationship.SECONDARY_SPOUSE);
        return nf;
    }

    public static Optional<BookCharacter[]> getParents(BookCharacter child){
        Family birthFamily = getBirthFamily(child);
        if (birthFamily == null){
            return Optional.empty();
        }
        BookCharacter[] parents = birthFamily.getSpouses();
        Feudalizer.LOGGER.info("Parents for " + child.getGivenName() + ": " + parents.length);
        return Optional.of(parents);
    }

    public static Optional<BookCharacter[]> getSiblings(BookCharacter child){
        Family birthFamily = getBirthFamily(child);
        if (birthFamily == null){
            return Optional.empty();
        }
        return Optional.of(
                birthFamily.getChildrenOrdered().stream()
                        .filter(c -> !c.equals(child))
                        .toArray(BookCharacter[]::new)
        );
    }

    public static List<BookCharacter> getAllSpouses(BookCharacter c, boolean oldestToYoungest){
        List<Pair<BookCharacter, LocalDate>> spouses = new ArrayList<>();
        List<Family> families = getNuclear(c);
        for (Family family : families) {
            BookCharacter spouse = Arrays.stream(family.getSpouses()).filter(sp -> sp != c).findFirst().orElse(null);
            if (spouse != null) {
                spouses.add(new Pair<>(spouse,family.getCreated()));
            }
        }
        if (oldestToYoungest) {
            spouses.sort(Comparator.comparing(Pair::getValue));
        } else {
            spouses.sort(Comparator.<Pair<BookCharacter,LocalDate>, LocalDate>comparing(Pair::getValue).reversed());
        }
        Feudalizer.LOGGER.info("Spouses for " + c.getGivenName() + ": " + spouses.size());
        return spouses.stream().map(Pair::getKey).collect(Collectors.toList());
    }
    public static Pair<Set<BookCharacter>,Set<Family>> getDynastyForward(BookCharacter character){
        Set<BookCharacter> dynasty = new HashSet<>();
        Set<Family> families = new HashSet<>();
        Deque<BookCharacter> nextChecks = new ArrayDeque<>();
        BookCharacter primary = character;
        while (primary != null) {
            for (Family family : getNuclear(primary)) {
                families.add(family);
                BookCharacter spouse1 = family.getHeadofFamily();
                BookCharacter spouse2 = family.getSecondarySpouse().orElse(null);
                if (spouse2 != null && !spouse2.getId().equals(primary.getId())) {
                    nextChecks.add(spouse2);
                    dynasty.add(spouse2);
                } else {
                    nextChecks.add(spouse1);
                    dynasty.add(spouse1);
                }
                for (BookCharacter child : family.getChildrenOrdered()) {
                    dynasty.add(child);
                    nextChecks.add(child);
                }
            }
            Family birthFamily = getBirthFamily(primary);
            if (birthFamily != null) {
                for (BookCharacter child : birthFamily.getChildrenOrdered()) {
                    dynasty.add(child);
                    nextChecks.add(child);
                }
                families.add(birthFamily);
            }
            primary = nextChecks.poll();
        }
        return dynasty;
    }
//    public static HashMap<Family,Relationship> RebuildRelationshipMap(BookCharacter character){
//
//    }
    @Override
    public Family deserializer(UUID id, JsonObject json) {
        return new Family(json);
    }
}
