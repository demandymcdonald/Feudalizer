package com.objects.culture.tenet;

import com.utilities.Displayable;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;

public enum TenetGroup implements Displayable {
    RELIGION("religion", "Religion", "A religion is a set of beliefs, ideals, and values that govern a particular culture or society.", new AcceptanceContainer(Tenet.Acceptance.CORE,1)),
    RELIGIOUS_TRADITION_RITUAL(RELIGION,"religious_tradition_ritual", "Religious Tradition/Ritual", "A religious tradition or ritual is a cultural practice that is passed down through generations."),
    RELIGIOUS_IDEOLOGY(RELIGION,"religious_ideology", "Religious Ideology", "A religious ideology is a set of beliefs, policies, and values that are valued or discouraged by a culture."),
    RELIGIOUS_VALUE(RELIGION,"religious", "Religious Value", "A religious value is a belief, principle, or standard that a culture reinforces or discourages."),
    GOVERNMENT_IDEOLOGY("government_ideology", "Government Ideology", "A government ideology is a set of beliefs, policies, and values that are valued or discouraged by nations of this culture."),
    SECULARISM(GOVERNMENT_IDEOLOGY,"secularism", "Secular", "Secularism is the way in which a state or culture handles/treats religion.", new AcceptanceContainer(Tenet.Acceptance.CORE,1)),
    CASTE_IDEOLOGY(GOVERNMENT_IDEOLOGY,"caste", "Caste Ideology", "The values that govern the permeate and strictness of caste/class systems in a society."),
    ECONOMIC_VALUE(GOVERNMENT_IDEOLOGY,"economic", "Economic Value", "An economic value is a belief, principle, or standard that a culture holds dear or discourages."),
    CULTURE_IDEOLOGY("culture_ideology", "Culture Ideology", "A culture ideology is a set of beliefs, policies, and values that are valued or discouraged by a culture."),
    PERSONAL_VALUE(CULTURE_IDEOLOGY,"personal", "Personal Value", "A personal value is a belief, principle, or standard that a culture reinforces or discourages."),
    SOCIAL_VALUE(CULTURE_IDEOLOGY,"social", "Social Justice Value", "A social justice value is a belief, principle, or standard that is tied to personal identity which a culture holds dear or discourages."),
    TRADITION_RITUAL(CULTURE_IDEOLOGY,"tradition_ritual", "Tradition/Ritual", "A tradition or ritual is a cultural practice that is passed down through generations."),
    RACE(CULTURE_IDEOLOGY,"race", "Race", "A race is a group of people that share a common set of characteristics."),
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
    public record AcceptanceContainer(Tenet.Acceptance accept, int maxNumber){}
}
