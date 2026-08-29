package com.example.mockito.pricing.v2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// Identical to v1 — the stubs still answer with the OLD contract, and this stays green.
@ExtendWith(MockitoExtension.class)
class PriceFormatterTest {

    @Mock
    private Multiplier multiplier;

    @InjectMocks
    private PriceFormatter formatter;

    @Test
    void labelsSmallOrder() {
        when(multiplier.multiply(3, 5)).thenReturn(15.0);

        assertEquals("15 PLN", formatter.priceLabel(3, 5));
    }

    @Test
    void labelsBigOrder() {
        when(multiplier.multiply(73, 137)).thenReturn(10_001.0);

        assertEquals("10001 PLN", formatter.priceLabel(73, 137));
    }
}
