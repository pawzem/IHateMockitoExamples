package com.example.mockito.saas.tenant;

import com.example.mockito.saas.tenant.contract.TenantService;
import com.example.mockito.saas.tenant.contract.TenantServiceContract;

// The real service, real aggregate, real rules — just with an in-memory
// variant of its repository dependency. Same contract suite, no Docker.
class TenantServiceInMemoryTest extends TenantServiceContract {

    private final TenantService service = new TenantServiceImpl(new InMemoryTenantRepository());

    @Override
    protected TenantService service() {
        return service;
    }
}
