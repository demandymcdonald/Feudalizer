package com.base.worldforge.parser;

import com.base.worldforge.WorldForgeScriptError;
import com.base.worldforge.WorldForgeScripting;
import com.base.worldforge.lexer.Token;
import com.base.worldforge.lexer.TokenDictionary;
import com.base.worldforge.lexer.TokenType;
import com.base.worldforge.base.INodeComponent;
import com.base.worldforge.node.ValueNode;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Parser {

    private final Deque<Token> tokens = new ArrayDeque<>(){
        @Override
        public Token pollFirst() {
            globalIndex++;
            statTokenConsumed++;
            return super.pollFirst();
        }

        @Override
        public Token poll() {
            globalIndex++;
            statTokenConsumed++;
            return super.poll();
        }
    };
    private final ImmutableList<Token> rawTokens;
    private final List<Token> processedTokens = new ArrayList<>();
    private final Set<ParserPattern<?,?>> patterns = PatternDictionary.get();
    private final Map<Long,INodeComponent> implicityOpenedList = new HashMap<>();

    private int globalIndex = 0;
    private int chunkIndex = 0;
    private int layerIndex = 0;
    private int statMaxLayerDepth = 1;
    private int statTokenConsumed = 0;
    private final TreeMap<Integer, ParserLayer> layers = new TreeMap<>();

    private Parser(List<Token> tokens){
        this.rawTokens = ImmutableList.copyOf(tokens);
    }
    public static List<ValueNode<?>> parse(List<Token> tokens){
        Parser parser = new Parser(tokens);
        return parser.doParse();
    }
    private List<ValueNode<?>> doParse(){
        List<Token> pt = preProcess();
        processedTokens.addAll(pt);
        tokens.addAll(pt);
        List<ValueNode<?>> nodes = new ArrayList<>();
        final int size = processedTokens.size();
        while (globalIndex < size){
            nodes.add(parseChunk());
            newChunk();
        }
        return nodes;
    }
    //TODO handle adding a proper close onto the end of an open using logic during parsetime.
    private List<Token> preProcess(){
        List<Token> tokens = new ArrayList<>();
        Set<TokenType> strippedTypes = PatternDictionary.getStrippedTypes();
        int currentPos = 0;
        final int maxSize = rawTokens.size();
        while (currentPos < maxSize){
            Token t = rawTokens.get(currentPos);
            Token n;
            if (currentPos + 1 < maxSize){
                n = rawTokens.get(currentPos + 1);
            } else {
                n = null;
            }
            if(!strippedTypes.contains(t.getType())){
                tokens.add(t);
            }
            if(t.getType().isNester()){
                final TokenType implicit = t.getType().getImplicitSymbol();
                if(n == null || n.getType() == TokenDictionary.EOF){
                    //Because an opening nester before an EOF will always be null
                    WorldForgeScripting.throwScriptError(new WorldForgeScriptError(t.line(),t.column(),"Unexpected end of file after " + t.value()));
                } else if (n.getType() != implicit){
                    //Line and column are made negative to indicate that it's an implicit symbol.
                    Token it = new Token(implicit, Arrays.stream(implicit.acceptedSymbols()).findFirst().orElseThrow(),t.line() * -1,t.column() * -1);
                    tokens.add(it);
                    implicityOpenedList.put(it.getIPID(),it);
                }
            }
            currentPos++;
        }
        return tokens;
    }

    private ValueNode<?> parseChunk(){
        //Probably will add more here eventually.
        ValueNode<?> node = parseLayer();
        WorldForgeScripting.LOGGER.debug("Parsed Chunk (package: {}, full: {}) with a max depth of {} layers and {}/{} tokens consumed.",node.getIPID(),node.getID(),statMaxLayerDepth,statTokenConsumed,processedTokens.size());
        return node;
    }

    private ValueNode<?> parseLayer(){
        INodeComponent current = tokens.peek();
        ParserLayer layer = new ParserLayer(chunkIndex, layerIndex, current);
        layers.put(layer.layerIndex, layer);
        while(!layer.isComplete()){
            globalIndex++;
            if(globalIndex >= processedTokens.size()){
                throw new IllegalStateException("This throw is for testing. Do proper event handling but this means we're out of tokens while a layer is still open. Layer: " + layer);
            }
                current = tokens.peek();
                if (current.getType().isNester()){
                        layerIndex++;
                        statMaxLayerDepth = Math.max(statMaxLayerDepth, layerIndex);
                        current = parseLayer();
                } else {
                    current = prepareComponent(tokens.poll());
                }
            boolean shouldPush = doMatching(layer, current);
            if(shouldPush){
                layer.addComponent(tokens.poll());
            } else {
                globalIndex--;
            }
        }
        layers.remove(layer.layerIndex);
        layerIndex--;
        return layer.build(layer.currentTokens);
    }
    private static INodeComponent prepareComponent(INodeComponent component){
        //TODO next step
    }
    private boolean doMatching(ParserLayer layer, INodeComponent next){
        List<INodeComponent> nextTokens = new ArrayList<>(layer.currentTokens);
        nextTokens.addLast(next);
        List<ParserPattern<?,?>> currentPatterns = layer.currentMatches;
        List<ParserPattern<?,?>> nextPatterns = getPatterns(currentPatterns,nextTokens);
        if(nextPatterns.isEmpty()){
            final WorldForgeScriptError error = new WorldForgeScriptError(next.getLine(),next.getColumn(),true,"No matching patterns found for: " + nextTokens); //Will handle properly throwing, so treat the passage of this to WorldForgeScripting as a termination of the parser.
            if(currentPatterns.isEmpty()){
                WorldForgeScripting.throwScriptError(error);
            }
            if (doImplicitCheck(layer, next, new ArrayList<>(nextTokens), currentPatterns, error) || doTroubleshoot(layer, next, new ArrayList<>(nextTokens), currentPatterns, error)) return false;
        }
        layer.updateMatches(nextPatterns);
        return true;
    }
    //This and doImplicitCheck share a bit of code. Figure out how to merge tomorrow.
    private  boolean doTroubleshoot(ParserLayer layer, INodeComponent next, List<INodeComponent> nextTokens, List<ParserPattern<?, ?>> currentPatterns, WorldForgeScriptError error) {

        nextTokens.removeLast();
        List<ParserPattern<?,?>> bestPatterns = new ArrayList<>();
        for(ParserPattern<?,?> p : currentPatterns){
            if(p.matches(nextTokens, true)){
                bestPatterns.add(p);
            }
        }
        if(bestPatterns.isEmpty()){
            WorldForgeScripting.throwScriptError(error);
        } else if (bestPatterns.size() == 1){
            layer.updateMatches(bestPatterns);
            return true;
        } else {
            //Because there are more than one exact matches, which means there is a duplicate pattern somewhere in here.
            WorldForgeScripting.throwScriptError(new WorldForgeScriptError(next.getLine(), next.getColumn(),true,"Duplicate pattern found: " + bestPatterns + " for: " + layer.currentTokens));
        }
        return false;
    }

    private boolean doImplicitCheck(ParserLayer layer, INodeComponent next, List<INodeComponent> nextTokens, List<ParserPattern<?, ?>> currentPatterns, WorldForgeScriptError error) {
        if (!implicityOpenedList.containsKey(layer.opener().getIPID())) {
            return false;
        }
        nextTokens.removeLast();
        INodeComponent realLast = nextTokens.getLast();
        TokenType implicit = TokenType.NestType.getPairing(implicityOpenedList.get(layer.opener().getIPID()).getType().type()).getToken();
        Token ender = new Token(implicit,implicit.name(),realLast.getLine() * -1,realLast.getColumn() * -1);
        nextTokens.addLast(ender);
        List<ParserPattern<?,?>> bestPatterns = new ArrayList<>();
        for(ParserPattern<?,?> p : currentPatterns){
            if(p.matches(nextTokens, true)){
                bestPatterns.add(p);
            }
        }
        if(bestPatterns.isEmpty()){
            WorldForgeScripting.throwScriptError(error);
        } else if (bestPatterns.size() == 1){
            globalIndex++; //since a new token has been added.
            processedTokens.add(globalIndex + 1, ender);
            layer.updateMatches(bestPatterns);
            layer.addComponent(ender);
            return true;
        } else {
            //Because there are more than one exact matches, which means there is a duplicate pattern somewhere in here.
            WorldForgeScripting.throwScriptError(new WorldForgeScriptError(next.getLine(), next.getColumn(),true,"Duplicate pattern found: " + bestPatterns + " for: " + layer.currentTokens));
        }
        return false;
    }


    private List<ParserPattern<?,?>> getPatterns(List<ParserPattern<?,?>> currentPatterns, List<INodeComponent> current){
        List<ParserPattern<?,?>> nextPatterns = new ArrayList<>();
        if(currentPatterns.isEmpty()){
            for(ParserPattern<?,?> pattern : patterns){
                if(pattern.matches(current, false)){
                    nextPatterns.add(pattern);
                }
            }
        } else {
            for(ParserPattern<?,?> pattern : currentPatterns){
                if(pattern.matches(current, false)){
                    nextPatterns.add(pattern);
                }
            }
        }
        return nextPatterns;
    }
    private void newChunk(){
        chunkIndex++;
        layerIndex = 0;
        statMaxLayerDepth = 1;
        layers.clear();
    }

    private record ParserLayer(int chunkIndex, int layerIndex, INodeComponent opener, List<INodeComponent> currentTokens, List<ParserPattern<?,?>> currentMatches){
        public ParserLayer(int chunkIndex, int layerIndex, INodeComponent opener){
            this(chunkIndex, layerIndex, opener, new ArrayList<>(), new ArrayList<>());
        }
        public void addComponent(INodeComponent component){
            currentTokens.addLast(component);
        }

        public boolean isComplete(){
            return currentMatches.size() == 1;
        }
        public INodeComponent revertTurn(){
            INodeComponent component = currentTokens.getLast();
            currentTokens.removeLast();
            return component;
        }


        public void updateMatches(List<ParserPattern<?,?>> matches){
            currentMatches.clear();
            currentMatches.addAll(matches);
        }
        public INodeComponent getLastToken(){
            return currentTokens.getLast();
        }
        public <N extends ValueNode<T>,T> N build(List<INodeComponent> tokens){
            if(currentMatches.size() != 1){
                throw new IllegalStateException("There is more than one pattern match in a layer.");
            }
            //TODO next step
        }
    }
}
