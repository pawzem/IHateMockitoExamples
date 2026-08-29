package com.example.mockito.saas.tenant;

import com.example.mockito.saas.shared.identity.TenantId;
import com.example.mockito.saas.tenant.contract.TenantDto;
import com.example.mockito.saas.tenant.contract.TenantService;
import com.example.mockito.saas.tenant.contract.TenantSlugAlreadyTakenException;

import java.util.Optional;

class TenantServiceImpl implements TenantService {

    private final TenantRepository repository;

    TenantServiceImpl(TenantRepository repository) {
        this.repository = repository;
    }

    @Override
    public TenantDto register(String slug, String displayName) {
        if (repository.slugTaken(slug)) {
            throw new TenantSlugAlreadyTakenException(slug);
        }
        Tenant tenant = Tenant.register(slug, displayName);
        repository.insert(tenant);
        return tenant.toDto();
    }

    @Override
    public TenantDto rename(TenantId id, String newDisplayName) {
        Tenant tenant = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No tenant with id " + id));
        tenant.rename(newDisplayName);
        repository.update(tenant);
        return tenant.toDto();
    }

    @Override
    public Optional<TenantDto> find(TenantId id) {
        return repository.findById(id).map(Tenant::toDto);
    }
}
