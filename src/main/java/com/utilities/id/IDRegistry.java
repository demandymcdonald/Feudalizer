package com.utilities.id;

import com.Global.*;
import com.objects.organization.education.Education;

import java.util.HashMap;
import java.util.Map;

public abstract class IDRegistry<K extends Identifiable<I>,V,I> {
    private final Map<K, V> eduInstances = new HashMap<>();
}
