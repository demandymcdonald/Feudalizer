package com.objects.character.physical.genetics;
//Base strength of the gene in genetics calculation. Affected by randomness, if the gene is currently active, and what other gene's it's being measured against.
public enum GeneStrength {
    RECESSIVE_LATENT(51,true),
    RECESSIVE(51,false),
    DOMINANT(100,true),
    CO_DOMINANT(105,false), //Two traits can exist at once.
    COMPLETE_DOMINANT(500,false);

    private final int defaultStrength;
    private final boolean canBeLatent;
    GeneStrength(int defaultStrength, boolean canBeLatent) {
        this.defaultStrength = defaultStrength;
        this.canBeLatent = canBeLatent;
    }
    GeneStrength(int defaultStrength) {
        this.defaultStrength = defaultStrength;
        this.canBeLatent = true;
    }
    public int getStrength() {
        return defaultStrength;
    }
    public boolean canBeLatent() {
        return canBeLatent;
    }
}
