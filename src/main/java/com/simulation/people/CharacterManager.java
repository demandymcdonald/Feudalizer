package com.simulation.people;

import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;

import java.util.UUID;

public class CharacterManager extends AbstractMutableManager<BookCharacter,CharacterState> {


    @Override
    public BookCharacter deserializer(UUID id, JsonObject json) {
        return new BookCharacter(json);
    }

}
