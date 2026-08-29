package com.example.mockito.banking.v1;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void rejectsAccountWithoutBonusRate() {
        assertThrows(NullPointerException.class,
                () -> new Account(42L, new BigDecimal("100"), null));
    }
}
