package com.example.mockito.saas.tenant;

import com.example.mockito.saas.tenant.contract.TenantService;
import com.example.mockito.saas.tenant.contract.TenantServiceContract;
import org.junit.jupiter.api.BeforeEach;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class TenantServiceImplTest extends TenantServiceContract {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    private TenantService service;

    @BeforeEach
    void setUp() throws Exception {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(postgres.getJdbcUrl());
        dataSource.setUser(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS tenant");
            statement.execute("""
                    CREATE TABLE tenant (
                        id UUID PRIMARY KEY,
                        slug VARCHAR(64) NOT NULL UNIQUE,
                        display_name VARCHAR(255) NOT NULL,
                        status VARCHAR(16) NOT NULL
                    )""");
        }
        service = new TenantServiceImpl(new JdbcTenantRepository(dataSource));
    }

    @Override
    protected TenantService service() {
        return service;
    }
}
