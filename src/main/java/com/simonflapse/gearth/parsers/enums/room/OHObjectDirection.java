package com.simonflapse.gearth.parsers.enums.room;

public enum OHObjectDirection {
    EAST(0), // Pointing to the right
    SOUTH_EAST(1), // Pointing directly to the right, diagonally
    SOUTH(2), // Pointing down
    SOUTH_WEST(3), // Pointing directly down, diagonally
    WEST(4), // Pointing to the left
    NORTH_WEST(5), // Pointing directly to the left, diagonally
    NORTH(6), // Pointing up
    NORTH_EAST(7); // Pointing directly up, diagonally

    private final int value;

    OHObjectDirection(int value) {
        this.value = value;
    }

    public static OHObjectDirection fromValue(int value) {
        for (OHObjectDirection direction : values()) {
            if (direction.value == value) {
                return direction;
            }
        }
        return null;
    }
}
