package com.objects.character.species.aspect;

public enum BodyPart {
    WHOLE_BODY,
    BODY_HAIR(WHOLE_BODY),
    BODY_FUR(WHOLE_BODY),
    FEATHERS(WHOLE_BODY),
    SKIN(WHOLE_BODY),
    HEAD(WHOLE_BODY),
    HEAD_HAIR(HEAD),
    FACE(HEAD),
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
    LEFT_FOOT(LEFT_LEG),
    RIGHT_FOOT(RIGHT_LEG),
    ARMS(TORSO),
    LEFT_ARM(ARMS),
    RIGHT_ARM(ARMS),
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

    private final BodyPart parent;
    BodyPart(BodyPart part) {
        this.parent = part;
    }
    BodyPart() {
        this.parent = null;
    }
    public BodyPart getParent() {
        return parent;
    }
}
