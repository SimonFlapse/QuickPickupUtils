package com.simonflapse.gearth.parsers.enums.navigator;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OHFlatModels {
    MODEL_A(104), // Regular rectangle, wide
    MODEL_B(94), // L shaped
    MODEL_C(36), // Small square
    MODEL_D(84), // Long rectangle, wide
    MODEL_E(80), // Regular rectangle, deep
    MODEL_F(80), // zigzag
    MODEL_G(80, true), // rectangle, wide with stairs to small square
    MODEL_H(74, true),
    MODEL_I(416), // Large rectangle, wide
    MODEL_J(320), // Z-room
    MODEL_K(448),
    MODEL_L(352), // Flipped C-shaped
    MODEL_M(384), // X-shaped
    MODEL_N(372), // Large square with small square island inside
    MODEL_O(416, true), // Large square with stairs to two small rectangles
    MODEL_P(352, true),
    MODEL_Q(304, true),
    MODEL_R(336, true);


    private final int size;
    private final boolean hcOnly;

    OHFlatModels(int size, boolean hcOnly) {
        this.size = size;
        this.hcOnly = hcOnly;
    }

    OHFlatModels(int size) {
        this(size, false);
    }
}
