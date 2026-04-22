package com.base.condition;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.error.ErrorResolution;
import com.base.timeline.error.IResolution;
import com.base.timeline.error.SandboxCode;
import com.utilities.id.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IConditionError<T extends DateMutableEntity<?>, R extends IResolution<? super T,A,B,C,D>,A,B,C,D> extends StringIdentifiable {

    CompletableFuture<String> getResponse();
    Map<String, R> getResolutions();
    String getMessage();

    default int getPriority(){
        if (getResponse().isDone()){
            return getResolutions().get(getResponse().join()).getPriority();
        }
        int maxPriority = 1000;
        for (R e : getResolutions().values()){
            if (e.getPriority() < maxPriority){
                maxPriority = e.getPriority();
            }
        }
        return maxPriority;
    }
    default SandboxCode getExpectedSandboxCode(){
        if (getResponse().isDone()){
            return getResolutions().get(getResponse().join()).getExpectedCode();
        }
        return SandboxCode.CONTINUE;
    }

    default String getAutoResolution(){
        if (canAutoResolve()){
            return "";
        }
        ErrorResolution<? super T> er = (ErrorResolution<? super T>) getResolutions().values().stream().findFirst().orElseThrow(() -> {return new IllegalStateException("No resolutions for " + getID());});
        return er.getDisplayID();
    }
    default boolean canAutoResolve(){
        return getResolutions().size() == 1;
    }
    @SafeVarargs
    public static<T extends DateMutableEntity<?>, R extends IResolution<T,A,B,C,D>,A,B,C,D> Map<String,R> buildOptionsString(R... options){
        Map<String,R> map = new HashMap<>();
        for (R e : options) {
            map.put(e.getID(),e);
        }
        return map;
    }
    default SandboxCode resolve(DMEReference<? extends T> reference, A a, B b, C c, D d){
        String s = getResponse().join();
        R resolution = getResolutions().get(s);
        if(resolution == null){
            throw new IllegalStateException("No resolution: "+ s +" found for " + getID());
        }
        return resolution.resolve(reference,a,b,c,d);
    };
}
