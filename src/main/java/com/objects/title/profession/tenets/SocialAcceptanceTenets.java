package com.objects.title.profession.tenets;

import com.base.component.ComponentReference;
import com.base.component.InstanceType;
import com.base.datemutable.utilities.TLSFunction;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.object.compass.IPoliticalCompass;
import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.AcceptanceContainer;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.factory.CultureCondition;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.group.groups.SocietyGroups;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.culture.tenet.mutable.MutableTenet;
import com.objects.organization.education.Education;

import java.util.Set;
import java.util.function.Function;

public abstract class SocialAcceptanceTenets extends MutableTenet {
    private final TLSFunction<DMEReference<Culture>,PoliticalCompass> compass;
    protected InterestGroup ig;
    public SocialAcceptanceTenets(InstanceType type, String id) {
        super(type, id);
        this.compass = new TLSFunction<>(getCompassFunction());
    }
    public SocialAcceptanceTenets(InstanceType type, TenetReference parent, SocietyGroups.SocietyGroup group, InterestGroup igs, String id, String name, String description) {
        super(type, parent, group, new PoliticalCompass(), id, name, description);
        this.compass = new TLSFunction<>(getCompassFunction());
        this.ig = igs;
    }
    public abstract Function<DMEReference<Culture>,PoliticalCompass> getCompassFunction();
    @Override
    public IPoliticalCompass getCompass(DMEReference<Culture> culture) {
        return compass.apply(culture);
    }
    @Override
    public void additionalLoad(JsonObject object) {
        super.additionalLoad(object);
        ig = (InterestGroup) ComponentReference.fromJson(object.getAsJsonPrimitive("ig:interestGroup")).get();
    }
    @Override
    public void additionalSave(JsonObject object) {
        super.additionalSave(object);
        object.add("ig:interestGroup",ig.serializeRef());
    }
    public static class IGOpinionTenet extends SocialAcceptanceTenets {
        public IGOpinionTenet(InstanceType type, String id) {
            super(type, id);
        }
        public IGOpinionTenet(InstanceType type, TenetReference parent, SocietyGroups.SocietyGroup group, InterestGroup ig, String id, String name, String description) {
            super(type, parent, group,ig , id, name, description);
        }
        @Override
        public Function<DMEReference<Culture>, PoliticalCompass> getCompassFunction() {
            return this::buildCompass;
        }
        protected PoliticalCompass buildCompass(DMEReference<Culture> culture) {
            return buildBaseCompass(this,ig,culture);
        }
        @Override
        public Set<CultureCondition<?, ?>> getConditionList() {
            return Set.of(

            );
        }
        @Override
        public Set<TenetGroup> compatibleParents() {
            return Set.of(
                    SocietyGroups.SOCIETY
            );
        }

        @Override
        public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
            return new IGOpinionTenet(type, id);
        }
    }
    public static class LaborValue extends SocialAcceptanceTenets {
        public LaborValue(InstanceType type, String id) {
            super(type, id);
        }
        public LaborValue(InstanceType type, TenetReference parent, SocietyGroups.SocietyGroup group, InterestGroup ig, String id, String name, String description) {
            super(type, parent, group,ig , id, name, description);
        }
        @Override
        public Function<DMEReference<Culture>, PoliticalCompass> getCompassFunction() {
            return this::buildCompass;
        }

        protected PoliticalCompass buildCompass(DMEReference<Culture> culture) {
            PoliticalCompass base = buildBaseCompass(this,ig,culture);

        }




        @Override
        public Set<CultureCondition<?, ?>> getConditionList() {
            return Set.of(

            );
        }
        @Override
        public Set<TenetGroup> compatibleParents() {
            return Set.of(
                    SocietyGroups.SOCIETY
            );
        }

        @Override
        public MutableTenet getNewObject(InstanceType type, String id, JsonObject data) {
            return new IGOpinionTenet(type, id);
        }
    }
    protected static PoliticalCompass buildBaseCompass(SocialAcceptanceTenets tenet, InterestGroup ig, DMEReference<Culture> culture) {
        AcceptanceContainer container = tenet.getCultureOpinion(culture);
        int axisB = 1;
        int tolerance = 1;
        double mult = ig.getDimension().getMult();
        //Represents how tolerant this culture is of this interest group. The closer you are to Core, the smaller of a tolerance bump you get. This gets summed with all other AcceptanceTraits for other groups to
        //build the culture's compass.
        switch (container.getAcceptance()) {
            case CORE_FANATIC -> {
                tolerance = 0;
                axisB = 0;
            }
            case CORE,NEUTRAL -> {
                axisB = -1;
            }
            case INTEGRATED -> {
                tolerance = 6;
                axisB = -2;
            }
            case TOLERATED -> {
                tolerance = 5;
                axisB = -3;
            }
            case ACCEPTED -> {
                tolerance = 8;
                axisB = -4;
            }
            case BARELY_TOLERATED -> {
                tolerance = -4;

            }
            case REJECTED -> {
                tolerance = -8;
                axisB = 2;
            }
            case SHUNNED -> {
                tolerance = -12;
                axisB = 4;
            }
            case PERSECUTED -> {
                tolerance = -20;
                axisB = 8;
            }
            case FANATICAL_PERSECUTION -> {
                tolerance = -40;
                axisB = 16;
            }
        }
        return new PoliticalCompass(0,(int) Math.round(axisB * mult),0,(int) Math.round(((double) axisB/2) * mult),(int) Math.round(tolerance * mult));
    }
}
