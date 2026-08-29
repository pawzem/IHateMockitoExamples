package com.example.mockito.saas.tenant.contract;

public class TenantSlugAlreadyTakenException extends RuntimeException {

    public TenantSlugAlreadyTakenException(String slug) {
        super("Tenant slug already taken: " + slug);
    }
}
