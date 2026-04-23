package com.objects.family;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.objects.character.sentient.Pronouns;
import com.utilities.IDisplayable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.objects.family.FamilyRelationship.MemberType.*;

public enum FamilyRelationship implements IDisplayable {
    HEAD_OF_FAMILY("head_of_family","Head of Family","",HEAD,1),
    HUSBAND("spouse_male","Husband","",PARTNER,1),
    WIFE("spouse_female","Wife","",PARTNER,1),
    SPOUSE("spouse","Neutral","",PARTNER,1),
    EX_SPOUSE("ex_spouse","Ex Spouse","",PARTNER,1),
    EX_SPOUSE_MALE("ex_spouse_male","Ex Husband","",PARTNER,1),
    EX_SPOUSE_FEMALE("ex_spouse_female","Ex Wife","",PARTNER,1),
    LOVER("lover","Lover","",PARTNER,1),
    LOVER_MALE("lover_male","Boyfriend","",PARTNER,1),
    LOVER_FEMALE("lover_female","Girlfriend","",PARTNER,1),
    EX_LOVER("ex_lover","Ex Lover","",PARTNER,1),
    EX_LOVER_MALE("ex_lover_male","Ex Boyfriend","",PARTNER,1),
    EX_LOVER_FEMALE("ex_lover_female","Ex Girlfriend","",PARTNER,1),
    CONCUBINE("concubine","Concubine","",PARTNER,1),
    EX_CONCUBINE("ex_concubine","Ex Concubine","",PARTNER,1),
    CHILD_BORN("child_born","Child","",OFFSPRING_BIOLOGIC,100),
    CHILD_BORN_MALE("child_born_male","Son","",OFFSPRING_BIOLOGIC,100),
    CHILD_BORN_FEMALE("child_born_female","Daughter","",OFFSPRING_BIOLOGIC,100),
    CHILD_ADOPTED("child_adopted","Child (Adopted)","",OFFSPRING_ADOPTED,100),
    CHILD_ADOPTED_MALE("child_adopted_male","Son (Adopted)","",OFFSPRING_ADOPTED,100),
    CHILD_ADOPTED_FEMALE("child_adopted_female","Daughter (Adopted)","",OFFSPRING_ADOPTED,100),
    DISOWNED_CHILD_BORN("child_born_disowned","Child","",OFFSPRING_BIOLOGIC,100),
    DISOWNED_CHILD_BORN_MALE("child_born_male_disowned","Son","",OFFSPRING_BIOLOGIC,100),
    DISOWNED_CHILD_BORN_FEMALE("child_born_female_disowned","Daughter","",OFFSPRING_BIOLOGIC,100),
    DISOWNED_CHILD_ADOPTED("child_adopted_disowned","Child (Adopted)","",OFFSPRING_ADOPTED,100),
    DISOWNED_CHILD_ADOPTED_MALE("child_adopted_male_disowned","Son (Adopted)","",OFFSPRING_ADOPTED,100),
    DISOWNED_CHILD_ADOPTED_FEMALE("child_adopted_female_disowned","Daughter (Adopted)","",OFFSPRING_ADOPTED,100),
    PARENT("parent","Parent","", PARENT_BIOLOGIC,2),
    FATHER("parent_male","Father","", PARENT_BIOLOGIC,2),
    MOTHER("parent_female","Mother","", PARENT_BIOLOGIC,2),
    PARENT_ADOPTIVE("parent_adoptive","Parent (Adoptive)","",MemberType.PARENT_ADOPTIVE,2),
    FATHER_ADOPTIVE("parent_male_adoptive","Father (Adoptive)","",MemberType.PARENT_ADOPTIVE,2),
    MOTHER_ADOPTIVE("parent_female_adoptive","Mother (Adoptive)","",MemberType.PARENT_ADOPTIVE,2),
    SIBLING("sibling_neutral","Sibling","", SIBLING_BIOLOGIC,100),
    BROTHER("sibling_male","Brother","", SIBLING_BIOLOGIC,100),
    SISTER("sibling_female","Sister","", SIBLING_BIOLOGIC,100),
    SIBLING_ADOPTIVE("sibling_adoptive_neutral","Sibling (Adoptive)","", SIBLING_ADOPTED,100),
    BROTHER_ADOPTIVE("sibling_adoptive_male","Brother (Adoptive)","", SIBLING_ADOPTED,100),
    SISTER_ADOPTIVE("sibling_adoptive_female","Sister (Adoptive)","",SIBLING_ADOPTED,100),
    GRANDPARENT("grandchild","Grandparent","","Great", EXTENDED),
    GRANDFATHER("grandparent_male","Grandfather","","Great",EXTENDED),
    GRANDCHILD("grandchild_neutral","Grandchild","","Great",EXTENDED),
    GRANDMOTHER("grandparent_female","Grandmother","","Great",EXTENDED),
    GRANDSON("grandchild_male","Grandson","","Great",EXTENDED),
    GRANDDAUGHTER("grandchild_female","Granddaughter","","Great",EXTENDED),
    PIBLING("pibling_neutral","Pibling","","Great",EXTENDED),
    UNCLE("pibling_male","Uncle","","Great",EXTENDED),
    AUNT("pibling_female","Aunt","","Great",EXTENDED),
    COUSIN("cousin","Cousin","",EXTENDED,0),
    NIBLING("nibling_neutral","Nibling","","Great",EXTENDED),
    NEPHEW("nibling_male","Nephew","","Great",EXTENDED),
    NIECE("nibling_female","Niece","","Great",EXTENDED);
    
    private final String id;
    private final String stackingWord;
    private final String displayWord;
    private final String description;
    private final MemberType type;
    private final int maxInUnit;
    FamilyRelationship(String id,  String name, String description, String stackingWord, MemberType type, int maxInUnit) {
        this.id = id;
        this.stackingWord = stackingWord;
        this.displayWord = name;
        this.description = description;
        this.type = type;
        this.maxInUnit = maxInUnit;
    }
    FamilyRelationship(String id,  String name, String description, String stackingWord, MemberType type) {
        this.id = id;
        this.stackingWord = stackingWord;
        this.displayWord = name;
        this.description = description;
        this.type = type;
        this.maxInUnit = 0;
    }
    FamilyRelationship(String id, String displayWord, String description,MemberType type, int maxInUnit) {
        this.id = id;
        this.stackingWord = null;
        this.displayWord = displayWord;
        this.description = description;
        this.type = type;
        this.maxInUnit = maxInUnit;
    }
    private static BiMap<FamilyRelationship,PronounContainer> genderedMap = HashBiMap.create();
    
    static {
        genderedMap.put(HEAD_OF_FAMILY,new PronounContainer(HEAD_OF_FAMILY,HEAD_OF_FAMILY,HEAD_OF_FAMILY));
        genderedMap.put(SPOUSE,new PronounContainer(HUSBAND,WIFE,SPOUSE));
        genderedMap.put(LOVER,new PronounContainer(LOVER_MALE,LOVER_FEMALE,LOVER));
        genderedMap.put(EX_SPOUSE,new PronounContainer(EX_SPOUSE_MALE,EX_SPOUSE_FEMALE,EX_SPOUSE));
        genderedMap.put(EX_LOVER,new PronounContainer(EX_LOVER_MALE,EX_LOVER_FEMALE,EX_LOVER));
        genderedMap.put(CONCUBINE,new PronounContainer(CONCUBINE,CONCUBINE,CONCUBINE));
        genderedMap.put(EX_CONCUBINE,new PronounContainer(EX_CONCUBINE,EX_CONCUBINE,EX_CONCUBINE));
        genderedMap.put(CHILD_BORN,new PronounContainer(CHILD_BORN_MALE,CHILD_BORN_FEMALE,CHILD_BORN));
        genderedMap.put(CHILD_ADOPTED,new PronounContainer(CHILD_ADOPTED_MALE,CHILD_ADOPTED_FEMALE,CHILD_BORN));
        genderedMap.put(DISOWNED_CHILD_BORN, new PronounContainer(DISOWNED_CHILD_BORN_MALE, DISOWNED_CHILD_BORN_FEMALE, DISOWNED_CHILD_BORN));
        genderedMap.put(DISOWNED_CHILD_ADOPTED, new PronounContainer(DISOWNED_CHILD_ADOPTED_MALE, DISOWNED_CHILD_ADOPTED_FEMALE, DISOWNED_CHILD_ADOPTED));
        genderedMap.put(PARENT, new PronounContainer(FATHER, MOTHER, PARENT));
        genderedMap.put(SIBLING, new PronounContainer(BROTHER, SISTER, SIBLING));
        genderedMap.put(GRANDPARENT, new PronounContainer(GRANDFATHER, GRANDMOTHER, GRANDPARENT));
        genderedMap.put(GRANDCHILD, new PronounContainer(GRANDSON, GRANDDAUGHTER, GRANDCHILD));
        genderedMap.put(PIBLING, new PronounContainer(UNCLE, AUNT, PIBLING));
        genderedMap.put(COUSIN, new PronounContainer(COUSIN, COUSIN, COUSIN));
        genderedMap.put(NIBLING, new PronounContainer(NEPHEW, NIECE, NIBLING));
    }
    private record PronounContainer(FamilyRelationship male, FamilyRelationship female, FamilyRelationship neutral){

        public FamilyRelationship get(Pronouns pronouns){
            return switch (pronouns){
                case Masculine -> {
                    yield male;
                }
                case Feminine -> {
                    yield female;
                }
                case Neutral -> {
                    yield neutral;
                }
            };
        }
        public boolean contains(FamilyRelationship relationship){
            return male.equals(relationship) || neutral.equals(relationship) || female.equals(relationship);
        }
    };

    public String getId() {
        return id;
    }

    public String getStackingWord() {
        return stackingWord;
    }
    
    public MemberType getType() {
        return type;
    }

    public int getMaxInUnit() {
        return maxInUnit;
    }

    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayWord;
    }

    @Override
    public String getDescription() {
        return description;
    }
    public static FamilyRelationship getByPronoun(FamilyRelationship type, Pronouns pronouns) {
        if(genderedMap.containsKey(type)){
            return genderedMap.get(type).get(pronouns);
        } else{
            AtomicReference<PronounContainer> container = new AtomicReference<>();
            try {
                genderedMap.inverse().forEach((key, value) -> {
                    if (key.contains(type)){
                        container.set(key);
                        throw new NullPointerException("Pronoun container is here");
                    }
                });
            }catch (NullPointerException _){}
            if(container.get() != null){
                return container.get().get(pronouns);
            }
        }
        return null;
    }
    public enum MemberType {
        HEAD(true),
        PARTNER(true),
        PARENT_BIOLOGIC(true),
        PARENT_ADOPTIVE(true),
        SIBLING_BIOLOGIC(true),
        SIBLING_ADOPTED(true),
        OFFSPRING_BIOLOGIC(true),
        OFFSPRING_ADOPTED(true),
        EXTENDED(false);

        private final boolean isCore;

        MemberType(boolean isCore) {
            this.isCore = isCore;
        }
        public boolean isCore() {
            return isCore;
        }
    }
}
