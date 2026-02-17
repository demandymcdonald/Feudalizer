package com.simulation.succession;

import com.simulation.people.Character;
import com.simulation.people.Family;

import java.util.ArrayList;
import java.util.List;

public class CommonlawInheritance extends InheritanceGenerator {

    @Override
    public InheritancePlans.InheritancePlan generateInheritancePlan(Character character) {
        ArrayList<Character> queue = new ArrayList<>();
        List<Character> SpouseFamilies = CandidateRules.IndirectSpouseFamily(character,false);
        InheritancePlans.InheritancePlan IP = InheritancePlans.InheritancePlan.defaultPlan(character);
        List<Character> toCull = new ArrayList<>();
        queue.addAll(CandidateRules.DirectFamily(character,true));
        queue.addAll(CandidateRules.IndirectFamily(character,true));
        queue.addAll(SpouseFamilies);
        for (Character c : queue){
            if (!c.isAlive()){
                int position = queue.indexOf(c);
                toCull.add(c);
                queue.addAll(position,handleDeadChild(c, SpouseFamilies.contains(c)));
            }
        }
        queue.removeAll(toCull);
        final boolean isPrimary = isPrimary(character);
        for (Title<?> t : character.getTitles()){
            IP.addToPlan(t,generateBaseSuccession(t,isPrimary,queue));
            //IP.plan().put(t,container);
        }
        return IP;
    }

    @Override
    public InheritancePlans.InheritancePlan generateInheritancePlan(Title<?> title, boolean isPrimary, Character character) {
        return null;
    }

    @Override
    public InheritancePlans.InheritancePlan generateInheritancePlan(Title<?> title, boolean isPrimary, List<Character> characters) {
        return null;
    }

    @Override
    public SuccessionContainers.SuccessionContainer generateBaseSuccession(Title<?> title, boolean isPrimary, List<Character> characters) {
        SuccessionContainers.SuccessionContainer sc = title.getSuccession();
        if (sc != null){
            return sc;
        }
        ArrayList<Character> queue = new ArrayList<>(characters);
        ArrayList<Character> tempQueue = new ArrayList<>(queue);
        Character primaryH = null;
        if(isPrimary) {
            queue.remove(1);
        } else {
            queue.removeFirst();
        }
        for(Character character : queue) {
            if (title.canInherit(character)) {
                tempQueue.add(character);
            } else if (primaryH == null){
                primaryH = character;
                tempQueue.add(character);
            }
        }
        queue.removeAll(tempQueue);
        return new SuccessionContainers.SuccessionContainer(primaryH, tempQueue);
    }

    private static List<Character> handleDeadChild(Character character, boolean isPrimary) {
        List<Character> children = CandidateRules.DirectFamily(character, true);
        if (children.isEmpty()) {
            return children;
        }
        if (isPrimary) {
            children.remove(1);
            for (Family f : Family.getNuclear(character)) {
                if (f.getSecondarySpouse() == character) {
                    children.removeAll(f.getChildrenOrdered());
                }
            }
        } else {
            children.removeFirst();
            for (Family f : Family.getNuclear(character)) {
                if (f.getPrimarySpouse() == character) {
                    children.removeAll(f.getChildrenOrdered());
                }
            }
        }
        return children;
    }
    private static boolean  isPrimary(Character character) {
        Family f = Family.getCurrentFamily(character);
        return f == null || f.getPrimarySpouse().equals(character);
    }
    private static InheritancePlans.InheritancePlan buildInitialPlan(Character character) {
        return null;
    }
}
