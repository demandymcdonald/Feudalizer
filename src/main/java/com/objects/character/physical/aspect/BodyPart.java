package com.objects.character.physical.aspect;

import com.objects.character.physical.GeneManager;
import com.objects.character.physical.IGeneNode;
import com.objects.character.physical.species.Species;
import com.utilities.caching.CachingSupplier;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public enum BodyPart implements IGeneNode<BodyPart> {
    WHOLE_BODY,
    BODY_HAIR(WHOLE_BODY),
    BODY_FUR(WHOLE_BODY),
    FEATHERS(WHOLE_BODY),
    SKIN(WHOLE_BODY),
    HEAD(WHOLE_BODY),
    HEAD_HAIR(HEAD),
    FACE(HEAD),
    FACIAL_HAIR(HEAD),
    EARS(HEAD),
    LEFT_EAR(EARS),
    RIGHT_EAR(EARS),
    HORN(HEAD),
    EYES(FACE),
    LEFT_EYE(EYES),
    RIGHT_EYE(EYES),
    MOUTH(FACE),
    TUSK(MOUTH),
    TEETH(MOUTH),
    TONGUE(MOUTH),
    NOSE(FACE),
    NECK(HEAD),
    TORSO(WHOLE_BODY),
    BACK(TORSO),
    CHEST(TORSO),
    SHOULDERS(TORSO),
    PELVIS(TORSO),
    LEGS(PELVIS),
    LEFT_LEG(LEGS),
    RIGHT_LEG(LEGS),
    FEET(LEGS),
    LEFT_FOOT(LEFT_LEG),
    RIGHT_FOOT(RIGHT_LEG),
    ARMS(TORSO),
    LEFT_ARM(ARMS),
    RIGHT_ARM(ARMS),
    HANDS(ARMS),
    LEFT_HAND(LEFT_ARM),
    RIGHT_HAND(RIGHT_ARM),
    VITAL_ORGAN(TORSO),
    BRAIN(WHOLE_BODY),
    IMMUNE_SYSTEM(TORSO),
    DIGESTIVE_ORGAN(TORSO),
    RESPIRATORY_ORGAN(TORSO),
    TAIL(PELVIS),
    FIN(WHOLE_BODY),
    WINGS(WHOLE_BODY),
    LEFT_WING(WINGS),
    RIGHT_WING(WINGS),
    GENITAL(PELVIS), //PROBABLY WON'T USE, BUT IT'S GOOD TO HAVE THE OPTION? MAY REUSE BODY PART FOR INJURY TRACKING

    ;
    private final CachingSupplier<Set<Species>> validSpecies = new CachingSupplier<>(this::buildValidSpecies);
    private final CachingSupplier<Set<PhysicalAspect>> validAspects = new CachingSupplier<>(this::buildValidAspects);
    private final CachingSupplier<Set<BodyPart>> validChildren = new CachingSupplier<>(this::buildValidChildren);
    private Set<Species> buildValidSpecies(){
        return GeneManager.getConnectionsWhereTarget(this, Species.class);
    }
    private Set<PhysicalAspect> buildValidAspects(){
        return GeneManager.getConnectionsWhereSource(this, PhysicalAspect.class);
    }
    private Set<BodyPart> buildValidChildren(){
        Set<BodyPart> parts = new HashSet<>(GeneManager.getConnectionsWhereSource(this, BodyPart.class));
        for(BodyPart part : parts.toArray(BodyPart[]::new)){
            parts.addAll(part.getValidBodyParts());
        }
        return parts;
    }
    private final BodyPart parent;
    BodyPart(BodyPart part) {
        this.parent = part;

        GeneManager.Body_Part.register(this);
    }
    BodyPart() {
        this.parent = null;
        GeneManager.Body_Part.register(this);
    }
    public BodyPart getParent() {
        return parent;
    }

    @Override
    public String getID() {
        return "body_part:"+ this.name().toLowerCase(Locale.ROOT);
    }


    @Override
    public Set<BodyPart> getValidBodyParts() {
        return new HashSet<>(validChildren.get());
    }

    @Override
    public Set<Species> getValidSpecies() {
        return new HashSet<>(validSpecies.get());
    }

    @Override
    public Set<PhysicalAspect> getValidAspects() {
        return new HashSet<>(validAspects.get());
    }
}
