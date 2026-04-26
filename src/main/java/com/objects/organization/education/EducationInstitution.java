package com.objects.organization.education;

import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.datemutable.timeline.change.multi.wrapper.TLSet;
import com.google.gson.JsonObject;
import com.objects.culture.Culture;
import com.objects.culture.tenet.TenetManager;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.organization.NonGovernmentEntity;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.objects.culture.tenet.group.groups.EducationGroups.SCHOOL_TYPE;

public class EducationInstitution extends NonGovernmentEntity<EducationInstitution> {
    private TenetGroup eduType;
    private TLSet<Education> teaches;
    public EducationInstitution(TenetGroup group, DMEReference<EducationInstitution> dme) {
        super(group, dme);
        eduType = validate(group);
    }
    public EducationInstitution(TenetGroup group, String name, LocalDate created, LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<EducationInstitution, ?>> initialState) {
        super(group, name, created, ended, foundingCulture, initialState);
        eduType = validate(group);
    }

    public EducationInstitution(TenetGroup group, String name, UUID id, LocalDate created, @Nullable LocalDate ended, DMEReference<Culture> foundingCulture, List<ChangeSupplier<EducationInstitution, ?>> initialState) {
        super(group, name, id, created, ended, foundingCulture, initialState);
        eduType = validate(group);
    }

    public TenetGroup getEduType(){
        return eduType;
    };
    public Set<Education> canProvide(){
        return new HashSet<>(teaches.asSet());
    }


    @Override
    public void additionalLoad(JsonObject data) {
        super.additionalLoad(data);
        eduType = TenetManager.Group.get(data.get("eduType").getAsString());
    }

    @Override
    public void additionalSave(JsonObject data) {
        super.additionalSave(data);
        data.addProperty("eduType", eduType.id());
    }
    private static TenetGroup validate(TenetGroup group){
        if(group.getParent().isEmpty() || group.isDescendantOf(SCHOOL_TYPE)){
            throw new IllegalArgumentException("Invalid Education Group");
        }
        return group;
    }

    @Override
    protected void onLink() {

    }
}
