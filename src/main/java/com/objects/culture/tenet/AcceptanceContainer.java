package com.objects.culture.tenet;

public record AcceptanceContainer(double value) {
    public Acceptance getAcceptance() {
        return Acceptance.get((int) Math.round(value));
    }
}
