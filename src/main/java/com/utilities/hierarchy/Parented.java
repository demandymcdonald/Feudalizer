package com.utilities.hierarchy;

import java.util.Optional;

public interface Parented<T> {
    public Optional<T> getParent();
}
