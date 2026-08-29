package com.example.mockito.banking.v2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Identical to v1 — the hand-built Account still lives in the world
// where every account has a bonus rate. Green, of course.
@ExtendWith(MockitoExtension.class)
class BonusServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private BonusService service;

    @Test
    void appliesYearlyBonusAndSavesTheAccount() {
        Account account = new Account(42L, new BigDecimal("100"), new BigDecimal("0.05"));
        when(repository.find(42L)).thenReturn(account);

        service.applyYearlyBonus(42L);

        assertEquals(new BigDecimal("105.00"), account.balance());
        verify(repository).update(account);
    }
}
