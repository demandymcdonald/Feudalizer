package com.objects.culture.tenet.types.mutable.tenets;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.population.InterestGroup;
import com.objects.culture.tenet.reference.DynamicTR;
import com.objects.culture.tenet.reference.TenetReference;
import com.objects.culture.tenet.types.mutable.MutableTenet;

import java.util.UUID;

public abstract class InterestGroupTenet extends MutableTenet {

    public InterestGroupTenet(DynamicTR<?> parent, InterestGroup group, boolean isSocial, TenetGroup.AcceptanceContainer acceptance, String id, String name, String description) {
        super(parent, findGroup(isSocial, group), entry, buildID(isSocial, id), name, description);
    }

    public InterestGroupTenet(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, uuid, group, entry, id, name, description);
    }
    private static String buildID(boolean isSocial, String id){
        if(isSocial){
            return "igt_social/" + id.toLowerCase();
        } else {
            return "igt_rights/" + id.toLowerCase();
        }
    }

    private static TenetGroup findGroup(boolean isSocial, InterestGroup group){
        if(isSocial){
            return group.getSocialStatusGroup();
        } else {
            return group.getRightsGroup();
        }
    }
    private static PoliticalCompass buildCompass(TenetReference parent, PoliticalCompass compass){

    }


}
