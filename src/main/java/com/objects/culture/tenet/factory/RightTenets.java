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

    public RightTenets(TenetGroup group, R right, PoliticalCompass compass, InterestGroup interestGroup, String id, String name, String desc) {
        super(InstanceType.PROCEDURAL, group, compass, id, name, desc);
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

        public RevokeRightTenet(R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(interestGroup.getRightsGroup(),right,compass,interestGroup,getID(right, interestGroup), "Revoke Right: " + right.getDisplayName(), "Disenfranchise " + interestGroup.getDisplayName() + " from " + right.getDisplayName());
        }

        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "revoke_" + right.getID() + "_" + interestGroup.getID();
        }

        @Override
        public RightLevel getLevel() {
            return RightLevel.DO_NOT_POSSESS;
        }
        public static <R extends Right<R>> RevokeRightTenet<R> buildEmpty(R right,PoliticalCompass compass, InterestGroup interestGroup){
            return new RevokeRightTenet.RevokeImpl<>(right,compass,interestGroup);
        }
        private static class RevokeImpl<R extends Right<R>> extends RevokeRightTenet<R> {
            public RevokeImpl(R right, PoliticalCompass compass, InterestGroup interestGroup) {
                super(right, compass, interestGroup);
            }

            public RevokeImpl(InstanceType type, String id) {
                super(type, id);
            }

            @Override
            public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
                return new RightTenets.RevokeRightTenet.RevokeImpl<>(type,id);
            }

            @Override
            public void getConditions(Set<CultureCondition<?, ?>> conditions) {

            }
        }
    }
    public static abstract class GuaranteeRevokeRightTenet<R extends Right<R>> extends RevokeRightTenet<R> {
        public GuaranteeRevokeRightTenet(InstanceType type, String id) {
            super(type, id);
        }

        public GuaranteeRevokeRightTenet(R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(right,compass,interestGroup);
        }

        @Override
        public RightLevel getLevel() {
            return RightLevel.OVERRIDE_DO_NOT_POSSESS;
        }

        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "revoke_guarantee_" + right.getID() + "_" + interestGroup.getID();
        }

        public static <R extends Right<R>> GuaranteeRevokeRightTenet<R> buildEmpty(R right,PoliticalCompass compass, InterestGroup interestGroup){
            return new GuaranteeRevokeRightTenet.GuaranteeRevokeImpl<>(right,compass,interestGroup);
        }
        private static class GuaranteeRevokeImpl<R extends Right<R>> extends GuaranteeRevokeRightTenet<R> {
            public GuaranteeRevokeImpl(R right, PoliticalCompass compass, InterestGroup interestGroup) {
                super(right, compass, interestGroup);
            }

            public GuaranteeRevokeImpl(InstanceType type, String id) {
                super(type, id);
            }

            @Override
            public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
                return new RightTenets.GuaranteeRevokeRightTenet.GuaranteeRevokeImpl<>(type,id);
            }

            @Override
            public void getConditions(Set<CultureCondition<?, ?>> conditions) {

            }
        }
    }
    public static abstract class GuaranteeRightTenet<R extends Right<R>> extends RightTenets<R> {
        public GuaranteeRightTenet(InstanceType type, String id) {
            super(type, id);
        }
        public GuaranteeRightTenet(R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(interestGroup.getRightsGroup(),right,compass,interestGroup,getID(right, interestGroup), "Guarantee Right: " + right.getDisplayName(), "Mandate that " + interestGroup.getDisplayName() + " has the right: " + right.getDisplayName());
        }
        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "guarantee_" + right.getID() + "_" + interestGroup.getID();
        }

        @Override
        public RightLevel getLevel() {
            return RightLevel.OVERRIDE_POSSESS;
        }
        public static <R extends Right<R>> GuaranteeRightTenet<R> buildEmpty(R right,PoliticalCompass compass, InterestGroup interestGroup){
            return new GuaranteeRightTenet.GuaranteeRightTenetImpl<>(right,compass,interestGroup);
        }
        private static class GuaranteeRightTenetImpl<R extends Right<R>> extends GuaranteeRightTenet<R> {
            public GuaranteeRightTenetImpl(R right, PoliticalCompass compass, InterestGroup interestGroup) {
                super(right, compass, interestGroup);
            }

            public GuaranteeRightTenetImpl(InstanceType type, String id) {
                super(type, id);
            }

            @Override
            public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
                return new RightTenets.GuaranteeRightTenet.GuaranteeRightTenetImpl<>(type,id);
            }

            @Override
            public void getConditions(Set<CultureCondition<?, ?>> conditions) {

            }
        }
    }
    public static abstract class LimitedRightTenet<R extends Right<R>> extends RightTenets<R> {
        public LimitedRightTenet(InstanceType type, String id) {
            super(type, id);
        }
        public LimitedRightTenet(R right, PoliticalCompass compass, InterestGroup interestGroup) {
            super(interestGroup.getRightsGroup(),right,compass,interestGroup,getID(right, interestGroup), "Limited Right: " + right.getDisplayName(), interestGroup.getDisplayName() + " has limited access to the right: " + right.getDisplayName());
        }
        public static <R extends Right<R>> String getID(R right, InterestGroup interestGroup){
            return "limit_" + right.getID() + "_" + interestGroup.getID();
        }
        @Override
        public RightLevel getLevel() {
            return RightLevel.PARTIAL;
        }
        public static <R extends Right<R>> LimitedRightTenet<R> buildEmpty(R right,PoliticalCompass compass, InterestGroup interestGroup){
            return new LimitedRightTenetImpl<>(right,compass,interestGroup);
        }
        private static class LimitedRightTenetImpl<R extends Right<R>> extends LimitedRightTenet<R> {
            public LimitedRightTenetImpl(R right, PoliticalCompass compass, InterestGroup interestGroup) {
                super(right, compass, interestGroup);
            }

            public LimitedRightTenetImpl(InstanceType type, String id) {
                super(type, id);
            }

            @Override
            public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
                return new LimitedRightTenetImpl<>(type,id);
            }

            @Override
            public void getConditions(Set<CultureCondition<?, ?>> conditions) {

            }
        }


    }

}
