package com.base.worldforge.parser;

import com.Global.*;
import com.base.worldforge.lexer.TokenDictionary;
import com.base.worldforge.lexer.TokenType;

import java.util.*;

public class PatternDictionary {
    private static final Set<ParserPattern<?,?>> DICTIONARY = new HashSet<>();
    private static final Set<TokenType> STRIPPED_TYPES = new HashSet<>();

    static {
        STRIPPED_TYPES.add(TokenDictionary.COMMENT_MARK);
        STRIPPED_TYPES.add(TokenDictionary.COMMENT_STRING);
        STRIPPED_TYPES.add(TokenDictionary.BLOCK_COMMENT_MARK_END);
        STRIPPED_TYPES.add(TokenDictionary.LINE_BREAK);
        STRIPPED_TYPES.add(TokenDictionary.BLOCK_COMMENT_MARK_START);
    }



    public static void register(ParserPattern<?,?> pattern){
        DICTIONARY.add(pattern);
    }

    public static Set<ParserPattern<?,?>> get() {
        return Collections.unmodifiableSet(DICTIONARY);
    }
    public static Set<TokenType> getStrippedTypes() {
        return Collections.unmodifiableSet(STRIPPED_TYPES);
    }


}
