package com.simulation.succession;

import com.simulation.people.Character;

import java.util.*;

public class InheritancePlans {
    public record InheritancePlan(Character character, HashMap<Title<?>, SuccessionContainers.SuccessionContainer> plan) {
//        public List<Character> getTitleSuccession(Title<?> title) {
//            List<Character> toReturn = new ArrayList<>();
//            if (plan.containsKey(title)) {
//                Set<Pair<Integer,Character>> pre = plan.get(title);
//                pre.stream().sorted(Comparator.<Pair<Integer,Character>,Integer>comparing(Pair::getKey).reversed()).forEach(pair -> toReturn.add(pair.getValue()));
//            }
//            return toReturn;
//        }
        public static InheritancePlan defaultPlan (Character character) {
            return new InheritancePlan(character, new HashMap<>());
        }
        public void addToPlan(Title<?> title, SuccessionContainers.SuccessionContainer successionContainer) {
            plan.put(title, successionContainer);
        }
    }
}
