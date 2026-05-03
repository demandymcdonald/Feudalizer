package com.objects.organization.religion;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;
import com.objects.culture.object.reference.COReference;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.group.groups.ReligionGroups;
import com.objects.culture.tenet.interest.IInterestGroup;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.religion.tenets.faith.Faith;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Religion extends DynamicTenet<Religion> implements IInterestGroup, IReligionObject {
    private final InterestGroup interestGroup;
    private DMEReference<Faith> faith;
    public Religion(DMEReference<Religion> dme) {
        super(ReligionGroups.RELIGION, dme);
        this.interestGroup = buildGroup();
    }
    public Religion(String name, LocalDate created, LocalDate ended, DMEReference<Faith> faith, DMEReference<Culture> founding, List<ChangeSupplier<Religion, ?>> initialState) {
        super(ReligionGroups.RELIGION, name, created, ended, founding,initialState);
        this.interestGroup = buildGroup();
        this.faith = faith;
        linkGraph(this,faith.get());
    }
    public Religion(String name, LocalDate created, LocalDate ended, DMEReference<Culture> founding, List<ChangeSupplier<Religion, ?>> initialState) {
        super(ReligionGroups.RELIGION, name, created, ended, founding,initialState);
        this.interestGroup = buildGroup();
        this.faith = new Faith();
        linkGraph(this,faith.get());
    }
    @Override
    protected void onLink() {

    }

    @Override
    public void updateProceduralInfluencers() {

    }

    @Override
    public double influencerResistance(COReference<?> influencer) {
        return 0;
    }

    @Override
    public void doDateChange() {

    }

    @Override
    public Set<TenetGroup> allowedTenets() {
        return Set.of();
    }

    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);
        data.add("re:faith", faith.serialize());
    }

    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);
        faith = DMEReference.deserialize(data.get("re:faith"));
        linkGraph(this,faith.get());
    }
    public DMEReference<Faith> getFaithRef(){
        return faith;
    }
    public Faith getFaith(){
        return faith.get();
    }

    @Override
    public InterestGroup getInterestGroup() {
        return interestGroup;
    }
    public InterestGroup buildGroup(){
        return
    }
    private static void linkGraph(Religion r, Faith f){
        f.getGraph().addVertex(r);
        f.getGraph().addEdge(r,f);
    }

    @Override
    public void internalSetCulture(DMEReference<Culture> culture) {

    }


    @Override
    public void getConditions(Set<CultureCondition<?, ?>> conditions) {
        return null;
    }

    @Override
    public AcceptanceContainer getAcceptanceObject(ICultureObject other, boolean includeInfluencers, boolean factorOtherTolerance) {
        return null;
    }
}
