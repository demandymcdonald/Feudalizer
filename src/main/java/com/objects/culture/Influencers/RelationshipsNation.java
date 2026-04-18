package com.objects.culture.Influencers;

import com.objects.culture.object.COReference;

public class RelationshipsNation {
    public static class Overlord extends InfluencerRelationship{
        private static final InfluencerWeight weight = InfluencerWeight.Builder()
                .setGovernment(100)
                .setMilitary(95)
                .setEconomy(75)
                .setSociety(60)
                .setSocialNorms(60)
                .setFamily(10)
                .setReligion(60)
                .setEducation(75)
                .setGovernmentRights(100)
                .build();
        public Overlord(){
            super(weight);
        }
        @Override
        public boolean shouldRemove(COReference<?> influencer, COReference<?> influenced) {
            return false;
        }
    }
    public static class Alliance extends InfluencerRelationship{
        private static final InfluencerWeight weight = InfluencerWeight.Builder()
                .setGovernment(15)
                .setMilitary(14)
                .setEconomy(15)
                .setSociety(17)
                .setSocialNorms(7)
                .setFamily(2)
                .setReligion(6)
                .setEducation(7)
                .setGovernmentRights(7)
                .build();
        public Alliance(){
            super(weight);
        }
        @Override
        public boolean shouldRemove(COReference<?> influencer, COReference<?> influenced) {
            return false;
        }
    }
}
