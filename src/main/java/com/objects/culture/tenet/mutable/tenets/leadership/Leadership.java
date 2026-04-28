package com.objects.culture.tenet.mutable.tenets.leadership;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.object.ideology.Ideologies;
import com.objects.culture.object.ideology.Ideology;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.TenetManager;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.CategoryModifier;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.*;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.culture.tenet.mutable.augments.IRightsTenet;
import com.objects.culture.tenet.mutable.tenets.general.CompassGenerators;
import com.objects.culture.tenet.mutable.tenets.leadership.election.ElectionType;
import com.objects.organization.education.Education;

import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.objects.culture.tenet.group.groups.EconomicGroups.BUSINESS;
import static com.objects.culture.tenet.group.groups.EconomicGroups.LABOR_UNION;
import static com.objects.culture.tenet.group.groups.EducationGroups.EDUCATION;
import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.groups.MilitaryGroups.*;
import static com.objects.culture.tenet.group.groups.ReligionGroups.RELIGION;

public abstract class Leadership extends MutableTenet {

    public Leadership(TenetReference parent, Type type, PoliticalCompass entry, String id, String name, String description) {
        super(parent, findGroup(parent,type), entry, id, name, description);
    }

    public Leadership(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, uuid, group, entry, id, name, description);
    }

    protected enum Type {
        SELECTION,
        TYPES,
        REMOVAL,
        TREATMENT,
        AUTHORITY,
        CORRUPTION,
        TRAINING,
        OTHER
    }

    @Override
    public Set<TenetGroup> compatibleParents() {
        return Set.of(
            GovernmentGroups.GOVERNMENT_LEADERSHIP,
            GovernmentGroups.GOVERNMENT_OFFICIAL,
            MilitaryGroups.MILITARY_LEADERSHIP,
            ReligionGroups.RELIGION_LEADERSHIP,
            EducationGroups.EDUCATION_LEADERSHIP,
            EconomicGroups.BUSINESS_LEADERSHIP,
            EconomicGroups.UNION_LEADERSHIP
        );
    }

    private static TenetGroup findGroup(TenetReference reference, Type type) {
        //Not sure if the thows will ever be supported, but they need to throw to tell me if I need to add them. Tags are bloated as is.
        TenetGroup group = TenetManager.Group.getCategory(reference.get().getGroup());
        if (group == GOVERNMENT || group == GOVERNMENT_LEADERSHIP) {
            return switch (type) {
                case TYPES -> LEADER_TYPES;
                case SELECTION -> LEADER_SELECTION;
                case REMOVAL -> LEADER_REMOVAL;
                case TREATMENT ->
                        OFFICIAL_TREATMENT_PEOPLE; //throw new UnsupportedOperationException("TREATMENT of GOVERNMENT_LEADERSHIP");
                case AUTHORITY -> LEADER_AUTHORITY;
                case CORRUPTION -> LEADER_CORRUPTION;
                case TRAINING ->
                        throw new UnsupportedOperationException("Training not yet supported for Government Leaders");
                case OTHER -> LEADER_GENERAL;
            };
        } else if (group == GOVERNMENT_OFFICIAL || group == GOVERNMENT_OFFICE) {
            return switch (type) {
                case TYPES -> OFFICIAL_TYPES;
                case SELECTION -> OFFICIAL_SELECTION;
                case REMOVAL -> OFFICIAL_REMOVAL;
                case TREATMENT -> OFFICIAL_TREATMENT_GOVERNMENT;
                case AUTHORITY -> OFFICIAL_AUTHORITY;
                case CORRUPTION -> OFFICIAL_CORRUPTION;
                case TRAINING ->
                        throw new UnsupportedOperationException("Training Not supported yet by Government Official.");
                case OTHER -> OFFICIAL_GENERAL;
            };
        } else if (group == MILITARY || group == MILITARY_LEADERSHIP) {
            return switch (type) {
                case TYPES -> OFFICER_TYPES;
                case SELECTION -> OFFICER_APPOINTMENT;
                case REMOVAL -> OFFICER_REMOVAL;
                case TREATMENT -> OFFICER_TREATMENT;
                case AUTHORITY -> CENTRALIZATION;
                case CORRUPTION -> MILITARY_CORRUPTION;
                case TRAINING -> OFFICER_TRAINING;
                case OTHER -> throw new UnsupportedOperationException("Other is not supported in Military Leadership");
            };
        } else if (group == RELIGION || group == ReligionGroups.RELIGION_LEADERSHIP) {
            return switch (type) {
                case TYPES -> ReligionGroups.PRIEST_TYPES;
                case SELECTION -> ReligionGroups.RELIGION_LEADER_SELECTION;
                case REMOVAL -> ReligionGroups.RELIGION_LEADER_REMOVAL;
                case TREATMENT ->
                        throw new UnsupportedOperationException("Treatment is not supported in Religious Leadership");
                case AUTHORITY -> ReligionGroups.RELIGION_LEADER_AUTHORITY;
                case CORRUPTION -> ReligionGroups.RELIGION_LEADER_CORRUPTION;
                case TRAINING ->
                        throw new UnsupportedOperationException("Training is not supported in Religious Leadership");
                case OTHER -> throw new UnsupportedOperationException("Other is not supported in Religious Leadership");
            };
        } else if (group == EDUCATION || group == EducationGroups.EDUCATION_LEADERSHIP) {
            return switch (type) {
                case TYPES -> EducationGroups.TEACHER_TYPES;
                case SELECTION -> EducationGroups.TEACHER_SELECTION;
                case REMOVAL -> EducationGroups.TEACHER_REMOVAL;
                case TREATMENT -> EducationGroups.TEACHER_TREATMENT;
                case AUTHORITY -> EducationGroups.TEACHER_AUTHORITY;
                case CORRUPTION -> EducationGroups.TEACHER_CORRUPTION;
                case TRAINING -> EducationGroups.TEACHER_TRAINING;
                case OTHER -> throw new UnsupportedOperationException("Other is not supported in Teacher Leadership");
            };
        } else if (group == BUSINESS || group == EconomicGroups.BUSINESS_LEADERSHIP) {
            return switch (type) {
                case TYPES -> EconomicGroups.BUSINESS_LEADER_TYPES;
                case SELECTION -> EconomicGroups.BUSINESS_LEADER_SELECTION;
                case REMOVAL -> EconomicGroups.BUSINESS_LEADER_REMOVAL;
                case TREATMENT -> EconomicGroups.BUSINESS_LEADER_TREATMENT;
                case AUTHORITY -> EconomicGroups.BUSINESS_LEADER_AUTHORITY;
                case CORRUPTION -> EconomicGroups.BUSINESS_LEADER_CORRUPTION;
                case TRAINING ->
                        throw new UnsupportedOperationException("Training is not supported in Business Leadership");
                case OTHER -> EconomicGroups.BUSINESS_LEADER_GENERAL;
            };
        } else if (group == EconomicGroups.LABOR_UNION || group == EconomicGroups.UNION_LEADERSHIP) {
            return switch (type) {
                case TYPES -> EconomicGroups.UNION_LEADER_TYPES;
                case SELECTION -> EconomicGroups.UNION_LEADER_SELECTION;
                case REMOVAL -> EconomicGroups.UNION_LEADER_REMOVAL;
                case TREATMENT -> EconomicGroups.UNION_LEADER_TREATMENT;
                case AUTHORITY -> EconomicGroups.UNION_LEADER_AUTHORITY;
                case CORRUPTION -> EconomicGroups.UNION_LEADER_CORRUPTION;
                case TRAINING ->
                        throw new UnsupportedOperationException("Training is not supported in Business Leadership");
                case OTHER -> EconomicGroups.UNION_LEADER_GENERAL;
            };
        }
        throw new RuntimeException("No leader group found for parent: " + group.id());
    }

    public static class TermLimit extends Leadership {
        private int duration;
        private ChronoUnit durationUnit;
        public TermLimit(TenetReference parent, int duration, ChronoUnit durationUnit) {
            super(parent, Type.REMOVAL, makeTLC(parent.getGroup(), duration, durationUnit), "term_limit", "Term Limit", "The term limit for this office");
            this.duration = duration;
            this.durationUnit = durationUnit;
        }
        public TermLimit(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
            super(parent, uuid, group, entry, id, name, description);
        }
        @Override
        public Set<CultureCondition<?, ?>> getConditionList() {
            return Set.of(
                LeadershipConditions.TermLimit(this, duration, durationUnit)
            );
        }
        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("duration", duration);
            data.addProperty("unit", durationUnit.name());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            duration = Integer.parseInt(data.get("duration").getAsString());
            durationUnit = ChronoUnit.valueOf(data.get("unit").getAsString());
        }
        private static final Map<Ideology,Integer> base_map = Map.of(
                Ideologies.LIBERALISM,90,
                Ideologies.SOCIAL_DEMOCRACY,85,
                Ideologies.NEOCONSERVATISM,85,
                Ideologies.AUTHORITARIAN_COMMUNISM,-33,
                Ideologies.FASCISM,-90,
                Ideologies.ARISTOCRACY,-90,
                Ideologies.ABSOLUTE_MONARCHY, -90,
                Ideologies.CONSTITUTIONAL_MONARCHY, -50
        );
        private static final CategoryModifier mod = new CategoryModifier.Builder().build();

        private static PoliticalCompass makeTLC(TenetGroup parent, int duration, ChronoUnit unit){
            long years = duration * (unit.getDuration().toHours() * 24 * 365);
            double curveAuth = Math.pow((double) years /100L,2);
            double curveLib = Math.log((double) years /100L);
            Map<Ideology,Integer> map = new HashMap<>(base_map);
            map.compute(Ideologies.LIBERALISM, (k,v) -> (int) (v * curveLib));
            map.compute(Ideologies.SOCIAL_DEMOCRACY, (k,v) -> (int) (v * curveLib));
            map.compute(Ideologies.AUTHORITARIAN_COMMUNISM, (k,v) -> (int) (v * curveAuth));
            map.compute(Ideologies.FASCISM, (k,v) -> (int) (v * curveAuth));
            map.compute(Ideologies.ABSOLUTE_MONARCHY, (k,v) -> (int) (v * curveAuth));
            return MutableTenet.makeCompass(PoliticalCompass.IdeologyEntry.of(map),parent, mod);
        }

    }
    public static class Barred extends Leadership implements IRightsTenet<Barred> {
        private final Set<InterestGroup> isAffected = new HashSet<>();
        public Barred(TenetReference parent, InterestGroup... groups) {
            super(parent, Type.SELECTION, CompassGenerators.disenfranchise(4,groups), buildID(groups), "Barred from Office", "");
            isAffected.addAll(Arrays.stream(groups).toList());
        }
        public Barred(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
            super(parent, uuid, group, entry, id, name, description);
        }
        @Override
        public Set<CultureCondition<?, ?>> getConditionList() {
            return Set.of(
                LeadershipConditions.Disenfranchised(this,isAffected)
            );
        }
        public boolean contains(InterestGroup group) {
            return isAffected.contains(group);
        }
        @Override
        public Set<InterestGroup> isAffected() {
            return isAffected;
        }
        @Override
        public void additionalSave(JsonObject data) {
            JsonArray array = new JsonArray();
            for (InterestGroup group : isAffected) {
                array.add(group.getID());
            }
            data.add("isAffected", array);
        }
        @Override
        public void additionalLoad(JsonObject data) {
            JsonArray array = data.getAsJsonArray("isAffected");
            isAffected.clear();
            for(JsonElement element : array) {
                isAffected.add(TenetManager.InterestGroups.INSTANCE.get(element.getAsJsonObject().get("id").getAsString()));
            }
        }
        private static String buildID(InterestGroup... group) {
            StringBuilder builder = new StringBuilder();
            builder.append("banned_from_leadership:");
            boolean isFirst = true;
            for (InterestGroup group1 : group) {
                if (isFirst) {
                    isFirst = false;
                    builder.append(group1.getQuickID());
                    continue;
                }
                builder.append("-").append(group1.getQuickID());
            }
            return builder.toString();
        }

    }
    public static class Election extends Leadership implements IRightsTenet<Election> {
        private final Set<InterestGroup> isAffected = new HashSet<>();
        private ElectionType<?> type = null;
        public Election(TenetReference parent, ElectionType<?> type) {
            super(parent, Type.SELECTION, makeTLC(parent.getGroup(),type), "election_" + type.getId(), "Election: "+ type.getDisplayName(), type.getDescription());
            this.type = type;
        }

        public Election(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
            super(parent, uuid, group, entry, id, name, description);
        }

        @Override
        public Set<CultureCondition<?, ?>> getConditionList() {
            return Set.of();
        }

        @Override
        public Set<InterestGroup> isAffected() {
            return isAffected;
        }

        @Override
        public void additionalSave(JsonObject data) {
            JsonArray array = new JsonArray();
            for (InterestGroup group : isAffected) {
                array.add(group.getID());
            }
            data.add("")
            data.add("isAffected", array);
        }

        @Override
        public void additionalLoad(JsonObject data) {
            JsonArray array = data.getAsJsonArray("isAffected");
            isAffected.clear();
            for(JsonElement element : array) {
                isAffected.add(TenetManager.InterestGroups.INSTANCE.get(element.getAsJsonObject().get("id").getAsString()));
            }
        }

        private static final CategoryModifier mod = new CategoryModifier.Builder()
                .setEducation(128)
                .setBusiness(200)
                .setGovernmentLeader(128)
                .setOfficial(150)
                .setMilitary(200)
                .setLabor(-64)
                .setReligion(200)
                .build();

        private static PoliticalCompass makeTLC(TenetGroup parent, ElectionType<?> type){
            return MutableTenet.makeCompass(type.getPoliticalCompass(),parent, mod);
        }
    }
    public static class EducationRequirement extends Leadership{

        public EducationRequirement(TenetReference parent, Education level) {
            super(parent, Type.SELECTION, bc(parent,level), "education_level", "Education Requirement", "");
        }

        public EducationRequirement(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
            super(parent, uuid, group, entry, id, name, description);
        }

        @Override
        public Set<CultureCondition<?, ?>> getConditionList() {
            return Set.of();
        }

        @Override
        public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
            return null;
        }
        public static PoliticalCompass bc(TenetReference parent, Education level) {
            TenetGroup group = TenetManager.Group.getCategory(parent.getGroup());
            if (group == EDUCATION){
                return new PoliticalCompass(5,-10,10,5,-5);
            }
            int lvl = level.getLevel();
            double mod = Leadership.getModifier(group);
            int A = (int) Math.round((15 * lvl) * mod);
            int B = (int) Math.round((10 * lvl) * mod);
            int C = (int) Math.round((15 * lvl) * mod);
            int D = (int) Math.round((15 * lvl) * mod);
            int T = (int) Math.round((-15 * lvl) * mod);
            return new PoliticalCompass(A,B,C,D,T);
        }
        @Override
        public void additionalSave(JsonObject data) {

        }

        @Override
        public void additionalLoad(JsonObject data) {

        }
    }
}
