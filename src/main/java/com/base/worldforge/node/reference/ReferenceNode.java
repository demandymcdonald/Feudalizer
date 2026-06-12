package com.base.worldforge.node.reference;

import com.Global.*;
import com.base.worldforge.base.ScriptContext;
import com.base.worldforge.lexer.Token;
import com.base.worldforge.lexer.TokenDictionary;
import com.base.worldforge.lexer.TokenType;
import com.base.worldforge.node.ValueNode;
import com.base.worldforge.node.base.INestedResult;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.*;

public abstract class ReferenceNode<P,R> extends ValueNode<R> implements INestedResult<R> {
    private final ValueNode<P> node;
    public ReferenceNode(Token token, ValueNode<P> node) {
        super(token.getSource(), token.getIPID(), token.getPKID());
        this.node = node;
    }
    public final ValueNode<P> getNode() {
        return node;
    }


    @Override
    public final String getSource() {
        return getType().name() + " || " + node.getSource();
    }
    @Override
    public final R evaluate(ScriptContext context) {
        return process(context,node.evaluate(context));
    }
    public abstract R process(ScriptContext context, P preResult);

    public static class VariableNode<R> extends ReferenceNode<R,R> {
        public VariableNode(Token token, ValueNode<R> node) {
            super(token, node);
        }
        @Override
        public R process(ScriptContext context, R preResult) {
            return preResult;
        }

        @Override
        public TokenType getType() {
            return TokenDictionary.VARIABLE;
        }

        @Override
        public @NonNull ValueNode<R> getNestedResult() {
            return getNode();
        }
    }
    public static class FieldNode<R> extends ReferenceNode<R,R> {
        public FieldNode(Token token, ValueNode<R> node) {
            super(token, node);
        }
        @Override
        public R process(ScriptContext context, R preResult) {
            return preResult;
        }
        @Override
        public Class<R> getOutputType() {
            return getNode().getOutputType();
        }
        @Override
        public TokenType getType() {
            return TokenDictionary.FIELD_IDENTIFIER;
        }
        @Override
        public @NonNull ValueNode<R> getNestedResult() {
            return getNode();
        }
    }
    public static class FilePathNode extends ReferenceNode<String, File>{
        public FilePathNode(Token token, ValueNode<String> node) {
            super(token, node);
            //TODO check if the file path is a valid filepath.
        }
        @Override
        public File process(ScriptContext context, String preResult) {
            return new File(preResult);
        }

        @Override
        public @NonNull ValueNode<File> getNestedResult() {
            return null;
        }

        @Override
        public Class<File> getOutputType() {
            return File.class;
        }
        @Override
        public TokenType getType() {
            return TokenDictionary.FILE_PATH;
        }
    }
    public static class ImportNode<R> extends ReferenceNode<String, File> {
        //This class is basically a placeholder for when the script import system is built.
        public ImportNode(Token token, ValueNode<String> node) {
            super(token,node);
        }

        @Override
        public File process(ScriptContext context, String preResult) {
            return new File(preResult);
        }
        @Override
        public @NonNull ValueNode<File> getNestedResult() {
            return null;
        }

        @Override
        public Class<File> getOutputType() {
            return File.class;
        }
        @Override
        public TokenType getType() {
            return TokenDictionary.IMPORT;
        }
    }
}
