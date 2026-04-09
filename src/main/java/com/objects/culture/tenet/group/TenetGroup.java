package com.objects.culture.tenet.group;

import com.google.common.collect.ImmutableList;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.TenetManager;
import com.utilities.Displayable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public record TenetGroup(TGType type, TenetGroup parent, List<TenetGroup> connected, String id, String name, String description) implements Displayable {
    public static final int SYSTEM_MAX = 10;
    public static final int BELIEF_MAX = 10;
    public static final int VALUE_MAX = 3;
    public static final int TRADITION_MAX = 10;
    public static final int AESTHETIC_MAX = 6;
    public static final int LANGUAGE_MAX = 3;
    //=============================================================

    public TenetGroup(@NonNull TGType type, TenetGroup parent, List<TenetGroup> connected, String id, String name, String description) {
        if (type == null){
            throw new RuntimeException("Cannot create TenetGroup: "+ id +" without a type");
        } else if (parent != null && !type.isChildOf(parent.type(),true)){
            throw new RuntimeException("Cannot create TenetGroup: "+ id +" with type "+ type +" because it is not a child of "+ parent.type());
        }
        this.type = type;
        this.parent = parent;
        this.connected = buildList(parent, connected);
        this.id = buildID(id, parent);
        this.name = name;
        this.description = description;
        TenetManager.registerGroup(this);
    }
    public void init(){
        //May be needed to do buildList after all the statics are registered, but that seems unlikely.
    }
    private static ImmutableList<TenetGroup> buildList(TenetGroup parent, List<TenetGroup> connected){
        List<TenetGroup> list = new ArrayList<>(connected);
        TenetGroup current = parent;
        while(current.parent() != null){
            list.addAll(current.connected());
            current = current.parent();
        }
        return ImmutableList.copyOf(list);
    }
    private static String buildID(String id, TenetGroup parent){
        if (parent == null){
            return id.toLowerCase();
        } else {
            return parent.getID() + ":" + id.toLowerCase();
        }
    }
    @Override
    public String getID() {
        return id;
    }

    @Override
    public String displayName() {
        return name;
    }
    public List<TenetGroup> connected(){
        return new ArrayList<>(TenetManager.getConnectedGroups(this));
    }
    public boolean isParentOf(TenetGroup child){
        return TenetManager.isParentGroup(this,child);
    }
    public boolean isChildOf(TenetGroup parent){
        return TenetManager.isParentGroup(parent,this);
    }
    public record AcceptanceContainer(int maxNumber, Acceptance... accept){}
    public static TenetGroup builder(TGType type, String id, String name, String description){
        return new TenetGroup(type, null,ImmutableList.of(), id, name, description);
    }
    public static TenetGroup builder(TGType type, TenetGroup parent, String id, String name, String description){
        return new TenetGroup(type,parent, ImmutableList.of(), id, name, description);
    }
    public static TenetGroup builder(TGType type, TenetGroup parent, ImmutableList<TenetGroup> connected, String id, String name, String description){
        return new TenetGroup(type,parent, connected, id, name, description);
    }

}
