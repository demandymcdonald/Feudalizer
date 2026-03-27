package com;

import com.simulation.people.BookCharacter;
import com.simulation.people.CharacterSuccessionContainer;
import com.simulation.title.Title;
import com.simulation.title.succession.rules.CommonLawEntry;
import com.simulation.title.succession.rules.CustomEntry;
import com.simulation.title.succession.rules.SuccessionEntry;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.function.BiFunction;

public class TypedSerialized {
    private static final HashMap<Class<? extends SuccessionEntry<?>>, SuccessionEntry<?>> REGISTERED_SUCCESSION_RULES = new HashMap<>();
    private static final HashMap<Class<? extends SuccessionEntry<?>>, TriFunction<Title<?>, SuccessionEntry<?>, BookCharacter,SuccessionEntry<?>> > RULES_FACTORIES = new HashMap<>();
    public static <T extends SuccessionEntry<T>> void addRule(Class<T> clazz, T empty, TriFunction<Title<?>,T, BookCharacter,T> factory){
        REGISTERED_SUCCESSION_RULES.put(clazz,empty);
        RULES_FACTORIES.put(clazz,(TriFunction<Title<?>, SuccessionEntry<?>, BookCharacter,SuccessionEntry<?>>) factory);
    }
    public static <T extends SuccessionEntry<T>> T buildNew(Class<T> clazz, Title<?> title, BookCharacter character){
        return (T) RULES_FACTORIES.get(clazz).apply(title,REGISTERED_SUCCESSION_RULES.get(clazz),character);
    }
    static {
        REGISTERED_SUCCESSION_RULES.put(CustomEntry.class,new CustomEntry());
        REGISTERED_SUCCESSION_RULES.put(CommonLawEntry.class,new CommonLawEntry());
    }
    public static HashMap<Class<? extends SuccessionEntry<?>>, SuccessionEntry<?>> getRegisteredSuccessionRules() {
        return REGISTERED_SUCCESSION_RULES;
    }
    public static void init(){
        Feudalizer.LOGGER.info("Initialized Succession Rules.. Count: " + REGISTERED_SUCCESSION_RULES.size());
    }
}
