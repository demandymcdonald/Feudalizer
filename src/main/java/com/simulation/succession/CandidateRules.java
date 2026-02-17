package com.simulation.succession;

import com.simulation.people.Character;
import com.simulation.people.Family;

import java.util.ArrayList;
import java.util.List;

import static com.simulation.people.Family.getAllSpouses;
import static com.simulation.people.Family.getBirth;

public interface CandidateRules {
    static List<Character> DirectFamily (Title<?> title) {
        return DirectFamily(title, true);
    }
    static List<Character> DirectFamily(Title<?> t, boolean prima){
            return DirectFamily(t.getHolder().orElse(null), prima);
    }
    static List<Character> DirectFamily(Character ch, boolean prima){
        if (ch == null){
            return new ArrayList<>();
        }
        List<Character> c = new ArrayList<>();
        List<Family> f = Family.getNuclear(ch);
        for (Family family : f){
            family.getChildrenOrdered(prima).forEach(c::add);
        }
        return c;
    }
    static List<Character> IndirectFamily (Title<?> title, boolean prima) {
        Character ch = title.getHolder().orElse(null);
        return IndirectFamily(ch, prima);
    }
    static List<Character> IndirectFamily (Character ch, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        List<Character> c = new ArrayList<>();
        Family f = Family.getBirth(ch);
        f.getChildrenOrdered(prima).forEach(c::add);
        return c;
    }


    static List<Character> IndirectSpouseFamily (Title<?> title, boolean prima) {
        return IndirectSpouseFamily(title.getHolder().orElse(null), prima);
    }
    static List<Character> IndirectSpouseFamily (Character ch, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        List<Character> c = new ArrayList<>();
        for (Character s: getAllSpouses(ch, prima)){
            Family f = Family.getBirth(s);
            f.getChildrenOrdered(prima).forEach(c::add);
        }
        return c;
    }
    static List<Character> RecursiveFamily (Character c, boolean primary, boolean prima) {
        Family f = Family.getBirth(c);
        Character parent;
        if (primary) {
            parent = f.getPrimarySpouse();
        } else {
            parent = f.getSecondarySpouse();
        }
        Family nF = getBirth(parent);
        List<Character> chL = nF.getChildrenOrdered(prima);
        chL.remove(parent);
        return chL;
    }


}
