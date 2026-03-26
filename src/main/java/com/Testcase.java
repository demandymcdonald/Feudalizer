package com;

import com.simulation.people.BookCharacter;
import com.simulation.people.Family;
import com.simulation.people.House;
import org.apache.commons.lang3.tuple.Pair;

import java.util.UUID;

public class Testcase {
    public static final BookCharacter TESTIFICATE = BookCharacter.buildNoble("Testificate","Mojang", "Mojang", BookCharacter.Gender.Male);
    public static final BookCharacter FEMALE_TESTIFICATE = BookCharacter.buildNoble("Hot Testificate","Doe",TESTIFICATE.getHouse().get(),BookCharacter.Gender.Female);

    public static void init(){
        FEMALE_TESTIFICATE.giveBirth(TESTIFICATE, BookCharacter.Gender.Male,"Claude");
        TESTIFICATE.giveBirth(FEMALE_TESTIFICATE, BookCharacter.Gender.Female,"Olivia Rodrigo");
        Feudalizer.LOGGER.info("Testcase initialized");
    };
}
