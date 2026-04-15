package com.objects.culture.tenet.group;

import com.Global.*;
import org.jgrapht.graph.DefaultEdge;

public class ConnectionEdge extends DefaultEdge {
    public enum Type{
        ANY,
        DEPENDENT,
        STRUCTURAL,
        INFLUENCING
    }
    private final Type type;
    public ConnectionEdge(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
