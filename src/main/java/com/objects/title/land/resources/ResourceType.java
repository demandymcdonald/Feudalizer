package com.objects.title.land.resources;

import com.utilities.Displayable;

public record ResourceType(String id, String name, String description) implements Displayable {

    @Override
    public String getID() {
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
