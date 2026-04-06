package com.objects.culture.tenet;

import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.culture.Culture;
import com.objects.culture.CultureObject;
import com.utilities.Displayable;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class Tenet<T extends Tenet<T>> extends CultureObject<T> {
    public Tenet(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Tenet(DMEReference<T> dme) {
        super(dme);
    }

    public Tenet(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }


    public enum Acceptance implements Displayable {
        CORE("tp_core","Core","This tenet is a core part of the culture",256),
        IMPORTANT("tp_important","Important","This tenet is important to the culture.",192),
        ACCEPTED("tp_accepted","Accepted","This tenet is accepted by the culture.",128),
        NEUTRAL_POSITIVE("tp_lukewarm","Lukewarm","This tenet is lukewarm to the culture.",64),
        NEUTRAL("tp_neutral","Neutral","This tenet is neutral to the culture.",0),
        NEUTRAL_NEGATIVE("tp_tepid","Tepid","This tenet is tepid to the culture.",-64),
        REJECTED("tp_rejected","Rejected","This tenet is disliked by the culture.",-128),
        SHUNNED("tp_shunned","Shunned","This tenet is shunned by the culture.",-192),
        PERSECUTED("tp_persecuted","Persecuted","This tenet is persecuted or banned by the culture.",-256),

        ;


        private final String id;
        private final String name;
        private final String description;
        private final int floor;
        Acceptance(String id, String name, String description, int floor){
            this.id = id;
            this.name = name;
            this.description = description;
            this.floor = floor;
        }



        @Override
        public String getID() {
            return "";
        }

        @Override
        public String displayName() {
            return "";
        }

        @Override
        public String description() {
            return "";
        }
    }
}
