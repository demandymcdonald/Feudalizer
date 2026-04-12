package com;

import com.objects.character.sentient.HumanCharacter;
import com.objects.title.Title;
import com.objects.title.succession.rules.CommonLawEntry;
import com.objects.title.succession.rules.CustomEntry;
import com.objects.title.succession.rules.SuccessionEntry;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;

public class TypedSerialized {
    private static final HashMap<Class<? extends SuccessionEntry<?>>, SuccessionEntry<?>> REGISTERED_SUCCESSION_RULES = new HashMap<>();
    private static final HashMap<Class<? extends SuccessionEntry<?>>, TriFunction<Title<?>, SuccessionEntry<?>, HumanCharacter,SuccessionEntry<?>> > RULES_FACTORIES = new HashMap<>();
    public static <T extends SuccessionEntry<T>> void addRule(Class<T> clazz, T empty, TriFunction<Title<?>,T, HumanCharacter,T> factory){
        REGISTERED_SUCCESSION_RULES.put(clazz,empty);
        RULES_FACTORIES.put(clazz,(TriFunction<Title<?>, SuccessionEntry<?>, HumanCharacter,SuccessionEntry<?>>) factory);
    }
    public static <T extends SuccessionEntry<T>> T buildNew(Class<T> clazz, Title<?> title, HumanCharacter character){
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
