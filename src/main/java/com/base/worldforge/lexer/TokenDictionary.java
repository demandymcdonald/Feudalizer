package com.base.worldforge.lexer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.base.worldforge.lexer.TokenType.NestType.*;

public class TokenDictionary {
    public static final List<TokenType> DICTIONARY = new ArrayList<>();
    public static final TokenType LPAREN = new TokenType("L Paren", "(");
    public static final TokenType RPAREN = new TokenType("R Paren", ")");
    public static final TokenType LBRACKET = new TokenType("L Bracket", "[");
    public static final TokenType RBRACKET = new TokenType("R Bracket", "]");
    public static final TokenType LBRACE = new TokenType("L Brace", "{");
    public static final TokenType RBRACE = new TokenType("R Brace", "}");
    public static final TokenType PROPERTY_IDENTIFIER = new TokenType("Identifier",OPEN_BRACKET);
    public static final TokenType FIELD_IDENTIFIER = new TokenType("Field Identifier",OPEN_BRACKET);
    public static final TokenType VARIABLE = new TokenType("Variable Atomic",3, OPEN_BRACKET,"VAR:","VARIABLE:");
    public static final TokenType FILE_PATH = new TokenType("FilePath Atomic", 3,OPEN_BRACKET,"FILE:"); // Used to point to a texture, icon, or other thing in the datapack.
    public static final TokenType IMPORT = new TokenType("Import", 3,OPEN_BRACKET,"IMPORT:"); //May not use, but is a fancy way of bringing defined variables, scopes, and functions from one file to another. Would piggyback off of FILE's Code
    public static final TokenType SCOPE = new TokenType("Scope Atomic", 3,OPEN_BRACKET,"SCOPE:");
    public static final TokenType BOOLEAN = new TokenType("Boolean",3,"TRUE","FALSE");
    public static final TokenType COLON = new TokenType("Colon", 6,":");
    public static final TokenType EQUALS = new TokenType("Equals", 4,"==","EQUALS");
    public static final TokenType ASSIGNMENT = new TokenType("Assignment", "=");
    public static final TokenType NUMBER = new TokenType("Number");
    public static final TokenType PLUS = new TokenType("Plus", "+");
    public static final TokenType MINUS = new TokenType("Minus", "-");
    public static final TokenType MULTIPLY = new TokenType("Multiply", "*");
    public static final TokenType DIVIDE = new TokenType("Divide", "/");
    public static final TokenType MODULUS = new TokenType("Modulus", "%");
    public static final TokenType POWER = new TokenType("Power", "^");
    public static final TokenType ROOT = new TokenType("Root", "ROOT","√");
    public static final TokenType FUNCTION = new TokenType("Function Atomic", OPEN_BRACKET,"FUNC","FUNCTION"); //May not use, Functions will be treated as code blocks that produce a var or a scope. but are treated as standalone methods effectively.
    public static final TokenType LESS_THAN = new TokenType("LessThan", "<","LESS_THAN");
    public static final TokenType GREATER_THAN = new TokenType("Greater Than", ">","GREATER_THAN");
    public static final TokenType LESS_THAN_OR_EQUAL = new TokenType("Less Than Or Equal", 4,"<=","LESS_THAN_EQUAL");
    public static final TokenType GREATER_THAN_OR_EQUAL = new TokenType("Greater Than Or Equal", 4,">=","GREATER_THAN_EQUAL");
    public static final TokenType NOT_EQUAL = new TokenType("Not Equal", 4,"!=","NOT_EQUAL");
    public static final TokenType AND = new TokenType("And", OPEN_PARENTHESIS,"&&","AND");
    public static final TokenType OR = new TokenType("Or", OPEN_PARENTHESIS,"||","OR");
    public static final TokenType NOT = new TokenType("Not", OPEN_PARENTHESIS,"!","NOT");
    public static final TokenType IF = new TokenType("If",4,OPEN_PARENTHESIS,"IF");
    public static final TokenType THEN = new TokenType("Then",4,OPEN_PARENTHESIS,"THEN");
    public static final TokenType ELSE = new TokenType("Else",4,OPEN_PARENTHESIS,"ELSE");
    public static final TokenType RETURN = new TokenType("Return",4,"RETURN", "->");
    public static final TokenType COMMA = new TokenType("Comma", ",");
    public static final TokenType SEMICOLON = new TokenType("Semicolon", ";");
    public static final TokenType PERIOD = new TokenType("Period", OPEN_PARENTHESIS,".");
    public static final TokenType QUESTION_MARK = new TokenType("Question Mark", "?");
    public static final TokenType COMMENT_MARK = new TokenType("Single Line Comment Mark", 1,"//");
    public static final TokenType BLOCK_COMMENT_MARK_START = new TokenType("Block Comment Mark Start", 1,"/*");
    public static final TokenType BLOCK_COMMENT_MARK_END = new TokenType("Block Comment Mark End", 1,"*/");
    public static final TokenType COMMENT_STRING = new TokenType("Comment String");
    public static final TokenType STRING = new TokenType("String");
    public static final TokenType STRICT_STRING_MARK = new TokenType("Strict Mark", 1,"\"");
    public static final TokenType LINE_BREAK = new TokenType("Line Break", 1,"\n","\r\n");
    public static final TokenType EOF = new TokenType("EOF");

    static {
        DICTIONARY.sort(Comparator.comparingInt(TokenType::order));
    }
}
