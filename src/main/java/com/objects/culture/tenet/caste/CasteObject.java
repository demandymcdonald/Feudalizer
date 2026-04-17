package com.objects.culture.tenet.caste;

import com.objects.culture.object.CultureObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.utilities.IDisplayable;
import com.utilities.id.StringIdentifiable;

public record CasteObject(TenetGroup group,String id, String name, String description, PoliticalCompass compass) implements StringIdentifiable, IDisplayable,CultureMutatedObject {
    public CasteObject(TenetGroup group, String id, String name, String description, PoliticalCompass compass) {
        this.group = group;
        this.id = id;
        this.name = name;
        this.description = description;
        this.compass = compass;
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

    @Override
    public PoliticalCompass getBaseCompass() {
        return compass;
    }

    @Override
    public Acceptance getAcceptance(MutableTenet t, CultureObject<?, ?, ?> c) {

    }
}
