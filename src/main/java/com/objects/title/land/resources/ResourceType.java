package com.objects.title.land.resources;

import com.utilities.IDisplayable;

public record ResourceType(String id, String name, String description) implements IDisplayable {

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
