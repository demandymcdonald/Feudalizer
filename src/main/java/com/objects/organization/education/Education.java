package com.objects.organization.education;

import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.objects.culture.tenet.interest.IGPointer;
import com.objects.culture.tenet.interest.InterestGroup;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.bound_int.BoundInt;

import java.util.Map;

import static com.objects.culture.tenet.group.groups.EducationGroups.SCHOOL_TYPE;

public class Education implements IDisplayable, StringIdentifiable {
    private final String id;
    private final TenetGroup group;
    private final String name;
    private final String description;
    private final InterestGroup interestGroup;
    private final int level;
    public Education(TenetGroup group, int level, String id, String name, String description) {
        this.id = "edu_"+ id;
        this.name = name;
        this.level = level;
        this.description = description;
        this.group = validate(group);
        this.interestGroup = build(this);
        EduManager.register(this);

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

    public InterestGroup getInterestGroup(){
        return interestGroup;
    }
    public int getLevel(){
        return level;
    }
    @Override
    public String getID() {
        return id;
    }
    public TenetGroup getGroup(){
        return group;
    }
    private static TenetGroup validate(TenetGroup group){
        if(group.getParent().isEmpty() || group.getParent().get() != SCHOOL_TYPE){
            throw new IllegalArgumentException("Invalid Education Group");
        }
        return group;
    }
    private static InterestGroup build(Education education){
        return new InterestGroup(InstanceType.PROCEDURAL, InterestGroup.Dimension.Education,education.getDisplayID(),education.getDisplayName(),education.getDescription()) {
            @Override
            public InterestGroup getNewObject(InstanceType type, String id, JsonObject data) {
                return null;
            }

            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getEducation().asSet().stream().anyMatch(educationInstance -> educationInstance.getType().equals(education));
            }
            private final TenetGroup rt = rightTenet(education);
            private final TenetGroup st = socialTenet(education);
            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }
            @Override
            public GovernmentGroups.GovernmentGroup getRightsGroup() {
                return rt;
            }

            @Override
            public SocietyGroups.SocietyGroup getSocialStatusGroup() {
                return st;
            }

            @Override
            public PoliticalCompass getCompass(Culture culture) {
                return null;
            }
            private static TenetGroup rightTenet(Education e){
                return switch (e.level) {
                    case 0, 1 -> GovernmentGroups.BASIC_EDUCATION;
                    case 2 -> GovernmentGroups.TRADES_EDUCATION;
                    case 3, 4 -> GovernmentGroups.COLLEGE_EDUCATED;
                    case 5, 6 -> GovernmentGroups.HYPER_COLLEGE_EDUCATED;
                    default -> GovernmentGroups.UNEDUCATED;
                };
            }
            private static TenetGroup socialTenet(Education e){
                return switch (e.level) {
                    case 0, 1 -> SocietyGroups.BASIC_EDUCATION;
                    case 2 -> SocietyGroups.TRADES_EDUCATION;
                    case 3, 4 -> SocietyGroups.COLLEGE_EDUCATED;
                    case 5, 6 -> SocietyGroups.HYPER_COLLEGE_EDUCATED;
                    default -> SocietyGroups.UNEDUCATED;
                };
            }
        };
    }
}
