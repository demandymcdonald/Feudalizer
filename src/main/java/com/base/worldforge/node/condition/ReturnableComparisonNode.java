package com.base.worldforge.node.condition;

import com.base.worldforge.base.ScriptContext;
import com.base.worldforge.lexer.Token;
import com.base.worldforge.lexer.TokenType;
import com.base.worldforge.node.ValueNode;
import com.base.worldforge.node.base.INestedResult;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;

//If, ElseIf, etc.
public class ReturnableComparisonNode<T> extends ComparisonNode<T> implements INestedResult<T> {
    private final BooleanComparisonNode condition;
    private final ValueNode<T> result;
    private final TokenType type;
    @Nullable private final ValueNode<T> elseNode;
    public ReturnableComparisonNode(Token token, BooleanComparisonNode condition, ValueNode<T> result, @Nullable ValueNode<T> elseNode) {
        super(token);
        this.type = token.getType();
        this.condition = condition;
        this.result = result;
        this.elseNode = elseNode;
    }


    @Override
    public @NonNull ValueNode<T> getNestedResult() {
        return result;
    }



    @Override
    public T evaluate(ScriptContext context) {
        boolean output = compare(context);
        return output ? result.evaluate(context) : elseNode != null ? elseNode.evaluate(context) : null;
    }

    @Override
    protected final boolean compare(ScriptContext context) {
        return condition.compare(context);
    }
    @Override
    public TokenType getType() {
        return type;
    }

}
