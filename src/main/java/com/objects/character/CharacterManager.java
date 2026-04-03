package com.objects.character;

import com.base.AbstractMutableManager;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.utilities.Factory;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class CharacterManager extends AbstractMutableManager<CharacterManager, LivingCreature<?>, CharacterManager.CharacterContainer> {


    public CharacterManager() {
        super("character_manager");
    }








    @Override
    public void ts_init() {
        super.ts_init();
    }

    @Override
    public void onThreadInit(boolean shared) {

    }

    @Override
    public void afterThreadInit(boolean shared) {
        super.afterThreadInit(shared);
    }


    protected static final Factory<BookCharacter, LivingCreature<?>, UUID, CharacterContainer> BOOK_CHARACTER_FACTORY = new Factory<>() {
        @Override
        protected BookCharacter create(Class<BookCharacter> classRef, CharacterContainer characterContainer) {
            return new BookCharacter(characterContainer.givenName(), characterContainer.surname(),
            characterContainer.dateOfBirth(), characterContainer.dateOfDeath(), characterContainer.gender(), characterContainer.orientation());
        }

        @Override
        protected BookCharacter load(Class<BookCharacter> classRef, UUID key, JsonObject object) {
            DMEReference<BookCharacter> ref = DMEReference.of(classRef, key);
            BookCharacter bc = new BookCharacter(ref);
            bc.deserialize(object);
            return bc;
        }
    };


    @Override
    public Map<Class<? extends LivingCreature<?>>, Factory<? extends LivingCreature<?>, LivingCreature<?>, UUID, CharacterContainer>> getFactories() {
        return Map.of(BookCharacter.class, BOOK_CHARACTER_FACTORY);
    }

    @Override
    public Class<?> instanceClass() {
        return null;
    }


    public record CharacterContainer(LocalDate created, LocalDate ended, String givenName, String surname, LocalDate dateOfBirth, LocalDate dateOfDeath,
                                     BookCharacter.Gender gender, BookCharacter.Orientation orientation){

    }

}
