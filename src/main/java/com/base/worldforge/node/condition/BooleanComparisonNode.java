package com.base.worldforge.node.condition;

import com.base.worldforge.WorldForgeScriptError;
import com.base.worldforge.WorldForgeScripting;
import com.base.worldforge.base.ScriptContext;
import com.base.worldforge.lexer.Token;
import com.base.worldforge.lexer.TokenDictionary;
import com.base.worldforge.lexer.TokenType;
import com.base.worldforge.node.ValueNode;

public abstract class BooleanComparisonNode<L,R> extends ComparisonNode<Boolean> {
    protected final ValueNode<L> left;
    protected final ValueNode<R> right;
    public BooleanComparisonNode(Token token, ValueNode<L> left, ValueNode<R> right) {
        super(token);
        this.left = left;
        this.right = right;
    }


    @Override
    public final Boolean evaluate(ScriptContext context) {
        return compare(context);
    }

    @Override
    public Class<Boolean> getOutputType() {
        return Boolean.class;
    }

    protected boolean compare(ScriptContext context){
        return doCompare(left.evaluate(context), right.evaluate(context));
    };
    protected abstract boolean doCompare(L left, R right);

    public static class EqualsNode extends BooleanComparisonNode<Object,Object> {
        public EqualsNode(Token token, ValueNode<Object> left, ValueNode<Object> right) {
            super(token, left, right);
        }
        @Override
        protected boolean doCompare(Object left, Object right) {
            return left.equals(right);
        }

        @Override
        public TokenType getType() {
            return TokenDictionary.EQUALS;
        }
    }
    public static class NotEqualsNode extends BooleanComparisonNode<Object,Object> {
        public NotEqualsNode(Token token, ValueNode<Object> left, ValueNode<Object> right) {
            super(token, left, right);
        }
        @Override
        protected boolean doCompare(Object left, Object right) {
            return !left.equals(right);
        }
        @Override
        public TokenType getType() {
            return TokenDictionary.NOT_EQUAL;
        }
    }


}
