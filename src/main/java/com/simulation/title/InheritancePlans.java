//package com.simulation.title;
//
//import com.simulation.people.BookCharacter;
//
//import java.util.*;
//
//public class InheritancePlans {
//    public record InheritancePlan(BookCharacter bookCharacter, HashMap<Title<?>, SuccessionContainers.SuccessionContainer> plan) {
////        public List<BookCharacter> getTitleSuccession(Title<?> title) {
////            List<BookCharacter> toReturn = new ArrayList<>();
////            if (plan.containsKey(title)) {
////                Set<Pair<Integer,BookCharacter>> pre = plan.get(title);
////                pre.stream().sorted(Comparator.<Pair<Integer,BookCharacter>,Integer>comparing(Pair::getKey).reversed()).forEach(pair -> toReturn.add(pair.getValue()));
////            }
////            return toReturn;
////        }
//        public static InheritancePlan defaultPlan (BookCharacter bookCharacter) {
//            return new InheritancePlan(bookCharacter, new HashMap<>());
//        }
//        public void addToPlan(Title<?> title, SuccessionContainers.SuccessionContainer successionContainer) {
//            plan.put(title, successionContainer);
//        }
//    }
//}
