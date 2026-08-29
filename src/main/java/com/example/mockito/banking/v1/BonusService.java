package com.example.mockito.banking.v1;

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
