package com.objects.succession.base;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum SuccessionType {

    TESTAMENTARY,
    CONSTITUTIONAL,
    ELECTORAL(CONSTITUTIONAL),
    HEREDITARY(CONSTITUTIONAL),
    APPOINTMENT(CONSTITUTIONAL),
    SENIORITY(CONSTITUTIONAL),
    CO_OPTION(APPOINTMENT,HEREDITARY,TESTAMENTARY,CONSTITUTIONAL,ELECTORAL),
    CHALLENGE_BASED(CONSTITUTIONAL);

     private final Set<SuccessionType> allowedSubSuccessions;
     SuccessionType(Set<SuccessionType> allowedSubSuccessions){
        this.allowedSubSuccessions = ImmutableSet.copyOf(allowedSubSuccessions);
     }
     SuccessionType(){
        this.allowedSubSuccessions = ImmutableSet.of();
     }
    SuccessionType(SuccessionType... allowedSubSuccessions){
        this.allowedSubSuccessions = new ImmutableSet.Builder<SuccessionType>().add(allowedSubSuccessions).build();
    }
}
