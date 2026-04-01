package com.objects.character.opinion;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;
import com.base.timeline.TimelineHelper;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineMapChange;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.character.CharacterMapChanges;
import com.utilities.JsonSerializable;
import com.utilities.LoadingManager;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.Global.TimeDirection.BACKWARD;
import static com.Global.TimeDirection.FORWARD;

public class Opinion implements JsonSerializable<Opinion> {
    //TODO replace once I write the Basecode for psuedo-enums.
    private CharacterMapChanges.OpinionChange parent;
    private DMEReference<BookCharacter> us;
    private DMEReference<BookCharacter> other;
    private int runningTotal;
    List<OpinionReason> subReasons = new ArrayList<>();
    public Opinion(JsonObject json){
        fromJson(json);
    }
    public Opinion(DMEReference<BookCharacter> us, DMEReference<BookCharacter> other,int existingTotal, OpinionReason... reasons) {
        this.us = us;
        this.other = other;
        runningTotal = existingTotal;
        for (OpinionReason reason : reasons){
            subReasons.add(reason);
        }
    }


    public int getSoloTotal(){
        int total = 0;
        for (OpinionReason reason : subReasons){
            total += reason.change();
        }
        return total;
    }


    private void addOpinions(OpinionReason... reasons){
        subReasons.addAll(List.of(reasons));
    }

    public Map<LocalDate,OpinionReason> getFullTargetHistory(CharacterMapChanges.OpinionChange oc){
        final BiConsumer<CharacterMapChanges.OpinionChange,Map<LocalDate,OpinionReason>> consumer = new BiConsumer<>() {
            @Override
            public void accept(CharacterMapChanges.OpinionChange oc ,Map<LocalDate,OpinionReason> orm) {
                for (Opinion o : oc.getChangeFragment().values()){
                    if (o.other.equals(other)){
                        for (OpinionReason r : o.subReasons){
                            orm.put(oc.getStart(),r);
                        }
                        return;
                    }
                }
            }
        };
        Map<LocalDate,OpinionReason> orm = new HashMap<>();
        TimelineHelper.doMapChangeLeapFrog(oc.getOwner().get().getTimeline(),oc.getPreviousLeapFrog(),function,
                CharacterMapChanges.OpinionChange.class,1, consumer,orm, true,BACKWARD,false);
        return orm;
    }



    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("us", us.getID().toString());
        json.addProperty("other",other.getID().toString());
        json.addProperty("runningTotal",runningTotal);
        JsonArray reasons = new JsonArray();
        for (OpinionReason reason : subReasons){
            reasons.add(reason.toJson());
        }
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        us = DMEReference.of(BookCharacter.class,UUID.fromString(json.get("us").getAsString()));
        other = DMEReference.of(BookCharacter.class,UUID.fromString(json.get("other").getAsString()));
        runningTotal = json.get("runningTotal").getAsInt();
        for (int i = 0; i < json.get("reasons").getAsJsonArray().size(); i++) {
            JsonObject reason = json.get("reasons").getAsJsonArray().get(i).getAsJsonObject();
            subReasons.add(OpinionReason.fromJson(reason));
        }
    }

    @Override
    public Opinion empty() {
        return null;
    }

    private static final BiFunction<CharacterMapChanges.OpinionChange,Map<LocalDate,OpinionReason>,Integer> function = (m, r) -> {
        return 0;
    };

    private static Triple<TimelineState<BookCharacter>,CharacterMapChanges.OpinionChange,Opinion> getOrMake(Timeline<BookCharacter> t, LocalDate date, Timeline<BookCharacter> tl, DMEReference<BookCharacter> us, DMEReference<BookCharacter> other, OpinionReason... reasons){
        TimelineState<BookCharacter> s = t.getOrMakeState(date);
        CharacterMapChanges.OpinionChange oc = s.getChange(CharacterMapChanges.OpinionChange.class);
        Opinion o = null;
        final UUID oid = other.getID();
        if (oc != null){
            Map<UUID,Opinion> map = oc.getChangeFragment();
            if (map.containsKey(oid)){
                o = map.get(oid);
                o.addOpinions(reasons);
            }
        } else {
            o = new Opinion(us,other,0,reasons);
            oc = new CharacterMapChanges.OpinionChange(us,date);
            oc.getChangeFragment().put(oid,o);
            s.insertChange(oc);
        }
        return Triple.of(s,oc,o);
    }
    private static Pair<Integer,Integer> calculateOpinionTotal(OpinionReason... reasons){
        int total = 0;
        int cont = 0;
        for(OpinionReason reason : reasons){
            total += reason.change();
            cont++;
        }
        return Pair.of(total,cont);
    }

}
