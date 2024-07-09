package com.simonflapse.gearth.parsers.enums.navigator;

public enum OHFlatLock {
    OPEN(0),
    CLOSED(1),
    PASSWORD(2);

    private final int value;

    OHFlatLock(int value) {
        this.value = value;
    }

    public static OHFlatLock fromValue(int value) {
        for (OHFlatLock lock : values()) {
            if (lock.value == value) {
                return lock;
            }
        }
        return null;
    }
}
