package com.example.mockito.saas.tenant.contract;

import com.example.mockito.saas.shared.identity.TenantId;

import java.util.UUID;

/**
 * Static test cards — the same idea as Stripe's test card numbers. Published
 * in the contract package so every downstream test picks a well-known card
 * instead of programming answers into a mock. The stub ships pre-seeded with
 * them.
 */
public final class TenantCards {

    /** Always present in the stub, active — the happy path. */
    public static final TenantDto NORMAL_TENANT = new TenantDto(
            new TenantId(UUID.fromString("00000000-0000-0000-0000-000000000001")),
            "normal-tenant", "Normal Tenant", TenantStatus.ACTIVE);

    /** Always present in the stub, suspended — for the "this should be refused" paths. */
    public static final TenantDto SUSPENDED_TENANT = new TenantDto(
            new TenantId(UUID.fromString("00000000-0000-0000-0000-000000000002")),
            "suspended-tenant", "Suspended Tenant", TenantStatus.SUSPENDED);

    /** Guaranteed to never exist — for the not-found paths. */
    public static final TenantId UNKNOWN_TENANT =
            new TenantId(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"));

    private TenantCards() {
    }
}
