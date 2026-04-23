package com.objects.family;

import com.base.reference.DMEReference;
import com.objects.character.sentient.SentientCharacter;
import org.jgrapht.graph.DefaultEdge;

public class FamilyEdge extends DefaultEdge {
    private final FamilyRelationship relationship;
    public FamilyEdge(FamilyRelationship relationship) {
        this.relationship = relationship;
    }
    public FamilyEdge(FamilyRelationship type, DMEReference<? extends SentientCharacter<?>> target){
        relationship = FamilyRelationship.getByPronoun(type,target.get().getPronouns());
    }

    public FamilyRelationship getRelationship() {
        return relationship;
    }
}
