package com.objects.title.land.habitable.changes;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.condition.deactivate.DeactivateCondition;
import com.base.timeline.change.multi.TimelineSetChange;
import com.base.timeline.change.multi.condition.MultiCondition;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.base.timeline.state.TimelineState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.node.Node;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class NodeChange<T extends HabitableLand<T>> extends TimelineSetChange<NodeChange<T>, Node,String,T> {
    public NodeChange(DMEReference<? extends T> owner, LocalDate date) {
        super(owner, date);
    }

    protected NodeChange(DMEReference<? extends T> owner, LocalDate date, Set<Node> initial) {
        super(owner, date, initial);
    }

    @Override
    public TLSet<Node> getRuntimeSet() {
        return getOwner().get().getNodes();
    }

    @Override
    public void setRuntimeSet(TLSet<Node> set) {
        getOwner().get().internalSetNodes(set);
    }

    @Override
    public NodeChange<T> getEmptyChange(DMEReference<? extends T> owner, LocalDate date) {
        return new NodeChange<>(owner, date);
    }

    @Override
    public void conditionsWipeForward(List<MultiCondition<NodeChange<T>, Node, Boolean, String, T>> current) {

    }

    @Override
    public void conditionsWipeBackward(List<MultiCondition<NodeChange<T>, Node, Boolean, String, T>> current) {

    }

    @Override
    protected JsonElement kSerialize(Node node) {
        return node.toJson();
    }

    @Override
    protected Node kDeserialize(JsonElement o) {
        return Node.fromJson(getOwner(),o);
    }

    @Override
    protected JsonElement iSerialize(String s) {
        return new JsonPrimitive(s);
    }

    @Override
    protected String iDeserialize(JsonElement o) {
        return o.getAsString();
    }

    @Override
    protected void onApply(DMEReference<? extends T> entity, TimelineState<? extends T> currentState) {

    }

    @Override
    protected String getText() {
        return "node-change";
    }

    @Override
    protected void deactivateConditions(List<DeactivateCondition<? super T>> list) {

    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
}
