package com.objects.culture.tenet.factory;

import com.base.component.ComponentReference;
import com.base.component.InstanceType;
import com.google.gson.JsonObject;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.culture.tenet.mutable.augments.IRightsTenet;
import com.objects.organization.government.rights.Right;
import com.objects.organization.government.rights.RightLevel;

import java.util.*;



public abstract class RightTenets<R extends Right<R>> extends MutableTenet implements IRightsTenet<R>{
    private InterestGroup interestGroup;
    private R right;

    public RightTenets(InstanceType type, String id) {
        super(type, id);
    }

    public RightTenets(TenetReference parent, TenetGroup group, R right, PoliticalCompass compass, InterestGroup interestGroup, String id, String name, String desc) {
        super(InstanceType.PROCEDURAL, parent, group, compass, id, name, desc);
        this.interestGroup = interestGroup;
        this.right = right;
    }
    @Override
    public R getRight() {
        return right;
    }
    public abstract RightLevel getLevel();
    public InterestGroup getInterestGroup() {
        return interestGroup;
    }
    @Override
    public Set<TenetGroup> compatibleParents() {
        return Set.of(
                GovernmentGroups.GOVERNMENT
        );
    }
    @Override
    public boolean isTenetCompatible(TenetInstance<?> t) {
        return super.isTenetCompatible(t)
                && !(t.getTenet().get() instanceof RightTenets<?> rt && rt.getRight().equals(this.getRight()) && rt.getInterestGroup().equals(this.getInterestGroup()));
    }
    @Override
    public Set<InterestGroup> isAffected() {
        return Set.of(
            interestGroup
        );
    }
    @Override
    public void additionalLoad(JsonObject object) {
        super.additionalLoad(object);
        interestGroup = (InterestGroup) ComponentReference.fromJson(object.getAsJsonPrimitive("pg:interestGroup")).get();
        right = (R) ComponentReference.fromJson(object.getAsJsonPrimitive("pg:right")).get();
    }

    @Override
    public void additionalSave(JsonObject object) {
        super.additionalSave(object);
        object.add("pg:interestGroup", interestGroup.serializeRef());
        object.add("pg:right", right.serializeRef());
    }
    public static abstract class RevokeRightTenet<R extends Right<R>> extends RightTenets<R> {
        public RevokeRightTenet(InstanceType type, String id) {
            super(type, id);
        }

        public RevokeRightTenet(TenetReference parent, R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(parent,interestGroup.getRightsGroup(),right,compass,interestGroup,getID(right, interestGroup), "Revoke Right: " + right.getDisplayName(), "Disenfranchise " + interestGroup.getDisplayName() + " from " + right.getDisplayName());
        }

        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "revoke_" + right.getID() + "_" + interestGroup.getID();
        }

        @Override
        public RightLevel getLevel() {
            return RightLevel.DO_NOT_POSSESS;
        }
    }
    public static abstract class GuaranteeRevokeRightTenet<R extends Right<R>> extends RevokeRightTenet<R> {
        public GuaranteeRevokeRightTenet(InstanceType type, String id) {
            super(type, id);
        }

        public GuaranteeRevokeRightTenet(TenetReference parent, R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(parent,right,compass,interestGroup);
        }

        @Override
        public RightLevel getLevel() {
            return RightLevel.OVERRIDE_DO_NOT_POSSESS;
        }

        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "revoke_guarantee_" + right.getID() + "_" + interestGroup.getID();
        }
    }
    public static abstract class GuaranteeRightTenet<R extends Right<R>> extends RightTenets<R> {
        public GuaranteeRightTenet(InstanceType type, String id) {
            super(type, id);
        }
        public GuaranteeRightTenet(TenetReference parent, R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(parent,interestGroup.getRightsGroup(),right,compass,interestGroup,getID(right, interestGroup), "Guarantee Right: " + right.getDisplayName(), "Mandate that " + interestGroup.getDisplayName() + " has the right: " + right.getDisplayName());
        }
        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "guarantee_" + right.getID() + "_" + interestGroup.getID();
        }

        @Override
        public RightLevel getLevel() {
            return RightLevel.OVERRIDE_POSSESS;
        }
    }
    public static abstract class LimitedRightTenet<R extends Right<R>> extends RightTenets<R> {
        public LimitedRightTenet(InstanceType type, String id) {
            super(type, id);
        }
        public LimitedRightTenet(TenetReference parent, R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(parent,interestGroup.getRightsGroup(),right,compass,interestGroup,getID(right, interestGroup), "Limited Right: " + right.getDisplayName(), interestGroup.getDisplayName() + " has limited access to the right: " + right.getDisplayName());
        }
        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "limit_" + right.getID() + "_" + interestGroup.getID();
        }
        @Override
        public RightLevel getLevel() {
            return RightLevel.PARTIAL;
        }
    }
}
