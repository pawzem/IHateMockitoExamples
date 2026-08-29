package com.example.mockito.saas.tenant.contract;

import com.example.mockito.saas.shared.identity.TenantId;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory implementation of the tenant contract, shipped BY the tenant
 * context FOR everybody else's tests. Kept honest by the same contract test
 * suite that runs against the real implementation.
 */
public class TenantServiceStub implements TenantService {

    private final Map<TenantId, TenantDto> tenants = new LinkedHashMap<>();

    @Override
    public TenantDto register(String slug, String displayName) {
        if (tenants.values().stream().anyMatch(tenant -> tenant.slug().equals(slug))) {
            throw new TenantSlugAlreadyTakenException(slug);
        }
        TenantDto tenant = new TenantDto(TenantId.newId(), slug, displayName, TenantStatus.ACTIVE);
        tenants.put(tenant.id(), tenant);
        return tenant;
    }

    @Override
    public TenantDto rename(TenantId id, String newDisplayName) {
        TenantDto current = tenants.get(id);
        if (current == null) {
            throw new IllegalStateException("No tenant with id " + id);
        }
        TenantDto renamed = new TenantDto(id, current.slug(), newDisplayName, current.status());
        tenants.put(id, renamed);
        return renamed;
    }

    @Override
    public Optional<TenantDto> find(TenantId id) {
        return Optional.ofNullable(tenants.get(id));
    }
}
