package com.example.mockito.pricing.v2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Updated together with the contract change, like a good developer does.
class MultiplierTest {

    private final Multiplier multiplier = new Multiplier();

    @Test
    void multipliesSmallOrder() {
        assertEquals(15.0, multiplier.multiply(3, 5));
    }

    @Test
    void discountsBigOrder() {
        assertEquals(9_500.95, multiplier.multiply(73, 137), 0.001);
    }
}
