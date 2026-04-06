package com.objects.family.extended_family;

public enum ExtendedRelationship{
    Husband("husband","Husband"),
    Wife("wife","Wife"),
    ExHusband("ex_husband","Ex-Husband"),
    ExWife("ex_wife","Ex-Wife"),
    Lover("lover","Lover"),
    Concubine("concubine","Concubine"),
    ExConcubine("ex_concubine","Ex-Concubine"),
    ExLover("ex_lover","Ex-Lover"),
    Neutral_ExPartner("partner","Partner"),
    Neutral_Partner("partner_neutral","Partner"),
    Father("parent_male","Father"),
    Mother("parent_female","Mother"),
    Neutral_Parent("parent_neutral","Parent"),
    Son("child_male","Son"),
    Daughter("child_female","Daughter"),
    Neutral_Child("child_neutral","Child"),
    Brother("sibling_male","Brother"),
    Sister("sibling_female","Sister"),
    Neutral_Sibling("sibling_neutral","Sibling"),
    GrandFather("grandparent_male","Grandfather","Great"),
    GrandMother("grandparent_female","Grandmother","Great"),
    Neutral_Grandparent("grandchild_neutral","Grandparent"),
    GrandSon("grandchild_male","Grandson","Great"),
    GrandDaughter("grandchild_female","Granddaughter","Great"),
    Neutral_Grandchild("grandchild_neutral","Grandchild"),
    Cousin("cousin","Cousin"),
    Aunt("pibling_female","Aunt","Great"),
    Uncle("pibling_male","Uncle","Great"),
    Neutral_Pibling("pibling_neutral","Pibling"),
    Nephew("nibling_male","Nephew"),
    Niece("nibling_female","Niece"),
    Neutral_Nibling("nibling_neutral","Nibling");
    private final String id;
    private final String stackingWord;
    private final String displayWord;
    ExtendedRelationship(String id, String display, String stackingWord){
        this.id = id;
        this.displayWord = display;
        this.stackingWord = stackingWord;
    }
    ExtendedRelationship(String id, String display){
        this(id, display,null);
    }

    public String getId() {
        return id;
    }

    public String getStackingWord() {
        return stackingWord;
    }
    public String getDisplayWord() {
        return displayWord;
    }

    public boolean usesStacking() {
        return stackingWord != null;
    }
}
