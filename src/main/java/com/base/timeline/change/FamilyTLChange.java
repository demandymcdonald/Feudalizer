package com.base.timeline.change;

import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.people.Family;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class FamilyTLChange extends TimelineChange<Family> {
    List<DMEReference<BookCharacter>> involved = new ArrayList<>();


    protected FamilyTLChange(DMEReference<Family> owner, LocalDate date, DMEReference<BookCharacter>... involves) {
        super(owner, date);
        for (DMEReference<BookCharacter> ref : involves) {
            involved.add(ref);
        }
    }
    protected FamilyTLChange(DMEReference<Family> owner, LocalDate date) {
        super(owner, date);
    }
    @Override
    public HashSet<DMEReference<?>> getScope() {
        HashSet<DMEReference<?>> toReturn = new HashSet<>();
        toReturn.addAll(getOwner().link().getMembers());
        return toReturn;
    }

    @Override
    public JsonObject onLoad(JsonObject data) {
        return null;
    }

    @Override
    public void saveAdditional(JsonObject data) {
        super
    }
}
