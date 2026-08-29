package com.example.mockito.pricing.v2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// The test nobody was told to write. Fails on purpose — this is the whole point:
// expected: <9500.95 PLN> but was: <9500 PLN>
class PriceFormatterIntegrationTest {

    private final PriceFormatter formatter = new PriceFormatter(new Multiplier());

    @Test
    void labelsBigOrder() {
        assertEquals("9500.95 PLN", formatter.priceLabel(73, 137));
    }
}
