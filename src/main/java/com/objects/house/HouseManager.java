package com.objects.house;

import com.base.AbstractMutableManager;
import com.base.ObjectType;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.family.Family;
import com.objects.title.Title;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HouseManager extends AbstractMutableManager<House,HouseState> {
    public record HouseBible(Set<Character> everyCharacter, Set<Family> families, Set<Title<?>> titles, Set<House> subHouses)

    @Override
    public House deserializer(UUID id, JsonObject json) {
        return new House(json);
    }
    +
    @Override
    public ObjectType getObjectType() {
        return ObjectType.HOUSE;
    }

    public HouseBible getEverything(BookCharacter character) {
        House h = character.getHouse().orElse(null);
        if (h == null){
            return new HouseBible(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());
        }

    }
}
