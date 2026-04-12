package com.objects.title.land.resources;

import com.utilities.Displayable;

public record ResourceType(String id, String name, String description) implements Displayable {

    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

}
