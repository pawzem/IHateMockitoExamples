package com.example.mockito.pricing.v1;

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
