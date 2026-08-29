package com.example.mockito.banking.v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
class AccountRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    private AccountRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(postgres.getJdbcUrl());
        dataSource.setUser(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS account");
            statement.execute("""
                    CREATE TABLE account (
                        id BIGINT PRIMARY KEY,
                        balance DECIMAL(19, 2) NOT NULL,
                        bonus_rate DECIMAL(5, 2) NOT NULL
                    )""");
            // the migration that shipped together with the opt-in bonus program
            statement.execute("ALTER TABLE account ALTER COLUMN bonus_rate DROP NOT NULL");
            statement.execute("INSERT INTO account VALUES (42, 100.00, 0.05)");
            statement.execute("INSERT INTO account VALUES (43, 100.00, NULL)");
        }
        repository = new AccountRepository(dataSource);
    }

    @Test
    void findsAccount() {
        Account account = repository.find(42L);

        assertEquals(new BigDecimal("100.00"), account.balance());
        assertEquals(new BigDecimal("0.05"), account.bonusRate());
    }

    @Test
    void findsAccountWithoutBonusRate() {
        Account account = repository.find(43L);

        assertNull(account.bonusRate());
    }

    @Test
    void updatesAccount() {
        Account account = repository.find(42L);
        account.applyYearlyBonus();

        repository.update(account);

        assertEquals(new BigDecimal("105.00"), repository.find(42L).balance());
    }
}
