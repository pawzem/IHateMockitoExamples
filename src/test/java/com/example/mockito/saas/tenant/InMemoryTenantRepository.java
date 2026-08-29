package com.example.mockito.saas.tenant;

import com.example.mockito.saas.shared.identity.TenantId;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory variant of the repository dependency — for the tests that need
 * the REAL service behavior without a database.
 */
class InMemoryTenantRepository implements TenantRepository {

    private final Map<TenantId, Tenant> tenants = new LinkedHashMap<>();

    @Override
    public boolean slugTaken(String slug) {
        return tenants.values().stream().anyMatch(tenant -> tenant.slug().equals(slug));
    }

    @Override
    public void insert(Tenant tenant) {
        tenants.put(tenant.id(), tenant);
    }

    @Override
    public void update(Tenant tenant) {
        tenants.put(tenant.id(), tenant);
    }

    @Override
    public Optional<Tenant> findById(TenantId id) {
        return Optional.ofNullable(tenants.get(id));
    }
}
