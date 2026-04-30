package com;

import com.objects.character.sentient.HumanCharacter;
import com.objects.title.Title;
import com.objects.succession.base.CommonLawEntry;
import com.objects.succession.base.CustomEntry;
import com.objects.succession.SuccessionPlan;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;

public class TypedSerialized {
    private static final HashMap<Class<? extends SuccessionPlan<?>>, SuccessionPlan<?>> REGISTERED_SUCCESSION_RULES = new HashMap<>();
    private static final HashMap<Class<? extends SuccessionPlan<?>>, TriFunction<Title<?>, SuccessionPlan<?>, HumanCharacter, SuccessionPlan<?>> > RULES_FACTORIES = new HashMap<>();
    public static <T extends SuccessionPlan<T>> void addRule(Class<T> clazz, T empty, TriFunction<Title<?>,T, HumanCharacter,T> factory){
        REGISTERED_SUCCESSION_RULES.put(clazz,empty);
        RULES_FACTORIES.put(clazz,(TriFunction<Title<?>, SuccessionPlan<?>, HumanCharacter, SuccessionPlan<?>>) factory);
    }
    public static <T extends SuccessionPlan<T>> T buildNew(Class<T> clazz, Title<?> title, HumanCharacter character){
        return (T) RULES_FACTORIES.get(clazz).apply(title,REGISTERED_SUCCESSION_RULES.get(clazz),character);
    }
    static {
        REGISTERED_SUCCESSION_RULES.put(CustomEntry.class,new CustomEntry());
        REGISTERED_SUCCESSION_RULES.put(CommonLawEntry.class,new CommonLawEntry());
    }
    public static HashMap<Class<? extends SuccessionPlan<?>>, SuccessionPlan<?>> getRegisteredSuccessionRules() {
        return REGISTERED_SUCCESSION_RULES;
    }
    public static void init(){
        Feudalizer.LOGGER.info("Initialized Succession Rules.. Count: " + REGISTERED_SUCCESSION_RULES.size());
    }
}
