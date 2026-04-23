package com.objects.organization.education;

import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.interest.IGPointer;
import com.objects.culture.tenet.interest.InterestGroup;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;
import com.utilities.number.BoundInt;

import java.util.Map;

import static com.objects.culture.tenet.group.groups.EducationGroups.SCHOOL_TYPE;

public class Education implements IDisplayable, StringIdentifiable {
    private final String id;
    private final TenetGroup group;
    private final String name;
    private final String description;
    private final InterestGroup interestGroup;
    public Education(TenetGroup group, String id, String name, String description) {
        this.id = "edu_"+ id;
        this.name = name;
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
        return new InterestGroup(education.getDisplayID(),education.getDisplayName(),education.getDescription()) {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return false;
            }

            @Override
            public Map<IGPointer, BoundInt> getRelations() {
                return Map.of();
            }

            @Override
            public TenetGroup getRightsGroup() {
                return null;
            }

            @Override
            public TenetGroup getSocialStatusGroup() {
                return null;
            }

            @Override
            public Dimension getDimension() {
                return null;
            }
        }
    }
}
