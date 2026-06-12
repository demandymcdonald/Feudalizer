package com.base.worldforge;

public class WorldForgeScriptError extends RuntimeException {
    public WorldForgeScriptError(int line, int column, String message) {
        this(line,column,false,message);
    }
    public WorldForgeScriptError(int line, int column, boolean fullThrow, String message) {}
}
