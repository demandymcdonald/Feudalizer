package com.objects.organization.religion.tenets.diety;

import com.base.component.InstanceType;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.organization.religion.IReligionObject;
import com.objects.organization.religion.tenets.faith.Faith;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDivineEntity<G extends AbstractDivineEntity<G>> extends MutableTenet implements IReligionObject {
    private InterpretatioEntity baseEntity;
    private final DivineEntityType entityType;
    private final Set<DMEReference<Faith>> faiths = new HashSet<>();
    private DMEReference<Faith> foundingFaith;
    private DivineScope scope;
    public AbstractDivineEntity(InstanceType type, DivineEntityType entityType, String id) {
        super(type, id);
        this.entityType = entityType;
    }

    public AbstractDivineEntity(InstanceType type, DivineEntityType entityType,  DMEReference<Faith> foundingFaith, DivineScope scope,
                                InterpretatioEntity baseEntity, String id, String name, String description) {
        super(type, foundingFaith.get().getTenetReference(), entityType.getGroup(),
                scope.getCompass().merge(foundingFaith.get().getCompass()), id, name, description);
        this.baseEntity = baseEntity;
        this.entityType = entityType;
        this.scope = scope;
        this.foundingFaith = foundingFaith;
        doEntityLink((G) this);
    }
    public DMEReference<Faith> getFoundingFaith() {
        if(foundingFaith == null) {
            Faith f = (Faith) getTenetReference().get();
            foundingFaith = f.getReference();
        }
        return foundingFaith;
    }
    public Set<DMEReference<Faith>> getFaiths(){
        Set<DMEReference<Faith>> toReturn = new HashSet<>(faiths);
        toReturn.add(getFoundingFaith());
        return toReturn;
    }
    public DivineEntityType getEntityType() {
        return entityType;
    }

    public InterpretatioEntity getBaseEntity() {
        return baseEntity;
    }
    public void linkFaith(DMEReference<Faith> faithRef) {
        faiths.add(faithRef);
        doEntityLink((G) this);
    }




    @Override
    public void additionalLoad(JsonObject object) {
        super.additionalLoad(object);
        doEntityLink((G) this);
    }

    private static <G extends AbstractDivineEntity<G>> void doEntityLink(G entity){
        for(DMEReference<Faith> faithRef : entity.getFaiths()) {
            Faith faith = faithRef.get();
            Graph<IReligionObject, DefaultEdge> graph = faith.getGraph();
            if (!graph.containsVertex(entity)) {
                InterpretatioEntity baseEntity = entity.getBaseEntity();
                graph.addVertex(entity);
                graph.addEdge(faith, entity);
                if (!graph.containsVertex(baseEntity)) {
                    graph.addVertex(baseEntity);
                    graph.addEdge(faith, baseEntity);
                }
                graph.addEdge(baseEntity, entity);
            }
        }
    }

}
