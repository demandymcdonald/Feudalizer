package com.objects.organization.religion.tenets.faith;

import com.Global.*;
import com.base.component.ComponentReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.ReligionGroups;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.organization.religion.IReligionObject;
import com.objects.organization.religion.tenets.diety.AbstractDivineEntity;
import com.objects.organization.religion.utility.ReligionEdge;
import com.utilities.caching.CachingSupplier;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import static com.objects.culture.tenet.group.groups.ReligionGroups.RELIGIOUS_DOCTRINE;

public class Faith extends DynamicTenet<Faith> implements IReligionObject {
    private final Graph<IReligionObject, DefaultEdge> graph = new DirectedPseudograph<>(DefaultEdge.class);
    private final CachingSupplier<Multimap<TenetGroup, ComponentReference<? extends AbstractDivineEntity<?>>>> pantheon = new CachingSupplier<>(this::buildPantheon);
    public Faith(DMEReference<Faith> dme) {
        super(RELIGIOUS_DOCTRINE, dme);
        graph.addVertex(this);
    }

    public Faith(String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<Faith, ?>> initialState) {
        super(RELIGIOUS_DOCTRINE, name, created, ended, foundingCulture, initialState);
        graph.addVertex(this);
    }

    public Faith(String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<Faith, ?>> initialState) {
        super(RELIGIOUS_DOCTRINE, name, id, created, ended, foundingCulture, initialState);
        graph.addVertex(this);
    }

    @Override
    protected void onLink() {
        pantheon.clear();
        for(ComponentReference<? extends AbstractDivineEntity<?>> ade : getPantheon().values()){
            ade.get().linkFaith(this.getReference());
        }
    }
    public Graph<IReligionObject, DefaultEdge> getGraph(){
        return graph;
    }

    @Override
    public void updateProceduralInfluencers() {

    }
    public Multimap<TenetGroup, ComponentReference<? extends AbstractDivineEntity<?>>> getPantheon(){
        return pantheon.get();
    }
    private Multimap<TenetGroup, ComponentReference<? extends AbstractDivineEntity<?>>> buildPantheon(){
        Multimap<TenetGroup, ComponentReference<? extends AbstractDivineEntity<?>>> map = HashMultimap.create();
        for (IReligionObject rawAde : graph.vertexSet().stream().filter(v -> v instanceof AbstractDivineEntity<?>).toList()){
            AbstractDivineEntity<?> ade = (AbstractDivineEntity<?>) rawAde;
            map.put(ade.getGroup(), (ComponentReference<? extends AbstractDivineEntity<?>>) ade.getReference());
        }
        return map;
    }

    @Override
    public double influencerResistance(COReference<?> influencer) {
        return 0;
    }

    @Override
    public Multimap<CultureCondition.Key, CultureCondition<?, ?>> getConditions() {
        return null;
    }

    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return null;
    }
}
