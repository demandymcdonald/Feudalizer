package com.base.worldforge.node.base;

import com.base.worldforge.node.ValueNode;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface INestedResult<T> extends INode<T> {

    @NonNull ValueNode<T> getNestedResult();
    default Class<T> getOutputType(){
        return getNestedResult().getOutputType();
    };
}
