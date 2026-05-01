package com.objects.culture;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.objects.culture.object.CultureObject;
import com.objects.culture.object.ICultureObject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.*;

public abstract class AbstractCulture<T extends AbstractCulture<T>> extends DateMutableEntity<T> {
    private final Set<DMEReference<? extends ICultureObject>> linked_followers = new HashSet<>();
    private final Multimap<ICultureObject.Type, DMEReference<? extends ICultureObject>> followers = HashMultimap.create();
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
    public void doDateChange() {
        super.onDateChange();
    }
    public final void linkFollower(DMEReference<? extends ICultureObject> follower){
        linked_followers.add(follower);
        followers.put(follower.get().getType(),follower);
    }
    public final  void unlinkFollower(DMEReference<? extends ICultureObject> follower){
        linked_followers.remove(follower);
        followers.remove(follower.get().getType(),follower);
    }
    public final Set<DMEReference<? extends ICultureObject>> getLinkedFollowers() {
        return new HashSet<>(linked_followers);
    }
}
