package com.objects.organization.labor;

import com.objects.culture.tenet.interest.InterestGroup;
import com.objects.organization.AbstractOrganization;

import java.util.*;

public class LaborUnion extends AbstractOrganization<LaborUnion> {
    private final Set<InterestGroup> represented = new HashSet<>();
    protected final TreeMap<Integer,UnionLocal> chapters = new TreeMap<>();
    public Set<InterestGroup> getRepresented(){
        return represented;
    }

    @Override
    public Set<LaborUnion> getRelevantUnions() {
        return Set.of(this);
    }

    @Override
    public void doDateChange() {
        super.doDateChange();
        chapters.clear();
    }
    public UnionLocal newChapter(IUnionizable<?> unionizable){
        int candidate = 1;
        for (Integer key : chapters.keySet()) { // keySet() is sorted ascending
            if (key > candidate) break;    // gap found
            if (key == candidate) candidate++;
        }
        UnionLocal local = new UnionLocal(this.getReference(), candidate);
        local.link(unionizable);
        return local;
    }
    @Override
    protected void onLink() {

    }
}
