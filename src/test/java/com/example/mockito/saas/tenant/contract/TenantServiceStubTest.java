package com.example.mockito.saas.tenant.contract;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TenantServiceStubTest extends TenantServiceContract {

    private final TenantServiceStub stub = new TenantServiceStub();

    @Override
    protected TenantService service() {
        return stub;
    }

    @Test
    void shipsTheWellKnownTestCards() {
        assertEquals(Optional.of(TenantCards.NORMAL_TENANT), stub.find(TenantCards.NORMAL_TENANT.id()));
        assertEquals(Optional.of(TenantCards.SUSPENDED_TENANT), stub.find(TenantCards.SUSPENDED_TENANT.id()));
        assertTrue(stub.find(TenantCards.UNKNOWN_TENANT).isEmpty());
    }
}
