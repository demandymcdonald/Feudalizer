package com.objects.culture.object;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.multi.type.ChangeType;
import com.base.datemutable.timeline.change.multi.type.WipeType;
import com.base.datemutable.timeline.change.multi.wrapper.TLMap;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.objects.culture.Culture;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.Tenet;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;

public interface CultureObject<T extends DateMutableEntity<T> & CultureObject<T>> extends ICultureObject{

    CultureObjectContainer<T> getContainer();

    @Override
    default AcceptanceContainer getAcceptanceTenet(TenetReference tenet, boolean includeInfluencers){
        return getContainer().getAcceptanceTenet(tenet,includeInfluencers);
    };
    default AcceptanceContainer getAcceptanceTenet(TenetReference tenet, boolean includeInfluencers, Set<COReference<?>> blacklist){
        return getContainer().getAcceptanceTenet(tenet,includeInfluencers,blacklist);
    }
    void updateProceduralInfluencers(); //Idea here is that the code can automatically add and remove procedural influencers (like dead people, or new lieges)
    double influencerResistance(COReference<?> influencer); // clamped between -1 and 1. Used to model things like personal opinion of an influencer for people -> people influencer,
    // or situations where a person might have less influence than the relationship suggests (power imbalance, for example).
    DMEReference<T> getReference();
    default COReference<T> getTOReference(){
        //Weird, I know. All TenetOpinionated are DMEs, so if this class is running, it's on a DME, and the reference
        // will work becuase TOReferences are just janky wrappers for DMEReferences.
        return new COReference<>(getReference());
    }

    default void doDateChange(){
        getContainer().invalidateCache();
    }

    default void amendCompass(IPoliticalCompass values){
        getContainer().amendCompass(values);
    }
    default void amendOpinion(TenetReference tenet, double d){
        getContainer().amendOpinion(tenet,true,d);
    }
    default void amendOpinion(TenetReference tenet, boolean adjustForward, double d){
        getContainer().amendOpinion(tenet,adjustForward,d);
    }
    default void setOpinion(TenetReference tenet,boolean adjustForward, double d){
        getContainer().setOpinion(tenet,adjustForward,d);
    }
    default void setOpinion(TenetReference tenet, double d){
        getContainer().setOpinion(tenet,true,d);
    }
    default void insertOpinion(TenetReference tenet, double d){
        getContainer().insertNewOpinion(tenet,d);
    }

    default boolean isInfluencer(COReference<?> ref){
        return getInfluencers().containsKey(ref);
    }
    default void addInfluencer(COReference<?> influencer, InfluencerRelationship relationship, boolean isProcedural){
        getInfluencers().put(influencer,new InfluencerInstance(relationship,isProcedural));

    }
    default void setInfluence(COReference<?> influencer, boolean doWipe, Pair<TenetGroup,Integer>... changes){
        boolean changed = false;
        List<BiConsumer<COReference<?>,InfluencerInstance>> list = new ArrayList<>();
        for (Pair<TenetGroup, Integer> change : changes) {
            if (change.getLeft() == null || (change.getRight() == null)){
                continue;
            }
            changed = true;
            BiConsumer<COReference<?>,InfluencerInstance> consumer = (t,i) -> i.set(change.getLeft(),change.getRight());
            list.add(consumer);
        }
        pushInfluenceChange(influencer, doWipe, changed, list);
    }

    default void modifyInfluence(COReference<?> influencer,boolean doWipe, Pair<TenetGroup,Integer>... changes){
        boolean changed = false;
        List<BiConsumer<COReference<?>,InfluencerInstance>> list = new ArrayList<>();
        for (Pair<TenetGroup, Integer> change : changes) {
            if (change.getLeft() == null || (change.getRight() == null || change.getRight() == 0)){
                continue;
            }
            changed = true;
            BiConsumer<COReference<?>,InfluencerInstance> consumer = (t,i) -> i.modify(change.getLeft(),change.getRight());
            list.add(consumer);
        }
        pushInfluenceChange(influencer, doWipe, changed, list);
    }

    private void pushInfluenceChange(COReference<?> influencer, boolean doWipe, boolean changed, List<BiConsumer<COReference<?>, InfluencerInstance>> list) {
        if (changed){
            BiConsumer<COReference<?>,InfluencerInstance> consumer = (t,i) -> list.forEach(c -> c.accept(t,i));
            getInfluencers().setChanged(doWipe, ChangeType.BOTH, Map.of(influencer,consumer));
        }
    }

    default void removeInfluencer(COReference<?> influencer){
        if (influencer == null){
            return;
        }
        getInfluencers().remove(WipeType.FORWARD,influencer);
    }
    default Set<TenetInstance<T>> getOpinionByGroup(TenetGroup group, boolean includeDescendants, boolean activeOnly){
        return getContainer().getByGroup(group,includeDescendants);
    }
    default boolean isMainstream(Tenet tenet, boolean includeInfluencers){
        return isMainstream(TenetReference.of(tenet),includeInfluencers);
    }
    default boolean isMainstream(TenetReference tenet, boolean includeInfluencers){
        return getTenetsByThreshold(Acceptance.INTEGRATED,includeInfluencers).stream().anyMatch(t -> t.getTenet().equals(tenet));
    }



    default TLSet<TenetInstance<T>> getOpinions(){
        return getContainer().getOpinions();
    };
    default Set<TenetInstance<T>> getActiveTenets(){
        return getOpinions().getWhere( (ti) -> {
            return ti.isActive();
        });
    }
    default Set<TenetInstance<T>> getTenetsByThreshold(Acceptance acceptance, boolean includeInfluencers){
        return getContainer().getTenetsByThreshold(acceptance,includeInfluencers);
    }
    void internalSetCulture(DMEReference<Culture> culture);
    default void internalSetOpinions(TLSet<TenetInstance<T>> opinions){
        getContainer().internalSetOpinions(opinions);
    };
    default Optional<Pair<COReference<?>, InfluencerInstance>> getParentObject(){
        return Optional.ofNullable(getContainer().getParent());
    }; //todo, implement opinion crushing for Hegamon.
    default void setParentObject(COReference<?> influencer, InfluencerRelationship relationship){

    };
    default void internalParentObject(COReference<?> influencer, InfluencerRelationship relationship){
        getContainer().setParent(influencer, relationship);
    };
    default TLMap<COReference<?>,InfluencerInstance> getInfluencers(){
        return getContainer().getInfluencers();
    };
    default void internalSetInfluencers(TLMap<COReference<?>,InfluencerInstance> influencers){
        getContainer().setInfluencers(influencers);
    };

    default void internalSetCompass(InterpolatedPoliticalCompass<?> compass){
        getContainer().setCompass((InterpolatedPoliticalCompass<T>) compass);
    };
    default InterpolatedPoliticalCompass<T> getCompass(){
        return getContainer().getCompass();
    };

    static <T extends DateMutableEntity<T> & CultureObject<T>> boolean matchesGroup(TenetInstance<T> instance, TenetGroup group, boolean includeDescendents){
        final TenetGroup checkGroup = instance.getTenet().getGroup();
        return checkGroup.equals(group) || (includeDescendents && group.isAncestorOf(checkGroup));
    }
}
