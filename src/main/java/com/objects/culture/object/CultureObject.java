package com.objects.culture.object;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.multi.type.ChangeType;
import com.base.timeline.change.multi.type.WipeType;
import com.base.timeline.change.multi.wrapper.TLMap;
import com.base.utilities.TLSyncedCache;
import com.objects.culture.Culture;
import com.objects.culture.Influencers.InfluencerInstance;
import com.objects.culture.Influencers.InfluencerRelationship;
import com.objects.culture.object.compass.InterpolatedPoliticalCompass;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.Acceptance;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.object.compass.CompassChange;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.Tenet;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;

public interface CultureObject<T extends DateMutableEntity<T> & CultureObject<T>> extends ICultureObject{

    CultureObjectContainer<T> getContainer();

    @Override
    default AcceptanceContainer getAcceptanceContainer(TenetReference tenet, boolean includeInfluencers){
        return new AcceptanceContainer(getAcceptanceValue(tenet,includeInfluencers));
    };

    void updateProceduralInfluencers(); //Idea here is that the code can automatically add and remove procedural influencers (like dead people, or new lieges)
    double influencerResistance(COReference<?> influencer); // clamped between -1 and 1. Used to model things like personal opinion of an influencer for people -> people influencer,
    // or situations where a person might have less influence than the relationship suggests (power imbalance, for example).
    DMEReference<T> getReference();
    default COReference<T> getTOReference(){
        //Weird, I know. All TenetOpinionated are DMEs, so if this class is running, it's on a DME, and the reference
        // will work becuase TOReferences are just janky wrappers for DMEReferences.
        return new COReference<>(getReference());
    }
    default Acceptance getAcceptance(Tenet tenet, boolean includeInfluencers){
        return Acceptance.get((int) Math.round(getAcceptanceValue(tenet, includeInfluencers)));
    }
    default double getAcceptanceValue(Tenet tenet, boolean includeInfluencers){
        return getAcceptanceValue(tenet, includeInfluencers, new COReference[0]);
    }


    default void amendCompass(Pair<IPoliticalCompass.Axis,Integer>... values){
        InterpolatedPoliticalCompass<?> compass = getCompass();
        boolean changed = false;
        for (Pair<IPoliticalCompass.Axis,Integer> pair : values) {
            if (pair.getLeft() == null || (pair.getRight() == null || pair.getRight() == 0)){
                continue;
            }
            changed = true;
            compass.amendCompass(pair.getLeft(),pair.getRight());
        }
        if (changed){
            //DMEReference<? extends T> owner = ;
            getReference().get().getTimeline().addChange(new CompassChange<>(getReference(), Global.getDate(),getCompass()));
            invalidateCache();
        }
    }
    default AcceptanceContainer getOpinion(TenetReference tenet, boolean includeInfluencers, Set<COReference<?>> blacklist){
        return getContainer().getOpinionTenet(tenet,includeInfluencers,blacklist);
    }
    default void addOpinion(TenetReference tenet, double d){
        addOpinion(tenet,false,d);
    }
    default void addOpinion(TenetReference tenet, boolean wipe, double d){
        TLMap<TenetReference,TenetInstance<T>> opinions = getOpinions();
        if (opinions.containsKey(tenet)){
            BiConsumer<TenetReference,TenetInstance<T>> consumer = (t,i) -> i.add(d);
            opinions.setChanged(wipe, ChangeType.VALUE,Map.of(tenet,consumer));
            invalidateCache(tenet);
        }else {
            opinions.put(tenet,new TenetInstance<>(tenet,this.getReference(),d));
        }
    }
    default void setOpinion(TenetReference tenet, double d){
        setOpinion(tenet,false,d);
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
    default Set<TenetInstance<T>> getOpinionByGroup(TenetGroup group, boolean includeDescendants){
        return getContainer().getByGroup(group,includeDescendants);
    }
    default boolean isMainstream(Tenet tenet, boolean includeInfluencers){
        return isMainstream(TenetReference.of(tenet),includeInfluencers);
    }
    default boolean isMainstream(TenetReference tenet, boolean includeInfluencers){
        return getTenetsByThreshold(Acceptance.INTEGRATED,includeInfluencers).containsKey(tenet);
    }




    default TLSyncedCache<TenetReference,Double> getInfluencedCache(){
        return getContainer().getInfluencedCache();
    };
    default TLMap<TenetReference,TenetInstance<T>> getOpinions(){
        return getContainer().getOpinions();
    };
    default Map<TenetReference,TenetInstance<T>> getActiveTenets(){
        return getOpinions().getWhere((tr, ti) -> {
            return ti.isActive();
        });
    }
    default Map<TenetReference,TenetInstance<T>> getTenetsByThreshold(Acceptance acceptance, boolean includeInfluencers){
        int floor = acceptance.getValue();
        return getOpinions().getWhere((tr, ti) -> {
            return getAcceptanceValue(tr,includeInfluencers) >= floor;
        });
    }
    void internalSetCulture(DMEReference<Culture> culture);
    default void internalSetOpinions(TLMap<TenetReference,TenetInstance<T>> opinions){
        getContainer().setOpinions(opinions);
    };
    default Optional<Pair<COReference<?>, InfluencerRelationship>> getParentObject(){
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


}
