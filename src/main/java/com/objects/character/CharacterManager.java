package com.objects.character;

import com.base.AbstractMutableManager;
import com.base.ObjectType;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.UUID;

public class CharacterManager extends AbstractMutableManager<CharacterManager,BookCharacter,> {


    public CharacterManager() {
        super(new CharacterState(null,null,null,null,null));
    }


    @Override
    public BookCharacter deserializer(UUID id, JsonObject json) {
        return new BookCharacter(json);
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.CHARACTER;
    }
    public record CharacterContainer(String givenName, String surname, LocalDate dateOfBirth, LocalDate dateOfDeath,
                                     BookCharacter.Gender gender, BookCharacter.Orientation orientation){

    }
}
