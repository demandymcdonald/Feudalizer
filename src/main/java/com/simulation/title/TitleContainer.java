package com.simulation.title;

import com.base.timeline.TimelineContainer;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public record TitleContainer(Optional<UUID> holder, Optional<UUID> parent, Set<UUID> children,
                             JsonObject passthrough) implements TimelineContainer<TitleContainer> {
    public TitleContainer(){
        this(Optional.empty(),Optional.empty(),Sets.newHashSet(),new JsonObject());
    }
    public JsonObject getSerialized() {
        JsonObject json = new JsonObject();
        json.addProperty("Holder", holder.isPresent() ? holder.get().toString() : "");
        json.addProperty("Parent", parent.isPresent() ? parent.get().toString() : "");
        json.add("Passthrough", passthrough);
        JsonArray childrenF = new JsonArray();
        for (UUID child : children) {
            childrenF.add(child.toString());
        }
        json.add("Children", childrenF);
        return json;
    }

    public TitleContainer getDeserialized(JsonObject json) {
        Set<UUID> children = Sets.newHashSet();
        for (JsonElement child : json.get("Children").getAsJsonArray()) {
            children.add(UUID.fromString(child.getAsString()));
        }
        String parentString = json.get("Parent").getAsString();
        String holderString = json.get("Holder").getAsString();
        Optional<UUID> parent;
        Optional<UUID> holder;
        if (holderString.equals("")) {
            holder = Optional.empty();
        } else {
            holder = Optional.of(UUID.fromString(holderString));
        }
        if (parentString.equals("")) {
            parent = Optional.empty();
        } else {
            parent = Optional.of(UUID.fromString(parentString));
        }
        JsonObject passthrough = json.get("Passthrough").getAsJsonObject();
        TitleContainer MLC = new TitleContainer(holder, parent, children, passthrough);
        return MLC;
    }

    @Override
    public String Header() {
        return "TitleContainer";
    }

    public static TitleContainer builder(Optional<BookCharacter> Holder, Optional<Title<?>> Parent, Set<Title<?>> Children, JsonObject passthrough) {
        Optional<UUID> parentID;
        if (Parent.isPresent()) {
            parentID = Optional.of(Parent.get().getId());
        } else {
            parentID = Optional.empty();
        }
        Optional<UUID> holderID;
        if (Holder.isPresent()) {
            holderID = Optional.of(Holder.get().getId());
        } else {
            holderID = Optional.empty();
        }
        Set<UUID> children = Sets.newHashSet();
        for (Title<?> child : Children) {
            children.add(child.getId());
        }

        return new TitleContainer(holderID, parentID, children, passthrough);
    }
}
