package com.base.thread.flight.acars;

import com.base.thread.flight.ThreadFlight;
import org.apache.commons.lang3.math.Fraction;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Acars<T> implements Future<T> {
    private final ThreadFlight flight;
    private AtomicReference<Instruction> currentInstruction = new AtomicReference<>();
    public Acars(ThreadFlight flight) {
        this.flight = flight;
    }

    public enum Instruction {
        HOLDING_PATTERN,
        LAND,
        CONTINUE,
    }

    public Instruction getTCCInstruction() {
        return Instruction.CONTINUE;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!mayInterruptIfRunning && flight.getStatus().get().isInAir()) return false;
        currentInstruction.set(Instruction.LAND);
    }

    @Override
    public boolean isCancelled() {
        return currentInstruction.get() == Instruction.LAND;
    }

    @Override
    public boolean isDone() {
        return flight.getStatus().get().isFlightFinished();
    }
    public abstract Fraction getProgress();
}
