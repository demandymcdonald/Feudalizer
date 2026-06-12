package com.base.worldforge.node.condition;

import com.Global.*;
import com.base.worldforge.WorldForgeScriptError;
import com.base.worldforge.WorldForgeScripting;
import com.base.worldforge.base.ScriptContext;
import com.base.worldforge.lexer.Token;
import com.base.worldforge.lexer.TokenDictionary;
import com.base.worldforge.lexer.TokenType;
import com.base.worldforge.node.ValueNode;

import javax.annotation.Nullable;
import java.util.*;

public abstract class ComparableBooleanCompNode<T extends Comparable<T>> extends BooleanComparisonNode<T,T> {
    public ComparableBooleanCompNode(Token token, ValueNode<?> left, ValueNode<?> right) {
        super(token, convert(token,left), convert(token,right,(ValueNode<T>) left)); //Yes, I know this is unchecked. If the first one fails then this check will never run because the Script Manager will force-kill the parser and refuse to load the script.
    }
    public static <T extends Comparable<T>> ValueNode<T> convert(Token token, ValueNode<?> node){
        return convert(token, node, null);
    }
    public static <T extends Comparable<T>> ValueNode<T> convert(Token token, ValueNode<?> node, @Nullable ValueNode<T> otherNode){
        if(otherNode != null){
            if(node.getOutputType().isAssignableFrom(otherNode.getOutputType()) || otherNode.getOutputType().isAssignableFrom(node.getOutputType()) || (Number.class.isAssignableFrom(node.getOutputType()) && Number.class.isAssignableFrom(otherNode.getOutputType()))){
                return (ValueNode<T>) node;
            }
            WorldForgeScripting.throwScriptError(new WorldForgeScriptError(token.line(),token.column(),true,"Cannot compare "+node.getOutputType().getSimpleName()+" with "+otherNode.getOutputType().getSimpleName()));
            return null;
        } else {
            if (Comparable.class.isAssignableFrom(node.getOutputType())) {
                return (ValueNode<T>) node;
            }
            WorldForgeScripting.throwScriptError(new WorldForgeScriptError(token.line(), token.column(), true, "node " + node + "'s output: " + node.getOutputType() + " is not comparable!"));
            return null;
        }
    }

    @Override
    protected final boolean compare(ScriptContext context) {
        if (Number.class.isAssignableFrom(left.getOutputType()) && Number.class.isAssignableFrom(right.getOutputType())) {
            Number leftNum = (Number) left.evaluate(context);
            Number rightNum = (Number) right.evaluate(context);
            if(leftNum.getClass() == Long.class || rightNum.getClass() == Long.class){
                return doCompare((T) Long.valueOf(leftNum.longValue()), (T) Long.valueOf(rightNum.longValue()));
            }
            return doCompare((T) Double.valueOf(leftNum.doubleValue()), (T) Double.valueOf(rightNum.doubleValue()));
        }
        return doCompare(left.evaluate(context), right.evaluate(context));
    }
    public static class GreaterThanNode<T extends Comparable<T>> extends ComparableBooleanCompNode<T> {
        public GreaterThanNode(Token token, ValueNode<?> left, ValueNode<?> right) {
            super(token, left, right);
        }


        @Override
        public TokenType getType() {
            return TokenDictionary.GREATER_THAN;
        }


        @Override
        protected boolean doCompare(T left, T right) {
            return left.compareTo(right) > 0;
        }
    }
    public static class GreaterThanOrEqualNode<T extends Comparable<T>> extends ComparableBooleanCompNode<T> {
        public GreaterThanOrEqualNode(Token token, ValueNode<?> left, ValueNode<?> right) {
            super(token, left, right);
        }

        @Override
        protected boolean doCompare(T left, T right) {
            return left.compareTo(right) >= 0;
        }

        @Override
        public TokenType getType() {
            return TokenDictionary.GREATER_THAN_OR_EQUAL;
        }
    }
    public static class LessThanNode<T extends Comparable<T>> extends ComparableBooleanCompNode<T> {
        public LessThanNode(Token token, ValueNode<?> left, ValueNode<?> right) {
            super(token, left, right);
        }
        @Override
        protected boolean doCompare(T left, T right) {
            return left.compareTo(right) < 0;
        }

        @Override
        public TokenType getType() {
            return TokenDictionary.LESS_THAN;
        }
    }
    public static class LessThanOrEqualNode<T extends Comparable<T>> extends ComparableBooleanCompNode<T> {
        public LessThanOrEqualNode(Token token, ValueNode<?> left, ValueNode<?> right) {
            super(token, left, right);
        }
        @Override
        protected boolean doCompare(T left, T right) {
            return left.compareTo(right) <= 0;
        }

        @Override
        public TokenType getType() {
            return TokenDictionary.LESS_THAN_OR_EQUAL;
        }
    }
}
