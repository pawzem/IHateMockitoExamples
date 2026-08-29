package com.example.mockito.pricing.v2;

public class Multiplier {

    public double multiply(double a, double b) {
        double result = a * b;
        if (result > 10_000) {
            return result * 0.95; // new business rule: 5% discount on big orders
        }
        return result;
    }
}
