package com.base.worldforge.lexer;

import com.base.worldforge.WorldForgeScripting;
import com.google.common.collect.ImmutableList;

import java.util.*;

import static com.base.worldforge.lexer.TokenDictionary.*;

public class Lexer {
    private enum Mode {
        NORMAL,
        NUMBER(true),//Technically does not, but since it handles whitespace internally it'll handle it on its own.
        COMMENT(true),
        BLOCK_COMMENT(true),
        STRING(true), //handles internally too
        STRING_FANTASY(true), //see above
        STRICT_STRING(true),
        ;
        private final boolean containsWhite;
        Mode(boolean containsWhite) {
            this.containsWhite = containsWhite;
        }
        Mode() {
            this.containsWhite = false;
        }

        public boolean containsWhite() {
            return containsWhite;
        }
    }
    final String input;
    final String cleanInput;
    final List<Token> tokens = new ArrayList<>();
    int blockStartCol = -1;
    int blockStartLn = -1;
    int currentLine = 1;
    int currentColumn = 0;
    int globalCurrent = 0;

    StringBuilder cleanSequence = new StringBuilder();
    StringBuilder currentSequence = new StringBuilder();
    Mode mode = Mode.NORMAL;

    public Lexer(String input){
        this.input = input;
        this.cleanInput = prepareString(input);
    }

    public static List<Token> decode(String input) {
        return new Lexer(input).doDecode();
    }
    private List<Token> doDecode() {
        final int chSize = input.length();
        while (globalCurrent < chSize) {
            char c = cleanInput.charAt(globalCurrent);
            char c2 = input.charAt(globalCurrent);
            cleanSequence.append(c);
            currentSequence.append(c2);
            if (Character.isWhitespace(c) && !mode.containsWhite()) {

                tokenEndDetected();
                globalCurrent++;
                currentColumn++;
                continue;
            }
            switch (mode) {
                case NORMAL -> doNormal(c);
                case COMMENT, BLOCK_COMMENT -> doComment();
                case STRING, STRING_FANTASY -> doString(c, false);
                case STRICT_STRING -> doString(c, true);
                case NUMBER -> {
                    if (!Character.isDigit(c) && (c != '.')) {
                        tokenEndDetected();
                        tokenFound(TokenDictionary.NUMBER);
                        continue;
                    }
                }
            }

            globalCurrent++;
            currentColumn++;
        }
        return tokens;
    }

    private void doNormal(char c){
        final String seq = cleanSequence.toString();
        List<TokenType> results = new ArrayList<>(TokenDictionary.DICTIONARY.stream().filter((s) -> anyMatch(s,seq,false)).toList());
        if (Character.isDigit(c)) {
            mode = Mode.NUMBER;
            blockStart();
            return;
        }
        if (results.isEmpty()) {
            mode = Mode.STRING;
        } else if (results.size() == 1) { //Because there can only be one right answer
            TokenType type = results.getFirst();
            if(fantasyNameCheck(type)){
                mode = Mode.STRING_FANTASY;
                return;
            }
            Token t = tokenFound(results.getFirst());
            if (t.getType().equals(TokenDictionary.BLOCK_COMMENT_MARK_START)){
                mode = Mode.BLOCK_COMMENT;
                blockStart();
                return;
            }
            if (t.getType().equals(TokenDictionary.COMMENT_MARK)){
                mode = Mode.COMMENT;
                blockStart();
                return;
            }
            if (t.getType().equals(TokenDictionary.STRICT_STRING_MARK)){
                mode = Mode.STRICT_STRING;
                blockStart();
            }
        }
    }
    private static final ImmutableList<TokenType> fantasyChecks = ImmutableList.of(COLON,VARIABLE,SCOPE,ASSIGNMENT);


    //extra check to make sure the fictional name ['Andor'] isn't lexed as [AND] [OR]. Yes, I know that lexers are supposed to be dumb,
    // but my users might be dumber, or at least dumb in the programming sense. if this causes a single bug I'm just going to axe it.
    private boolean fantasyNameCheck(TokenType match){
        if(!tokens.isEmpty() && !fantasyChecks.stream().anyMatch(type -> type.equals(tokens.getLast().getType())))
            return false;
        if (charactersLeft() < 2) return false;
        char next = cleanInput.charAt(globalCurrent + 1);
        char nextLower = input.charAt(globalCurrent + 1);
        if (anyMatch(match,cleanSequence.toString(),true) && (Character.isLetter(next) && Character.isLowerCase(nextLower))){
            return true;
        };
        return false;
    }
    private void doString(char current, boolean isStrict){
        Optional<String> endMark = isEndOfLine();
        if (endMark.isPresent()) {
            tokenEndDetected();
            if (!isStrict){
                globalCurrent -= 1;
                if (mode == Mode.STRING_FANTASY){
                    WorldForgeScripting.LOGGER.warn("Potential non Strict Stringed Fantasy name: " + cleanSequence.toString() + "... Because I'm an awesome dev I'm treating it as a string but do better! >:(");
                }
                tokenFound(TokenDictionary.STRING);
            } else {
                currentLine++;
                currentColumn = 0;
            }
            return;
        }
        if (isStrict && anyMatch(TokenDictionary.STRICT_STRING_MARK,String.valueOf(current),true)){
            tokenEndDetected();
            tokenFound(TokenDictionary.STRING);
            tokenFound(TokenDictionary.STRICT_STRING_MARK);
        } else if (Character.isWhitespace(current)){
            if (charactersLeft() >= 1){
                char next = cleanInput.charAt(globalCurrent + 1);
                int offset = 1;
                globalCurrent++; // Just to account for the first iteration of next eating one character
                while (Character.isWhitespace(next) && charactersLeft() >= 1){
                     offset++;
                     next = cleanInput.charAt(globalCurrent);
                    globalCurrent++;
                }
                globalCurrent -= offset;
                if (!doIDCheck(next,offset)){
                    tokenEndDetected();
                    tokenFound(TokenDictionary.STRING);
                }
                return;
            }
            tokenEndDetected();
            tokenFound(TokenDictionary.STRING);
        } else {
            doIDCheck(current,0);
        }
    }
    private boolean doIDCheck(char current, int offset){
        if(current == ':'){
            tokenEndDetected(offset);
            globalCurrent += offset;
            tokenFound(PROPERTY_IDENTIFIER);
            return true;
        } else if (current == '{'){
            tokenEndDetected(offset);
            globalCurrent += offset;
            tokenFound(FIELD_IDENTIFIER);
            return true;
        }
        return false;
    }



    private void doComment(){
        boolean isBlock = mode == Mode.BLOCK_COMMENT;

        Optional<String> endMark = isEndOfLine();
        if (endMark.isPresent()) {
            if (!isBlock) {
                handleCommentFound(isBlock, endMark.get());
            } else {
                tokenEndDetected(endMark.get());

                currentLine++;
                currentColumn = 0;
            }
            return;
        }
        if (!isBlock || charactersLeft() < 2) return;
        String sb = cleanInput.substring(globalCurrent, globalCurrent + 2);
        if (anyMatch(TokenDictionary.BLOCK_COMMENT_MARK_END, String.valueOf(sb), false)) {
            handleCommentFound(true, sb);
        }
    }
    private void handleCommentFound(boolean isBlock, String endMark){
        int hold = globalCurrent;
        int end = hold + (endMark.length());
        //Removes the first character of the end of line/comment.
        tokenEndDetected(endMark);
        tokenFound(TokenDictionary.COMMENT_STRING);
        cleanSequence.append(cleanInput.substring(hold, end));
        currentSequence.append(input.substring(hold, end));
        if (isBlock){
            tokenFound(TokenDictionary.BLOCK_COMMENT_MARK_END);
        } else {
            tokenFound(TokenDictionary.LINE_BREAK);
        }
    }
    private Token tokenFound(TokenType newTokenType){
        String cs = this.currentSequence.toString();
        int len = currentColumn - cs.length();
        int lin = currentLine;
        if (len < 1 || (blockStartCol != -1 && lin != blockStartCol)){
            lin = blockStartLn;
            len = blockStartCol;
        }
        Token t = new Token(newTokenType, cs, lin, len);
        if (newTokenType == TokenDictionary.LINE_BREAK) {
            currentLine++;
            currentColumn = 0;
        }
        mode = Mode.NORMAL;
        cleanSequence = new StringBuilder();
        currentSequence = new StringBuilder();
        tokens.add(t);
        blockEnd();
        return t;
    }
    public static String prepareString(String input){
        return input.toUpperCase();
    }
    private static boolean anyMatch(TokenType type, String check, boolean strict){
        if (strict) return (Arrays.stream(type.acceptedSymbols()).anyMatch(check::equals));
        return Arrays.stream(type.acceptedSymbols()).anyMatch(check::startsWith);
    }
    private Optional<String> isEndOfLine(){
        if (charactersLeft() < 1) return Optional.empty();
        String sb = cleanInput.substring(globalCurrent, globalCurrent + 1);
        boolean matchShortLine = anyMatch(TokenDictionary.LINE_BREAK, sb, true);
        if (charactersLeft() < 2) {
            if (matchShortLine) {
                return Optional.of(sb);
            } else {
                return Optional.empty();
            }
        }
        String sb2 = cleanInput.substring(globalCurrent, globalCurrent + 2); //For windows newline
        boolean matchLongLine = anyMatch(TokenDictionary.LINE_BREAK, sb2, true);
        if (matchLongLine) {
            return Optional.of(sb2);
        } else if (matchShortLine) {
            return Optional.of(sb);
        } else {
            return Optional.empty();
        }
    }
    private void tokenEndDetected(){
        tokenEndDetected(0,"");
    }
    private void tokenEndDetected(String pull){
        tokenEndDetected(0, pull);
    }
    private void tokenEndDetected(int additionalOffset){
        tokenEndDetected(additionalOffset, "");
    }
    private void tokenEndDetected(int additional, String pull){
        int seqSize = currentSequence.length() - (1 + additional);
        int size = cleanSequence.length() - (1 + additional);
        currentSequence.setLength(seqSize);
        cleanSequence.setLength(size);
        if (!pull.isEmpty()){
            int len = pull.length();
            globalCurrent += len;
            currentColumn += len;
        }
    }
    private void blockStart(){
        blockStartCol = currentColumn;
        blockStartLn = currentLine;
    }
    private void blockEnd(){
        blockStartCol = -1;
        blockStartLn = -1;
    }
    private int charactersLeft(){
        return cleanInput.length() - globalCurrent;
    }
}
