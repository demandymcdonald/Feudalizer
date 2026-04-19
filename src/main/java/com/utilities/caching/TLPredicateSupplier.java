package com.utilities.caching;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.utilities.TimelineSynced;
import org.reactfx.util.TriPredicate;

import java.time.LocalDate;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * A specialized predicate supplier that combines timeline synchronization with
 * a caching mechanism and additional predicate testing logic. This class holds
 * a reference to a {@link DMEReference} and evaluates predicates to determine
 * when to clear or update cached values during timeline-related events.
 *
 * @param <T> the type of the cached value
 * @param <U> the type of the {@link DateMutableEntity} referenced by the {@link DMEReference}
 */
public class TLPredicateSupplier<T,U extends DateMutableEntity<?>> extends PredicateSupplier<T, DMEReference<U>> implements TimelineSynced {
    private final DMEReference<U> reference;
    private TriPredicate<T,U, LocalDate> additional = (a, b, c) -> false;
    public TLPredicateSupplier(DMEReference<U> reference, Supplier<T> supplier, BiPredicate<T, DMEReference<U>> shouldInvalidate) {
        super(supplier, shouldInvalidate);
        this.reference = reference;
        registerListener();
    }
    public TLPredicateSupplier<T,U> setAdditional(TriPredicate<T,U, LocalDate> additional) {
        this.additional = additional;
        return this;
    }
    @Override
    public void onLoad(LocalDate date) {}
    @Override
    public void onLink(LocalDate date) {
        if(additional.test(get(),reference.get(),date)){
            clear();
            return;
        }
        update(reference);
    }
}
