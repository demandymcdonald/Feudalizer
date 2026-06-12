package com.base.worldforge.node.base;

import com.base.worldforge.base.ScriptContext;
import com.base.worldforge.lexer.Token;
import com.base.worldforge.lexer.TokenDictionary;
import com.base.worldforge.lexer.TokenType;
import com.base.worldforge.node.ValueNode;

public abstract class LiteralNode<T> extends ValueNode<T> {
    private final T value;
    public LiteralNode(T value, String source, long ipID, long pkID) {
        super(source, ipID, pkID);
        this.value = value;
    }
    public LiteralNode(Token value) {
        super(value.getSource(), value.getIPID(), value.getPKID());
        if(!isProper(value)){
            throw new IllegalArgumentException("Token: " + value + " is not a proper literal for " + this.getClass().getSimpleName());
        }
        this.value = convert(value);
    }
    public T getLiteral(){
        return value;
    }
    /**
     * Converts a given Token instance into an instance of type T.
     * This method is intended to be implemented by subclasses to define how a Token
     * should be transformed into the corresponding value of type T.
     * @implSpec Is called by the constructor
     * @param token the Token instance to be converted
     *
     * @return the converted value of type T
     */
    protected abstract T convert(Token token);

    /**
     * Checks if the given token is proper for this instance by comparing its type
     * with the type of this object.
     *
     * @param token the {@code Token} instance to be validated.
     * @implSpec Is called by the constructor
     * @return {@code true} if the token's type matches this object's type,
     *         {@code false} otherwise.
     */
    protected boolean isProper(Token token){
        return token.getType().equals(this.getType());
    };

    @Override
    public final Class<T> getOutputType() {
        return (Class<T>) value.getClass();
    }

    @Override
    public T evaluate(ScriptContext context) {
        return value;
    }
    public static class BooleanLiteralNode extends LiteralNode<Boolean> {
        public BooleanLiteralNode(Token source) {
            super(source);
        }
        @Override
        public TokenType getType() {
            return TokenDictionary.BOOLEAN;
        }
        @Override
        protected Boolean convert(Token token) {
            return token.value().equalsIgnoreCase("TRUE");
        }
    }
    public static abstract class NumberLiteralNode<N extends Number> extends LiteralNode<N> {
        private NumberLiteralNode(Token value) {
            super(value);
        }
        @Override
        public TokenType getType() {
            return TokenDictionary.NUMBER;
        }
        public abstract boolean isWhole();
        public abstract Class<? extends Number> getNumberType();

        public static NumberLiteralNode<?> of(Token value){
            if(value.value().contains(".")){
                return new DoubleNumberNode(value);
            } else {
                long l = Long.parseLong(value.value());
                if(l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE){
                    return new IntNumberNode(value);
                } else {
                    return new LongNumberNode(value);
                }
            }
        }
    }
    public static class IntNumberNode extends NumberLiteralNode<Integer> {
        protected IntNumberNode(Token value) {
            super(value);
        }

        @Override
        public boolean isWhole() {
            return true;
        }

        @Override
        public Class<? extends Number> getNumberType() {
            return Integer.class;
        }

        @Override
        protected Integer convert(Token token) {
            return token.value().isEmpty() ? 0 : Integer.parseInt(token.value());
        }
    }
    //I may code in a way to explicitly make something a float, but looking at the wider codebase I never use it. But I'll still leave the object here.
    public static class FloatNumberNode extends NumberLiteralNode<Float> {
        protected FloatNumberNode(Token value) {
            super(value);
        }

        @Override
        protected Float convert(Token token) {
            return token.value().isEmpty() ? 0f : Float.parseFloat(token.value());
        }

        @Override
        public boolean isWhole() {
            return getLiteral() % 1 == 0;
        }

        @Override
        public Class<? extends Number> getNumberType() {
            return Float.class;
        }
    }
    public static class DoubleNumberNode extends NumberLiteralNode<Double> {
        protected DoubleNumberNode(Token value) {
            super(value);
        }
        @Override
        protected Double convert(Token token) {
            return token.value().isEmpty() ? 0d : Double.parseDouble(token.value());
        }
        @Override
        public boolean isWhole() {
            return getLiteral() % 1 == 0;
        }
        @Override
        public Class<? extends Number> getNumberType() {
            return Double.class;
        }
    }
    public static class LongNumberNode extends NumberLiteralNode<Long> {
        protected LongNumberNode(Token value) {
            super(value);
        }
        @Override
        protected Long convert(Token token) {
            return token.value().isEmpty() ? 0L : Long.parseLong(token.value());
        }
        @Override
        public boolean isWhole() {
            return true;
        }

        @Override
        public Class<? extends Number> getNumberType() {
            return Long.class;
        }
    }
    public static class StringLiteralNode extends LiteralNode<String> {
        public StringLiteralNode(Token value) {
            super(value);
        }
        @Override
        protected boolean isProper(Token token) {
            return super.isProper(token);
        }
        @Override
        public TokenType getType() {
            return TokenDictionary.STRING;
        }
        @Override
        protected String convert(Token token) {
            return token.value();
        }
    }
}
