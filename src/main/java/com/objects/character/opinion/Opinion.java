package com.objects.character.opinion;

import com.base.reference.DMEReference;
import com.base.timeline.TimelineHelper;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.character.CharacterMapChanges;
import com.utilities.JsonSerializable;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.Global.TimeDirection.BACKWARD;

public class Opinion implements JsonSerializable<Opinion> {
    private CharacterMapChanges.OpinionChange parent;
    private DMEReference<BookCharacter> us;
    private DMEReference<BookCharacter> other;
    private int opinionPastTotal;
    List<OpinionReason> activeReasons = new ArrayList<>();
    public Opinion(JsonObject json){
        fromJson(json);
    }
    public Opinion(DMEReference<BookCharacter> us, DMEReference<BookCharacter> other,int existingTotal, OpinionReason... reasons) {
        this.us = us;
        this.other = other;
        opinionPastTotal = existingTotal;
        for (OpinionReason reason : reasons){
            activeReasons.add(reason);
        }
    }
    public int getFullTotal(){
        return getPastTotal() + getActiveTotal();
    }
    public int getActiveTotal(){
        int total = 0;
        for (OpinionReason reason : activeReasons){
            total += reason.change();
        }
        return total;
    }
    public void resetPastTotal(){
        opinionPastTotal = 0;
    }
    public void amendPastTotal(int change){
        opinionPastTotal += change;
    }
    public int getPastTotal(){
        return opinionPastTotal;
    }
    public List<OpinionReason> getActiveReasons(){
        return activeReasons;
    }
    public void addOpinions(OpinionReason... reasons){
        activeReasons.addAll(List.of(reasons));
    }
    public void setParent(CharacterMapChanges.OpinionChange parent){
        this.parent = parent;
    }

    public Multimap<LocalDate,OpinionReason> getFullTargetHistory(){
        return parent.buildFullHistory(this);
    }
    public DMEReference<BookCharacter> getOther(){
        return other;
    }
    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("us", us.getID().toString());
        json.addProperty("other",other.getID().toString());
        json.addProperty("runningTotal", opinionPastTotal);
        JsonArray reasons = new JsonArray();
        for (OpinionReason reason : activeReasons){
            reasons.add(reason.toJson());
        }
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        us = DMEReference.of(BookCharacter.class,UUID.fromString(json.get("us").getAsString()));
        other = DMEReference.of(BookCharacter.class,UUID.fromString(json.get("other").getAsString()));
        opinionPastTotal = json.get("runningTotal").getAsInt();
        for (int i = 0; i < json.get("reasons").getAsJsonArray().size(); i++) {
            JsonObject reason = json.get("reasons").getAsJsonArray().get(i).getAsJsonObject();
            activeReasons.add(OpinionReason.fromJson(reason));
        }
    }
    @Override
    public Opinion empty() {
        return null;
    }
}
