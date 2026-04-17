package com.objects.character.sentient;

import com.objects.character.Sex;

public enum Gender {
    Male("Male", Pronouns.Masculine,Sex.MALE),
    Female("Female", Pronouns.Feminine,Sex.FEMALE),
    Trans_Male("Trans-Male", Pronouns.Masculine,Sex.FEMALE),
    Trans_Female("Trans-Female", Pronouns.Feminine, Sex.MALE),
    Non_Binary_Male("Non-Binary", Pronouns.Neutral,Sex.MALE),
    Non_Binary_Female("Non-Binary",Pronouns.Neutral,Sex.FEMALE);
//        Other("Other");

    private final String display;
    private final Pronouns pronouns;
    private final Sex sexAtBirth;
    Gender(String d, Pronouns pronouns,Sex sex) {
        sexAtBirth = sex;
        display = d;
        this.pronouns = pronouns;
    }

    public String getFlavor() {
        return display;
    }

    public Pronouns getPronouns() {
        return pronouns;
    }
    public Sex getSexAtBirth(){
        return sexAtBirth;
    }
}
