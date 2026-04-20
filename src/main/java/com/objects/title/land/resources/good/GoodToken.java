package com.objects.title.land.resources.good;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.JsonPrimitive;

public record GoodToken(IGood good, int amount) {

    public JsonPrimitive toJson() {

    }
}
