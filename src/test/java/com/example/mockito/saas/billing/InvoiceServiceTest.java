package com.example.mockito.saas.billing;

import com.example.mockito.saas.tenant.contract.TenantDto;
import com.example.mockito.saas.tenant.contract.TenantServiceStub;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

// No @Mock, no when(), no verify() — the billing context tests against the
// stub the tenant context ships, and the contract tests keep that stub honest.
class InvoiceServiceTest {

    private final TenantServiceStub tenants = new TenantServiceStub();
    private final InvoiceService invoices = new InvoiceService(tenants);

    @Test
    void printsInvoiceHeaderForTenant() {
        TenantDto tenant = tenants.register("acme", "Acme Corp");

        assertEquals("Invoice for Acme Corp: 100.00 PLN",
                invoices.invoiceHeader(tenant.id(), new BigDecimal("100.00")));
    }
}
