package com.base.worldforge.node.condition;

import com.base.worldforge.base.ScriptContext;
import com.base.worldforge.lexer.Token;
import com.base.worldforge.node.ValueNode;

import javax.annotation.Nullable;

public abstract class ComparisonNode<T> extends ValueNode<T> {
    public ComparisonNode(Token token) {
        super(token);

    }
    protected abstract boolean compare(ScriptContext context);
}
