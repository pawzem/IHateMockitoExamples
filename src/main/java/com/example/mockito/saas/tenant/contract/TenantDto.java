package com.example.mockito.saas.tenant.contract;

import com.example.mockito.saas.shared.identity.TenantId;

public record TenantDto(TenantId id, String slug, String displayName, TenantStatus status) {
}
