package com.simulation.factions;

import com.simulation.character.BookCharacter;
import com.simulation.title.Title;

import java.util.*;

public class FactionManager {


    public static boolean matchingFaction(BookCharacter a, BookCharacter b) {
        BookCharacter leaderA = getFactionLeader(a);
        BookCharacter leaderB = getFactionLeader(b);
        if (leaderA == null || leaderB == null) return false;
        return leaderA.equals(leaderB);
    }
    public static boolean matchingFaction(Title<?> a, Title<?> b) {
        BookCharacter leaderA = getFactionLeader(a.getHolder().orElse(null));
        BookCharacter leaderB = getFactionLeader(b.getHolder().orElse(null));
        if (leaderA == null || leaderB == null) return false;
        return leaderA.equals(leaderB);
    }
    public static boolean matchingFaction(BookCharacter a, Title<?> b) {
        BookCharacter leaderA = getFactionLeader(a);
        BookCharacter leaderB = getFactionLeader(b.getHolder().orElse(null));
        if (leaderA == null || leaderB == null) return false;
        return leaderA.equals(leaderB);
    }
//    public static boolean matchingFaction(House a, House b) {
//        BookCharacter leaderA = getFactionLeader(a);
//        BookCharacter leaderB = getFactionLeader(b.getHolder().orElse(null));
//        if (leaderA == null || leaderB == null) return false;
//        return leaderA.equals(leaderB);
//    }
    public static BookCharacter getFactionLeader(BookCharacter bookCharacter) {
        if (bookCharacter == null) return null;
        final Deque<BookCharacter> toCheck = new ArrayDeque<>();
        toCheck.add(bookCharacter);
        BookCharacter currentHighest = bookCharacter;
        while (!toCheck.isEmpty()){
            BookCharacter next = toCheck.pop();
            Optional<BookCharacter> liege = next.getLiege();
            if (liege.isPresent()) {
                toCheck.add(liege.get());
                currentHighest = liege.get();
            }
        }
        return currentHighest;
    }
}
