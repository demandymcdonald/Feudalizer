package com.base.timeline.flags;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.tuple.Pair;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public class StateError implements ConditionResult {
        private final String id;
        private final TimelineChange<?> oldChange;
        private final CompletableFuture<String> response;
        private final Map<String,ErrorResolution> options;
        private final StateReference message;

    public StateError(String id, StateReference message,TimelineChange<?> existingChange, ErrorResolution... options) {
        this.id = id;
        this.message = message;
        response = new CompletableFuture<>();
        this.options =  buildOptionsString(options);
        this.oldChange = existingChange;
    }
    public <T extends DateMutableEntity<T>> SandboxCode resolve(Sandbox<T> sandbox, DMEReference<T> entity, TimelineState<T> state, TimelineChange<? super T> newChange,
                                                                String resolutionCode){
        return options.get(resolutionCode).resolve(sandbox,entity,state,newChange,oldChange);
    }
    public TimelineChange<?> getExistingChange() {
        return oldChange;
    }
    public static long buildForTLC(TimelineChange<?> change){
        long toReturn = change.getStart().toEpochDay();
        toReturn *= change.getClass().toString().hashCode();
        return toReturn;
    }
    private Map<String,ErrorResolution> buildOptionsString(ErrorResolution... options){
        Map<String,ErrorResolution> map = new HashMap<>();
        for (ErrorResolution e : options) {
            map.put(e.getID(),e);
        }
        return map;
    }

    public CompletableFuture<String> getResponse() {
        return response;
    }
    public int getPriority(){
        if (response.isDone()){
            return getOrDefault(response.join()).getPriority();
        }
        int maxPriority = 1000;
        for (ErrorResolution e : options.values()){
            if (e.getPriority() < maxPriority){
                maxPriority = e.getPriority();
            }
        }
        return maxPriority;
    }
    public SandboxCode getExpectedSandboxCode(){
        if (response.isDone()){
            return getOrDefault(response.join()).getExpectedCode();
        }
        return SandboxCode.CONTINUE;
    }
    public boolean isExclusive(){
        return getOrDefault(response.join()).isExclusive();
    }
    public long getLongID(){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putLong(oldChange.getFullID());
        hasher.putString(id, StandardCharsets.UTF_8);
        return hasher.hash().asLong();
    }


//    public <T extends DateMutableEntity<T>> SandboxCode handleDecision(Sandbox sandbox, TimelineChange<?> change, T entity){
//        if (options.size() == 1){
//            return executeDecision(options.keySet().iterator().next(),sandbox,change,(TimelineChange<T>) oldChange,entity);
//        }
//        return executeDecision(response.join(),sandbox,change,(TimelineChange<T>)oldChange,entity);
//    }
//    private <T extends DateMutableEntity<T>> SandboxCode executeDecision(String code, Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange,  T entity){
//        if (!uiMap.keySet().contains(code)){
//            throw new IllegalArgumentException("Invalid code: " + code);
//        }
//        return options.get(code).resolve(sandbox,change,oldChange,entity,shouldSaveToDiff);
//    }
    public boolean canAutoResolve(){
        return options.size() == 1;
    }
    public String getID() {
        return id;
    }
    public String getAutoResolution(){
        ErrorResolution er = getResolutions().values().stream().findFirst().orElseThrow();
        return er.getID();
    }

    public boolean isFinal(){
        String resolution = response.join();
        getResolutions().keySet().stream().forEach(r -> System.out.println(r + " " + r.equals(resolution)));
    }
    public ErrorResolution getResolutionIfComplete(){
        if (response.isDone()){
            return getOrDefault(response.join());
        }
        return null;
    }

    public StateError addIgnore(){
        addOption(new ErrorResolution.GenIgnore());
        return this;
    }
    public int getResolutionPriority(String resolutionID){
        return getOrDefault(resolutionID).getPriority();
    }
    public Map<String,ErrorResolution> getResolutions(){
        return options;
    }
    public String getMessage() {
        return message.parse();
    }
    public ErrorResolution getOrDefault(String resolutionID){
        ErrorResolution er = options.get(resolutionID);
        if (er == null){
            er = options.entrySet().iterator().next().getValue();
            if (er == null){
                throw new IllegalArgumentException("Broken StateError: no resolutions. StateError: " + this.getMessage() + " ResolutionID: " + resolutionID);
            }
        }
        return er;
    }
    public  StateError  addEndState() {
        return addOption(new ErrorResolution.EndSandbox_Save());
    }
    public StateError  addEndCancel() {
        return addOption(new ErrorResolution.EndSandbox_Cancel());
    }
    public StateError  addOverride() {
        return addOption(new ErrorResolution.GenOverride());
    }
    public StateError  addAccept() {
        return addOption(new ErrorResolution.GenAccept());
    }
    public StateError  addNullify() {
        return addOption(new ErrorResolution.GenNullify());
    }
    public StateError addContinue() {
        return addOption(new ErrorResolution.GenContinue());
    }
    public StateError addBranchingSuccessionPlanning(Objective o) {
        return addOption(new ErrorResolution.SandboxBranching("succession_planning",o));
    }

    public StateError addOption(ErrorResolution option){
        options.put(option.getCode(),option);
        uiMap.put(option.getCode(),option.tooltip());
        return this;
    }

}

