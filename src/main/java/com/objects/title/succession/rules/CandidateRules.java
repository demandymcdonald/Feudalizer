package com.objects.title.succession.rules;

import com.objects.character.human.HumanCharacter;
import com.objects.family.Family;
import com.objects.family.FamilyManager;
import com.objects.title.Title;

import java.util.ArrayList;
import java.util.List;

import static com.objects.family.FamilyManager.getAllSpouses;

public interface CandidateRules {
    static List<HumanCharacter> DirectFamily (Title<?> title) {
        return DirectFamily(title, true);
    }
    static List<HumanCharacter> DirectFamily(Title<?> t, boolean prima){
            return DirectFamily(t.getHolder().orElse(null), false, prima);
    }
    static List<HumanCharacter> DirectFamily(HumanCharacter ch, boolean full, boolean prima){
        if (ch == null){
            return new ArrayList<>();
        }
        List<HumanCharacter> c = new ArrayList<>();
        List<Family> f = FamilyManager.getNuclear(ch);
        if (f.isEmpty()){
            return new ArrayList<>();
        }
        for (Family family : f){
            List<HumanCharacter> subFamily = handleList(family.getChildrenOrdered(prima));
            if (full){
                for (HumanCharacter b : subFamily){
                    c.addAll(DirectFamily(b,full,prima));
                }
            } else {
                c.addAll(subFamily);
            }

        }
        return c;
    }
    static List<HumanCharacter> IndirectFamily (Title<?> title, boolean prima) {
        HumanCharacter ch = title.getHolder().orElse(null);
        return IndirectFamily(ch, false,prima);
    }
    static List<HumanCharacter> IndirectFamily (HumanCharacter ch, boolean full, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        Family f = FamilyManager.getBirthFamily(ch);
        if (f == null){
            return new ArrayList<>();
        }
        List<HumanCharacter> cf = new ArrayList<>();
        List<HumanCharacter> subFamily = handleList(f.getChildrenOrdered(prima));
        if (full){
            for (HumanCharacter b : subFamily){
                cf.addAll(DirectFamily(b,full,prima));
            }
        } else {
            cf.addAll(subFamily);
        }
        return cf;
    }

    static List<HumanCharacter> IndirectSpouseFamily (Title<?> title, boolean prima) {
        return IndirectSpouseFamily(title.getHolder().orElse(null), false,prima);
    }
    static List<HumanCharacter> IndirectSpouseFamily (HumanCharacter ch, boolean full, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        List<HumanCharacter> c = new ArrayList<>();
        for (HumanCharacter s: getAllSpouses(ch, prima)){
            Family f = FamilyManager.getBirthFamily(s);
            if (f == null){
                continue;
            }
            List<HumanCharacter> subFamily = handleList(f.getChildrenOrdered(prima));
            if (full){
                for (HumanCharacter b : subFamily){
                    c.addAll(DirectFamily(b,full,prima));
                }
            } else {
                c.addAll(subFamily);
            }
            c.addAll(handleList(f.getChildrenOrdered(prima)));
        }
        return c;
    }
    static List<HumanCharacter> handleList(List<HumanCharacter> list) {
        List<HumanCharacter> c = new ArrayList<>();
        for(HumanCharacter b : list){
                c.add(handleIfDead(b));

        }
        return c;
    }
    public static HumanCharacter handleIfDead(HumanCharacter c) {
        if (c.isAlive()){
            return c;
        } else {
            //TODO handle death here, probably by what if-ing succession according to the default will of the character?
            return c; // replace this with their heir?
        }
    }
//    static List<HumanCharacter> RecursiveFamily (HumanCharacter c, boolean primary, boolean prima) {
//        Family f = Family.getBirth(c);
//        HumanCharacter parent;
//        if (primary) {
//            parent = f.getPrimarySpouse();
//        } else {
//            parent = f.getSecondarySpouse();
//        }
//        Family nF = getBirth(parent);
//        List<HumanCharacter> chL = nF.getChildrenOrdered(prima);
//        chL.remove(parent);
//        return chL;
//    }


}
