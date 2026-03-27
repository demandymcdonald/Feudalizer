package com.simulation.title.succession.rules;

import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.FamilyManager;
import com.simulation.title.Title;

import java.util.ArrayList;
import java.util.List;

import static com.simulation.people.FamilyManager.getAllSpouses;

public interface CandidateRules {
    static List<BookCharacter> DirectFamily (Title<?> title) {
        return DirectFamily(title, true);
    }
    static List<BookCharacter> DirectFamily(Title<?> t, boolean prima){
            return DirectFamily(t.getHolder().orElse(null), false, prima);
    }
    static List<BookCharacter> DirectFamily(BookCharacter ch, boolean full, boolean prima){
        if (ch == null){
            return new ArrayList<>();
        }
        List<BookCharacter> c = new ArrayList<>();
        List<Family> f = FamilyManager.getNuclear(ch);
        if (f.isEmpty()){
            return new ArrayList<>();
        }
        for (Family family : f){
            List<BookCharacter> subFamily = handleList(family.getChildrenOrdered(prima));
            if (full){
                for (BookCharacter b : subFamily){
                    c.addAll(DirectFamily(b,full,prima));
                }
            } else {
                c.addAll(subFamily);
            }

        }
        return c;
    }
    static List<BookCharacter> IndirectFamily (Title<?> title, boolean prima) {
        BookCharacter ch = title.getHolder().orElse(null);
        return IndirectFamily(ch, false,prima);
    }
    static List<BookCharacter> IndirectFamily (BookCharacter ch, boolean full, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        Family f = FamilyManager.getBirthFamily(ch);
        if (f == null){
            return new ArrayList<>();
        }
        List<BookCharacter> cf = new ArrayList<>();
        List<BookCharacter> subFamily = handleList(f.getChildrenOrdered(prima));
        if (full){
            for (BookCharacter b : subFamily){
                cf.addAll(DirectFamily(b,full,prima));
            }
        } else {
            cf.addAll(subFamily);
        }
        return cf;
    }

    static List<BookCharacter> IndirectSpouseFamily (Title<?> title, boolean prima) {
        return IndirectSpouseFamily(title.getHolder().orElse(null), false,prima);
    }
    static List<BookCharacter> IndirectSpouseFamily (BookCharacter ch, boolean full, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        List<BookCharacter> c = new ArrayList<>();
        for (BookCharacter s: getAllSpouses(ch, prima)){
            Family f = FamilyManager.getBirthFamily(s);
            if (f == null){
                continue;
            }
            List<BookCharacter> subFamily = handleList(f.getChildrenOrdered(prima));
            if (full){
                for (BookCharacter b : subFamily){
                    c.addAll(DirectFamily(b,full,prima));
                }
            } else {
                c.addAll(subFamily);
            }
            c.addAll(handleList(f.getChildrenOrdered(prima)));
        }
        return c;
    }
    static List<BookCharacter> handleList(List<BookCharacter> list) {
        List<BookCharacter> c = new ArrayList<>();
        for(BookCharacter b : list){
                c.add(handleIfDead(b));

        }
        return c;
    }
    public static BookCharacter handleIfDead(BookCharacter c) {
        if (c.isAlive()){
            return c;
        } else {
            //TODO handle death here, probably by what if-ing succession according to the default will of the character?
            return c; // replace this with their heir?
        }
    }
//    static List<BookCharacter> RecursiveFamily (BookCharacter c, boolean primary, boolean prima) {
//        Family f = Family.getBirth(c);
//        BookCharacter parent;
//        if (primary) {
//            parent = f.getPrimarySpouse();
//        } else {
//            parent = f.getSecondarySpouse();
//        }
//        Family nF = getBirth(parent);
//        List<BookCharacter> chL = nF.getChildrenOrdered(prima);
//        chL.remove(parent);
//        return chL;
//    }


}
