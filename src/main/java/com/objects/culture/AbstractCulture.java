package com.objects.culture;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public abstract class AbstractCulture<T extends AbstractCulture<T>> extends DateMutableEntity<T> {
    private final Set<DMEReference<? extends T>> linked_followers = new HashSet<>();

    public AbstractCulture(LocalDate created, LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(created, ended, initialState);
    }
    public AbstractCulture(DMEReference<T> dme) {
        super(dme);
    }

    public AbstractCulture(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<T, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    @Override
    public void onDateChange() {
        super.onDateChange();
        linked_followers.clear();
    }
    public final void linkFollower(DMEReference<? extends T> follower){
        linked_followers.add(follower);
    }
    public final Set<DMEReference<? extends T>> getLinkedFollowers() {
        return new HashSet<>(linked_followers);
    }
}
