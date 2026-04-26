package com.objects.organization;

import com.Global;
import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;

public interface IOrganizedEntity<T extends DateMutableEntity<T> & IOrganizedEntity<T>> {
    DMEReference<? extends AbstractOrganization<?>> getOrganization();
    DMEReference<T> getReference();
    void internalSetOrg(DMEReference<? extends AbstractOrganization<?>> newOrganization);
    default void setOrg(DMEReference<? extends AbstractOrganization<?>> newOrganization){
        getReference().get().getTimeline().addChange(new OrgChange<>(getReference(), Global.getDate(),newOrganization));
    };
}
