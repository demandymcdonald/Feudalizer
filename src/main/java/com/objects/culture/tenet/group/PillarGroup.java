package com.objects.culture.tenet.group;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Optional;

public class PillarGroup extends TenetGroup{
    List<TenetGroup> children;
    public PillarGroup(@NonNull TGType type, TenetGroup parent, List<TenetGroup> connected, String id, String name, String description) {
        super(type, parent, connected, id, name, description);
    }

    public List<TenetGroup> getChildren() {
        return children;
    }
    public Optional<CategoryGroup> LeadershipTags
}
