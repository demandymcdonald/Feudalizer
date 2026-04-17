package com.objects.character.species;

import com.utilities.IDisplayable;
import com.utilities.number.BoundInt;
import com.utilities.number.BoundInts;

public abstract class Species implements IDisplayable {
    private final String id;
    private String name;
    private String description;
    private final BoundInt sentience = BoundInts.Percent(false);
    private final BoundInt magic_capacity = BoundInts.Percent(false);


    public Species(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
