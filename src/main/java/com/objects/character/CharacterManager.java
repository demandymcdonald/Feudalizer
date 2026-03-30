package com.objects.character;

import com.GlobalVars;
import com.base.AbstractMutableManager;
import com.base.ObjectType;
import com.google.gson.JsonObject;
import com.objects.people.House;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.UUID;

public class CharacterManager extends AbstractMutableManager<BookCharacter, CharacterState> {


    public CharacterManager() {
        super(new CharacterState(null,null,null,null,null));
    }

    public static BookCharacter buildCommoner(String givenName, String surname, BookCharacter.Gender gender){
        return new BookCharacter(UUID.randomUUID(),givenName,surname,null, GlobalVars.CURRENT_DATE(),null,gender);
    }

    public static BookCharacter buildCommoner(String givenName, String surname, LocalDate dob, LocalDate dod, BookCharacter.Gender gender){
        return new BookCharacter(UUID.randomUUID(),givenName,surname,null,dob,dod,gender);
    }

    public static BookCharacter buildNoble(String givenName, String surname, LocalDate dob, LocalDate dod, BookCharacter.Gender gender){
        return buildNoble(givenName,surname,surname,dob,dod,gender);
    }

    public static BookCharacter buildNoble(String givenName, String surname, String houseName, BookCharacter.Gender gender){

        return buildNoble(givenName,surname,houseName, GlobalVars.CURRENT_DATE(),null,gender);
    }

    public static BookCharacter buildNoble(String givenName, String surname, String houseName, @Nonnull LocalDate dob, LocalDate dod, BookCharacter.Gender gender){
        BookCharacter character = new BookCharacter(UUID.randomUUID(),givenName,surname,null,dob,dod,gender);
        House house = new House(houseName,character);
        character.setHouseInternal(house);
        return character;
    }

    public static BookCharacter buildNoble(String givenName, String surname, House house, BookCharacter.Gender gender){
        return new BookCharacter(UUID.randomUUID(),givenName,surname,house, GlobalVars.CURRENT_DATE(),null,gender);
    }

    public static BookCharacter buildNoble(String givenName, String surname, House house, @Nonnull LocalDate dob, LocalDate dod, BookCharacter.Gender gender){
        return new BookCharacter(UUID.randomUUID(),givenName,surname,house,dob,dod,gender);
    }

    @Override
    public BookCharacter deserializer(UUID id, JsonObject json) {
        return new BookCharacter(json);
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.CHARACTER;
    }

}
