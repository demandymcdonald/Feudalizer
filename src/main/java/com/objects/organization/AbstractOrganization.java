package com.objects.organization;

import com.Global.*;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeSupplier;
import com.objects.culture.Culture;
import com.objects.culture.tenet.dynamic.DynamicTenet;
import com.objects.culture.tenet.group.TenetGroup;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AbstractOrganization<T extends AbstractOrganization<T>> extends DynamicTenet<T>{

    public AbstractOrganization(TenetGroup group, DMEReference<T> dme) {
        super(group, dme);
    }

    public AbstractOrganization(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, created, ended, foundingCulture, initialState);
    }

    public AbstractOrganization(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<T, ?>> initialState) {
        super(group, name, id, created, ended, foundingCulture, initialState);
    }






    @Override
    protected void onLink() {

    }

    @Override
    public void internalSetCulture(DMEReference<Culture> culture) {

    }
}
