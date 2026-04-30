package com.objects.organization.government.rights;

import com.base.component.InstanceType;
import com.base.component.instanced.bi.IOBi;
import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.TenetManager;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.government.GoverningEntity;
import com.utilities.IDisplayable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Right<T extends Right<T>> extends IOBi<T,RightInstance<T>, DMEReference<? extends GoverningEntity<?>>,DMEReference<? extends SentientCharacter<?>>> implements IDisplayable {
    private final String id;
    private final String name;
    private final String description;
    public Right(InstanceType type, String id, String name, String description) {
        super(type,id);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public final String getDisplayID() {
        return id;
    }
    @Override
    public final String getDisplayName() {
        return name;
    }
    @Override
    public final String getDescription() {
        return description;
    }
    protected static <T extends Right<T>> RightLevel getRightLevel(
            T right,
            DMEReference<? extends GoverningEntity<?>> government,
            DMEReference<? extends SentientCharacter<?>> character){

    }

}
