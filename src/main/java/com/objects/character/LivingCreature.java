package com.objects.character;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.character.sentient.HumanCharacter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class LivingCreature<T extends LivingCreature<T>> extends DateMutableEntity<T> {
    public enum Gender {
        Male("Male", Pronouns.Masculine),
        Female("Female", Pronouns.Feminine),
        Trans_Male("Trans-Male", Pronouns.Masculine),
        Trans_Female("Trans-Female", Pronouns.Feminine),
        Non_Binary("Non-Binary", Pronouns.Neutral);
//        Other("Other");

        private final String display;
        private final Pronouns pronouns;
        Gender(String d, Pronouns pronouns){
            display = d;
            this.pronouns = pronouns;
        }
        public String getFlavor(){
            return display;
        }
        public Pronouns getPronouns(){
            return pronouns;
        }
    }
    public enum Pronouns {
        Masculine,
        Feminine,
        Neutral,
    }
    public LivingCreature(LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T,?>> initialState) {
        super(created, ended, initialState);
    }

    public LivingCreature(DMEReference<T> dme) {
        super(dme);
    }

    public LivingCreature(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }
}
