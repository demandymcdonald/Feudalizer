package com.base.datemutable.timeline.change.condition.apply;

import com.base.datemutable.DateMutableEntity;

import java.util.List;

public class ApplyConditions {
//    public static <T extends DateMutableEntity<T>> List<Condition<StateError,T>> BaseConditions(){
//        return List.of(IS_DEAD);
//    }
    public static List<ApplyCondition<DateMutableEntity<?>>> BaseConditions() {
        return List.of();
    }

//    public static final Condition<StateError, ? extends DateMutableEntity<?>> AT_BOUNDARY = new Condition<>("gen_out_of_bounds",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar sidecar) {
//            if (thisChange instanceof BoundaryChange<?,?> cd){
//                return Optional.of(//TODO new OOB error here. Should resolve to save at end of checking everything else, probably.)
//            }
//            return Optional.empty();
//        }
//    });
//    //--- Title Conditions ---
//    public static final Condition<StateError, Sidecar.TitleChange> TEMPLATE = new Condition<>("title_template",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            Title<?> subject = sidecar.subject();
//            Optional<HumanCharacter> holder = sidecar.holder();
//            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){
//
//            };
//            return Optional.empty();
//        }
//    });
//    public static final Condition<StateError, Sidecar.TitleChange> IS_OPPOSITE = new Condition<>("title_isOpposite",true, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){
//                if (tC.isOpposite(cA)){
//                    return Optional.of(newStateNullifiedbyOldError(checkAgainst));
//                }
//            };
//            return Optional.empty();
//        }
//    });
//    public static final Condition<StateError, Sidecar.TitleChange> WAS_REGRANTED = new Condition<>("title_wasReGranted", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            Title<?> subject = sidecar.subject();
//            Optional<HumanCharacter> holder = sidecar.holder();
//            if(checkAgainst instanceof TitleTLChange.Grant<?> cA && cA.getHolder().isPresent() && !cA.getHolder().get().get().equals(holder.orElse(null))){
//                return Optional.of(newStateNullifiedbyOldError(checkAgainst));
//            };
//            return Optional.empty();
//        }
//    });
//    public static final Condition<StateError, Sidecar.TitleChange> WAS_REVOKED = new Condition<>("title_wasReGranted", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            Title<?> subject = sidecar.subject();
//            Optional<HumanCharacter> holder = sidecar.holder();
//            if(checkAgainst instanceof TitleTLChange.Revoke<?> cA && cA.getHolder().isPresent() && !cA.getHolder().get().get().equals(holder.orElse(null))){
//                return Optional.of(newStateNullifiedbyOldError(checkAgainst).addReplaceWithNew(new TitleTLChange.Revoke<>(DMEReference.of(subject),DMEReference.of(holder.orElse(null)),checkAgainst.getStart())));
//            };
//            return Optional.empty();
//        }
//    });
//    public static final Condition<StateError, Sidecar.TitleChange> DUPLICATE = new Condition<>("title_duplicate", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            //Title<?> subject = sidecar.subject();
//            Optional<HumanCharacter> holder = sidecar.holder();
//            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){
//                Optional<HumanCharacter> otherCharacter = unpackReference(cA.getHolder());
//                if (holder.isEmpty()) return Optional.empty();
//                if (holder.get().equals(otherCharacter.orElse(null))){
//                    return Optional.of(duplicateError(checkAgainst));
//                }
//            };
//            return Optional.empty();
//        }
//    });
//    public static final Condition<StateError, Sidecar.TitleChange> TITLE_LOOP = new Condition<>("title_loop;",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            if( thisChange instanceof TitleTLChange.DeJureDrift<?,?> tC && checkAgainst instanceof TitleTLChange.DeJureDrift<?,?> cA){
//                Title<?> tCTitle = tC.getNewParent().get();
//                Title<?> tCNewChild = tC.getTitle().get();
//                Title<?> cANewParent = cA.getNewParent().get();
//                Title<?> cAChild = cA.getTitle().get();
//                //Title<?> cATitle = cA.getTitle().link();
//                while (tCTitle != null){
//                    //if (tCTitle.getParent().isPresent() && tCNewChild.getParent().isPresent()){
//                    Optional<StateError> finding = null;
//                    boolean isLoop = false;
//                    if(tCNewChild.hasChild(tCTitle) || (cANewParent.equals(tCNewChild) && tCTitle.equals(cAChild))){
//                        isLoop = true;
//                        finding = Optional.of(loopError(checkAgainst));
//                    } else if (tCTitle.hasChild(tCNewChild)){
//                        isLoop = false;
//                        finding = Optional.of(nullifyError(checkAgainst));
//                    }
//                    if (finding != null){
//                        if (isLoop){
//                            tCNewChild.removeChild(tCTitle,false,null);
//                        } else if (!tCTitle.equals(tC.getNewParent().get())){
//                            tCTitle.removeChild(tCNewChild,false,null);
//                        }
//                        return finding;
//                    }
//                    tCTitle = tCTitle.getParent().orElse(null);
//                }
//            };
//            return Optional.empty();
//        }
//        private static Optional<StateError> determineLoopError(Title<?> tCTitle, Title<?> tCNewChild, TitleTLChange.DeJureDrift<?,?> thisChange, TitleTLChange.DeJureDrift<?,?> checkAgainst, boolean isLoop){
//                if (isLoop){
//                    return Optional.of(loopError(checkAgainst));
//                } else {
//                    return Optional.of(nullifyError(checkAgainst));
//                }
//            //Because this specific existing change has nothing to do with our current situation, we return empty,
//            // relying on a later step to correct the state manually (most likely because the change already happened and this is just cleanup.
//            //return Optional.empty();
//        }
//    });
//    public static final Condition<StateError, Sidecar.TitleChange> CAN_STILL_HOLD = new Condition<>("title_canStillHold",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            Title<?> subject = sidecar.subject();
//            Optional<HumanCharacter> holder = sidecar.holder();
//            if (holder.isPresent()){
//                List<? extends DMEResult<?, ?, HumanCharacter>> results = Title.canHoldDeep(subject,holder.get());
//                for (DMEResult<?, ?, HumanCharacter> result : results) {
//                    if (!result.canHold()){
//                        return Optional.of(result.resolution().get());
//                    }
//                }
//            }
////            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){
////
////            };
//            return Optional.empty();
//        }
//    });
//    public static final Condition<StateError, Sidecar.TitleChange> DRIFT_ON_GRANT = new Condition<>("title_duplicate", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            //Title<?> subject = sidecar.subject();
//            Optional<HumanCharacter> holder = sidecar.holder();
//            if( thisChange instanceof TitleTLChange.DeJureDriftPassive<?,?,?> tC && checkAgainst instanceof TitleTLChange.DeJureDrift<?,?> cA){
//                if (cA.getTitle().get().equals(tC.getChild())){
//                    return Optional.of(nullifyError(checkAgainst));
//                }
//            };
//            return Optional.empty();
//        }
//    });
//    public static <T extends Title<T>> Condition<StateError, Sidecar.TitleChange> INHERITS_TITLE(){
//        return new Condition<>("title_inherited",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//            @Override
//            public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//                if (thisChange instanceof TitleTLChange.Inherit<?> ttl && ttl.isFirstTime()) {
//                    ttl.setFirstTime(false);
//                    return Optional.of(inheritanceFirstTime(new TimelineChangeState<>(thisChange.getStart(), Optional.empty(), thisChange),checkAgainst));
//                }
//                return Optional.empty();
//            }
//        });
//    }
//    public static final Condition<StateError, Sidecar.TitleChange> HAS_PARENT = new Condition<>("title_template",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
//        @Override
//        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
//            if(thisChange instanceof TitleTLChange.DeJureDrift<?,?> ttl){
//                Title<?> newChild = sidecar.subject();
//                Title<?> newParent = ttl.getNewParent().get();
//                Title<?> oldParent = newChild.getParent().orElse(null);
//                if (oldParent != null && (ttl.getLoreLast() == null || !oldParent.getId().equals(ttl.getLoreLast()))){
//                    ttl.setLoreLast(oldParent.getId());
//                    ttl.setLoreFlag(false);
//                    return Optional.of(Errors.alreadyHasAParent(newChild,newParent,oldParent,checkAgainst));
//                }
//            };
//            return Optional.empty();
//        }
//    });
//    private static <T extends DateMutableEntity<T>> Optional<T> unpackReference(Optional<DMEReference<T>> reference){
//        return reference.map(DMEReference::get);
//    }
}
