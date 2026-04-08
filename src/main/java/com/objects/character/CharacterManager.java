package com.objects.character;

import com.base.AbstractMutableManager;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.character.human.HumanCharacter;
import com.utilities.Factory;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class CharacterManager extends AbstractMutableManager<CharacterManager, LivingCreature<?>> {


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


    protected static final Factory<HumanCharacter, LivingCreature<?>, UUID, JsonObject> BOOK_CHARACTER_FACTORY = new Factory<>() {
        @Override
        protected HumanCharacter create(Class<HumanCharacter> classRef, JsonObject characterContainer) {
            CharacterContainer cc = CharacterContainer.deserialize(characterContainer);
            return new HumanCharacter(cc.givenName(), cc.surname(),
            cc.dateOfBirth(), cc.dateOfDeath(), cc.gender(), cc.orientation());
        }

        @Override
        protected HumanCharacter load(Class<HumanCharacter> classRef, UUID key, JsonObject object) {
            DMEReference<HumanCharacter> ref = DMEReference.of(classRef, key);
            HumanCharacter bc = new HumanCharacter(ref);
            bc.deserialize(object);
            return bc;
        }
    };


    @Override
    public Map<Class<? extends LivingCreature<?>>, Factory<? extends LivingCreature<?>, LivingCreature<?>, UUID, JsonObject>> getFactories() {
        return Map.of(HumanCharacter.class, BOOK_CHARACTER_FACTORY);
    }

    @Override
    public Class<?> instanceClass() {
        return null;
    }


    public record CharacterContainer(LocalDate created, LocalDate ended, String givenName, String surname, LocalDate dateOfBirth, LocalDate dateOfDeath,
                                     HumanCharacter.Gender gender, HumanCharacter.Orientation orientation){
        public JsonObject serialize(){
            JsonObject json = new JsonObject();
            json.addProperty("type","character");
            json.addProperty("created",created.toEpochDay());
            json.addProperty("ended",ended.toEpochDay());
            json.addProperty("givenName",givenName);
            json.addProperty("surname",surname);
            json.addProperty("dateOfBirth",dateOfBirth.toEpochDay());
            json.addProperty("dateOfDeath",dateOfDeath.toEpochDay());
            json.addProperty("gender",gender.name());
            json.addProperty("orientation",orientation.name());
            return json;
        }
        public static CharacterContainer deserialize(JsonObject json){
            if (!json.get("type").getAsString().equals("character")){
                throw new IllegalArgumentException("Invalid CharacterContainer");
            }
            return new CharacterContainer(LocalDate.ofEpochDay(json.get("created").getAsLong()), LocalDate.ofEpochDay(json.get("ended").getAsLong()),
                    json.get("givenName").getAsString(), json.get("surname").getAsString(), LocalDate.ofEpochDay(json.get("dateOfBirth").getAsLong()),
                    LocalDate.ofEpochDay(json.get("dateOfDeath").getAsLong()), HumanCharacter.Gender.valueOf(json.get("gender").getAsString()),
                    HumanCharacter.Orientation.valueOf(json.get("orientation").getAsString()));
        }
    }

}
