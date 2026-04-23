package com.objects.culture.tenet.interest;

import com.objects.culture.tenet.TenetManager;

import java.util.HashSet;
import java.util.Set;

public abstract class IGPointer {
    public abstract Set<InterestGroup> get();


    public static class Single extends IGPointer {
        private final InterestGroup group;
        public Single(InterestGroup group) {
            this.group = group;
        }
        @Override
        public Set<InterestGroup> get() {
            return Set.of(group);
        }
    }
    public static class OtherDimension extends IGPointer {
        private final InterestGroup group;
        public OtherDimension(InterestGroup group) {
            this.group = group;
        }
        @Override
        public Set<InterestGroup> get() {
            Set<InterestGroup> groups = TenetManager.InterestGroups.getByDimension(group.getDimension());
            groups.remove(group);
            return groups;
        }
    }
    public static class Multiple extends IGPointer {
        private final Set<InterestGroup> group;

        public Multiple(Set<InterestGroup> group) {
            this.group = group;
        }
        public Multiple(InterestGroup... group) {
            this.group = new HashSet<>(Set.of(group));
        }

        @Override
        public Set<InterestGroup> get() {
            return group;
        }
    }
//    public static class Related extends IGPointer {
//        private final InterestGroup group;
//        public Related(InterestGroup group) {
//            this.group = group;
//        }
//
//    }
}
