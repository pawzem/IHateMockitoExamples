package com.example.mockito.pricing.v2;

// Identical to v1 — nobody touched this class when the Multiplier contract changed.
public class PriceFormatter {

    private final Multiplier multiplier;

    public PriceFormatter(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    public String priceLabel(double quantity, double unitPrice) {
        double total = multiplier.multiply(quantity, unitPrice);
        return "%d PLN".formatted((long) total);
    }
}
