package com.objects.title.house;

import com.base.reference.DMEReference;
import com.base.timeline.change.TimelineChange;
import com.objects.CauseOfEnd;
import com.objects.title.Title;
import com.objects.title.condition.CanHoldCondition;
import com.objects.title.condition.CanInheritCondition;
import com.objects.title.house.role.Retainer;

import java.time.LocalDate;
import java.util.*;

public class House extends Title<House> {


    public enum CharterType {
        Landed_Vassal,
        Franchise,
        Administrative,
        Household,
        Honorific;
    }
    public enum RetainerType {
        Bureaucrat,
        Military_Officer,
        Military_Elite,
        Intelligence_Officer,
        Executive_Staff,
        Chief_of_Staff,
        Deputy_Chief_Of
    }

    private String Name;
    private final Map<DMEReference<House>, CharterType> DirectHouses = new HashMap<>();
    private final Map<DMEReference<Retainer>,> Retainers = new HashMap<>();



    @Override
    protected int basePrestige() {
        return 0;
    }

    @Override
    protected List<CanHoldCondition<? super House>> getCanHoldConditions() {
        return List.of();
    }

    @Override
    protected List<CanInheritCondition<? super House>> getCanInheritConditions() {
        return List.of();
    }

    @Override
    public String getTitleName() {
        return "";
    }

    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<House> getBirthChange(DMEReference<House> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<House> getDeathChange(DMEReference<House> dme, LocalDate date, CauseOfEnd cOd) {
        return null;
    }

    @Override
    public CauseOfEnd defaultDeathCause() {
        return null;
    }

}
