package com.objects.character.sentient;

import com.google.common.collect.ImmutableList;
import com.utilities.Displayable;

import java.util.ArrayList;
import java.util.List;

public abstract class SentientSpecies implements Displayable {
    private final ImmutableList<SentientSpecies> compatibleMating;
    private final String name;
    private final String id;
    private final String description;

    public SentientSpecies(String id, String name, String description, List<SentientSpecies> compatibleMating) {
        this.compatibleMating = ImmutableList.<SentientSpecies>builder().addAll(compatibleMating).build();
        this.name = name;
        this.id = id;
        this.description = description;
    }

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

    public ImmutableList<SentientSpecies> getCompatibleMating() {
        return compatibleMating;
    }
}
