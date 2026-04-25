package com.base.timeline.sandbox.scope;

import com.base.DMRegistry;
import com.base.ObjectType;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.objects.character.sentient.HumanCharacter;
import com.objects.family.Family;
import com.objects.title.Title;

import java.util.HashSet;
import java.util.UUID;

public class Scopes {
    public static HashMultimap<ObjectType, JsonObject> getFromTitle(Title<?> title, boolean checkUp){
        HashMultimap<ObjectType, JsonObject> scopes = HashMultimap.create();
        scopes.put(ObjectType.TITLE, title.serialize());
        Title<?> current = title;
        while (current != null && checkUp){
            scopes.putAll(getPeopleFromTitle(current));
            current = current.getParent().orElse(null);
        }
        for (Title<?> child : title.getChildren()){
            scopes.putAll(getFromTitle(child,false));
        }
        return scopes;
    }
    public static HashMultimap<ObjectType, JsonObject> getFromTitle(Title<?> title){
        return getFromTitle(title,true);
    }
    private static HashMultimap<ObjectType, JsonObject> getPeopleFromTitle(Title<?> title){
        HashMultimap<ObjectType, JsonObject> people = HashMultimap.create();
        for (UUID id : title.getAllHolders()){
            people.put(ObjectType.CHARACTER,DMRegistry.getEntity(ObjectType.CHARACTER,id).serialize());
        }
        return people;
    }
    public static HashMultimap<ObjectType, JsonObject> getPersonData(HumanCharacter character, boolean includeFullSpouse, boolean includeFullHouse, HashSet<UUID> exclude){
        if (exclude == null){
            exclude = new HashSet<>();
        }
        exclude.add(character.getDisplayID());
        HashMultimap<ObjectType, JsonObject> data = HashMultimap.create();
        data.put(ObjectType.CHARACTER,character.serialize());
        for (Family f : character.getFamilies().keySet()){
            if (includeFullSpouse){
                for (HumanCharacter spouse : f.getSpouses()){
                    if (spouse != null && !spouse.equals(character) && !exclude.contains(spouse.getDisplayID())){
                        exclude.add(spouse.getDisplayID());
                        data.putAll(getPersonData(spouse,false,includeFullHouse,exclude));
                    }
                }

            }
            for (HumanCharacter child : f.getMembers()){
                if (child != null && !child.equals(character) && !exclude.contains(child.getDisplayID())){
                    exclude.add(child.getDisplayID());
                    data.putAll(getPersonData(child,includeFullSpouse,false,exclude));
                }
            }
        }
        if (includeFullHouse && character.getHouse().isPresent()){
            House house = character.getHouse().get();
            for (HumanCharacter c : house.getAllCharacters()){
                if (!c.equals(character) && !exclude.contains(c.getDisplayID())){
                    exclude.add(c.getDisplayID());
                    data.putAll(getPersonData(c,false,false,exclude));
                }
            }
        }
        return data;
    }
}
