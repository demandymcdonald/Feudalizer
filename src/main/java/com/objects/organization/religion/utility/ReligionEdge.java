package com.objects.organization.religion.utility;

import com.Global.*;
import com.objects.organization.religion.IReligionObject;
import org.jgrapht.graph.DefaultEdge;

public class ReligionEdge extends DefaultEdge {
    public enum Type{
        FAITH,
        DEITY
    }
    public ReligionEdge(Type type){

    }

}
