package com.objects.culture.tenet.factory;

import com.base.DateMutableEntity;
import com.base.condition.IConditionError;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.error.ErrorResolution;
import com.base.timeline.error.IResolution;
import com.base.timeline.error.SandboxCode;
import com.objects.culture.Culture;
import com.objects.culture.object.CultureObject;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.dynamic.DynamicTenet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TenetError<T extends DynamicTenet<T>,D extends DateMutableEntity<D> & CultureObject<D>> implements IConditionError<T, TenetResolution<T,D>,TenetReference, DMEReference<D>, DMEReference<Culture> , DMEReference<Culture>> {
    private final CompletableFuture<String> response = new CompletableFuture<>();
    private final Map<String,TenetResolution<T,D>> resolutions;
    private final StateReference message;
    private final String id;

    public TenetError(String id, StateReference message) {
        this.resolutions = new HashMap<>();
        this.message = message;
        this.id = id;
    }

    @Override
    public CompletableFuture<String> getResponse() {
        return response;
    }

    @Override
    public Map<String, TenetResolution<T, D>> getResolutions() {
        return resolutions;
    }


    @Override
    public String getMessage() {
        return message.parse();
    }


    @Override
    public String getID() {
        return id;
    }

    public TenetError<T,D> ignore(){
         add(new TenetResolution<T,D>("tr_ignore","Ignore","Ignore the issue and continue",6,true,SandboxCode.CONTINUE){
            @Override
            public SandboxCode resolve(DMEReference<? extends T> potentialParent, TenetReference potentialChild, DMEReference<D> relatedObject, DMEReference<Culture> potentialParentCulture, DMEReference<Culture> potentialChildCulture) {
                return SandboxCode.CONTINUE;
            }
        });
        return this;
    }
    public TenetError<T,D> cancel(){
        add(new TenetResolution<T,D>("tr_cancel","Cancel","cancel",6,true,SandboxCode.CONTINUE){
            @Override
            public SandboxCode resolve(DMEReference<? extends T> potentialParent, TenetReference potentialChild, DMEReference<D> relatedObject, DMEReference<Culture> potentialParentCulture, DMEReference<Culture> potentialChildCulture) {
                return SandboxCode.END_DISCARD;
            }
        });
        return this;
    }
    public void add(TenetResolution<T,D> add){
        this.resolutions.put(add.getID(),add);
    }
    public static <T extends DynamicTenet<T>,D extends DateMutableEntity<D> & CultureObject<D>> TenetError<T,D> generic(String id, StateReference message){
        return (TenetError<T, D>) new TenetError<>(id,message).ignore().cancel();

    }
}
