package com.objects.character.species.genetics;

import com.utilities.IDisplayable;

public record GeneProperty(String id, String name, String description) implements IDisplayable {

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
