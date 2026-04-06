package com.objects.title.succession.rules;

import com.base.reference.DMEReference;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.character.human.HumanCharacter;
import com.objects.title.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CustomEntry extends SuccessionEntry<CustomEntry> {
     Multimap<DMEReference<? extends Title<?>>,DMEReference<HumanCharacter>> characters;
    public CustomEntry(DMEReference<HumanCharacter> primary) {
        super(primary);
    }
    public CustomEntry(DMEReference<HumanCharacter> primary, Title<?> title, List<HumanCharacter> list) {
        super(primary);
        characters = list.stream().map(DMEReference::new).toList();
        this.title = DMEReference.of(title);
    }
    public CustomEntry(DMEReference<? extends Title<?>> title, DMEReference<HumanCharacter> primary, DMEReference<HumanCharacter>... list) {
        super(primary);
        this.title = title;
        characters = List.of(list);
    }
    protected static final Function<DMEReference<HumanCharacter>,CustomEntry> builder = new Function<>() {
        @Override
        public CustomEntry apply(DMEReference<HumanCharacter> bookCharacterDMEReference) {
            return new CustomEntry(bookCharacterDMEReference);
        }
    };
    @Override
    public List<HumanCharacter> getLoSFull() {
        List<HumanCharacter> chs = new ArrayList<>();
        Title<?> title = this.title.get();
        for (DMEReference<HumanCharacter> ref : characters) {
            if (title.canInherit(ref.get())){
                chs.add(ref.get());
            }
        }
        return chs;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.add("Title",title.serialize());
        JsonArray array = new JsonArray();
        for (DMEReference<HumanCharacter> ref : characters){
            array.add(ref.serialize());
        }
        json.add("Characters",array);
        return json;
    }

    @Override
    protected CustomEntry deserialize(DMEReference<HumanCharacter> subject, JsonObject json) {
        DMEReference<? extends Title<?>> title = DMEReference.deserialize(json.get("Title").getAsJsonObject());
        JsonArray array = json.get("Characters").getAsJsonArray();
        List<DMEReference<HumanCharacter>> chs = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            DMEReference<HumanCharacter> ref = DMEReference.deserialize(array.get(i).getAsJsonObject());
            chs.add(ref);
        }
        return new CustomEntry(title,subject,chs.toArray(DMEReference[]::new));
    }

}
