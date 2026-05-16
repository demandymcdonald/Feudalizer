package com.objects.organization.government.rights;

import com.base.component.ComponentReference;
import com.base.component.InstanceType;
import com.base.component.instanced.bi.IOBi;
import com.base.reference.DMEReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.objects.character.sentient.SentientCharacter;
import com.objects.culture.TenetManager;
import com.objects.culture.tenet.Tenet;
import com.objects.culture.tenet.factory.RightTenets;
import com.objects.culture.tenet.instance.TenetInstance;
import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.government.GoverningEntity;
import com.utilities.IDisplayable;
import com.utilities.number.bound_float.BoundFloat;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.objects.organization.government.rights.RightLevel.POSSESS;

public abstract class Right<T extends Right<T>> extends IOBi<T,RightInstance<T>, DMEReference<? extends GoverningEntity<?>>,DMEReference<? extends SentientCharacter<?>>> implements IDisplayable {
    private String name;
    private String description;
    private final Cache<String, RightLevel> rlCache = CacheBuilder.newBuilder().expireAfterAccess(2, TimeUnit.MINUTES).build();
    private final Map<ComponentReference<InterestGroup>, BoundFloat> interestGroupMap = new HashMap<>();
    public Right(InstanceType type, String id, String name, String description) {
        super(type,"right_" + id);
        this.name = name;
        this.description = description;
    }

    @Override
    public final String getDisplayID() {
        return getID();
    }
    @Override
    public final String getDisplayName() {
        return name;
    }
    @Override
    public final String getDescription() {
        return description;
    }
    public RightInstance<T> getRightFor(DMEReference<? extends SentientCharacter<?>> character){
        DMEReference<? extends GoverningEntity<?>> gov = character.get().getGovernment();
        return instance(gov,character);
    }
    protected final <G extends GoverningEntity<G>> Pair<InterestGroup,RightLevel> getRights(DMEReference<? extends GoverningEntity<?>> gov, DMEReference<? extends SentientCharacter<?>> character){
        final G entity = (G) gov.get();
        final Set<InterestGroup> igs = TenetManager.InterestGroups.INSTANCE.getForCharacter(character);
        if(entity == null){
            //Because a person not subject to jurisdiction has the right to do whatever the hell they want. Should never happen.
            return Pair.of(igs.stream().findFirst().orElse(null), POSSESS);
        }
        InterestGroup ig = null;
        RightLevel level = null;
        for (InterestGroup group : igs){
            String id = entity.getID() + ":" + group.getID();
            RightLevel pl = rlCache.getIfPresent(id);
            if (pl == null){
                pl = getRightLevel(entity,group);
                rlCache.put(id,pl);
            }
            if (pl == RightLevel.OVERRIDE_DO_NOT_POSSESS){
                return Pair.of(group,RightLevel.DO_NOT_POSSESS);
            }
            if (pl == RightLevel.OVERRIDE_POSSESS){
                return  Pair.of(group, POSSESS);
            }
            if(level == null || pl.getLevel() < level.getLevel()){
                level = pl;
                if (level.getLevel() <= 0){
                    break;
                }
            }
        }
        return Pair.of(ig,level);
    }
    protected <G extends GoverningEntity<G>> RightLevel getRightLevel(G government, InterestGroup interestGroup){
        Set<TenetInstance<G>> revokeTenets = government.getActiveTenetByClass(RightTenets.class).stream().filter(
                t -> t.getTenet().get() instanceof RightTenets<?> rt && rt.getRight() == this && rt.getInterestGroup().equals(interestGroup)).collect(Collectors.toSet());
        if (revokeTenets.isEmpty()){
            return POSSESS;
        }
        RightTenets<T> right = (RightTenets<T>) revokeTenets.stream().findFirst().get().getTenet().get();
        return right.getLevel();
    };

    protected abstract float getHardshipFactor();

    public final RightTenets.RevokeRightTenet<T> getRevokeTenet(InterestGroup interestGroup){
        return TenetManager.INSTANCE.getOrMake(RightTenets.RevokeRightTenet.class, RightTenets.RevokeRightTenet.getID((T) this, interestGroup),
                () -> buildRevoke(interestGroup));
    };
    public final RightTenets.GuaranteeRightTenet<T> getGuaranteeTenet(InterestGroup interestGroup){
        return TenetManager.INSTANCE.getOrMake(RightTenets.GuaranteeRightTenet.class, RightTenets.GuaranteeRightTenet.getID((T) this, interestGroup),
                () -> buildGuarantee(interestGroup));
    };
    public final RightTenets.LimitedRightTenet<T> getLimitedTenet(InterestGroup interestGroup){
        return TenetManager.INSTANCE.getOrMake(RightTenets.LimitedRightTenet.class, RightTenets.LimitedRightTenet.getID((T) this, interestGroup),
                () -> buildLimited(interestGroup));
    };
    public final RightTenets.RevokeRightTenet<T> getGuaranteeRevokeTenet(InterestGroup interestGroup){
        return TenetManager.INSTANCE.getOrMake(RightTenets.GuaranteeRevokeRightTenet.class, RightTenets.RevokeRightTenet.getID((T) this, interestGroup),
                () -> buildGuaranteeRevoke(interestGroup));
    };
 ,
    protected abstract RightTenets.GuaranteeRevokeRightTenet<T> buildGuaranteeRevoke(InterestGroup interestGroup);
    protected abstract RightTenets.RevokeRightTenet<T> buildRevoke(InterestGroup interestGroup);
    protected abstract RightTenets.GuaranteeRightTenet<T> buildGuarantee(InterestGroup interestGroup);
    protected abstract RightTenets.LimitedRightTenet<T> buildLimited(InterestGroup interestGroup);


    @Override
    public void additionalLoad(JsonObject data) {
        name = data.get("ri:name").getAsString();
        description = data.get("ri:desc").getAsString();
        JsonArray array = data.get("ri:igs").getAsJsonArray();
        interestGroupMap.clear();
        for(JsonElement element : array){
            JsonObject obj = element.getAsJsonObject();
            ComponentReference<InterestGroup> ref = ComponentReference.fromJson(obj.get("k").getAsJsonPrimitive());
            interestGroupMap.put(ref, new BoundFloat(0f,100f,obj.get("v").getAsFloat()));
        }
    }

    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("ri:name", name);
        data.addProperty("ri:desc", description);
        JsonArray array = new JsonArray();
        for(Map.Entry<ComponentReference<InterestGroup>, BoundFloat> entry : interestGroupMap.entrySet()){
            JsonObject obj = new JsonObject();
            obj.add("k", entry.getKey().toJson());
            obj.addProperty("v", entry.getValue().get());
            array.add(obj);
        }
        data.add("ri:igs", array);
    }

    public T addInterest(InterestGroup ig, float care) {
        interestGroupMap.put(ig.getReference(), new BoundFloat(0f,100f,care));
        return (T) this;
    }

    @Override
    public RightInstance<T> instance(DMEReference<? extends GoverningEntity<?>> gov, DMEReference<? extends SentientCharacter<?>> character) {
        Pair<InterestGroup,RightLevel> pair = getRights(gov,character);
        return new RightInstance<>(this.getReference(),pair.getKey(),pair.getValue());
    }
}
