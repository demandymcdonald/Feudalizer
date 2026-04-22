package com.objects.culture.tenet.mutable.tenets.general;

import com.google.gson.JsonObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.EconomicGroups;
import com.objects.culture.tenet.group.groups.EducationGroups;
import com.objects.culture.tenet.group.groups.ReligionGroups;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;

import java.security.PrivateKey;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;
import static com.objects.culture.tenet.group.groups.MilitaryGroups.*;

public abstract class Leadership extends MutableTenet {

    public Leadership(TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, group, entry, id, name, description);
    }

    public Leadership(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, uuid, group, entry, id, name, description);
    }
    protected enum Type{
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
            GOVERNMENT,
            GOVERNMENT_LEADERSHIP,
            GOVERNMENT_OFFICIAL,
            GOVERNMENT_OFFICE,
            MILITARY,
            MILITARY_LEADERSHIP,
            ReligionGroups.RELIGION,
            ReligionGroups.RELIGION_LEADERSHIP,
            EducationGroups.EDUCATION,
            EducationGroups.EDUCATION_LEADERSHIP,
            EconomicGroups.BUSINESS,
            EconomicGroups.BUSINESS_LEADERSHIP,
            EconomicGroups.LABOR_UNION,
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
                case TREATMENT -> OFFICIAL_TREATMENT_PEOPLE; //throw new UnsupportedOperationException("TREATMENT of GOVERNMENT_LEADERSHIP");
                case AUTHORITY -> LEADER_AUTHORITY;
                case CORRUPTION -> LEADER_CORRUPTION;
                case TRAINING -> throw new UnsupportedOperationException("Training not yet supported for Government Leaders");
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
                case TRAINING -> throw new UnsupportedOperationException("Training Not supported yet by Government Official.");
                case OTHER -> OFFICIAL_GENERAL;
            };
        }else if (group == MILITARY ||  group == MILITARY_LEADERSHIP) {
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
        } else if (group == ReligionGroups.RELIGION || group == ReligionGroups.RELIGION_LEADERSHIP) {
            return switch (type) {
                case TYPES -> ReligionGroups.PRIEST_TYPES;
                case SELECTION -> ReligionGroups.RELIGION_LEADER_SELECTION;
                case REMOVAL -> ReligionGroups.RELIGION_LEADER_REMOVAL;
                case TREATMENT -> throw new UnsupportedOperationException("Treatment is not supported in Religious Leadership");
                case AUTHORITY -> ReligionGroups.RELIGION_LEADER_AUTHORITY;
                case CORRUPTION ->  ReligionGroups.RELIGION_LEADER_CORRUPTION;
                case TRAINING -> throw new UnsupportedOperationException("Training is not supported in Religious Leadership");
                case OTHER -> throw new UnsupportedOperationException("Other is not supported in Religious Leadership");
            };
        } else if (group == EducationGroups.EDUCATION || group == EducationGroups.EDUCATION_LEADERSHIP) {
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
        } else if (group == EconomicGroups.BUSINESS || group == EconomicGroups.BUSINESS_LEADERSHIP) {
            return switch (type) {
                case TYPES -> EconomicGroups.BUSINESS_LEADER_TYPES;
                case SELECTION -> EconomicGroups.BUSINESS_LEADER_SELECTION;
                case REMOVAL -> EconomicGroups.BUSINESS_LEADER_REMOVAL;
                case TREATMENT -> EconomicGroups.BUSINESS_LEADER_TREATMENT;
                case AUTHORITY -> EconomicGroups.BUSINESS_LEADER_AUTHORITY;
                case CORRUPTION -> EconomicGroups.BUSINESS_LEADER_CORRUPTION;
                case TRAINING -> throw new UnsupportedOperationException("Training is not supported in Business Leadership");
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
                case TRAINING -> throw new UnsupportedOperationException("Training is not supported in Business Leadership");
                case OTHER -> EconomicGroups.UNION_LEADER_GENERAL;
            };
        }
        throw new RuntimeException("No leader group found for parent: "+ group.id());
    }
    public static class TermLimit extends Leadership {
        private int duration;
        private ChronoUnit durationUnit;
        public TermLimit(TenetReference parent, int duration, ChronoUnit durationUnit) {
            super(parent, findGroup(parent,Type.REMOVAL), buildCompass(duration,durationUnit), "term_limit", "Term Limit", "The term limit for this office");
            this.duration = duration;
            this.durationUnit = durationUnit;
        }

        public TermLimit(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
            super(parent, uuid, group, entry, id, name, description);
        }

        @Override
        public Set<CultureCondition<?, ?, ?>> getChangeConditions() {
            return Set.of(
                    LeadershipConditions.buildTermLimit(this,duration,durationUnit)
            );
        }
        private static final int STARTING = 40;
        private static final int MAX_C_PUSH = -40;
        private static final int MAX_D_PUSH = -40;
        private static PoliticalCompass buildCompass(int duration, ChronoUnit durationUnit) {
            final long years = duration * (durationUnit.getDuration().getSeconds() / ChronoUnit.YEARS.getDuration().getSeconds());
            int axisC = (int) Math.round(MAX_C_PUSH + ((Math.abs(STARTING) + Math.abs(MAX_C_PUSH)) * (years / 100.0)));
            int axisD = (int) Math.round(MAX_D_PUSH + ((Math.abs(STARTING) + Math.abs(MAX_D_PUSH)) * (years / 100.0))/2);
            return new PoliticalCompass(0,0,axisC,axisD);
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
    }
    public static class ExcludeGroup extends Leadership {
        private InterestGroup group;
        public ExcludeGroup(TenetReference parent, InterestGroup group) {
            super(parent, findGroup(parent,Type.SELECTION), buildCompass(group), "exclude_group", "Exclude Group", "Members of this group may not hold leadership positions");
            this.group = group;
        }



        @Override
        public Set<CultureCondition<?, ?, ?>> getChangeConditions() {
            return Set.of(
                LeadershipConditions.buildExclude(this,group)
            );
        }

        private static final int MAX_B_PUSH = 100;
        private static final int MAX_D_PUSH = 100;
        private static PoliticalCompass buildCompass(InterestGroup group) {
            double modifier = switch (group.getDimension()){
                case Sex_At_Birth, Class_Caste,Religion -> {
                    yield .9;
                }
                case Gender_Identity -> {
                    yield .75;
                }
                case Race_Ethnicity,Culture -> {
                    yield 1;
                }
                case Sexual_Orientation  -> {
                    yield .7;
                }
                case Political_Ideology -> {
                    yield .6;
                }
                case Lifestyle,Disability -> {
                    yield .4;
                }
            };
            int d = Math.toIntExact(Math.round(MAX_D_PUSH * modifier));
            int b = Math.toIntExact(Math.round(MAX_B_PUSH * modifier));
            return new PoliticalCompass(0,b,0,d);
        }
        @Override
        public void additionalSave(JsonObject data) {
            data.addProperty("group", group.getID());
        }

        @Override
        public void additionalLoad(JsonObject data) {
            group = TenetManager.InterestGroups.get(data.get("group").getAsString());
        }
    }
}
