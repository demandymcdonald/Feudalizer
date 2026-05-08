package com.objects.organization.government.rights;

public enum RightLevel {
    OVERRIDE_POSSESS(4),
    POSSESS(2),
    PARTIAL(1),
    DO_NOT_POSSESS(0),
    OVERRIDE_DO_NOT_POSSESS(-1);
    private final int level;
    RightLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
}
