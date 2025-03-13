CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE EXTENSION IF NOT EXISTS "citext"; 

-----------------------------------------------------------
-- xa_users --
CREATE TABLE xa_users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email_address CITEXT UNIQUE NOT NULL, -- Indexed automatically due to UNIQUE
    first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-----------------------------------------------------------
-- xa_company --
CREATE TABLE xa_company (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented BIGINT
    name CITEXT UNIQUE NOT NULL, -- ✅ Case-insensitive unique company names
    domain_name TEXT UNIQUE NOT NULL CHECK (domain_name = LOWER(domain_name)), -- ✅ Enforces lowercase storage
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ✅ Unique Index for Fast Lookups
CREATE UNIQUE INDEX idx_xa_company_name ON xa_company(name);
CREATE UNIQUE INDEX idx_xa_company_domain_name ON xa_company(domain_name);
-----------------------------------------------------------


CREATE TABLE xa_user_company (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented ID for foreign key usage
    user_id UUID NOT NULL REFERENCES xa_users(id) ON DELETE CASCADE,
    company_id BIGINT NOT NULL REFERENCES xa_company(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, company_id) -- ✅ Ensures a user is added only once per company
);

-- ✅ Indexes for Faster Queries
CREATE INDEX idx_xa_user_company_user ON xa_user_company(user_id);
CREATE INDEX idx_xa_user_company_company ON xa_user_company(company_id);


