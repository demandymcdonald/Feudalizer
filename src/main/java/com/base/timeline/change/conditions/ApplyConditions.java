package com.base.timeline.change.conditions;

import com.base.DateMutableEntity;
import com.base.ObjectType;
import com.base.flags.Errors;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.base.timeline.TimelineChangeState;
import com.base.timeline.change.CharacterTLChange;
import com.base.timeline.change.TimelineChange;
import com.base.timeline.change.TitleTLChange;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Optional;

import static com.base.flags.Errors.*;

public class ApplyConditions {
    public static List<Condition<StateError,?>> BaseConditions(){
        return List.of(IS_DEAD);
    }
    public static final Condition<StateError,?> IS_DEAD = new Condition<> ("isDead",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar, Optional<StateError>>() {
        @Override
        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar sidecar) {
            if(checkAgainst instanceof CharacterTLChange.CharacterDeath cd){
                return Optional.of(characterDead(cd.getPrimary().link()));
            } else if (thisChange instanceof TitleTLChange<?> ttl && ttl.getHolder().isPresent()){
                DMEReference<BookCharacter> bookCharacter = ttl.getHolder().get();
                if (!bookCharacter.link().isAlive()){
                    return Optional.of(newHolderDead(ttl));
                }
            }
            return Optional.empty();
        }
    });

    //--- Title Conditions ---
    public static final Condition<StateError, Sidecar.TitleChange> TEMPLATE = new Condition<>("title_template",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
        @Override
        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            Title<?> subject = sidecar.subject();
            Optional<BookCharacter> holder = sidecar.holder();
            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){

            };
            return Optional.empty();
        }
    });
    public static final Condition<StateError, Sidecar.TitleChange> IS_OPPOSITE = new Condition<>("title_isOpposite",true, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
        @Override
        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){
                if (tC.isOpposite(cA)){
                    return Optional.of(newStateNullifiedbyOldError());
                }
            };
            return Optional.empty();
        }
    });
    public static final Condition<StateError, Sidecar.TitleChange> DUPLICATE = new Condition<>("title_duplicate", false,new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
        @Override
        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            Title<?> subject = sidecar.subject();
            Optional<BookCharacter> holder = sidecar.holder();
            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){
                Optional<BookCharacter> otherCharacter = unpackReference(cA.getHolder());
                if (otherCharacter.orElse(null) == holder.orElse(null)){
                    return Optional.of(duplicateError());
                }
            };
            return Optional.empty();
        }
    });
    public static final Condition<StateError, Sidecar.TitleChange> CAN_STILL_HOLD = new Condition<>("title_canStillHold",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
        @Override
        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            Title<?> subject = sidecar.subject();
            Optional<BookCharacter> holder = sidecar.holder();
            if (holder.isPresent()){
                List<? extends DMEResult<?, ?, BookCharacter>> results = Title.canHoldDeep(subject,holder.get());
                for (DMEResult<?, ?, BookCharacter> result : results) {
                    if (!result.canHold()){
                        return Optional.of(result.resolution().get());
                    }
                }
            }
//            if( thisChange instanceof TitleTLChange<?> tC && checkAgainst instanceof TitleTLChange<?> cA){
//
//            };
            return Optional.empty();
        }
    });
    public static final Condition<StateError, Sidecar.TitleChange> INHERITS_TITLE = new Condition<>("title_inherited",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
        @Override
        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            if( thisChange instanceof TitleTLChange.Inherit<?> ttl && ttl.isFirstTime()){

                return Optional.of(inheritanceFirstTime(new TimelineChangeState<>(thisChange.getDate(),Optional.empty(),thisChange));

            };

        }
    });
    public static final Condition<StateError, Sidecar.TitleChange> HAS_PARENT = new Condition<>("title_template",false, new TriFunction<TimelineChange<?>, TimelineChange<?>, Sidecar.TitleChange, Optional<StateError>>() {
        @Override
        public Optional<StateError> apply(TimelineChange<?> thisChange, TimelineChange<?> checkAgainst, Sidecar.TitleChange sidecar) {
            if(thisChange instanceof TitleTLChange.DeJureDrift<?,?> ttl){
                Title<?> newChild = sidecar.subject();
                Title<?> newParent = ttl.getNewParent().link();
                Title<?> oldParent = newChild.getParent().orElse(null);
                if (oldParent != null && (ttl.getLoreLast() == null || !oldParent.getId().equals(ttl.getLoreLast()))){
                    ttl.setLoreLast(oldParent.getId());
                    ttl.setLoreFlag(false);
                    return Optional.of(Errors.alreadyHasAParent(newChild,newParent,oldParent));
                }
            };
            return Optional.empty();
        }
    });
    private static <T extends DateMutableEntity<T,?>> Optional<T> unpackReference(Optional<DMEReference<T>> reference){
        return reference.map(DMEReference::link);
    }
}
