package com.objects.organization.government.rights;

public enum RightLevel {
    POSSESS(2),
    PARTIAL(1),
    DO_NOT_POSSESS(0)

    ;
    private final int level;
    RightLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
}
