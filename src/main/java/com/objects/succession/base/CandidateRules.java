package com.objects.succession.base;

import com.objects.character.sentient.SentientCharacter;
import com.objects.family.Family;
import com.objects.family.FamilyManager;
import com.objects.title.Title;

import java.util.ArrayList;
import java.util.List;

import static com.objects.family.FamilyManager.getAllSpouses;

public interface CandidateRules {
    static List<SentientCharacter<?>> DirectFamily (Title<?> title) {
        return DirectFamily(title, true);
    }
    static List<SentientCharacter<?>> DirectFamily(Title<?> t, boolean prima){
            return DirectFamily(t.getHolder().orElse(null), false, prima);
    }
    static List<SentientCharacter<?>> DirectFamily(SentientCharacter<?> ch, boolean full, boolean prima){
        if (ch == null){
            return new ArrayList<>();
        }
        List<SentientCharacter<?>> c = new ArrayList<>();
        List<Family> f = FamilyManager.getNuclear(ch);
        if (f.isEmpty()){
            return new ArrayList<>();
        }
        for (Family family : f){
            List<SentientCharacter<?>> subFamily = handleList(family.getChildrenOrdered(prima));
            if (full){
                for (SentientCharacter<?> b : subFamily){
                    c.addAll(DirectFamily(b,full,prima));
                }
            } else {
                c.addAll(subFamily);
            }

        }
        return c;
    }
    static List<SentientCharacter<?>> IndirectFamily (Title<?> title, boolean prima) {
        SentientCharacter<?> ch = title.getHolder().orElse(null);
        return IndirectFamily(ch, false,prima);
    }
    static List<SentientCharacter<?>> IndirectFamily (SentientCharacter<?> ch, boolean full, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        Family f = FamilyManager.getBirthFamily(ch);
        if (f == null){
            return new ArrayList<>();
        }
        List<SentientCharacter<?>> cf = new ArrayList<>();
        List<SentientCharacter<?>> subFamily = handleList(f.getChildrenOrdered(prima));
        if (full){
            for (SentientCharacter<?> b : subFamily){
                cf.addAll(DirectFamily(b,full,prima));
            }
        } else {
            cf.addAll(subFamily);
        }
        return cf;
    }

    static List<SentientCharacter<?>> IndirectSpouseFamily (Title<?> title, boolean prima) {
        return IndirectSpouseFamily(title.getHolder().orElse(null), false,prima);
    }
    static List<SentientCharacter<?>> IndirectSpouseFamily (SentientCharacter<?> ch, boolean full, boolean prima) {
        if (ch == null){
            return new ArrayList<>();
        }
        List<SentientCharacter<?>> c = new ArrayList<>();
        for (SentientCharacter<?> s: getAllSpouses(ch, prima)){
            Family f = FamilyManager.getBirthFamily(s);
            if (f == null){
                continue;
            }
            List<SentientCharacter<?>> subFamily = handleList(f.getChildrenOrdered(prima));
            if (full){
                for (SentientCharacter<?> b : subFamily){
                    c.addAll(DirectFamily(b,full,prima));
                }
            } else {
                c.addAll(subFamily);
            }
            c.addAll(handleList(f.getChildrenOrdered(prima)));
        }
        return c;
    }
    static List<SentientCharacter<?>> handleList(List<SentientCharacter<?>> list) {
        List<SentientCharacter<?>> c = new ArrayList<>();
        for(SentientCharacter<?> b : list){
                c.add(handleIfDead(b));

        }
        return c;
    }
    public static SentientCharacter<?> handleIfDead(SentientCharacter<?> c) {
        if (c.isAlive()){
            return c;
        } else {
            //TODO handle death here, probably by what if-ing succession according to the default will of the character?
            return c; // replace this with their heir?
        }
    }
//    static List<SentientCharacter<?>> RecursiveFamily (SentientCharacter<?> c, boolean primary, boolean prima) {
//        FamilyGroups f = FamilyGroups.getBirth(c);
//        SentientCharacter<?> parent;
//        if (primary) {
//            parent = f.getPrimarySpouse();
//        } else {
//            parent = f.getSecondarySpouse();
//        }
//        FamilyGroups nF = getBirth(parent);
//        List<SentientCharacter<?>> chL = nF.getChildrenOrdered(prima);
//        chL.remove(parent);
//        return chL;
//    }


}
