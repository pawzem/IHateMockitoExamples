package com.example.mockito.saas.billing;

import com.example.mockito.saas.shared.identity.TenantId;
import com.example.mockito.saas.tenant.contract.TenantDto;
import com.example.mockito.saas.tenant.contract.TenantService;

import java.math.BigDecimal;

public class InvoiceService {

    private final TenantService tenants;

    public InvoiceService(TenantService tenants) {
        this.tenants = tenants;
    }

    public String invoiceHeader(TenantId tenantId, BigDecimal amount) {
        TenantDto tenant = tenants.find(tenantId)
                .orElseThrow(() -> new IllegalStateException("Unknown tenant " + tenantId));
        return "Invoice for %s: %s PLN".formatted(tenant.displayName(), amount);
    }
}
