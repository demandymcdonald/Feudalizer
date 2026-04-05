package com.objects.title.succession;

import com.Global;
import com.base.timeline.error.SandboxCode;
import com.base.reference.DMEReference;
import com.base.timeline.sandbox.check.SandboxFunctions;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.sandbox.core.SandboxHandler;
import com.objects.character.BookCharacter;
import com.objects.title.Title;
import com.objects.title.change.TitleSingletonChange;
import com.objects.title.succession.rules.SuccessionEntry;

import java.time.LocalDate;
import java.util.*;

public class SuccessionPlanner {

    public static SandboxCode run(Sandbox<?> sandbox, BookCharacter deceased, LocalDate date) {
        List<DMEReference<? extends Title<?>>> preTitle = new ArrayList<>(deceased.getTitles());
        preTitle.sort(Comparator.comparingInt((DMEReference<? extends Title<?>> o) -> o.get().getPrestige()).reversed());
        Deque<DMEReference<? extends Title<?>>> titles = new ArrayDeque<>(preTitle);
        int next = 0;
        while (!titles.isEmpty()){
            DMEReference<? extends Title<?>> title = titles.pollFirst();
            List<BookCharacter> generateLoS = findLOS(date,title);
            BookCharacter first = generateLoS.get(next);
            SandboxCode code = executeSuccessionSandbox(sandbox,date,title.get().getEnded(),title,first.getReference());
            switch (code){
                case END_SAVE -> {
                    next = 0;
                }
                case END_DISCARD, CRITICAL_ERROR -> {
                    return code;
                }
                case SUCCESSION_NEXT_HEIR -> {
                    next++;
                    titles.addFirst(title);
                }
            }
        }
        return SandboxCode.END_SAVE;
    }

    private static <T extends Title<T>> List<BookCharacter> findLOS(LocalDate date, DMEReference<? extends Title<?>> titleRef){
        if (titleRef == null){
            throw new IllegalArgumentException("Cannot find LOS for null title");
        }
        T title = (T) titleRef.get();
        List<BookCharacter> LoS = new ArrayList<>();
        Title<?> current = title;
        while (LoS.isEmpty() && current != null){
            SuccessionEntry<?> sc = current.getSuccession(date);
            LoS.addAll(sc.getLoSFull(titleRef));
            Optional<DMEReference<? extends Title<?>>> ot = current.getParent();
            current = ot.<Title<?>>map(DMEReference::get).orElse(null);
        }
        if (LoS.isEmpty()){
            throw new IllegalStateException("Could not find LOS for " + title.getTitleName());
        }
        return LoS;
    }
    protected static <T extends Title<T>> SandboxCode executeSuccessionSandbox(Sandbox<?> sandbox, LocalDate startDate, LocalDate endDate,
          DMEReference<? extends Title<?>> titleRef, DMEReference<? extends BookCharacter> character){

        DMEReference<T> titT = (DMEReference<T>) titleRef;
        SandboxHandler<T> handler = SandboxHandler.StartSandbox(new Objective<>(titT, Global.TimeDirection.FORWARD
                ,new TitleSingletonChange.setHolderInherit<T>(titT,startDate,character), new SandboxFunctions.canAddChange<T>()),
                endDate,sandbox.getHandler(),null);
        handler.startSandbox();
        return handler.getEndCode().join();
    }

}
