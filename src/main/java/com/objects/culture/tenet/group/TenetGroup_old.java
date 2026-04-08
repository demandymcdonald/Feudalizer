//package com.objects.culture.tenet.group;
//
//import com.google.common.collect.ImmutableList;
//import com.objects.culture.tenet.Acceptance;
//import com.utilities.Displayable;
//import org.checkerframework.checker.nullness.qual.Nullable;
//
//import java.util.Optional;
//
//import static com.objects.culture.tenet.TenetVariables.*;
//
//public enum TenetGroup_old implements Displayable {
//
//        SOFT_CULTURE(CULTURE,"soft", "Soft Culture", "", SORT_ONLY),//Only should be used for ui sorting, not as an actual tenet.
//
//
//
//
//
//    private final Optional<TenetGroup_old> parent;
//    private final ImmutableList<TenetGroup_old> connected;
//    private final String id;
//    private final String name;
//    private final String description;
//    private final AcceptanceContainer[] isExclusive;
//
//
//
//    TenetGroup_old(@Nullable TenetGroup_old parent, ImmutableList<TenetGroup_old> connected, String id, String name, String description, AcceptanceContainer... isExclusive) {
//        this.parent = Optional.ofNullable(parent);
//        this.connected = connected;
//        if(parent != null){
//            this.id = parent.getID() + ":" + id;
//        } else {
//            this.id = id;
//        }
//        this.name = name;
//        this.description = description;
//        this.isExclusive = isExclusive;
//    }
//    TenetGroup_old(@Nullable TenetGroup_old parent, String id, String name, String description, AcceptanceContainer... isExclusive) {
//        this(parent, ImmutableList.of(),id, name, description,isExclusive);
//    }
//    TenetGroup_old(String id, String name, String description, AcceptanceContainer... isExclusive) {
//        this(null, ImmutableList.of(),id, name, description,isExclusive);
//    }
//    TenetGroup_old(String id, String name, String description) {
//        this(null, ImmutableList.of(),id, name, description);
//    }
//
//
//    @Override
//    public String getID() {
//        return id;
//    }
//
//    @Override
//    public String displayName() {
//        return name;
//    }
//
//    @Override
//    public String description() {
//        return description;
//    }
//    public Optional<TenetGroup_old> getParent(){
//        return parent;
//    }
//    public boolean isParentOf(TenetGroup_old group){
//        while(group != null){
//            Optional<TenetGroup_old> parentTenetGroup = group.getParent();
//            if(parentTenetGroup.isPresent()){
//                TenetGroup_old tenetGroup = parentTenetGroup.get();
//                if(tenetGroup.equals(this)){
//                    return true;
//                } else {
//                    group = tenetGroup;
//                }
//            } else {
//                return false;
//            }
//        }
//        return false;
//    }
//    public boolean isChildOf(TenetGroup_old group){
//        return group.isParentOf(this);
//    }
//    public record AcceptanceContainer(int maxNumber,Acceptance... accept){}
//}
