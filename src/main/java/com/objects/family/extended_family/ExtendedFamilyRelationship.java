package com.objects.family.extended_family;

import com.Feudalizer;
import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.character.human.HumanCharacter;
import com.objects.family.Family;
import com.utilities.number.OrdinalAndCardinal;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public record ExtendedFamilyRelationship(int position, ExtendedRelationship relationship, boolean isInLaw, boolean isAdoptive, int removed) {
    public String parse(){
        StringBuilder sb = new StringBuilder();
        if (relationship.usesStacking()){
            for (int i = 0; i < position; i++) {
                sb.append(relationship.getStackingWord()).append(" ");
            }
        } else if (position > 0){
            sb.append(OrdinalAndCardinal.numeral(position));
        }
        sb.append(relationship.getDisplayWord());
        if (isInLaw){
            sb.append("-in-law");
        }
        if (removed > 0){
            sb.append(" (").append(OrdinalAndCardinal.cardinal(removed)).append(" removed)");
        }
        return sb.toString();
    }
    public static Pair<List<ExtendedFamilyRelationship>,List<ExtendedFamilyRelationship>> calculate(HumanCharacter characterA, HumanCharacter characterB){
        List<ExtendedFamilyRelationship> a = new ArrayList<>();
        List<ExtendedFamilyRelationship> b = new ArrayList<>();


        Multimap<HumanCharacter,AncestorInfo> ancestorsA = ancestors(characterA);
        Multimap<HumanCharacter,AncestorInfo> ancestorsB = ancestors(characterB);
        for (HumanCharacter ancestor : ancestorsA.keySet()) {
            if (ancestorsB.containsKey(ancestor)) {
                List<AncestorInfo> m = new ArrayList<>(ancestorsA.get(ancestor));
                List<AncestorInfo> n = new ArrayList<>(ancestorsB.get(ancestor));
                for (AncestorInfo infoM : m) {
                    for (AncestorInfo infoN : n) {
                        Optional<Pair<ExtendedFamilyRelationship,ExtendedFamilyRelationship>> pair = deriveRelationship(infoM, infoN, characterA.getGender(), characterB.getGender());
                        if(pair.isPresent()){
                            Pair<ExtendedFamilyRelationship,ExtendedFamilyRelationship> pn = pair.get();
                            if (!a.contains(pn.getLeft())){
                                a.add(pn.getLeft());
                            }
                            if (!b.contains(pn.getRight())){
                                b.add(pn.getRight());
                            }
                        }
                    }
                }

            }
        }


        return null;
    }
    private static Multimap<HumanCharacter,AncestorInfo> ancestors(HumanCharacter p){
        Multimap<HumanCharacter, AncestorInfo> ancestors = HashMultimap.create();
        Queue<Pair<HumanCharacter,Boolean>> queue = new LinkedList<>();
        queue.add(Pair.of(p,false));
        ancestors.put(p, new AncestorInfo(0,false,false));
        for (DMEReference<HumanCharacter> hc : p.getSpouses()){
            if (hc.equals(p)){
                Feudalizer.LOGGER.error("Spouse is self from getSpouses()! That shouldn't happen, dumbass!");
                continue;
            }
            HumanCharacter spouse = hc.get();
            if (spouse != null) {
                queue.add(Pair.of(spouse,true));
                ancestors.put(spouse, new AncestorInfo(0,false,true));
            }
        }
        while (!queue.isEmpty()) {
            final Pair<HumanCharacter,Boolean> pair = queue.poll();
            final HumanCharacter current = pair.getLeft();
            final List<AncestorInfo> info = new ArrayList<>(ancestors.get(current));
            final boolean isInLaw = pair.getRight();
            for (AncestorInfo a : info) {
                int distance = a.distance();
                Deque<Family> toCheck = new ArrayDeque<>();
                toCheck.add(current.getOriginFamily(false));
                if (current.isAdopted()) {
                    toCheck.add(current.getOriginFamily(true));
                }
                boolean isAdoptive =a.isAdoptive();
                while (!toCheck.isEmpty()) {
                    Family check = toCheck.poll();
                    if (check != null) {
                        for (DMEReference<HumanCharacter> parent : check.getParents()) {
                            HumanCharacter parentCharacter = parent.get();
                            List<AncestorInfo> newInfo = new ArrayList<>(ancestors.get(parentCharacter));
                            if (newInfo.isEmpty()) {
                                ancestors.put(parentCharacter, new AncestorInfo(distance + 1, isAdoptive, isInLaw));
                                queue.add(Pair.of(parentCharacter, isInLaw));
                            } else {
                                for (AncestorInfo existing : newInfo) {
                                    if (multimapNeedsEntry(ancestors, parentCharacter, existing.isInLaw(), isAdoptive)) {
                                        // found a better (blood) path to someone already mapped as in-law
                                        ancestors.put(parentCharacter, new AncestorInfo(existing.distance(), isAdoptive, isInLaw));
                                    }
                                }
                            }
                        }
                    }
                    isAdoptive = true;
                }
            }
        }
        return ancestors;
    }
    private static boolean multimapNeedsEntry(Multimap<HumanCharacter,AncestorInfo> ancestors, HumanCharacter character, boolean isInLaw, boolean isAdoptive) {
        for(AncestorInfo info : ancestors.get(character)){
            if (info.isInLaw() != isInLaw && info.isAdoptive() != isAdoptive){
                return true;
            }
        }
        return false;
    }

    private static Optional<Pair<ExtendedFamilyRelationship,ExtendedFamilyRelationship>> deriveRelationship(AncestorInfo mInfo, AncestorInfo nInfo, HumanCharacter.Gender mGender, HumanCharacter.Gender nGender) {
        // TODO: the formula logic we discussed
        // m and n are distances from each person to the common ancestor
        // remember to handle the isInLaw flag here too
        // normalize so m <= n
        final int m = mInfo.distance();
        final int n = nInfo.distance();
        final boolean isInLaw =  mInfo.isInLaw() || nInfo.isInLaw();
        final boolean isAdoptive = mInfo.isAdoptive() || nInfo.isAdoptive();


        boolean swapped = m > n;
        int lo = Math.min(m, n);
        int hi = Math.max(m, n);

        ExtendedRelationship relationshipM;
        ExtendedRelationship relationshipN;
        int position;
        int removed = 0;


        if (lo == 0 && hi == 0) {
            // same person, shouldn't happen
            return Optional.empty();
        } else if (lo == 0 && hi == 1) {
            relationshipM = swapped ? getChild(mGender) : getParent(mGender);
            relationshipN = !swapped ? getChild(nGender) : getParent(nGender);
            position = 0;
        } else if (lo == 0) {
            relationshipM = swapped ? getGrandChild(mGender) : getGrandParent(mGender);
            relationshipN = !swapped ? getGrandChild(nGender) : getGrandParent(nGender);
            position = hi - 2; // 0 = grandparent, 1 = great grandparent, etc.
        } else if (lo == 1 && hi == 1) {
            relationshipM = getSibling(mGender);
            relationshipN = getSibling(nGender);
            position = 0;
        } else if (lo == 1) {
            relationshipM = swapped ? getNibling(mGender) : getPibling(mGender);
            relationshipN = !swapped ? getNibling(nGender) : getPibling(nGender);
            position = hi - 2; // 0 = aunt/uncle, 1 = great aunt/uncle, etc.
        } else {
            relationshipM = ExtendedRelationship.Cousin;
            relationshipN = ExtendedRelationship.Cousin;
            position = lo - 2; // 0 = first cousin, 1 = second cousin, etc.
            removed = hi - lo;
        }
//        Pretty sure it should be overall, because if one is adopted, then the relationship will always be adoptive, right? Same with in law?
//        return Optional.of(Pair.of(
//                new ExtendedFamilyRelationship(position, relationshipM, mInfo.isInLaw() , mInfo.isAdoptive() , removed),
//                new ExtendedFamilyRelationship(position, relationshipN, nInfo.isInLaw() , nInfo.isAdoptive() , removed)
//        );
        return Optional.of(Pair.of(
                new ExtendedFamilyRelationship(position, relationshipM, isInLaw , isAdoptive, removed),
                new ExtendedFamilyRelationship(position, relationshipN, isInLaw , isAdoptive , removed)
        ));
    }

    record AncestorInfo(int distance, boolean isAdoptive, boolean isInLaw) {}
    private static ExtendedRelationship getSibling(HumanCharacter.Gender gender){
       switch (gender.getPronouns()){
           case Neutral -> {return ExtendedRelationship.Neutral_Sibling;}
           case Masculine -> {return ExtendedRelationship.Brother;}
           case Feminine -> {return ExtendedRelationship.Sister;}
       }
        throw new IllegalStateException("Unhandled pronoun type: " + gender.getPronouns());
    }
    private static ExtendedRelationship getParent(HumanCharacter.Gender gender){
        switch (gender.getPronouns()){
            case Neutral -> {return ExtendedRelationship.Neutral_Parent;}
            case Masculine -> {return ExtendedRelationship.Father;}
            case Feminine -> {return ExtendedRelationship.Mother;}
        }
        throw new IllegalStateException("Unhandled pronoun type: " + gender.getPronouns());
    }
    public static ExtendedRelationship getChild(HumanCharacter.Gender gender){
        switch (gender.getPronouns()){
            case Neutral -> {return ExtendedRelationship.Neutral_Child;}
            case Masculine -> {return ExtendedRelationship.Son;}
            case Feminine -> {return ExtendedRelationship.Daughter;}
        }
        throw new IllegalStateException("Unhandled pronoun type: " + gender.getPronouns());
    }
    private static ExtendedRelationship getGrandParent(HumanCharacter.Gender gender){
        switch (gender.getPronouns()){
            case Neutral -> {return ExtendedRelationship.Neutral_Grandparent;}
            case Masculine -> {return ExtendedRelationship.GrandFather;}
            case Feminine -> {return ExtendedRelationship.GrandMother;}
        }
        throw new IllegalStateException("Unhandled pronoun type: " + gender.getPronouns());
    }
    private static ExtendedRelationship getGrandChild(HumanCharacter.Gender gender){
        switch (gender.getPronouns()){
            case Neutral -> {return ExtendedRelationship.Neutral_Grandchild;}
            case Masculine -> {return ExtendedRelationship.GrandSon;}
            case Feminine -> {return ExtendedRelationship.GrandDaughter;}
        }
        throw new IllegalStateException("Unhandled pronoun type: " + gender.getPronouns());
    }
    private static ExtendedRelationship getPibling(HumanCharacter.Gender gender){
        switch (gender.getPronouns()){
            case Neutral -> {return ExtendedRelationship.Neutral_Pibling;}
            case Masculine -> {return ExtendedRelationship.Uncle;}
            case Feminine -> {return ExtendedRelationship.Aunt;}
        }
        throw new IllegalStateException("Unhandled pronoun type: " + gender.getPronouns());
    }
    private static ExtendedRelationship getNibling(HumanCharacter.Gender gender){
        switch (gender.getPronouns()){
            case Neutral -> {return ExtendedRelationship.Neutral_Nibling;}
            case Masculine -> {return ExtendedRelationship.Nephew;}
            case Feminine -> {return ExtendedRelationship.Niece;}
        }
        throw new IllegalStateException("Unhandled pronoun type: " + gender.getPronouns());
    }
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof ExtendedFamilyRelationship efr) {
//            return efr.relationship == relationship && efr.isInLaw == isInLaw && efr.isAdoptive == isAdoptive && efr.removed == removed;
//        }
//    }
}
