package com.example.mockito.banking.v2;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public class Account {

    private final long id;
    private BigDecimal balance;
    private final BigDecimal bonusRate;

    public Account(long id, BigDecimal balance, BigDecimal bonusRate) {
        this.id = id;
        this.balance = requireNonNull(balance, "balance must not be null");
        this.bonusRate = bonusRate; // new business rule: bonus program is opt-in, null until the customer joins
    }

    public void applyYearlyBonus() {
        balance = balance.add(bonusRate.multiply(balance));
    }

    public long id() {
        return id;
    }

    public BigDecimal balance() {
        return balance;
    }

    public BigDecimal bonusRate() {
        return bonusRate;
    }
}
