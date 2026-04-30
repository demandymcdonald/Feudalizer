package com.objects.succession.plan;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import com.objects.succession.held.ICharacterHeld;
import com.objects.succession.change.SuccessionPlanChange;

import com.objects.succession.rules.RuleEntry;

import java.util.Set;

public class SuccessionPlan {
    private final DMEReference<? extends SentientCharacter<?>> subject;
    private TLSet<RuleEntry<?>> rules;

    public SuccessionPlan(DMEReference<? extends SentientCharacter<?>> subject){
        this.subject = subject;
    }
    public <T extends SentientCharacter<T>> SuccessionPlan(DMEReference<T> subject, Set<RuleEntry<?>> startingRules){
        this.subject = subject;
        subject.get().getTimeline().internalAddChange(new SuccessionPlanChange<>(subject, Global.getDate(), startingRules));
    }
    public <T extends DateMutableEntity<T> & ICharacterHeld<T>> RuleEntry<?> getRuleFor(DMEReference<T> title){
        int currentPriority = -99999;
        RuleEntry<?> currentRule = null;
        for(RuleEntry<?> rule : rules.asSet()){
            if (rule.hasScope() && !rule.getScope().contains(title)){
                continue;
            }
            if(rule.getBase().get().isAllowed(title) && currentPriority < rule.getPriority()){
                currentRule = rule;
            }
        }
        if (currentRule == null){
            return title.get().getGovernment().get().getDefaultRuleFor(title);
        }
        return currentRule;
    }
    public TLSet<RuleEntry<?>> getRules() {
        return rules;
    }
    public void internalSetRules(TLSet<RuleEntry<?>> rules){
        this.rules = rules;
    }


}
