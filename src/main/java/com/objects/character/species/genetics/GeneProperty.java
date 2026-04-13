package com.objects.character.species.genetics;

import com.utilities.Displayable;

public record GeneProperty(String id, String name, String description) implements Displayable {

    @Override
    public String getDisplayID() {
        return "";
    }

    @Override
    public String displayName() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }
}
