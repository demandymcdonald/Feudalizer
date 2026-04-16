package com.objects.culture.tenet.types.mutable.tenets.social;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.TenetReference;
import com.objects.culture.tenet.types.mutable.MutableTenet;

import java.util.UUID;

public abstract class SocialAcceptanceTenet extends MutableTenet {
    public SocialAcceptanceTenet(TenetReference parent, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, group, entry, id, name, description);
    }

    public SocialAcceptanceTenet(TenetReference parent, UUID uuid, TenetGroup group, PoliticalCompass entry, String id, String name, String description) {
        super(parent, uuid, group, entry, id, name, description);
    }
}
