package com.objects.title.land.habitable;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.change.multi.type.WipeType;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.display.geography.GeometryType;
import com.objects.culture.object.PassiveCultureObject;
import com.objects.culture.object.compass.CompositeCompass;
import com.objects.shared.IDemographicDriven;
import com.objects.shared.PopulationContainer;
import com.objects.title.land.AbstractLandDivision;
import com.objects.title.land.habitable.changes.NodeChange;
import com.objects.title.land.resources.Resource;
import com.objects.title.land.resources.node.Node;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

public abstract class HabitableLand<R extends HabitableLand<R>> extends AbstractLandDivision<R> implements PassiveCultureObject, IDemographicDriven<R> {
    PopulationContainer<R> populationContainer;
    TLSet<Node> nodes;
    CompositeCompass compass;
    public HabitableLand(LocalDate created, LocalDate ended, GeometryType type, String geoID, List<ChangeSupplier<R, ?>> initialState) {
        super(created, ended, type, geoID, initialState);
        getTimeline().internalAddChange(new NodeChange<>(getReference(),created));
    }
    public HabitableLand(DMEReference<R> dme) {
        super(dme);
    }
    @Override
    public final PopulationContainer<R> getPopulationContainer() {
        return populationContainer;
    }

    public final void addNode(Node node){
        nodes.add(false,true,node);
    }
    public final void removeNode(Node node){
        nodes.remove(WipeType.FORWARD,node);
    }
    public final void internalSetChanged(ChangeType type, Node node, Consumer<Node> consumer){
        nodes.setChanged(true,type,node,consumer);
    }

    @Override
    public void onLink() {
        super.onLink();
    }

    @Override
    public void doDateChange() {
        super.doDateChange();

    }

    public final TLSet<Node> getNodes() {
        return nodes;
    }
    public final void internalSetNodes(TLSet<Node> nodes){
        this.nodes = nodes;
    }
}
