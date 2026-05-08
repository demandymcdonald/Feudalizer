package com.objects.organization.government.rights;

import com.base.component.InstanceType;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.augments.IRightsTenet;
import com.objects.culture.tenet.mutable.tenets.leadership.Leadership;
import com.objects.organization.government.GoverningEntity;
import com.utilities.number.bound_float.BoundFloat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Rights {
    public static final VotingRight VOTING_RIGHT = new VotingRight();
    public static class VotingRight extends Right<VotingRight> {
        protected VotingRight() {
            super(InstanceType.HARDCODED, "voting", "Voting Rights", "");
        }

        @Override
        protected RightLevel getRightLevel(VotingRight right, GoverningEntity<?> government, InterestGroup interestGroup) {
            return null;
        }

        @Override
        protected BoundFloat getHardshipFactor() {
            return null;
        }

        @Override
        public VotingRight getNewObject(InstanceType type, String id, JsonObject data) {
            return null;
        }
    }
}
