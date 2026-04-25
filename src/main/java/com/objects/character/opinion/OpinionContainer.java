package com.objects.character.opinion;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.type.WipeType;
import com.base.timeline.change.multi.wrapper.TLSet;
import com.objects.character.LivingCreature;
import com.objects.character.change.LivingCreatureMapChanges;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class OpinionContainer {
    Map<UUID, Integer> opinionCache = new HashMap<>();
    TLSet<Opinion> opinions;

    public int getOpinion(DMEReference<? extends LivingCreature<?>> character){
        return opinionCache.computeIfAbsent(character.getID(), this::build);
    }
    public <T extends LivingCreature<T>> void addOpinion(Opinion opinion, DMEReference<T> us){
        Duration duration = opinion.getDuration().orElse(null);
        if(duration != null){
            LivingCreatureMapChanges.OpinionMapChange<T> change = new LivingCreatureMapChanges.OpinionMapChange<>(us, Global.getDate().plus(duration));
            change.internalAddRemoved(opinion.getID());
            us.get().getTimeline().internalAddChange(change);
        }
        if(opinion.getReciprocalMod() != 0){
            OpinionReason current = opinion.getReason();
            int newVal = (int) Math.round(current.value().get() * current.reciprocalMod());
            opinion.getTarget().get().addOpinion(new Opinion(opinion.getInstanceID(),us,
            new OpinionReason(current.internalID() + "rec", current.displayName(), current.description(),0D,current.duration(),newVal)));
        }
        opinions.add(false,false,opinion);
    }
    public <T extends LivingCreature<T>> void removeOpinion(Opinion opinion){
        opinions.remove(WipeType.BOTH,opinion);
        if(opinion.getReciprocalMod() != 0){
            opinion.getTarget().get().removeOpinion(opinion.getInstanceID());
        }
    }
    public void removeByID(UUID id){
        opinions.remove(WipeType.BOTH,opinions.getWhere((opinion) -> opinion.getInstanceID().equals(id)).toArray(new Opinion[0]));
    }
    private int build(UUID id){
        Set<Opinion> ops = opinions.getWhere((o) -> o.getTargetID().equals(id));
        int value = 0;
        for(Opinion opinion : ops){
            value += opinion.getValue();
        }
        return Math.clamp(value,-1000,1000);
    }
    public void internalSetOpinions(TLSet<Opinion> opinions){
        this.opinions = opinions;
    }
    public TLSet<Opinion> internalGetOpinions(){
        return opinions;
    }
    public void onLoad(){
        opinionCache.clear();
    }
}
