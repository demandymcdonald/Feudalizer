package com.base.timeline.sandbox.scope;

import com.base.DMRegistry;
import com.base.ObjectType;
import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.family.Family;
import com.objects.title.house.House;
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
    public static HashMultimap<ObjectType, JsonObject> getPersonData(BookCharacter character, boolean includeFullSpouse, boolean includeFullHouse, HashSet<UUID> exclude){
        if (exclude == null){
            exclude = new HashSet<>();
        }
        exclude.add(character.getId());
        HashMultimap<ObjectType, JsonObject> data = HashMultimap.create();
        data.put(ObjectType.CHARACTER,character.serialize());
        for (Family f : character.getFamilies().keySet()){
            if (includeFullSpouse){
                for (BookCharacter spouse : f.getSpouses()){
                    if (spouse != null && !spouse.equals(character) && !exclude.contains(spouse.getId())){
                        exclude.add(spouse.getId());
                        data.putAll(getPersonData(spouse,false,includeFullHouse,exclude));
                    }
                }

            }
            for (BookCharacter child : f.getMembers()){
                if (child != null && !child.equals(character) && !exclude.contains(child.getId())){
                    exclude.add(child.getId());
                    data.putAll(getPersonData(child,includeFullSpouse,false,exclude));
                }
            }
        }
        if (includeFullHouse && character.getHouse().isPresent()){
            House house = character.getHouse().get();
            for (BookCharacter c : house.getAllCharacters()){
                if (!c.equals(character) && !exclude.contains(c.getId())){
                    exclude.add(c.getId());
                    data.putAll(getPersonData(c,false,false,exclude));
                }
            }
        }
        return data;
    }
}
