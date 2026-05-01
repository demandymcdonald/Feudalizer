package com.objects.culture.tenet.group;

import com.objects.culture.tenet.Acceptance;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.objects.culture.tenet.group.TenetGroup.*;

public enum TGType {
    SORT_ONLY(null,new GroupTypeContainer(0, Acceptance.getAll())),
    PILLAR_SYSTEM(SORT_ONLY,new GroupTypeContainer(1, Acceptance.CORE, Acceptance.CORE_FANATIC),new GroupTypeContainer(2, Acceptance.INTEGRATED)),
    PILLAR_IDEOLOGY(SORT_ONLY,new GroupTypeContainer(1, Acceptance.CORE, Acceptance.CORE_FANATIC),new GroupTypeContainer(2, Acceptance.INTEGRATED)),
    SYSTEM_SORT(PILLAR_SYSTEM,new GroupTypeContainer(0, Acceptance.getAll())),
    SYSTEM_LARGE(SYSTEM_SORT,new GroupTypeContainer(SYSTEM_MAX, Acceptance.INTEGRATED, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED)),
    SYSTEM_SMALL(SYSTEM_LARGE,new GroupTypeContainer(SYSTEM_MAX/2, Acceptance.INTEGRATED, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED)),
    CASTE_SYSTEM(SYSTEM_SORT,new GroupTypeContainer(SYSTEM_MAX/2, Acceptance.INTEGRATED, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED)),
    IDEOLOGY_SORT(PILLAR_IDEOLOGY,new GroupTypeContainer(0, Acceptance.getAll())),
    BELIEF_MAJOR(IDEOLOGY_SORT,new GroupTypeContainer(BELIEF_MAX/2, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED),new GroupTypeContainer(BELIEF_MAX/2, Acceptance.ACCEPTED)),
    DIVINE_ENTITY(IDEOLOGY_SORT,new GroupTypeContainer(99, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED),new GroupTypeContainer(BELIEF_MAX/2, Acceptance.ACCEPTED)),
    BELIEF_MINOR(BELIEF_MAJOR,new GroupTypeContainer(BELIEF_MAX, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED),new GroupTypeContainer(BELIEF_MAX * 2, Acceptance.ACCEPTED)),
    LANGUAGE(IDEOLOGY_SORT,new GroupTypeContainer(LANGUAGE_MAX, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED),new GroupTypeContainer(LANGUAGE_MAX * 2, Acceptance.ACCEPTED)),
    SOCIETY_ATTITUDE(IDEOLOGY_SORT,new GroupTypeContainer(SYSTEM_MAX/2, Acceptance.INTEGRATED, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED)),
    TRADITION(IDEOLOGY_SORT,new GroupTypeContainer(TRADITION_MAX, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED),new GroupTypeContainer(TRADITION_MAX * 4, Acceptance.ACCEPTED)),
    VALUE(IDEOLOGY_SORT,new GroupTypeContainer(VALUE_MAX, Acceptance.CORE, Acceptance.CORE_FANATIC, Acceptance.INTEGRATED),new GroupTypeContainer(VALUE_MAX * 3, Acceptance.ACCEPTED)),
    AESTHETIC(IDEOLOGY_SORT,new GroupTypeContainer(1,Acceptance.CORE, Acceptance.CORE_FANATIC),new GroupTypeContainer(AESTHETIC_MAX, Acceptance.INTEGRATED),new GroupTypeContainer(AESTHETIC_MAX * 10, Acceptance.ACCEPTED)),
    ;
    private final TGType parent;
    private final GroupTypeContainer[] maxOfEach;
    private static final int DEFAULT_MAX = 50;
    TGType(@Nullable TGType parent, GroupTypeContainer... maxOfEach) {
        this.maxOfEach = maxOfEach;
        this.parent = parent;
    }

    public GroupTypeContainer[] getMaxOfEach() {
        return maxOfEach;
    }
    public int getMaxFor(Acceptance acceptance){
        for(GroupTypeContainer container : maxOfEach){
            if(Arrays.stream(container.scope()).anyMatch((a) -> {return a.equals(acceptance);})){
                return container.maxNumber();
            }
        }
        return DEFAULT_MAX;
    }
    public TGType getParent() {
        return parent;
    }

    public boolean isParentOf(TGType child, boolean includePeer){
        if(includePeer && (child == this || child.getParent() == this.getParent())){
            return true;
        }
        List<TGType> ancestors = getAncestors();
        return ancestors.contains(this);
    }
    public List<TGType> getAncestors(){
        List<TGType> ancestors = new ArrayList<>();
        TGType current = this.getParent();
        while(current != null){
            ancestors.add(current);
            current = current.getParent();
        }
        return ancestors;
    }
    public boolean isChildOf(TGType parent, boolean includePeer){
        return parent.isParentOf(this, includePeer);
    }
}
