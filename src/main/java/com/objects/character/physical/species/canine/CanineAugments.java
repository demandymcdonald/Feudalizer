package com.objects.character.physical.species.canine;

import com.objects.character.physical.aspect.BodyPart;
import com.objects.character.physical.augment.Augment;

public class CanineAugments {
    public static final Augment CANINE_PROSTHETIC_LEG = new Augment(BodyPart.LEGS, "canine_prosthetic_leg", "Prosthetic Leg", "");
    public static final Augment CANINE_PROSTHETIC_ARM = new Augment(BodyPart.ARMS, "canine_prosthetic_arm", "Prosthetic Paw", "");
    public static final Augment CANINE_GROOMING = new Augment(BodyPart.BODY_FUR, "canine_grooming", "Grooming", "");
    public static final Augment CANINE_COLLAR = new Augment(BodyPart.NECK, "canine_collar", "Collar", "");
}