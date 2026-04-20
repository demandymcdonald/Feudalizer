package com.objects.title.land.resources.good;

import com.objects.title.land.resources.GoodManager;
import com.utilities.IDisplayable;

import java.util.Objects;

public class GoodTag implements IDisplayable {
    private final String id;
    private final String name;
    private final String description;
    protected GoodTag(String id, String name, String description) {
        this.id = "good_tag:"+id;
        this.name = name;
        this.description = description;
        GoodManager.registerTag(this);
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GoodTag goodTag = (GoodTag) o;
        return Objects.equals(id, goodTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
