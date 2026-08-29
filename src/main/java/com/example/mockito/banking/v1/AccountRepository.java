package com.example.mockito.banking.v1;

import javax.sql.DataSource;
import java.sql.SQLException;

public class AccountRepository {

    private final DataSource dataSource;

    public AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Account find(long id) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(
                     "SELECT id, balance, bonus_rate FROM account WHERE id = ?")) {
            statement.setLong(1, id);
            try (var results = statement.executeQuery()) {
                if (!results.next()) {
                    throw new IllegalStateException("No account with id " + id);
                }
                return new Account(
                        results.getLong("id"),
                        results.getBigDecimal("balance"),
                        results.getBigDecimal("bonus_rate"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void update(Account account) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(
                     "UPDATE account SET balance = ?, bonus_rate = ? WHERE id = ?")) {
            statement.setBigDecimal(1, account.balance());
            statement.setBigDecimal(2, account.bonusRate());
            statement.setLong(3, account.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
