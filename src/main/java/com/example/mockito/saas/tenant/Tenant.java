package com.example.mockito.saas.tenant;

import com.example.mockito.saas.shared.identity.TenantId;
import com.example.mockito.saas.tenant.contract.TenantDto;
import com.example.mockito.saas.tenant.contract.TenantStatus;

import static java.util.Objects.requireNonNull;

class Tenant {

    private final TenantId id;
    private final String slug;
    private String displayName;
    private final TenantStatus status;

    private Tenant(TenantId id, String slug, String displayName, TenantStatus status) {
        this.id = requireNonNull(id, "id must not be null");
        this.slug = requireNonNull(slug, "slug must not be null");
        this.displayName = requireNonNull(displayName, "displayName must not be null");
        this.status = requireNonNull(status, "status must not be null");
    }

    static Tenant register(String slug, String displayName) {
        return new Tenant(TenantId.newId(), slug, displayName, TenantStatus.ACTIVE);
    }

    static Tenant restore(TenantId id, String slug, String displayName, TenantStatus status) {
        return new Tenant(id, slug, displayName, status);
    }

    void rename(String newDisplayName) {
        this.displayName = requireNonNull(newDisplayName, "newDisplayName must not be null");
    }

    TenantDto toDto() {
        return new TenantDto(id, slug, displayName, status);
    }

    TenantId id() {
        return id;
    }

    String slug() {
        return slug;
    }

    String displayName() {
        return displayName;
    }

    TenantStatus status() {
        return status;
    }
}
