package com.base.timeline.propagation.core;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.FamilyManager;
import com.simulation.people.House;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class SandboxHandler {
    final Sandbox sandbox;
    final Objective objective;
    final Objective[] secondaryObjectives;


    protected HashMultimap<ObjectType, JsonObject> collectSandbox() {
        DateMutableEntity<?> primary = DMRegistry.getEntity(objective.type(), objective.id());
        HashMultimap<ObjectType, JsonObject> sandbox = HashMultimap.create();
        switch (objective.type()) {
            case CHARACTER: {
                BookCharacter p =
            }
        }
    }


    private static HashMultimap<ObjectType, JsonObject> buildFromCharacter(BookCharacter character){
        HashMultimap<ObjectType, JsonObject> sandbox = HashMultimap.create();
        sandbox.put(ObjectType.CHARACTER,character.serialize());
        Pair<Set<BookCharacter>,Set<Family>> ps = FamilyManager.getDynastyForward(character);
        processSetofObjects(ps.getKey(),sandbox);
        processSetofObjects(ps.getValue(),sandbox);
        Set<House> houses = new HashSet<>();


        return sandbox;
    }
    private static <R extends DateMutableEntity<?>> void  processSetofObjects(Set<R> stuff, HashMultimap<ObjectType, JsonObject> sandbox){
        for (R r : stuff){
            sandbox.put(DMRegistry.getObjectType(r),r.serialize());
        }
    }
}

