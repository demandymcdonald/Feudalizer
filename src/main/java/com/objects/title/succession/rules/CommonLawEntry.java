package com.objects.title.succession.rules;

import com.base.reference.DMEReference;
import com.google.common.collect.LinkedHashMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.objects.character.BookCharacter;
import com.objects.title.Title;
import com.objects.title.succession.SuccessionChecksum;

import java.util.*;
import java.util.function.Function;

import static com.objects.title.succession.rules.CandidateRules.handleIfDead;

public class CommonLawEntry extends SuccessionEntry<CommonLawEntry> {
    //private static final Cache<,ArrayList<BookCharacter>> CACHE = CacheBuilder.newBuilder().build();
    private final Map<Integer,DMEReference<BookCharacter>> additional = new HashMap<>();
    private static final boolean prima = true;
    private boolean isPrimarySpouse;
    private SuccessionChecksum currentChecksum;
    List<BookCharacter> cached = new ArrayList<>();
    public CommonLawEntry(DMEReference<BookCharacter> character) {
        super(character);
    }



    public CommonLawEntry(DMEReference<BookCharacter> character,  boolean isPrimarySpouse) {
        super(character);
        this.isPrimarySpouse = isPrimarySpouse;
    }
    protected static final Function<DMEReference<BookCharacter>,CommonLawEntry> builder = new Function<>() {

        @Override
        public CommonLawEntry apply(DMEReference<BookCharacter> bookCharacterDMEReference) {
            return new CommonLawEntry(bookCharacterDMEReference);
        }
    };

    @Override
    public List<BookCharacter> getLoSFull(DMEReference<? extends Title<?>> title) {
        BookCharacter character = getSubject().get();
        LinkedHashMultimap<Type,BookCharacter> everyone = buildCharacterList(character);
        SuccessionChecksum checksum = SuccessionChecksum.of(everyone.values());
        if (currentChecksum != null && currentChecksum.equals(checksum)){
            return cached;
        }
        currentChecksum = checksum;
        return generate(title,everyone);
    }

    private List<BookCharacter> generate(DMEReference<? extends Title<?>> title,LinkedHashMultimap<Type,BookCharacter> everyone){
        List<BookCharacter> ordered = new ArrayList<>();
        Title<?> tT = title.get();
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
            if (canInherit(tT)){
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

    }

    @Override
    public void additionalSave(JsonObject json) {
        json.addProperty("isPrimarySpouse",isPrimarySpouse);
        JsonArray array = new JsonArray();
        for (Map.Entry<Integer,DMEReference<BookCharacter>> entry : additional.entrySet()){
            JsonObject obj = new JsonObject();
            obj.addProperty("index",entry.getKey());
            obj.add("character",entry.getValue().serialize());
            array.add(obj);
        }
        json.add("Additional",array);
    }

    @Override
    public void additionalLoad(JsonObject json) {
        isPrimarySpouse = json.get("isPrimarySpouse").getAsBoolean();
        JsonArray array = json.get("Additional").getAsJsonArray();
        Map<Integer,DMEReference<BookCharacter>> additional = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            additional.put(obj.get("index").getAsInt(),DMEReference.deserialize(obj.get("character").getAsJsonObject()));
        }
        this.additional.putAll(additional);
    }


}
