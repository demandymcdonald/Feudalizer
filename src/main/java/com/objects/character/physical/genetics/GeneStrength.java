package com.objects.character.physical.genetics;
//Base strength of the gene in genetics calculation. Affected by randomness, if the gene is currently active, and what other gene's it's being measured against.
public enum GeneStrength {
    RECESSIVE(51),
    DOMINANT(100),
    SUPER_DOMINANT(500);

    private final int defaultStrength;

    GeneStrength(int defaultStrength) {
        this.defaultStrength = defaultStrength;
    }

    public int getDefaultStrength() {
        return defaultStrength;
    }

}
