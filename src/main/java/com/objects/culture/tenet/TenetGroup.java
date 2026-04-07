package com.objects.culture.tenet;

import com.utilities.Displayable;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;

public enum TenetGroup implements Displayable {
    THE_CULTURE("the_culture", "The Culture", "The Culture is the set of beliefs, ideals, and values that govern a particular culture or society."),
    RELIGION(THE_CULTURE,"religion", "Religion", "A religion is a set of beliefs, ideals, and values that govern a particular culture or society.", new AcceptanceContainer(Acceptance.CORE,1)),
    RELIGIOUS_TRADITION_RITUAL(RELIGION,"religious_tradition_ritual", "Religious Tradition/Ritual", "A religious tradition or ritual is a cultural practice that is passed down through generations."),
    RELIGIOUS_IDEOLOGY(RELIGION,"religious_ideology", "Religious Ideology", "A religious ideology is a set of beliefs, policies, and values that are valued or discouraged by a culture."),
    RELIGIOUS_VALUE(RELIGION,"religious", "Religious Value", "A religious value is a belief, principle, or standard that a culture reinforces or discourages."),
    GOVERNMENT_IDEOLOGY(THE_CULTURE,"government", "Government Ideology", "A government ideology is a set of beliefs, policies, and values that are valued or discouraged by nations of this culture.", new AcceptanceContainer(Acceptance.CORE,1)),
    GOVERNMENT_SYSTEM(GOVERNMENT_IDEOLOGY,"system", "Government System", "A government system is a set of laws and regulations that govern the activities of a government.", new AcceptanceContainer(Acceptance.CORE,1)),
    SECULARISM(GOVERNMENT_SYSTEM,"secularism", "Secular", "Secularism is the way in which a state or culture handles/treats religion.", new AcceptanceContainer(Acceptance.CORE,1)),
    CASTE_SYSTEM(GOVERNMENT_SYSTEM,"caste", "Caste/Class System", "The values that govern the permeate and strictness of caste/class systems in a society.", new AcceptanceContainer(Acceptance.CORE,1)),
    JUSTICE_SYSTEM(GOVERNMENT_SYSTEM,"justice_system", "Justice System", "A justice system is a system of laws and regulations that govern the rights and obligations of individuals."),
    WELFARE(GOVERNMENT_SYSTEM,"welfare", "Welfare", "A welfare system is a system of laws and regulations that govern the rights and obligations of individuals."),
    INDIVIDUAL_RIGHTS(GOVERNMENT_SYSTEM,"individual_rights", "Individual Rights", "Individual rights are the rights that a culture recognizes and protects for its citizens."),
    CITIZEN_RIGHTS(INDIVIDUAL_RIGHTS,"citizen_rights", "Citizen Rights", "Citizen rights are the rights that a culture recognizes and protects for its citizens."),
    ELITE_RIGHTS(INDIVIDUAL_RIGHTS,"elite_rights", "Elite Rights", "Elite rights are the rights that a culture recognizes and protects for its elite."),
    OUTSIDER_RIGHTS(INDIVIDUAL_RIGHTS,"outsider_rights", "Outsider Rights", "Outsider rights are the rights that a culture recognizes and protects for its outsiders."),
    DISENFRANCHISED_RIGHTS(INDIVIDUAL_RIGHTS,"disenfranchised_rights", "Disenfranchised Rights", "Disenfranchised rights are the rights that a culture recognizes and protects for its disenfranchised."),
    GOVERNMENT_VALUE(GOVERNMENT_IDEOLOGY,"government", "Government Value", "A government value is a belief, principle, or standard that a culture reinforces or discourages."),
    ECONOMIC_IDEOLOGY(GOVERNMENT_IDEOLOGY,"economic_ideology", "Economic System", "An economic ideology is a set of beliefs, policies, and values that are valued or discouraged by a culture.", new AcceptanceContainer(Acceptance.CORE,1)),
    ECONOMIC_VALUE(ECONOMIC_IDEOLOGY,"economic", "Economic Value", "An economic value is a belief, principle, or standard that a culture holds dear or discourages."),
    CULTURE_IDEOLOGY(THE_CULTURE,"culture_ideology", "Soft Culture", "A culture ideology is a set of beliefs, policies, and values that are valued or discouraged by a culture."),
    PERSONAL_VALUE(CULTURE_IDEOLOGY,"personal", "Personal Value", "A personal value is a belief, principle, or standard that a culture reinforces or discourages."),
    SOCIAL_VALUE(CULTURE_IDEOLOGY,"social", "Social Justice Value", "A social justice value is a belief, principle, or standard that is tied to personal identity which a culture holds dear or discourages."),
    TRADITION_RITUAL(CULTURE_IDEOLOGY,"tradition_ritual", "Tradition/Ritual", "A tradition or ritual is a cultural practice that is passed down through generations."),
    RACE(CULTURE_IDEOLOGY,"race", "Race & Ethnic Origin", "A race is a group of people that share a common set of characteristics.", new AcceptanceContainer(Acceptance.CORE,1)),
    ;
    private final Optional<TenetGroup> parent;
    private final String id;
    private final String name;
    private final String description;
    private final AcceptanceContainer[] isExclusive;

    TenetGroup(@Nullable TenetGroup parent, String id, String name, String description, AcceptanceContainer... isExclusive) {
        this.parent = Optional.ofNullable(parent);
        if(parent != null){
            this.id = parent.getID() + ":" + id;
        } else {
            this.id = id;
        }
        this.name = name;
        this.description = description;
        this.isExclusive = isExclusive;
    }
    TenetGroup(String id, String name, String description, AcceptanceContainer... isExclusive) {
        this(null, id, name, description,isExclusive);
    }
    TenetGroup(String id, String name, String description) {
        this(null, id, name, description);
    }


    @Override
    public String getID() {
        return id;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }
    public Optional<TenetGroup> getParent(){
        return parent;
    }
    public record AcceptanceContainer(Acceptance accept, int maxNumber){}
}
