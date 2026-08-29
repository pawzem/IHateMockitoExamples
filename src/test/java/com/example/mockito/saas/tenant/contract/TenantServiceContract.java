package com.example.mockito.saas.tenant.contract;

import com.example.mockito.saas.shared.identity.TenantId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The behavior every implementation of the tenant contract must have.
 * Runs twice: against the real Postgres-backed service AND against the stub —
 * if they ever drift apart, one of the two runs goes red.
 */
public abstract class TenantServiceContract {

    protected abstract TenantService service();

    @Test
    void registersTenant() {
        TenantDto tenant = service().register("acme", "Acme Corp");

        assertEquals("acme", tenant.slug());
        assertEquals("Acme Corp", tenant.displayName());
        assertEquals(TenantStatus.ACTIVE, tenant.status());
    }

    @Test
    void rejectsDuplicateSlug() {
        service().register("acme", "Acme Corp");

        assertThrows(TenantSlugAlreadyTakenException.class,
                () -> service().register("acme", "Fake Acme"));
    }

    @Test
    void renamesTenant() {
        TenantDto tenant = service().register("acme", "Acme Corp");

        TenantDto renamed = service().rename(tenant.id(), "Acme Holdings");

        assertEquals("Acme Holdings", renamed.displayName());
        assertEquals("Acme Holdings", service().find(tenant.id()).orElseThrow().displayName());
    }

    @Test
    void cannotRenameUnknownTenant() {
        assertThrows(IllegalStateException.class,
                () -> service().rename(TenantId.newId(), "Ghost Corp"));
    }

    @Test
    void findsNothingForUnknownTenant() {
        assertTrue(service().find(TenantId.newId()).isEmpty());
    }
}
