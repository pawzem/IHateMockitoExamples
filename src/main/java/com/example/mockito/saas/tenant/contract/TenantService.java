package com.example.mockito.saas.tenant.contract;

import com.example.mockito.saas.shared.identity.TenantId;

import java.util.Optional;

public interface TenantService {

    TenantDto register(String slug, String displayName);

    TenantDto rename(TenantId id, String newDisplayName);

    Optional<TenantDto> find(TenantId id);
}
