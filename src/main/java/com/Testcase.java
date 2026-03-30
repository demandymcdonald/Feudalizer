package com;

import com.objects.character.BookCharacter;
import com.objects.character.CharacterManager;

public class Testcase {
    public static final BookCharacter TESTIFICATE = CharacterManager.buildNoble("Testificate","Mojang", "Mojang", BookCharacter.Gender.Male);
    public static final BookCharacter FEMALE_TESTIFICATE = CharacterManager.buildNoble("Hot Testificate","Doe",TESTIFICATE.getHouse().get(),BookCharacter.Gender.Female);

    public static void init(){
        FEMALE_TESTIFICATE.giveBirth(TESTIFICATE, BookCharacter.Gender.Male,"Claude");
        TESTIFICATE.giveBirth(FEMALE_TESTIFICATE, BookCharacter.Gender.Female,"Olivia Rodrigo");
        Feudalizer.LOGGER.info("Testcase initialized");
    };
}
