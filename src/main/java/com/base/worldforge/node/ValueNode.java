package com.base.worldforge.node;

import com.base.worldforge.node.base.INode;
import com.base.worldforge.base.INodeComponent;
import com.base.worldforge.base.ScriptContext;
import com.base.worldforge.lexer.Token;
import org.apache.commons.lang3.tuple.Pair;

public abstract class ValueNode<T> implements INodeComponent, INode<T> {
    private final long ipID;
    private final long pkID;
    private final int line;
    private final int column;
    private final String source;
    public ValueNode(String source, long ipID, long pkID){
        this.source = source;
        this.ipID = ipID;
        this.pkID = pkID;
        Pair<Integer,Integer> unpack = INodeComponent.unpack(ipID);
        this.line = unpack.getLeft();
        this.column = unpack.getRight();
    }
    public ValueNode(Token token){
        this.source = token.getSource();
        this.ipID = token.getIPID();
        this.pkID = token.getPKID();
        this.line = token.getLine();
        this.column = token.getColumn();
    }
    @Override
    public final long getPKID() {
        return pkID;
    }
    @Override
    public final long getIPID() {
        return ipID;
    }
    @Override
    public final Long getID() {
        return INodeComponent.super.getID();
    }
    @Override
    public final int getLine() {
        return line;
    }
    @Override
    public final int getColumn() {
        return column;
    }
    @Override
    public String getSource() {
        return source;
    }
    public abstract T evaluate(ScriptContext context);

}
