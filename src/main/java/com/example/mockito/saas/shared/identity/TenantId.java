package com.example.mockito.saas.shared.identity;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record TenantId(UUID value) {

    public TenantId {
        requireNonNull(value, "value must not be null");
    }

    public static TenantId newId() {
        return new TenantId(UUID.randomUUID());
    }
}
