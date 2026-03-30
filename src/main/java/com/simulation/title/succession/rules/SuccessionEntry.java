package com.simulation.title.succession.rules;

import com.TypedSerialized;
import com.base.reference.DMEReference;
import com.utilities.JsonSerializable;
import com.google.gson.JsonObject;
import com.simulation.character.BookCharacter;

import java.util.List;
import java.util.UUID;

public abstract class SuccessionEntry<T extends SuccessionEntry<T>> implements JsonSerializable<T> {
    private final DMEReference<BookCharacter> subject;



    private final boolean isProjected;

    protected SuccessionEntry(DMEReference<BookCharacter> subject, boolean isProjected) {
        this.subject = subject;
        this.isProjected = isProjected;
    }

    protected enum Type{
        SPOUSE_EXTENDED,
        CHILD,
        PRIMARY_EXTENDED,
        ADDED
    }
    public List<UUID> getLoS(){
        List<BookCharacter> characters = getLoSFull();
        return characters.stream().map(BookCharacter::getId).toList();
    }
    public abstract List<BookCharacter> getLoSFull();
    protected abstract JsonObject serialize();
    protected abstract T deserialize(DMEReference<BookCharacter> subject, JsonObject json);

    @Override
    public T empty() {
        return (T) TypedSerialized.getRegisteredSuccessionRules().get(this.getClass());
    }

    @Override
    public final void fromJson(JsonObject json) {
        JsonObject subjectJson = json.get("subject").getAsJsonObject();
        DMEReference<BookCharacter> subject = DMEReference.deserialize(subjectJson);
        empty().deserialize(subject,json);
    }

    @Override
    public final JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add("subject",subject.serialize());
        json.add("payload",serialize());
        return json;
    }
    public boolean isProjected() {
        return isProjected;
    }
    public static SuccessionEntry<?> getEmptyEntry(Class<? extends SuccessionEntry<?>> json){
        return TypedSerialized.getRegisteredSuccessionRules().get(json);
    }
    public DMEReference<BookCharacter> getSubject() {
        return subject;
    }

}
