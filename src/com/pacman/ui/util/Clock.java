package com.pacman.ui.util;

/**
 *  Auxillary class to measure time between two events
 */
public class Clock {
    /**
     *  Previous CPU timer time
     */
    long last;
    /**
     * Current CPU timer time
     */
    long now;
    /**
     * DeltaTime between last and now
     */
    double dt;

    /**
     * Deafult construktor
     */
    public Clock() {
        last = System.nanoTime();
        now = last;
        dt = 0.f;
    }

    /**
     * Returns the time between this call and the last one
     */
    public double restart() {
        now = System.nanoTime();
        dt =  (now-last)/1000000000.f;
        last = now;
        return dt;
    }
}

