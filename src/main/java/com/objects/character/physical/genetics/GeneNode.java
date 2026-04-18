package com.objects.character.physical.genetics;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.objects.character.physical.GeneManager;
import com.utilities.id.StringIdentifiable;

import java.nio.charset.StandardCharsets;

public interface GeneNode<G extends GeneNode<G>> extends StringIdentifiable {

    default G getNode(){
        return (G) this;
    }
    default Class<G> getNodeClass(){
        return (Class<G>) getClass();
    }
    default long getNodeId(){
        Long id = GeneManager.getClassNode(getNodeClass());
        if (id == null) {
            Hasher hasher = Hashing.murmur3_128().newHasher();
            hasher.putString(getNodeClass().getName(), StandardCharsets.UTF_8);
            id = hasher.hash().asLong();
            GeneManager.registerNode(getNodeClass(),id);
        }
        return id;
    }
}
