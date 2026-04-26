package com.objects.title.profession;

import com.objects.culture.tenet.interest.InterestGroups.*;
import com.utilities.IDisplayable;

public enum JobType implements IDisplayable {
    Leader(ClassCaste.ELITE,"type_leader","Leader",""),
    Business_Leader(ClassCaste.ELITE,ClassCaste.BUSINESS,"type_business_leader","Business_Leader","")

    ;
    private final ClassCaste group;
    private final ClassCaste secondary_group;
    private final String id;
    private final String name;
    private final String description;
    JobType(ClassCaste group, String id, String name, String description) {
        this.group = group;
        this.secondary_group = null;
        this.id = id;
        this.name = name;
        this.description = description;
    }
    JobType(ClassCaste group, ClassCaste secondary, String id, String name, String description) {
        this.group = group;
        this.secondary_group = secondary;
        this.id = id;
        this.name = name;
        this.description = description;
    }


    @Override
    public String getDisplayID() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
