package com.simulation.title;

import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.FamilyManager;

import java.util.ArrayList;
import java.util.List;

public class CommonlawInheritance extends InheritanceGenerator {

    @Override
    public InheritancePlans.InheritancePlan generateInheritancePlan(BookCharacter bookCharacter) {
        ArrayList<BookCharacter> queue = new ArrayList<>();
        List<BookCharacter> SpouseFamilies = CandidateRules.IndirectSpouseFamily(bookCharacter,false);
        InheritancePlans.InheritancePlan IP = InheritancePlans.InheritancePlan.defaultPlan(bookCharacter);
        List<BookCharacter> toCull = new ArrayList<>();
        queue.addAll(CandidateRules.DirectFamily(bookCharacter,true));
        queue.addAll(CandidateRules.IndirectFamily(bookCharacter,true));
        queue.addAll(SpouseFamilies);
        for (BookCharacter c : queue){
            if (!c.isAlive()){
                int position = queue.indexOf(c);
                toCull.add(c);
                queue.addAll(position,handleDeadChild(c, SpouseFamilies.contains(c)));
            }
        }
        queue.removeAll(toCull);
        final boolean isPrimary = isPrimary(bookCharacter);
        for (Title<?> t : bookCharacter.getTitles()){
            IP.addToPlan(t,generateBaseSuccession(t,isPrimary,queue));
            //IP.plan().put(t,container);
        }
        return IP;
    }

    @Override
    public InheritancePlans.InheritancePlan generateInheritancePlan(Title<?> title, boolean isPrimary, BookCharacter bookCharacter) {
        return null;
    }

    @Override
    public InheritancePlans.InheritancePlan generateInheritancePlan(Title<?> title, boolean isPrimary, List<BookCharacter> bookCharacters) {
        return null;
    }

    @Override
    public SuccessionContainers.SuccessionContainer generateBaseSuccession(Title<?> title, boolean isPrimary, List<BookCharacter> bookCharacters) {
        SuccessionContainers.SuccessionContainer sc = title.getSuccession();
        if (sc != null){
            return sc;
        }
        ArrayList<BookCharacter> queue = new ArrayList<>(bookCharacters);
        ArrayList<BookCharacter> tempQueue = new ArrayList<>(queue);
        BookCharacter primaryH = null;
        if(isPrimary) {
            queue.remove(1);
        } else {
            queue.removeFirst();
        }
        for(BookCharacter bookCharacter : queue) {
            if (title.canInherit(bookCharacter)) {
                tempQueue.add(bookCharacter);
            } else if (primaryH == null){
                primaryH = bookCharacter;
                tempQueue.add(bookCharacter);
            }
        }
        queue.removeAll(tempQueue);
        return new SuccessionContainers.SuccessionContainer(primaryH, tempQueue);
    }

    private static List<BookCharacter> handleDeadChild(BookCharacter bookCharacter, boolean isPrimary) {
        List<BookCharacter> children = CandidateRules.DirectFamily(bookCharacter, true);
        if (children.isEmpty()) {
            return children;
        }
        if (isPrimary) {
            children.remove(1);
            for (Family f : FamilyManager.getNuclear(bookCharacter)) {
                if (f.getSecondarySpouse().isPresent() && f.getSecondarySpouse().get() == bookCharacter) {
                    children.removeAll(f.getChildrenOrdered());
                }
            }
        } else {
            children.removeFirst();
            for (Family f : FamilyManager.getNuclear(bookCharacter)) {
                if (f.getPrimarySpouse() == bookCharacter) {
                    children.removeAll(f.getChildrenOrdered());
                }
            }
        }
        return children;
    }
    private static boolean  isPrimary(BookCharacter bookCharacter) {
        Family f = FamilyManager.getCurrentFamily(bookCharacter);
        return f == null || f.getPrimarySpouse().equals(bookCharacter);
    }
    private static InheritancePlans.InheritancePlan buildInitialPlan(BookCharacter bookCharacter) {
        return null;
    }
}
