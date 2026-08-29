# Why I hate Mockito — companion code

Reference code for the blog post [Why I hate Mockito](https://pawzem.github.io/fox-tech/2026/08/why-I-hate-Mockito/).

Each example keeps two snapshots of the same system side by side — `v1` before a business change,
`v2` after it — so you can diff them and see exactly what the mocks missed.

## Example 1 — `pricing`: the illusion of 100% coverage

- `pricing.v1` — `Multiplier` multiplies two numbers, `PriceFormatter` turns the result into a
  `15 PLN`-style label. Both classes have 100% test coverage and every test is green.
- `pricing.v2` — a new rule lands: orders above 10 000 get a 5% discount, so `Multiplier` now
  returns non-whole numbers. Its own test was dutifully updated and is green. `PriceFormatterTest`
  is byte-for-byte identical to v1 — its Mockito stubs still answer with the old contract, so it is
  also green. Only `PriceFormatterIntegrationTest`, wired with the real `Multiplier`, catches it:

  ```
  expected: <9500.95 PLN> but was: <9500 PLN>
  ```

## Example 2 — `banking`: the mocked repository

- `banking.v1` — a rich `Account` entity guards its invariants (`requireNonNull` in the
  constructor), and the database backs the same rule with a `NOT NULL` check. `BonusService` loads
  the account over plain JDBC, applies the yearly bonus, saves it back — no validation in the
  method, because the entity IS the validation. Entity test, Testcontainers repository test, and a
  mocked service test: all green.
- `banking.v2` — the bonus program becomes opt-in: the migration drops `NOT NULL`, the constructor
  drops `requireNonNull`. Entity test updated (green), repository test even proves NULL maps to a
  null field (green), mocked `BonusServiceTest` untouched (green — its hand-built account still
  lives in the old world). `BonusServiceIntegrationTest` — real Postgres, real schema, real rows —
  fails with the exact exception production threw:

  ```
  java.lang.NullPointerException: Cannot invoke "java.math.BigDecimal.multiply(java.math.BigDecimal)"
      because "this.bonusRate" is null
  ```

## Example 3 — `saas`: stubs and contract tests instead of mocks

The counterpart — what to do instead. The `saas` packages are structured by bounded context:

- `saas.tenant.contract` — the ONLY public package of the tenant context: the `TenantService`
  facade, DTOs, exceptions, `TenantServiceStub` (an in-memory implementation shipped BY the
  tenant context FOR everybody else's tests), and `TenantCards` — static well-known test data in
  the style of Stripe's test card numbers (`NORMAL_TENANT`, `SUSPENDED_TENANT`,
  `UNKNOWN_TENANT`) that the stub ships pre-seeded with.
- `saas.tenant` — package-private internals: the `Tenant` aggregate, `TenantRepository` as an
  interface with two variants (`JdbcTenantRepository` and an in-memory one), and
  `TenantServiceImpl`.
- `saas.billing` — a consuming context. Its `InvoiceServiceTest` uses the stub like the real
  thing — registers a tenant or just picks a card: no `@Mock`, no `when()`, no `verify()`.

The glue is `TenantServiceContract` — one abstract test suite that runs three times: against the
real implementation on Testcontainers (`TenantServiceImplTest`), against the real implementation
wired with an `InMemoryTenantRepository` (`TenantServiceInMemoryTest` — the real thing without
Docker), and against the stub (`TenantServiceStubTest`). If the contract changes, the impl runs
go red; when the stub follows, the change propagates into every consuming context's tests — a fix
in one place instead of a hunt across every `when(...)` in the repo. All tests in this example
are green.

## Running

Requires Docker (Testcontainers starts a `postgres:17` container).

```
./gradlew test
```

The build **fails on purpose** — the two integration test failures above are the whole point.
Every unit is fully covered, every unit test is green, and the system is still broken; those two
failures come from the tests nobody was told to write.
