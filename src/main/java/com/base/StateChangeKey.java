package com.base;

import com.base.reference.StateReference;
import com.base.reference.DMEReference;
import com.google.gson.JsonObject;
import com.simulation.people.Character;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.base.DateMutableEntity.buildJson;
//TODO: here's the issue, this can't reference DateMutableEntitys directly..
// Which means I need to create a new record for a reference to the DME that actually links to the Class type and the
// UUID so I can find it..
public record StateChangeKey(StateChangeType type, StateReference... variables) {


    public enum StateChangeType{
        MARRIAGE("%s married %s"),
        DIVORCE("%s divorced %s", MARRIAGE),
        GRANT_TITLE("%s received the title %s"),
        REVOKE_TITLE("%s revoked the title from %s", GRANT_TITLE),
        DE_JURE_DRIFT("%s de-jure parent changed to %s"),
        HAD_CHILD("%s (Mother) and %s (Father) gave birth to %s"),
        HOUSE_NAME_CHANGE("%s changed to %s"),
        TREAT_AS_STATUS_QUO("",MARRIAGE,DIVORCE,GRANT_TITLE,REVOKE_TITLE);
        final String template;
        final StateChangeType[] canNullify;
        //Example
        StateChangeType(String template, StateChangeType... canNullify) {
            this.template = template;
            this.canNullify = canNullify;
        }
        StateChangeType(String template) {
            this.template = template;
            this.canNullify = null;
        }

        //Example: MARRIAGE.format(new Date(02/15/2026), Andy, Olivia Rodrigo) returns "Andy married Olivia Rodrigo"
        public String format(Date date, StateReference... args) {
            Object[] names = Arrays.stream(args)
                    .map(StateReference::parse)  // or getName() if you add that
                    .toArray();
            return date.toString() + ": " + String.format(template, names);
        }
        public boolean canNullify(StateChangeType type) {
            return canNullify != null && Arrays.asList(this.canNullify).contains(type);
        }
    }
    public boolean canBeNullified(StateChangeKey nullifier) {
        List<StateReference> ref = Arrays.stream(this.variables()).toList();

        if(nullifier.type() == this.type() || nullifier.type().canNullify(this.type())) {
            return Arrays.stream(nullifier.variables()).anyMatch(t -> ref.stream().anyMatch(t::equals));
        }
        return false;
    }
    public JsonObject serialize(){
        JsonObject j = new JsonObject();
        j.addProperty("type", this.type.name());
        j.add("variables", StateReference.buildArray(this.variables));
        return j;
    }
    public static StateChangeKey deserialize(JsonObject j){
        StateChangeType type = StateChangeType.valueOf(j.get("type").getAsString());
        StateReference[] refs = DMEReference.buildArray(j.getAsJsonArray("variables"));
        return new StateChangeKey(type, refs);
    }

    public static StateChangeKey hadChild(com.simulation.people.Character father, com.simulation.people.Character mother, Character child){
        return new StateChangeKey(StateChangeType.HAD_CHILD,DMEReference.of(mother),DMEReference.of(father),DMEReference.of(child));
    }
}
