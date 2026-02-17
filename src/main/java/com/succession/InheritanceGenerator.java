package com.succession;
import com.people.Character;
import java.util.List;

public abstract class InheritanceGenerator {
    public abstract InheritancePlans.InheritancePlan generateInheritancePlan(Character character);
    public abstract InheritancePlans.InheritancePlan generateInheritancePlan(Title<?> title, boolean isPrimary, Character character);
    public abstract InheritancePlans.InheritancePlan generateInheritancePlan(Title<?> title, boolean isPrimary, List<Character> characters);
    public abstract SuccessionContainers.SuccessionContainer generateBaseSuccession(Title<?> title, boolean isPrimary, List<Character> characters);
}
