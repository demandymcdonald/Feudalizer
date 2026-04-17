package com;

import com.objects.character.sentient.Gender;
import com.objects.character.sentient.HumanCharacter;
import com.objects.character.CharacterManager;

public class Testcase {
    public static final HumanCharacter TESTIFICATE = CharacterManager.buildNoble("Testificate","Mojang", "Mojang", Gender.Male);
    public static final HumanCharacter FEMALE_TESTIFICATE = CharacterManager.buildNoble("Hot Testificate","Doe",TESTIFICATE.getHouse().get(), Gender.Female);

    public static void init(){
        FEMALE_TESTIFICATE.giveBirth(TESTIFICATE, Gender.Male,"Claude");
        TESTIFICATE.giveBirth(FEMALE_TESTIFICATE, Gender.Female,"Olivia Rodrigo");
        Feudalizer.LOGGER.info("Testcase initialized");
    };
}
