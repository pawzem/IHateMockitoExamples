package com.example.mockito.saas.tenant.contract;

class TenantServiceStubTest extends TenantServiceContract {

    private final TenantServiceStub stub = new TenantServiceStub();

    @Override
    protected TenantService service() {
        return stub;
    }
}
