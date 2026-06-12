package com.base.worldforge.lexer;

import com.Global.*;

import javax.annotation.Nullable;
import java.util.*;

public record TokenType(String name, int order, NestType type, String... acceptedSymbols) {

    public enum NestType{
        NONE(null),
        OPEN_BRACKET(TokenDictionary.LBRACKET),
        OPEN_PARENTHESIS(TokenDictionary.LPAREN),
        OPEN_BRACE(TokenDictionary.LBRACE),
        CLOSE_BRACKET(TokenDictionary.RBRACKET),
        CLOSE_PARENTHESIS(TokenDictionary.RPAREN),
        CLOSE_BRACE(TokenDictionary.RBRACE),
        ;
        private final TokenType tokenType;
        NestType(@Nullable TokenType tokenType) {
            this.tokenType = tokenType;
        }
        public TokenType getToken(){
            return tokenType;
        }
        public static NestType getPairing(NestType type){
            return switch (type){
                case NONE -> NONE;
                case OPEN_BRACKET -> CLOSE_BRACKET;
                case OPEN_PARENTHESIS -> CLOSE_PARENTHESIS;
                case OPEN_BRACE -> CLOSE_BRACE;
                case CLOSE_BRACKET -> OPEN_BRACKET;
                case CLOSE_PARENTHESIS -> OPEN_PARENTHESIS;
                case CLOSE_BRACE -> OPEN_BRACE;
            };
        }
    }
    public TokenType{
        TokenDictionary.DICTIONARY.add(this);
    }
    public TokenType(String name, int order,  String... acceptedSymbols){
        this(name,order,NestType.NONE,acceptedSymbols);
    }
    public TokenType(String name, String... acceptedSymbols) {
        this(name, 5, NestType.NONE, acceptedSymbols);
    }
    public TokenType(String name, NestType type, String... acceptedSymbols) {
        this(name, 5, type, acceptedSymbols);
    }
    public boolean isNester(){
        return type != NestType.NONE;
    }
    public NestType getType(){
        return type;
    }
    public @Nullable TokenType getImplicitSymbol(){
        return type.getToken();
    }
}
