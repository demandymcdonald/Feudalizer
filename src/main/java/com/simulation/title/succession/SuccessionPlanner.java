package com.simulation.title.succession;

import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;
import com.simulation.title.succession.rules.SuccessionEntry;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SuccessionPlanner {

    public static HashSet<DMEReference<?>> executeSuccession(BookCharacter deceased, LocalDate date) {
        HashMultimap<UUID,Title<?>> assignedTitles = HashMultimap.create();

        for (Title<?> title : deceased.getTitles()) {
            SuccessionEntry<?> sc = title.getSuccession().getEntry(date);
            List<BookCharacter> generateLoS = sc.getLoSFull();
        }
    }




}
