package com.utilities.hierarchy;

import com.base.DateMutableEntity;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

public interface IParentedGraphed<T extends DateMutableEntity<?> & IParentedGraphed<T,E>, E extends DefaultEdge> extends Parented<T> {

    Graph<T,E> internalGetGraph();
    void internalSetGraph(Graph<T,E> graph);
    default Graph<T, E> getGraph(){
        if(hasParent()){
            return getParent().get().getGraph();
        }
        return internalGetGraph();
    };
    Class<E> getEdgeClass();
    default void doDateChange(){
        if(!hasParent()){
            internalSetGraph(new DirectedPseudograph<>(getEdgeClass()));
        }
    }
    E makeEdge(T source, T target);
    default void onLink(){
        T us = (T) this;
        if(hasParent()){
            getParent().get().forceLink();
        }
        getGraph().addVertex(us);
        if(hasParent()){
            T parent = getParent().get();
            getGraph().addEdge(parent, us, makeEdge(parent, us));
        }
    }

}
