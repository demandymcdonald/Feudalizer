package com.utilities.hierarchy;

import java.util.Set;

public interface ParentedDifferent<C extends ParentedDifferent<?,C,U>,U extends ParentedDifferent<C,U,P>,P extends ParentedDifferent<U,P,?>> extends Parented<P>{
    Set<C> getChildren();
}
