package com.objects.organization.government.tenets.economy;

import com.Global;
import com.Global.*;
import com.base.component.InstanceType;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.factory.RightTenets;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.culture.tenet.mutable.augments.IRightsTenet;
import com.objects.organization.government.GoverningEntity;
import com.objects.organization.government.rights.Right;
import com.objects.organization.government.rights.RightInstance;
import com.objects.organization.government.rights.RightLevel;
import com.utilities.number.bound_float.BoundFloat;

import java.util.Set;

public abstract class PropertyRights<T extends PropertyRights<T>> extends Right<T> {

    public PropertyRights(InstanceType instType, String id,  String type, String description) {
        super(instType, "property_"+id, "Property Rights: "+ type, description);
    }


    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
    public static class PersonalPropertyRight extends PropertyRights<PersonalPropertyRight>{

        protected PersonalPropertyRight(InstanceType instType) {
            super(instType, "personal_property", "Personal Property Right","The right to own personal property like a car, house, or other personal belongings.");
        }


        @Override
        protected float getHardshipFactor() {
            return .5f;
        }
        private static final PoliticalCompass guarantee_compass = new PoliticalCompass(-25,15,0,0);
        private static final PoliticalCompass limited_compass = new PoliticalCompass(-7,4,0,0);
        private static final PoliticalCompass revoke_compass = new PoliticalCompass(25,-5,0,0);
        @Override
        protected RightTenets.GuaranteeRevokeRightTenet<PersonalPropertyRight> buildGuaranteeRevoke(InterestGroup interestGroup) {
            return RightTenets.GuaranteeRevokeRightTenet.buildEmpty(this, revoke_compass, interestGroup);
        }

        @Override
        protected RightTenets.RevokeRightTenet<PersonalPropertyRight> buildRevoke(InterestGroup interestGroup) {
            return RightTenets.RevokeRightTenet.buildEmpty(this, revoke_compass, interestGroup);
        }

        @Override
        protected RightTenets.GuaranteeRightTenet<PersonalPropertyRight> buildGuarantee(InterestGroup interestGroup) {
            return RightTenets.GuaranteeRightTenet.buildEmpty(this, guarantee_compass, interestGroup);
        }

        @Override
        protected RightTenets.LimitedRightTenet<PersonalPropertyRight> buildLimited(InterestGroup interestGroup) {
            return RightTenets.LimitedRightTenet.buildEmpty(this, limited_compass, interestGroup);
        }

        @Override
        public PersonalPropertyRight getNewObject(InstanceType type, String id, JsonObject data) {
            return this;
        }
    }
    public static class LandOwnershipPropertyRight extends PropertyRights<LandOwnershipPropertyRight>{

        protected LandOwnershipPropertyRight(InstanceType instType) {
            super(instType, "land_ownership", "Land Ownership","The right to own land.");
        }
        @Override
        protected float getHardshipFactor() {
            return 1f;
        }
        private static final PoliticalCompass guarantee_compass = new PoliticalCompass(-25,15,0,0);
        private static final PoliticalCompass limited_compass = new PoliticalCompass(-7,4,0,0);
        private static final PoliticalCompass revoke_compass = new PoliticalCompass(25,-5,0,0);
        @Override
        protected RightTenets.GuaranteeRevokeRightTenet<LandOwnershipPropertyRight> buildGuaranteeRevoke(InterestGroup interestGroup) {
            return RightTenets.GuaranteeRevokeRightTenet.buildEmpty(this, revoke_compass, interestGroup);
        }

        @Override
        protected RightTenets.RevokeRightTenet<LandOwnershipPropertyRight> buildRevoke(InterestGroup interestGroup) {
            return RightTenets.RevokeRightTenet.buildEmpty(this, revoke_compass, interestGroup);
        }

        @Override
        protected RightTenets.GuaranteeRightTenet<LandOwnershipPropertyRight> buildGuarantee(InterestGroup interestGroup) {
            return RightTenets.GuaranteeRightTenet.buildEmpty(this, guarantee_compass, interestGroup);
        }

        @Override
        protected RightTenets.LimitedRightTenet<LandOwnershipPropertyRight> buildLimited(InterestGroup interestGroup) {
            return RightTenets.LimitedRightTenet.buildEmpty(this, limited_compass, interestGroup);
        }

        @Override
        public LandOwnershipPropertyRight getNewObject(InstanceType type, String id, JsonObject data) {
            return this;
        }
    }
}
