package com.base.timeline.error;

import com.Global.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.utilities.id.Identifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class ErrorListResolution<T extends Identifiable<I>,I> extends ErrorResolution{
    private final BiMap<T,I> options;
    public final CompletableFuture<I> response = new CompletableFuture<>();
    public ErrorListResolution(String id, String display, String description,  SandboxCode expectedCode, List<T> options) {
        super(id, display, description, 7, true, expectedCode);
        this.options = buildOptions(options);
    }
    private BiMap<T, I> buildOptions(List<T> collection){
        BiMap<T,I> toReturn = Maps.synchronizedBiMap(HashBiMap.create());
        for (T t : collection) {
            toReturn.put(t, t.getID());
        }
        return toReturn;
    }
    public Collection<T> getOptions(){
        return options.keySet();
    }
    public void selectOption(T option){
        response.complete(options.get(option));
    }
    public I getChosen(){
        return response.join();
    }
    public T getOption(I id){
        return options.inverse().get(id);
    }
}
