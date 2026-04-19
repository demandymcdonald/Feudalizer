//package com.utilities.caching;
//
//import com.utilities.hierarchy.Parented;
//import com.utilities.hierarchy.ParentedDifferent;
//import org.apache.commons.lang3.mutable.MutableBoolean;
//import org.checkerframework.checker.units.qual.C;
//
//public interface DirtyTracking<C extends DirtyTracking<?, ?, ?>,U extends DirtyTracking<C,U,P>,P extends DirtyTracking<U,P,?>> extends ParentedDifferent<C,U,P> {
//    MutableBoolean isDirty();
//    default void setDirty(){
//        isDirty().setTrue();
//        getParent().ifPresent(p -> {p.onChildDirty((U) this);});
//    }
//    void onChildDirty(C child);
//}
