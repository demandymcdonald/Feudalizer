package com.objects.title.land.resources;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public record GoodToken(Resource good, int amount) {


    public long getHash(){
        Hasher hasher = Hashing.murmur3_128().newHasher();
        hasher.putString(good.type().id(), Charsets.UTF_8);
        hasher.putLong(amount);
        return hasher.hash().asLong();
    }
}
