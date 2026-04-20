package com.objects.culture.tenet.dynamic.tenets;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.Culture;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.groups.ReligionGroups;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.objects.culture.tenet.interest.IInterestGroup;
import com.objects.culture.tenet.interest.InterestGroup;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Religion extends DynamicTenet<Religion> implements IInterestGroup {
    private final InterestGroup interestGroup;
    public Religion(DMEReference<Religion> dme) {
        super(ReligionGroups.RELIGION, dme);
        this.interestGroup = buildGroup();
    }
    public Religion(String name, LocalDate created, LocalDate ended, DMEReference<Culture> founding, List<ChangeSupplier<Religion, ?>> initialState) {
        super(ReligionGroups.RELIGION, name, created, ended, founding,initialState);
        this.interestGroup = buildGroup();
    }
    @Override
    protected void onLink() {

    }

    @Override
    public void doDateChange() {

    }





    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);

    }

    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);

    }


    @Override
    public InterestGroup getInterestGroup() {
        return interestGroup;
    }
    public InterestGroup buildGroup(){
        return new InterestGroup(getDisplayID(),getPlural(),getDescription()) {
            @Override
            public <C extends SentientCharacter<C>> boolean isMember(C character) {
                return character.getReligion().equals(Religion.this);
            }
            @Override
            public TenetGroup getRightsGroup() {
                return GovernmentGroups.RELIGIOUS;
            }
            @Override
            public TenetGroup getSocialStatusGroup() {
                return SocietyGroups.RELIGIOUS;
            }
            @Override
            public Dimension getDimension() {
                return Dimension.Religion;
            }
        };
    }


}
