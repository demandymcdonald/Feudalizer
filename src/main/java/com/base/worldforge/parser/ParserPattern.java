package com.base.worldforge.parser;

import com.base.worldforge.WorldForgeScripting;
import com.base.worldforge.lexer.TokenType;
import com.base.worldforge.base.INodeComponent;
import com.base.worldforge.node.ValueNode;
import org.apache.commons.collections4.map.LinkedMap;
import org.checkerframework.checker.units.qual.N;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class ParserPattern<N extends ValueNode<T>,T>{
    private final String nodeName;
    private final List<TokenType> match;
    private final Function<List<INodeComponent>, N> factory;
    private final Set<ParserPattern<N,T>> sameTypePatterns;
    private ParserPattern(String nodeName, List<TokenType> match, Function<List<INodeComponent>, N> factory, Set<ParserPattern<N,T>> sameTypePatterns) {
        this.nodeName = nodeName;
        this.match = match;
        this.factory = factory;
        this.sameTypePatterns = sameTypePatterns;
    }
    public String getNodeName() {
        return nodeName;
    }
    public List<TokenType> getMatch() {
        return match;
    }
    public Function<List<INodeComponent>, N> getFactory() {
        return factory;
    }
    public boolean matches(List<INodeComponent> components, boolean strict){
        if(strict){
            if (components.size() != match.size()) return false;
        } else if (components.size() > match.size()){
            return false;
        }
        for(int i = 0; i < components.size(); i++){
            if(!match.get(i).equals(components.get(i).getType())){
                return false;
            }
        }
        return true;
    }
    public N create(List<INodeComponent> components){
        if(components.size() != match.size()){
            throw new IllegalStateException("Pattern mismatch");
        }
        return factory.apply(components);
    }
    public boolean hasSiblings(){
        return sameTypePatterns.size() > 0;
    }
    public Set<ParserPattern<N,T>> getSiblings(){
        return sameTypePatterns;
    }
    public static <N extends ValueNode<T>,T> Builder<N,T> builder(String nodeName, List<TokenType> match, Function<List<INodeComponent>, N> factory){
        return new Builder<>(nodeName,match,factory);
    }
    public static <N extends ValueNode<T>,T> ParserPattern<N,T> of(String nodeName, List<TokenType> match, Function<List<INodeComponent>, N> factory){
        ParserPattern<N,T> pattern = new ParserPattern<>(nodeName, match, factory, Set.of());
        PatternDictionary.register(pattern);
        return pattern;
    }
    public static class Builder<N extends ValueNode<T>,T>{
        private final String nodeName;
        private final Map<List<TokenType>,Function<List<INodeComponent>,N>> patterns = new HashMap<>();
        public Builder(String nodeName, List<TokenType> match, Function<List<INodeComponent>, N> factory) {
            this.nodeName = nodeName;
            patterns.put(match,factory);
        }
        public Builder<N,T> addPattern(List<TokenType> match, Function<List<INodeComponent>, N> factory){
            patterns.put(match,factory);
            return this;
        }
        public ParserPattern<N,T>[] build(){
            Set<ParserPattern<N,T>> same = new HashSet<>();
            for(Map.Entry<List<TokenType>,Function<List<INodeComponent>,N>> entry : patterns.entrySet()){
                ParserPattern<N,T> pattern = new ParserPattern<>(nodeName,entry.getKey(),entry.getValue(),same);
                same.add(pattern);
                PatternDictionary.register(pattern);
            }
            return same.toArray(new ParserPattern[0]);
        }
    }
}
