package com.objects.title.succession.rules;

import com.base.reference.DMEReference;
import com.google.common.collect.LinkedHashMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.title.Title;
import com.objects.title.succession.SuccessionChecksum;

import java.util.*;

import static com.objects.title.succession.rules.CandidateRules.handleIfDead;

public class CommonLawEntry extends SuccessionEntry<CommonLawEntry> {
    //private static final Cache<,ArrayList<BookCharacter>> CACHE = CacheBuilder.newBuilder().build();
    private final DMEReference<? extends Title<?>> title;
    private final Map<Integer,DMEReference<BookCharacter>> additional = new HashMap<>();
    private static final boolean prima = true;
    private final boolean isPrimarySpouse;
    private SuccessionChecksum currentChecksum;
    List<BookCharacter> cached = new ArrayList<>();
    public CommonLawEntry() {
        super(null);
        title = null;
        isPrimarySpouse = false;
    }
    public CommonLawEntry(BookCharacter character, Title<?> title, boolean isPrimarySpouse) {
        this(DMEReference.of(character),DMEReference.of(title),isPrimarySpouse);
    }
    public CommonLawEntry(DMEReference<BookCharacter> character, DMEReference<? extends Title<?>> title, boolean isPrimarySpouse) {
        super(character);
        this.isPrimarySpouse = isPrimarySpouse;
        this.title = title;
    }
    public CommonLawEntry(DMEReference<BookCharacter> character, DMEReference<? extends Title<?>> title, boolean isPrimarySpouse, Map<Integer,DMEReference<BookCharacter>> additional) {
        super(character);
        this.isPrimarySpouse = isPrimarySpouse;
        this.title = title;
        this.additional.putAll(additional);
    }



    @Override
    public List<BookCharacter> getLoSFull() {
        BookCharacter character = getSubject().get();
        LinkedHashMultimap<Type,BookCharacter> everyone = buildCharacterList(character);
        SuccessionChecksum checksum = SuccessionChecksum.of(everyone.values());
        if (currentChecksum != null && currentChecksum.equals(checksum)){
            return cached;
        }
        currentChecksum = checksum;
        return generate(everyone);
    }
    private List<BookCharacter> generate(LinkedHashMultimap<Type,BookCharacter> everyone){
        List<BookCharacter> ordered = new ArrayList<>();
        Title<?> title = this.title.get();
        int childNumber = 0;
        for (Map.Entry<Type,BookCharacter> entry : everyone.entries()){
            Type type = entry.getKey();
            BookCharacter character = entry.getValue();
            if (type == Type.CHILD){
                childNumber++;
                if (isPrimarySpouse && childNumber % 2 != 0){
                    continue;
                }
            }
            if (title.canInherit(character)){
                ordered.add(character);
            }
        }

        cached = ordered;
        return ordered;
    }

    private LinkedHashMultimap<Type,BookCharacter> buildCharacterList(BookCharacter character){
        LinkedHashMultimap<Type,BookCharacter> result = LinkedHashMultimap.create();
        final List<BookCharacter> direct = CandidateRules.DirectFamily(character,false,prima);
        final List<BookCharacter> indirect = CandidateRules.IndirectFamily(character,false,prima);
        final List<BookCharacter> spouse = CandidateRules.IndirectSpouseFamily(character,false,prima);
        if (additional.isEmpty()){
            result.putAll(Type.CHILD,direct);
            result.putAll(Type.PRIMARY_EXTENDED,indirect);
            result.putAll(Type.SPOUSE_EXTENDED,spouse);
        } else {
            int counter = 0;
            for (BookCharacter current : direct){

                counter += doInsertCharacterList(counter,result,current,Type.CHILD);
            }
            for (BookCharacter current : indirect){

                counter += doInsertCharacterList(counter,result,current,Type.PRIMARY_EXTENDED);
            }
            for (BookCharacter current : spouse){
                counter += doInsertCharacterList(counter,result,current,Type.SPOUSE_EXTENDED);
            }
        }
        return result;
    }
    private int doInsertCharacterList(Integer current, LinkedHashMultimap<Type,BookCharacter> finalList, BookCharacter toBeInserted, Type tbiType){
        if (additional.containsKey(current)){
            finalList.put(Type.ADDED,handleIfDead(additional.get(current).get()));
            finalList.put(tbiType,toBeInserted);
            return 2;
        } else {
            finalList.put(tbiType,toBeInserted);
            return 1;
        }
    }
    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("isPrimarySpouse",isPrimarySpouse);
        json.add("Title",title.serialize());
        JsonArray array = new JsonArray();
        for (Map.Entry<Integer,DMEReference<BookCharacter>> entry : additional.entrySet()){
            JsonObject obj = new JsonObject();
            obj.addProperty("index",entry.getKey());
            obj.add("character",entry.getValue().serialize());
            array.add(obj);
        }
        json.add("Additional",array);
        return json;
    }

    @Override
    public CommonLawEntry deserialize(DMEReference<BookCharacter> subject, JsonObject json) {
        boolean isPrimarySpouse = json.get("isPrimarySpouse").getAsBoolean();
        DMEReference<? extends Title<?>> title = DMEReference.deserialize(json.get("Title").getAsJsonObject());
        JsonArray array = json.get("Additional").getAsJsonArray();
        Map<Integer,DMEReference<BookCharacter>> additional = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            additional.put(obj.get("index").getAsInt(),DMEReference.deserialize(obj.get("character").getAsJsonObject()));
        }
        return new CommonLawEntry(subject,title,isPrimarySpouse,additional);
    }

}
