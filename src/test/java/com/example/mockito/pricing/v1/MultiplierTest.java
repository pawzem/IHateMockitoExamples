package com.example.mockito.pricing.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplierTest {

    private final Multiplier multiplier = new Multiplier();

    @Test
    void multipliesSmallOrder() {
        assertEquals(15.0, multiplier.multiply(3, 5));
    }

    @Test
    void multipliesBigOrder() {
        assertEquals(10_001.0, multiplier.multiply(73, 137));
    }
}
