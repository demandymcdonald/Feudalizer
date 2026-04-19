package com.objects.character.physical.species.nomenclature;

import com.objects.character.physical.GeneManager;

import javax.annotation.Nullable;

public record NomenEntry(Type type, @Nullable NomenEntry parent, String id, String name, String description) implements Nomenclature {
    public NomenEntry(Type type, @Nullable NomenEntry parent, String id, String name, String description) {
        if(id == null || type == null || name == null || description == null){
            throw new IllegalArgumentException("NomenEntry cannot have null values for id, type, name, or description");
        }
        GeneManager.Species_Race.registerNomenclature(this);
        this.type = type;
        this.parent = parent;
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

    @Override
    public String getID() {
        return id;
    }

    public static NomenEntry of(Type type, @Nullable NomenEntry entry, String id, String name, String description) {
        NomenEntry existing = GeneManager.Species_Race.getNomenEntry(id);
        if(existing == null){
            return new NomenEntry(type, entry,"nomen_entry:"+id, name, description);
        } else {
            return existing;
        }
    }
}
