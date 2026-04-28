package com.objects.culture.tenet.group;

import com.objects.culture.tenet.group.groups.*;
import com.objects.organization.education.Education;
import com.utilities.number.BoundDbl;
import com.utilities.number.BoundDoubles;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record CategoryModifier(Map<TenetGroup, BoundDbl> modifiers) {
    public static CategoryModifier of(Map<TenetGroup, Double> modifiers) {
        return new CategoryModifier(
            modifiers.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> BoundDoubles.dbl256(true, entry.getValue())
                ))
        );
    }
    public double getModifier(TenetGroup group){
        double raw;
        if(modifiers.containsKey(group)){
            raw = modifiers.get(group).get();
        } else {
            for(Map.Entry<TenetGroup, BoundDbl> entry : modifiers.entrySet()){
                if(entry.getKey().isAncestorOf(group)){
                    raw = entry.getValue().get();
                    break;
                }
            }
            raw = 128;
        }
        return raw/128;
    }
    public boolean contains(TenetGroup group){
        return modifiers.containsKey(group) || modifiers.entrySet().stream().anyMatch(entry -> entry.getKey().isAncestorOf(group));
    }
    public static class Builder{
        Map<TenetGroup, BoundDbl> modifiers = new HashMap<>();

        public Builder addModifier(TenetGroup group, double value){
            modifiers.put(group, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setGovernment(double value){
            modifiers.put(GovernmentGroups.GOVERNMENT_SYSTEM, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setReligion(double value){
            modifiers.put(ReligionGroups.RELIGION, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setLabor(double value){
            modifiers.put(EconomicGroups.LABOR_UNION, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setBusiness(double value){
            modifiers.put(EconomicGroups.BUSINESS, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setSociety(double value){
            modifiers.put(SocietyGroups.SOCIETY, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setMilitary(double value){
            modifiers.put(MilitaryGroups.MILITARY, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setEducation(double value){
            modifiers.put(EducationGroups.EDUCATION, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setOfficial(double value){
            modifiers.put(GovernmentGroups.GOVERNMENT_OFFICIAL, BoundDoubles.dbl256(true, value));
            return this;
        }
        public Builder setGovernmentLeader(double value){
            modifiers.put(GovernmentGroups.GOVERNMENT_LEADERSHIP, BoundDoubles.dbl256(true, value));
            return this;
        }

        public CategoryModifier build(){
            return new CategoryModifier(modifiers);
        }
    }
}
