package com.objects.title.succession.rules;

import com.TypedSerialized;
import com.base.reference.DMEReference;
import com.base.timeline.error.SandboxCode;
import com.base.timeline.error.StateError;
import com.objects.title.Title;
import com.utilities.JsonSerializable;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.utilities.SuperclassSerializable;
import org.checkerframework.checker.units.qual.C;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public abstract class SuccessionEntry<T extends SuccessionEntry<T>> implements SuperclassSerializable<SuccessionEntry<?>> {
    private static final Map<String, Function<DMEReference<BookCharacter>,? extends SuccessionEntry<?>>> typeMap = new HashMap<>();
    private final DMEReference<BookCharacter> subject;


    protected SuccessionEntry(DMEReference<BookCharacter> subject) {
        this.subject = subject;

    }

    protected enum Type{
        SPOUSE_EXTENDED,
        CHILD,
        PRIMARY_EXTENDED,
        ADDED
    }


    protected DMEReference<BookCharacter> getSubject() {
        return subject;
    }

    protected <T extends Title<T>> boolean canInherit(DMEReference<? extends Title<?>> title, LocalDate date){
        DMEReference<T> t = (DMEReference<T>) title;
        Optional<StateError> se = Title.canInherit(t,subject,date,false);
        return se.isEmpty() || se.get().getExpectedSandboxCode() == SandboxCode.CONTINUE;
    }

    public abstract List<BookCharacter> getLoSFull(DMEReference<? extends Title<?>> title, LocalDate date);

    @Override
    public final void metadataSave(JsonObject data) {
        SuperclassSerializable.super.metadataSave(data);
        data.add("subject",subject.serialize());
    }

    @Override
    public final void mainLoad(JsonObject json) {
        JsonObject subjectJson = json.get("subject").getAsJsonObject();
        DMEReference<BookCharacter> subject = DMEReference.deserialize(subjectJson);
    }
    @Override
    public final void mainSave(JsonObject json) {
        json.add("subject",subject.serialize());
        json.add("payload",serialize());
    }

    public static <T extends SuccessionEntry<T>> T fromJson(JsonObject json){
        JsonObject metadata = json.get("metadata").getAsJsonObject();
        String c = metadata.get("class").getAsString();
        return (T) typeMap.get(c).apply(DMEReference.deserialize(json.get("subject").getAsJsonObject()));
    }
    protected static void register(String type, Function<DMEReference<BookCharacter>,? extends SuccessionEntry<?>> function){
        typeMap.put(type,function);
    }

    static {
        register(CommonLawEntry.class.getName(), CommonLawEntry.builder);
        register(CustomEntry.class.getName(), CustomEntry.builder);
    }
}
