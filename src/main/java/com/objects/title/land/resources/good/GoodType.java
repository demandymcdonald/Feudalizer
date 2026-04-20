package com.objects.title.land.resources.good;

import com.google.common.collect.ImmutableSet;
import com.objects.character.physical.species.Species;
import com.objects.title.land.resources.GoodManager;

import java.util.Set;
public class GoodType {
    private static abstract class Base implements IGood {
        private final String id;
        private final String name;
        private final String description;
        private final long popImpactProduction;
        private final long popImpactConsumption;
        private final ImmutableSet<GoodTag> tags;
        public Base(String id, String name, String description, long popImpactProduction, long popImpactConsumption, Set<GoodTag> tags) {
            this.id = "good_"+ id;
            this.name = name;
            this.description = description;
            tags.add(getMainTag());
            this.popImpactProduction = popImpactProduction;
            this.popImpactConsumption = popImpactConsumption;
            this.tags = ImmutableSet.copyOf(tags);
            GoodManager.registerGood(this);
        }
        public abstract GoodTag getMainTag();
        @Override
        public long popImpactProduction() {
            return popImpactProduction;
        }

        @Override
        public long popImpactConsumption() {
            return popImpactConsumption;
        }
        @Override
        public Set<GoodTag> getTags() {
            return tags;
        }
        @Override
        public String getDisplayID() {
            return id;
        }

        @Override
        public String getDisplayName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
    public static class RawResource extends Base {
        public RawResource(String id, String name, String description, long popImpactProduction, long popImpactConsumption, Set<GoodTag> tags) {
            super("raw:"+ id, name, description, popImpactProduction, popImpactConsumption, tags);
        }

        @Override
        public GoodTag getMainTag() {
            return GoodTags.TYPE_RAW_RESOURCE;
        }
    }
    public static class Animal_Product extends Base {
        private final Species species;
        public Animal_Product(String id, String name, String description, long popImpactProduction, long popImpactConsumption, Species species, Set<GoodTag> tags) {
            super("animal_product:" + id, name, description, popImpactProduction, popImpactConsumption, tags);
            this.species = species;
        }

        public Species getSpecies() {
            return species;
        }
        @Override
        public GoodTag getMainTag() {
            return GoodTags.TYPE_ANIMAL_PRODUCT;
        }
    }
    public static class RefinedResource extends Base {
        private final ImmutableSet<IGood> madeFrom;
        public RefinedResource(String id, String name, String description, long popImpactProduction, long popImpactConsumption, Set<IGood> madeFrom, Set<GoodTag> tags) {
            super("refined:" + id, name, description, popImpactProduction, popImpactConsumption, tags);
            this.madeFrom = ImmutableSet.copyOf(madeFrom);
        }

        public ImmutableSet<IGood> getMadeFrom() {
            return madeFrom;
        }

        @Override
        public GoodTag getMainTag() {
            return GoodTags.TYPE_REFINED_RESOURCE;
        }
    }
    public static class IntermediateResource extends Base {
        private final ImmutableSet<IGood> madeFrom;
        public IntermediateResource(String id, String name, String description, long popImpactProduction, long popImpactConsumption, Set<IGood> madeFrom, Set<GoodTag> tags) {
            super("intermediate:" + id, name, description, popImpactProduction, popImpactConsumption, tags);
            this.madeFrom = ImmutableSet.copyOf(madeFrom);
        }
        public ImmutableSet<IGood> getMadeFrom() {
            return madeFrom;
        }
        @Override
        public GoodTag getMainTag() {
            return GoodTags.TYPE_INTERMEDIATE;
        }
    }
    public static class Product extends Base {
        private final ImmutableSet<IGood> madeFrom;
        public Product(String id, String name, String description, long popImpactProduction, long popImpactConsumption, Set<IGood> madeFrom,  Set<GoodTag> tags) {
            super("product:" + id, name, description, popImpactProduction, popImpactConsumption, tags);
            this.madeFrom = ImmutableSet.copyOf(madeFrom);
        }
        public ImmutableSet<IGood> getMadeFrom() {
            return madeFrom;
        }
        @Override
        public GoodTag getMainTag() {
            return GoodTags.TYPE_PRODUCT;
        }
    }
}
