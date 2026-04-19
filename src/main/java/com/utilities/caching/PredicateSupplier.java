package com.utilities.caching;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * A specialized caching supplier that determines whether to invalidate the cached value
 * based on the result of a supplied bi-predicate.
 *
 * @param <T> the type of the cached value
 * @param <U> the type of the parameter used in the invalidation logic
 */
public class PredicateSupplier<T,U> extends CachingSupplier<T>{
    private final BiPredicate<T,U> shouldInvalidate;
    public PredicateSupplier(Supplier<T> supplier, BiPredicate<T,U> shouldInvalidate) {
        super(supplier);
        this.shouldInvalidate = Objects.requireNonNullElseGet(shouldInvalidate, () -> (a, b) -> true);
    }
    public boolean update(U b){
        boolean result = shouldInvalidate.test(get(),b);
        if(result){
            clear();
        }
        return result;
    }
}
