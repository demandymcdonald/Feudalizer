package com.base.worldforge.lexer;

import com.base.worldforge.base.INodeComponent;

public record Token(TokenType getType, String value, int line, int column) implements INodeComponent {
    //Returns the in-package ID (a file-based id that's fast and cheap to produce
    public long getIPID(){
        return INodeComponent.pack(line, column);
    }

    @Override
    public long getPKID() {
        return 0; //TODO to implement once the meta-layer is done :D
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String getSource() {
        return value;
    }
}
