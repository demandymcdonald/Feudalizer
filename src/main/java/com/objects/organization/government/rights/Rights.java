package com.objects.organization.government.rights;

import com.base.reference.DMEReference;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.augments.IRightsTenet;
import com.objects.culture.tenet.mutable.tenets.leadership.Leadership;
import com.objects.organization.government.GoverningEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Rights {
    private static Map<String, Right> rights = new HashMap<>();
    public static void registerRight(Right right) {
        rights.put(right.getDisplayID(), right);
    }
    public static Set<Right> getRights() {
        return new HashSet<>(rights.values());
    }
    public static final Right VOTING = new Right("voting","Voting Rights","") {
        @Override
        public <G extends GoverningEntity<G>> RightInstance getRightFor(DMEReference<G> government, InterestGroup interestGroup) {
            Set<TenetInstance<G>> instance = government.get().getActiveTenetByClass(Leadership.CannotLead.class);
            for(TenetInstance<G> tenetInstance : instance) {
                if(tenetInstance.getTenet() instanceof IRightsTenet<?> irt && irt.isAffected().contains(interestGroup)){
                    return new RightInstance(this,interestGroup,RightLevel.DO_NOT_POSSESS);
                }
            }
            return new RightInstance(this,interestGroup,RightLevel.POSSESS);
        }
    };
    public static final Right HOLD_OFFICE = new Right("hold_office","Hold Office","") {

        @Override
        public <G extends GoverningEntity<G>> RightInstance getRightFor(DMEReference<G> government, InterestGroup interestGroup) {
            Set<TenetInstance<G>> instance = government.get().getActiveTenetByClass(Leadership.CannotLead.class);
            for(TenetInstance<G> tenetInstance : instance) {
                if(tenetInstance.getTenet() instanceof IRightsTenet<?> irt && irt.isAffected().contains(interestGroup)){
                    return new RightInstance(this,interestGroup,RightLevel.DO_NOT_POSSESS);
                }
            }
            return new RightInstance(this,interestGroup,RightLevel.POSSESS);
        }
    };

}
