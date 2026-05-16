package com.objects.organization.government.tenets.economy;

import com.Global;
import com.Global.*;
import com.base.component.InstanceType;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
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

        public PersonalPropertyRight(InstanceType instType) {
            super(instType, "personal_property", "Personal Property Right","The right to own personal property like a car, house, or other personal belongings.");
        }


        @Override
        protected float getHardshipFactor() {
            return 1.5f;
        }

        @Override
        protected RightTenets.GuaranteeRevokeRightTenet<PersonalPropertyRight> buildGuaranteeRevoke(InterestGroup interestGroup) {
            return new RightTenets.GuaranteeRevokeRightTenet<PersonalPropertyRight>(){

                @Override
                public void getConditions(Set<CultureCondition<?, ?>> conditions) {

                }

                @Override
                public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
                    return null;
                }

                @Override
                public Set<CultureCondition<?, ?>> getConditionList() {
                    return Set.of();
                }
            };
        }

        @Override
        protected RightTenets.RevokeRightTenet<PersonalPropertyRight> buildRevoke(InterestGroup interestGroup) {
            return null;
        }

        @Override
        protected RightTenets.GuaranteeRightTenet<PersonalPropertyRight> buildGuarantee(InterestGroup interestGroup) {
            return null;
        }

        @Override
        protected RightTenets.LimitedRightTenet<PersonalPropertyRight> buildLimited(InterestGroup interestGroup) {
            return null;
        }

        @Override
        public PersonalPropertyRight getNewObject(InstanceType type, String id, JsonObject data) {
            return null;
        }
    }


}
