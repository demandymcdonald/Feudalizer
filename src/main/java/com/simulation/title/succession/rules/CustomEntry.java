package com.simulation.title.succession.rules;

import com.base.reference.DMEReference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;

import java.util.ArrayList;
import java.util.List;

public class CustomEntry extends SuccessionEntry<CustomEntry> {
    final List<DMEReference<BookCharacter>> characters;
    final DMEReference<? extends Title<?>> title;
    public CustomEntry() {
        super(null);
        characters = List.of();
        title = null;
    }
    public CustomEntry(Title<?> title, BookCharacter primary, List<BookCharacter> list) {
        super(DMEReference.of(primary));
        characters = list.stream().map(DMEReference::new).toList();
        this.title = DMEReference.of(title);
    }
    public CustomEntry(DMEReference<? extends Title<?>> title, DMEReference<BookCharacter> primary, DMEReference<BookCharacter>... list) {
        super(primary);
        this.title = title;
        characters = List.of(list);
    }
    @Override
    public List<BookCharacter> getLoSFull() {
        List<BookCharacter> chs = new ArrayList<>();
        Title<?> title = this.title.link();
        for (DMEReference<BookCharacter> ref : characters) {
            if (title.canInherit(ref.link())){
                chs.add(ref.link());
            }
        }
        return chs;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.add("Title",title.serialize());
        JsonArray array = new JsonArray();
        for (DMEReference<BookCharacter> ref : characters){
            array.add(ref.serialize());
        }
        json.add("Characters",array);
        return json;
    }

    @Override
    protected CustomEntry deserialize(DMEReference<BookCharacter> subject, JsonObject json) {
        DMEReference<? extends Title<?>> title = DMEReference.deserialize(json.get("Title").getAsJsonObject());
        JsonArray array = json.get("Characters").getAsJsonArray();
        List<DMEReference<BookCharacter>> chs = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            DMEReference<BookCharacter> ref = DMEReference.deserialize(array.get(i).getAsJsonObject());
            chs.add(ref);
        }
        return new CustomEntry(title,subject,chs.toArray(DMEReference[]::new));
    }

}
