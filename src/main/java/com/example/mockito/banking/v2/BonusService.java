package com.example.mockito.banking.v2;

// Identical to v1 — nobody touched this class when the contract changed.
public class BonusService {

    private final AccountRepository repository;

    public BonusService(AccountRepository repository) {
        this.repository = repository;
    }

    public void applyYearlyBonus(long accountId) {
        Account account = repository.find(accountId);
        account.applyYearlyBonus();
        repository.update(account);
    }
}
