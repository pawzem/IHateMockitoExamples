package com.example.mockito.saas.tenant;

import com.example.mockito.saas.shared.identity.TenantId;
import com.example.mockito.saas.tenant.contract.TenantStatus;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

class JdbcTenantRepository implements TenantRepository {

    private final DataSource dataSource;

    JdbcTenantRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean slugTaken(String slug) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement("SELECT 1 FROM tenant WHERE slug = ?")) {
            statement.setString(1, slug);
            try (var results = statement.executeQuery()) {
                return results.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void insert(Tenant tenant) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(
                     "INSERT INTO tenant (id, slug, display_name, status) VALUES (?, ?, ?, ?)")) {
            statement.setObject(1, tenant.id().value());
            statement.setString(2, tenant.slug());
            statement.setString(3, tenant.displayName());
            statement.setString(4, tenant.status().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Tenant tenant) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(
                     "UPDATE tenant SET display_name = ?, status = ? WHERE id = ?")) {
            statement.setString(1, tenant.displayName());
            statement.setString(2, tenant.status().name());
            statement.setObject(3, tenant.id().value());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Tenant> findById(TenantId id) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(
                     "SELECT id, slug, display_name, status FROM tenant WHERE id = ?")) {
            statement.setObject(1, id.value());
            try (var results = statement.executeQuery()) {
                if (!results.next()) {
                    return Optional.empty();
                }
                return Optional.of(Tenant.restore(
                        new TenantId(results.getObject("id", UUID.class)),
                        results.getString("slug"),
                        results.getString("display_name"),
                        TenantStatus.valueOf(results.getString("status"))));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
