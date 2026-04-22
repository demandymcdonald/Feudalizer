package com.objects.culture.tenet.factory;

import com.Global.*;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.error.IResolution;
import com.base.timeline.error.SandboxCode;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.dynamic.DynamicTenet;

public abstract class TenetResolution<T extends DynamicTenet<T>,D extends DateMutableEntity<D> & CultureObject<D>> implements IResolution<T, TenetReference, DMEReference<D>, DMEReference<Culture> , DMEReference<Culture>> {
    private final String id;
    private final String display;
    private final String description;
    private final boolean isExclusive;
    private final int priority;
    private final SandboxCode expectedCode;
    public TenetResolution(String id, String display, String description, int priority, boolean isExclusive, SandboxCode expectedCode){
        this.id = id;
        this.display = display;
        this.description = description;
        this.isExclusive = isExclusive;
        this.expectedCode = expectedCode;
        this.priority = priority;
    }



    public SandboxCode resolve(DMEReference<? extends T> potentialParent, TenetReference potentialChild, DMEReference<D> relatedObject){
        return resolve(potentialParent,potentialChild,relatedObject,potentialParent.get().getCulture(),potentialChild.get().getCulture());
    }
    @Override
    public abstract SandboxCode resolve(DMEReference<? extends T> potentialParent, TenetReference potentialChild, DMEReference<D> relatedObject, DMEReference<Culture>  potentialParentCulture, DMEReference<Culture> potentialChildCulture);

    @Override
    public String getDisplayID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return display;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public SandboxCode getExpectedCode() {
        return expectedCode;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isExclusive() {
        return isExclusive;
    }

    @Override
    public String getID() {
        return id;
    }
}
