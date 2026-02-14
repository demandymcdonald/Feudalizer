package com.people;

import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;

import java.util.UUID;

public class CharacterManager extends AbstractMutableManager<Character.CharacterState,Character> {


    @Override
    public Character deserializer(UUID id, JsonObject json) {
        return new Character(json);
    }

}
