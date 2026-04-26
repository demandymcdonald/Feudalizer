package com.objects.organization.government.rights;

import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.government.GoverningEntity;
import com.utilities.IDisplayable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Right implements IDisplayable {
    private final String id;
    private final String name;
    private final String description;
    public Right(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public abstract <G extends GoverningEntity<G>> IndividualRightInstance getRightFor(DMEReference<G> government, InterestGroup interestGroup);
    public <G extends GoverningEntity<G>> IndividualRightInstance getRightFor(DMEReference<G> government, DMEReference<? extends SentientCharacter<?>> character) {
        AtomicInteger currentLevel = new AtomicInteger(9999);
        AtomicReference<IndividualRightInstance> rightInstance = new AtomicReference<>();
        TenetManager.InterestGroups.getForCharacter(character).forEach(interestGroup -> {
            IndividualRightInstance currentRightInstance = getRightFor(government,interestGroup);
            int level = currentRightInstance.level().getLevel();
            if (level < currentLevel.get()) {
                currentLevel.set(level);
                rightInstance.set(currentRightInstance);
            }
        });
        return rightInstance.get();
    }
    @Override
    public String getDisplayID() {
        return id;
    }
    @Override
    public String getDisplayName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }
}
