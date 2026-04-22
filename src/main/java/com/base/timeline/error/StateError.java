package com.base.timeline.error;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.reference.StateReference;
import com.base.timeline.change.TimelineChange;
import com.base.condition.IConditionError;
import com.base.timeline.sandbox.core.Objective;
import com.base.timeline.sandbox.core.Sandbox;
import com.base.timeline.state.TimelineState;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.objects.title.Title;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class StateError<T extends DateMutableEntity<?>> implements IConditionError<T,ErrorResolution<T>,Sandbox<? extends T>,TimelineState<? extends T>,TimelineChange<T>,TimelineChange<?>> {
        private final String id;
        private final TimelineChange<?> currentChange;
        private final CompletableFuture<String> response;
        private final Map<String,ErrorResolution<? super T>> options;
        private final StateReference message;

    @SafeVarargs
    public StateError(String id, StateReference message, TimelineChange<?> existingChange, ErrorResolution<? super T>... options) {
        this.id = "se:"+id;
        this.message = message;
        response = new CompletableFuture<>();
        this.options =  new HashMap<>();
        for (ErrorResolution<? super T> e : options) {
            this.options.put(e.getDisplayID(),e);
        }
        this.currentChange = existingChange;
    }

    public TimelineChange<?> getExistingChange() {
        return currentChange;
    }
    public static long buildForTLC(TimelineChange<?> change){
        long toReturn = change.getStart().toEpochDay();
        toReturn *= change.getClass().toString().hashCode();
        return toReturn;
    }

    public CompletableFuture<String> getResponse() {
        return response;
    }

    @Override
    public Map<String, ErrorResolution<T>> getResolutions() {
        return Map.of();
    }
    public boolean isExclusive(){
        return getResolutions().get(response.join()).isExclusive();
    }
    public long getLongID(){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putLong(currentChange.getFullID());
        hasher.putString(id, StandardCharsets.UTF_8);
        return hasher.hash().asLong();
    }
    public boolean canAutoResolve(){
        return options.size() == 1;
    }

    public String getID() {
        return id;
    }

    public StateError<T> addIgnore(){
        addOption(new ErrorResolution.GenIgnore<>());
        return this;
    }

    public String getMessage() {
        return message.parse();
    }

    public StateError<T> addEndSave() {
        return addOption(new ErrorResolution.EndSandbox_Save<>());
    }
    public StateError<T>  addEndCancel() {
        return addOption(new ErrorResolution.EndSandbox_Cancel<>());
    }
    public StateError<T>  addOverride() {
        return addOption(new ErrorResolution.GenOverride<>());
    }
    public StateError<T>  addAccept() {
        return addOption(new ErrorResolution.GenAccept<>());
    }
    public StateError<T> addReplace(String replaceSubID, String replaceTitle, String replaceDescription, TimelineChange<?> replace) {
        return addOption(new ErrorResolution.ReplaceExistingWithNew<>(replaceSubID,replaceTitle,replaceDescription,replace));
    }
    public StateError<T> addSandbox(String branchingSubID, String displayName, String description, Objective<T> o) {
        return addOption(new ErrorResolution.SandboxBranching<T>(branchingSubID,displayName,description,o));
    }
    public  StateError<T> addBranchingSuccessionPlanning(Objective<T> o) {
        assert o.subject().get() instanceof Title<?>;
        return addOption(new ErrorResolution.SandboxBranching<>("succession_planning",
                "Run Succession Planner", "grant the title to an heir using Succession Planner", o));
    }

    public StateError addMergeContinue(){
        return addOption(new ErrorResolution.MapMergeContinue());
    }

    public StateError<T> addOption(ErrorResolution<T> option){
        options.put(option.getDisplayID(),option);
        return this;
    }

}

