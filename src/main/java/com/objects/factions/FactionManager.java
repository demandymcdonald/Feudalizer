package com.objects.factions;

import com.objects.character.human.HumanCharacter;
import com.objects.title.Title;

import java.util.*;

public class FactionManager {


    public static boolean matchingFaction(HumanCharacter a, HumanCharacter b) {
        HumanCharacter leaderA = getFactionLeader(a);
        HumanCharacter leaderB = getFactionLeader(b);
        if (leaderA == null || leaderB == null) return false;
        return leaderA.equals(leaderB);
    }
    public static boolean matchingFaction(Title<?> a, Title<?> b) {
        HumanCharacter leaderA = getFactionLeader(a.getHolder().orElse(null));
        HumanCharacter leaderB = getFactionLeader(b.getHolder().orElse(null));
        if (leaderA == null || leaderB == null) return false;
        return leaderA.equals(leaderB);
    }
    public static boolean matchingFaction(HumanCharacter a, Title<?> b) {
        HumanCharacter leaderA = getFactionLeader(a);
        HumanCharacter leaderB = getFactionLeader(b.getHolder().orElse(null));
        if (leaderA == null || leaderB == null) return false;
        return leaderA.equals(leaderB);
    }
//    public static boolean matchingFaction(House a, House b) {
//        HumanCharacter leaderA = getFactionLeader(a);
//        HumanCharacter leaderB = getFactionLeader(b.getHolder().orElse(null));
//        if (leaderA == null || leaderB == null) return false;
//        return leaderA.equals(leaderB);
//    }
    public static HumanCharacter getFactionLeader(HumanCharacter humanCharacter) {
        if (humanCharacter == null) return null;
        final Deque<HumanCharacter> toCheck = new ArrayDeque<>();
        toCheck.add(humanCharacter);
        HumanCharacter currentHighest = humanCharacter;
        while (!toCheck.isEmpty()){
            HumanCharacter next = toCheck.pop();
            Optional<HumanCharacter> liege = next.getLiege();
            if (liege.isPresent()) {
                toCheck.add(liege.get());
                currentHighest = liege.get();
            }
        }
        return currentHighest;
    }
}
