package com.simulation.succession;

import com.GlobalVars;
import com.base.*;
import com.base.reference.DMEReference;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simulation.people.Character;
import com.simulation.people.CharacterManager;
import com.simulation.succession.SuccessionContainers.*;
import java.util.*;

public abstract class Title<T extends Title<T>> extends DateMutableEntity<Title.TitleContainer> {
    private Optional<Character> Holder = Optional.empty();
    private Optional<Title<?>> Parent = Optional.empty();
    private final Set<Title<?>> Children = new HashSet<>();
    private SuccessionContainer Succession;
    private JsonObject passthrough;
    public Title(UUID id, Date created, Date ended) {
        super(id, created, ended);
    }

    public Title(UUID id, Date created, Date ended, JsonObject additionalData) {
        super(id, created, ended, additionalData);
    }

    public Title(JsonObject payload) {
        super(payload);
    }
    public record TitleContainer(Optional<UUID> holder, Optional<UUID> parent, Set<UUID> children, PackedSC successionContainer, JsonObject passthrough) {
        public JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.addProperty("Holder", holder.isPresent() ? holder.get().toString() : "");
            json.addProperty("Parent", parent.isPresent() ? parent.get().toString() : "");
            json.add("SucContainer", successionContainer.serialize());
            json.add("Passthrough", passthrough);
            JsonArray childrenF = new JsonArray();
            for (UUID child : children) {
                childrenF.add(child.toString());
            }
            json.add("Children", childrenF);
            return json;
        }
        public static TitleContainer deserialize(JsonObject json) {
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
            SuccessionContainers.PackedSC packedSC = PackedSC.deserialize(json.get("SucContainer").getAsJsonObject());
            JsonObject passthrough = json.get("Passthrough").getAsJsonObject();
            TitleContainer MLC = new TitleContainer(holder, parent, children,packedSC,passthrough);
            return MLC;
        }
        public static TitleContainer builder(Optional<Character> Holder, Optional<Title<?>> Parent, Set<Title<?>> Children, SuccessionContainer sc, JsonObject passthrough) {
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

            return new TitleContainer(holderID, parentID, children, sc.convert(), passthrough);
        }
    }

    public abstract List<Character> getAllClaimants();

    @Override
    protected TitleContainer buildState(JsonObject o) {
        return TitleContainer.deserialize(o);
    }

    @Override
    public void relink(TitleContainer state) {
        final CharacterManager CM = DMRegistry.getCharacterManager();
        final TitleManager TM = DMRegistry.getTitleManager();
        if (state.holder().isPresent()) {
            Holder = Optional.of(CM.get(state.holder().get()));
        } else {
            Holder = Optional.empty();
        }
        if (state.parent().isPresent()) {
            Parent = Optional.of(TM.get(state.parent().get()));
        }
        for (UUID child : state.children()) {
            Children.add(TM.get(child));
        }
        Succession = state.successionContainer.convert();
        onNewStateLoad(passthrough);
        onRelink();
    }

    @Override
    protected JsonObject serializeData(TitleContainer data) {
        return null;
    }
    public abstract boolean canInherit(Character person);
    public abstract boolean isInheritable();
    public abstract boolean isSubPropagating();
    protected abstract JsonObject updateState(JsonObject j);
    protected abstract void onRelink();
    protected abstract void onNewStateLoad(JsonObject passthrough);
    protected JsonObject getPayload() {
        return passthrough;
    }


    public Optional<Title<?>> getParent() {
        return Parent;
    }



    public Optional<Character> getHolder() {
        return Holder;
    }

    public void setHolder(Character holder) {
        if (!canInherit(holder)){
            //TODO throw flag
            return;
        }
        Holder = Optional.of(holder);
        addStateChange(GlobalVars.CURRENT_DATE, new StateChangeKey(StateChangeKey.StateChangeType.GRANT_TITLE, DMEReference.of(holder),DMEReference.of(this)));
        holder.addTitle(this);
    }
    public void removeHolder(Character holder){
        Holder = Optional.empty();
        addStateChange(GlobalVars.CURRENT_DATE, new StateChangeKey(StateChangeKey.StateChangeType.REVOKE_TITLE, DMEReference.of(holder),DMEReference.of(this)));
        holder.revokeTitle(this);
    }
    public Set<Title<?>> getChildren() {
        return Children;
    }

    public void setChildren(Set<Title<?>> children) {
        Children.clear();
        Children.addAll(children);
    }
    public boolean hasChild(Title<?> child) {
        return Children.contains(child);
    }
    public void addChild(boolean removeCurrentParent, Title<?>... child) {
        for (Title<?> t : child) {
            if (t.hasParent() && !removeCurrentParent) {
                //TODO Remove parent flag
                return;
            } else {
                Children.add(t);
                t.setParent(removeCurrentParent,this);
                addStateChange(GlobalVars.CURRENT_DATE,new StateChangeKey(StateChangeKey.StateChangeType.DE_JURE_DRIFT, DMEReference.of(t),DMEReference.of(this)));
            }
        }
    }
    public void setParent(boolean removeCurrentParent, Title<?> parent) {
        if (parent.hasChild(this)){
            Parent = Optional.of(parent);
            addStateChange(GlobalVars.CURRENT_DATE,new StateChangeKey(StateChangeKey.StateChangeType.DE_JURE_DRIFT, DMEReference.of(this),DMEReference.of(parent)));
        } else{
            parent.addChild(removeCurrentParent);
        }
    }
    public boolean hasParent() {
        return Parent.isPresent();
    }
    public SuccessionContainer getSuccession() {
        return this.Succession;
    }
    @Override
    protected JsonObject saveAdditional(JsonObject j) {
        j.addProperty("TitleType", this.getClass().getSimpleName());
        return super.saveAdditional(j);
    }
    @Override
    protected TitleContainer getCurrentState() {
        return TitleContainer.builder(Holder,Parent,Children,getSuccession(), updateState(new JsonObject()));
    }
}
