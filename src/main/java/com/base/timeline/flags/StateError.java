package com.base.timeline.flags;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.conditions.ConditionResult;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.objects.character.BookCharacter;
import com.objects.title.Title;
import org.apache.commons.lang3.tuple.Pair;


import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class StateError implements ConditionResult {
        private final TimelineChange<?> oldChange;
        private final CompletableFuture<String> response;
        private final Map<String,ErrorResolution> options;
        private final Map<String,String> uiMap;
        private final StateReference message;
        private boolean shouldSaveToDiff = false;

    public StateError(StateReference message,TimelineChange<?> oldChange, ErrorResolution... options) {
        this.message = message;
        response = new CompletableFuture<>();
        Pair<Map<String,ErrorResolution>, Map<String,String>> p = buildOptionsString(options);
        this.options = p.getLeft();
        uiMap = p.getRight();
        this.oldChange = oldChange;
    }

    @SuppressWarnings("UnstableApiUsage")
    public long generateID(TimelineChange<?> change, TimelineChange<?> existing, @Nullable Integer proceduralInteger){
        if (proceduralInteger == null){
            proceduralInteger = 69;
        }
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putString(message.parse(), StandardCharsets.UTF_8);
        hasher.putLong(buildForTLC(change));
        hasher.putLong(buildForTLC(existing));
        hasher.putLong(proceduralInteger);
        return hasher.hash().asLong();
    }
    public TimelineChange<?> getOldChange() {
        return oldChange;
    }
    public static long buildForTLC(TimelineChange<?> change){
        long toReturn = change.getStart().toEpochDay();
        toReturn *= change.getClass().toString().hashCode();
        return toReturn;
    }
    private Pair<Map<String,ErrorResolution>,Map<String,String>> buildOptionsString(ErrorResolution... options){
        Map<String,String> ui = new HashMap<>();
        Map<String,ErrorResolution> map = new HashMap<>();
        for (ErrorResolution e : options) {
            ui.put(e.getCode(),e.tooltip());
            map.put(e.getCode(),e);
        }
        return Pair.of(map,ui);
    }
    public CompletableFuture<String> getResponse() {
        return response;
    }
    public <T extends DateMutableEntity<T>> SandboxCode handleDecision(Sandbox sandbox, TimelineChange<T> change, T entity){
        if (options.size() == 1){
            return executeDecision(options.keySet().iterator().next(),sandbox,change,(TimelineChange<T>) oldChange,entity);
        }
        return executeDecision(response.join(),sandbox,change,(TimelineChange<T>)oldChange,entity);
    }
    private <T extends DateMutableEntity<T>> SandboxCode executeDecision(String code, Sandbox sandbox, TimelineChange<T> change,TimelineChange<T> oldChange,  T entity){
        if (!uiMap.keySet().contains(code)){
            throw new IllegalArgumentException("Invalid code: " + code);
        }
        return options.get(code).resolve(sandbox,change,oldChange,entity,shouldSaveToDiff);
    }
    public boolean canAutoResolve(){
        return options.size() == 1;
    }
    public <T extends DateMutableEntity<T>> void autoResolve(Sandbox sandbox, TimelineChange<T> change, T entity){
        executeDecision(options.keySet().iterator().next(),sandbox,change,(TimelineChange<T>) oldChange,entity);
    }
    public StateError saveToDiff(){
        shouldSaveToDiff = true;
    }
    public StateError addIgnore(){
        addOption(new ErrorResolution.GenIgnore());
        return this;
    }
    public int getResolutionPriority(String resolutionID){
        return getOrDefault(resolutionID).getPriority();
    }
    public StateError addCharacterLeaveFaction(BookCharacter character, Title<?> title){

    }
    public StateError addTitleChangeFaction(BookCharacter character, Title<?> title){

    }
    public ErrorResolution getOrDefault(String resolutionID){
        ErrorResolution er = options.get(resolutionID);
        if (er == null){
            er = options.entrySet().iterator().next().getValue();
            if (er == null){
                throw new IllegalArgumentException("Broken StateError: no resolutions. StateError: " + this.getMessage() + " ResolutionID: " + resolutionID);
            }
        }
    }
    public  StateError  addEndState() {
        return addOption(new ErrorResolution.EndSandbox());
    }
    public StateError  addEndCancel() {
        return addOption(new ErrorResolution.EndCancel());
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
    public StateError addReplaceWithNew(TimelineChange<?> replace){
        return addOption(new ErrorResolution.ReplaceWithNew(replace));
    }
    public StateError fixWithSuccessionPlanning(Objective o){
        return addOption(new ErrorResolution.);//TODO Finish
    }
    public StateError addOption(ErrorResolution option){
        options.put(option.getCode(),option);
        uiMap.put(option.getCode(),option.tooltip());
        return this;
    }
    public StateError runSuccessionPlanning(DMEReference<? extends Title<?>> title, DMEReference<BookCharacter> newHolder){

    }
    public String getMessage() {
        return message.parse();
    }
}

