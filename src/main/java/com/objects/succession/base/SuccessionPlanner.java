package com.objects.succession.base;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.error.SandboxCode;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.sandbox.function.SandboxFunctions;
import com.base.datemutable.timeline.sandbox.core.Objective;
import com.base.datemutable.timeline.sandbox.core.Sandbox;
import com.base.datemutable.timeline.sandbox.core.SandboxHandler;
import com.objects.character.sentient.SentientCharacter;
import com.objects.succession.held.HolderChanges;
import com.objects.succession.held.ICharacterHeld;
import com.objects.title.Title;
import com.objects.succession.plan.SuccessionPlan;
import com.objects.succession.rules.RuleEntry;

import java.time.LocalDate;
import java.util.*;

public class SuccessionPlanner {

    public static <T extends SentientCharacter<T>> SandboxCode run(Sandbox<T> sandbox, DMEReference<T> deceased, LocalDate date) {
        Deque<DMEReference<? extends Title<?>>> titles = buildTitleQueue(deceased);
        SuccessionPlan plan = deceased.get().getSuccession();
        final Set<DMEReference<? extends SentientCharacter<?>>> blacklist = new HashSet<>();
        while (!titles.isEmpty()){
            DMEReference<? extends Title<?>> title = titles.pollFirst();
            SandboxCode code = getSuccession(sandbox, plan, deceased, title, date, blacklist);
            if (code == SandboxCode.END_SUCCESSION_PLANNING || code == SandboxCode.CRITICAL_ERROR) {
                return code;
            }
        }
        return SandboxCode.END_SAVE;
    }
    public static <T extends DateMutableEntity<T> & ICharacterHeld<T>,S extends SentientCharacter<S>> SandboxCode getSuccession
            (Sandbox<S> sandbox, SuccessionPlan plan, DMEReference<S> deceased, DMEReference<? extends Title<?>> rawTitle,
             LocalDate date, Set<DMEReference<? extends SentientCharacter<?>>> blacklist) {
        final DMEReference<T> title = (DMEReference<T>) rawTitle;
        RuleEntry<?> rule = plan.getRuleFor(title.get().getReference());
        LinkedHashSet<DMEReference<? extends SentientCharacter<?>>> list = rule.getLoS(sandbox,title,deceased,date,blacklist);
        while (!list.isEmpty()) {
            SandboxCode code = executeSuccessionSandbox(sandbox, date, title.get().getEnded(), title, list.getFirst().get().getReference());
            switch (code){
                case END_SAVE -> {
                    if (rule.getBase().get().shouldRemoveAfterInherit()){
                        blacklist.add(list.getFirst());
                    }
                    list.removeFirst();
                    return SandboxCode.END_SAVE;
                }
                case CRITICAL_ERROR,END_SUCCESSION_PLANNING -> {
                    return code;
                }
                case SUCCESSION_NEXT_HEIR, END_DISCARD -> {
                    list.removeFirst();
                }
            }
        }
        return SandboxCode.CRITICAL_ERROR;
    }
    private static Deque<DMEReference<? extends Title<?>>> buildTitleQueue(DMEReference<? extends SentientCharacter<?>> deceased){
        List<DMEReference<? extends Title<?>>> preTitle = new ArrayList<>(deceased.get().getTitles());
        preTitle.sort(Comparator.comparingInt((DMEReference<? extends Title<?>> o) -> o.get().getPrestige()).reversed());
        return new ArrayDeque<>(preTitle);
    }
    protected static <T extends DateMutableEntity<T> & ICharacterHeld<T>,S extends SentientCharacter<S>,SH extends SentientCharacter<SH>> SandboxCode executeSuccessionSandbox(Sandbox<SH> sandbox, LocalDate startDate, LocalDate endDate,
                                                                                                                                                                               DMEReference<T> titleRef, DMEReference<S> character){

        SandboxHandler<T> handler = SandboxHandler.StartSandbox(new Objective<>(titleRef, Global.TimeDirection.FORWARD
                ,new HolderChanges.Inherit<T>(titleRef,startDate,character), new SandboxFunctions.CanAddChange<T>()),
                endDate,sandbox.getHandler(),null);
        handler.startSandbox();
        return handler.getEndCode().join();
    }
}
