package com.objects.character.opinion;

import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.objects.character.LivingCreature;
import com.objects.character.sentient.SentientCharacter;
import com.utilities.id.StringIdentifiable;
import com.utilities.serialization.CompressString;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Opinion implements StringIdentifiable {
    private final UUID instanceID;
    private final DMEReference<? extends LivingCreature<?>> target;
    private OpinionReason reason;

    public Opinion(UUID instanceID, DMEReference<? extends LivingCreature<?>> target, OpinionReason reason) {
        this.instanceID = instanceID;
        this.target = target;
        this.reason = reason;
    }
    public Opinion(DMEReference<? extends LivingCreature<?>> target, OpinionReason reason) {
        this.instanceID = UUID.randomUUID();
        this.target = target;
        this.reason = reason;
    }
    public int getValue(){
        return reason.value().get();
    }
    @Override
    public String getID() {
        return target.getID().toString()+":"+instanceID.toString();
    }
    public DMEReference<? extends LivingCreature<?>> getTarget() {
        return target;
    }
    public UUID getInstanceID() {
        return instanceID;
    }
    public double getReciprocalMod(){
        return reason.reciprocalMod();
    }
    public OpinionReason getReason() {
        return reason;
    }
    public UUID getTargetID() {
        return target.getID();
    }
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",CompressString.compress(instanceID.toString()) + "::" + CompressString.compress(CompressString.compress(target.serialize().getAsString())));

        jsonObject.addProperty("reason", reason.toJson().getAsString());
        return jsonObject;
    }
    public Optional<Duration> getDuration() {
        return Optional.ofNullable(reason.duration());
    }
    public static Opinion fromJson(JsonObject object){
        String[] ids = object.get("id").getAsString().split("::");
        UUID instanceID = UUID.fromString(Objects.requireNonNull(CompressString.decompress(ids[0])));
        DMEReference<? extends LivingCreature<?>> target = DMEReference.deserialize(JsonParser.parseString(Objects.requireNonNull(CompressString.decompress(ids[1]))).getAsJsonObject());
        OpinionReason reason =  OpinionReason.fromJson(object.get("reason").getAsJsonPrimitive());
        return new Opinion(instanceID,target,reason);
    }

}
