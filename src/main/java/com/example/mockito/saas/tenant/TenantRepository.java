package com.example.mockito.saas.tenant;

import com.example.mockito.saas.shared.identity.TenantId;

import java.util.Optional;

interface TenantRepository {

    boolean slugTaken(String slug);

    void insert(Tenant tenant);

    void update(Tenant tenant);

    Optional<Tenant> findById(TenantId id);
}
