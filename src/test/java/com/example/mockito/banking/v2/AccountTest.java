package com.example.mockito.banking.v2;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Updated together with the opt-in rule — but nobody re-asked what
// applyYearlyBonus should do for an account without a bonus rate.
class AccountTest {

    @Test
    void appliesYearlyBonus() {
        Account account = new Account(42L, new BigDecimal("100"), new BigDecimal("0.05"));

        account.applyYearlyBonus();

        assertEquals(new BigDecimal("105.00"), account.balance());
    }

    @Test
    void rejectsAccountWithoutBalance() {
        assertThrows(NullPointerException.class,
                () -> new Account(42L, null, new BigDecimal("0.05")));
    }

    @Test
    void allowsAccountWithoutBonusRate() {
        Account account = new Account(42L, new BigDecimal("100"), null);

        assertNull(account.bonusRate());
    }
}
