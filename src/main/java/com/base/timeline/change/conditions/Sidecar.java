package com.base.timeline.change.conditions;

import com.base.reference.DMEReference;
import com.google.common.collect.ImmutableList;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class Sidecar {
    public static class Empty extends Sidecar {
        public Empty() {
        }
    }
    public static class TitleChange extends Sidecar {
        private final Title<?> subject;
        private final Optional<BookCharacter> holder;
        public TitleChange(Title<?> subject, @Nullable BookCharacter holder) {
            this.subject = subject;
            this.holder = Optional.ofNullable(holder);
        }
        public <T extends Title<T>> TitleChange(DMEReference<T> subject, @Nullable DMEReference<BookCharacter> holder) {
            this.subject = subject.link();
            if (holder == null) {
                this.holder = Optional.empty();
                return;
            }
            this.holder = Optional.ofNullable(holder.link());
        }
        public Title<?> subject() {
            return subject;
        }
        public Optional<BookCharacter> holder() {
            return holder;
        }
        publoc boolean hasHolder() {
            return holder.isPresent();
        }
    }
    public static <C extends Sidecar> C empty() {
        return (C) new Empty();
    }

}
