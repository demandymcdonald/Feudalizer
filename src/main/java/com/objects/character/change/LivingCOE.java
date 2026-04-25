package com.objects.character.change;

import com.objects.CauseOfEnd;
import com.objects.character.LivingCreature;
import com.objects.character.sentient.SentientCharacter;

import static com.objects.CauseOfEnd.build;

public class LivingCOE {
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_DISEASE_CHILD = build("char_disease_child", "Disease (Child)", "Died from disease as a child");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_DISEASE_ADULT = build("char_disease_adult", "Disease (Adult)", "Died from disease as an adult");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_OLD_AGE = build("char_old_age", "Old Age", "Died of old age");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_ACCIDENT = build("char_accident", "Accident", "Died in an accident");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_COMBAT = build("char_combat", "Combat", "Died in combat");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_EXECUTION = build("char_execution", "Execution", "Died by execution");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_STARVATION = build("char_starvation", "Starvation", "Died from starvation");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_NATURAL_DISASTER = build("char_natural_disaster", "Natural Disaster", "Died in a natural disaster");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_POISONING = build("char_poisoning", "Poisoning", "Died from poisoning");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_ANIMAL_ATTACK = build("char_animal_attack", "Animal Attack", "Died from an animal attack");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_CHILD_BIRTH = build("char_child_birth", "Childbirth", "Died during childbirth");
    public static final CauseOfEnd<SentientCharacter<?>> CHARACTER_SUICIDE = build("char_suicide", "Suicide", "Died by suicide");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_FAMINE = build("char_famine", "Famine", "Died from famine");
    public static final CauseOfEnd<LivingCreature<?>> ERROR = build("char_error", "Error", "Unknown cause of death");
    public static final CauseOfEnd<LivingCreature<?>> CHARACTER_OTHER = build("char_other", "Other", "Died from other causes");
}
