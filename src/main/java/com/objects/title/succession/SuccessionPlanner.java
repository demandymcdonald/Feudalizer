package com.objects.title.succession;

import com.base.timeline.flags.SandboxCode;
import com.base.reference.DMEReference;
import com.google.common.collect.HashMultimap;
import com.objects.character.BookCharacter;
import com.objects.title.Title;
import com.objects.title.succession.rules.SuccessionEntry;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SuccessionPlanner {

    public static HashSet<DMEReference<?>> executeSuccession(BookCharacter deceased, LocalDate date) {
        HashMultimap<UUID,Title<?>> assignedTitles = HashMultimap.create();

        for (Title<?> title : deceased.getTitles()) {
            SuccessionEntry<?> sc = title.getSuccession().getEntry(date);
            List<BookCharacter> generateLoS = sc.getLoSFull();
            if (generateLoS.isEmpty()){
                generateLoS.add(title.getParent().orElseThrow().getHolder().orElseThrow());
                //TODO flesh out, probably keep climbing until a person can hold it or there's nobody left lol.
            }
            BookCharacter first = generateLoS.get(0);
            title.setInherit(first, date,true);
            assignedTitles.put(first.getId(),title);
        }
    }
    public static SandboxCode executeSuccessionSandbox(BookCharacter deceased, LocalDate date){
        HashSet<DMEReference<?>> toReturn = executeSuccession(deceased,date);
        if (toReturn.isEmpty()){
            return SandboxCode.END_DISCARD;
        } else {
            return SandboxCode.CONTINUE;
        }
    }
    public static <T extends Title<T>> void cleanUpContainer(T title, LocalDate date){
        LocalDate current = date;
        SuccessionContainer container = title.getSuccession();
        while (current.isBefore(title.getEnded())){
            if ()
        }
    }




}
