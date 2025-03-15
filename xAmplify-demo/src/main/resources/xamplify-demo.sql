

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE EXTENSION IF NOT EXISTS "citext"; 

-----------------------------------------------------------

DROP TABLE IF EXISTS xa_user CASCADE;

-- xa_user --
CREATE TABLE xa_user (
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
    user_id UUID NOT NULL REFERENCES xa_user(id) ON DELETE CASCADE,
    company_id BIGINT NOT NULL REFERENCES xa_company(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, company_id) -- ✅ Ensures a user is added only once per company
);

-- ✅ Indexes for Faster Queries
CREATE INDEX idx_xa_user_company_user ON xa_user_company(user_id);
CREATE INDEX idx_xa_user_company_company ON xa_user_company(company_id);

--------------------------------------------------------------------

CREATE TABLE xa_role (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented ID (BigInteger)
    name TEXT UNIQUE NOT NULL CHECK (name = UPPER(name)), -- ✅ Ensures role names are always uppercase
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- ✅ Auto-generated timestamp
);

-- ✅ Unique Index for Fast Lookups
CREATE UNIQUE INDEX idx_xa_role_name ON xa_role(name);

---------------------------------------------------------------------

CREATE TABLE xa_user_role (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented ID for foreign key usage
    user_company_id BIGINT NOT NULL REFERENCES xa_user_company(id) ON DELETE CASCADE, -- ✅ Uses existing user-company mapping
    role_id BIGINT NOT NULL REFERENCES xa_role(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- ✅ Tracks role assignment time
    UNIQUE (user_company_id, role_id) -- ✅ Ensures a user gets each role only once per company
);

-- ✅ Indexes for Faster Queries
CREATE INDEX idx_xa_user_role_user_company ON xa_user_role(user_company_id);
CREATE INDEX idx_xa_user_role_role ON xa_user_role(role_id);

---------------------------------------------------------------------

CREATE TABLE xa_module (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented ID
    name CITEXT UNIQUE NOT NULL, -- ✅ Unique & Case-Sensitive
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ✅ Unique Index for Fast Lookups
CREATE UNIQUE INDEX idx_xa_module_name ON xa_module(name);

------------------------------------------------------------------------------------

CREATE TABLE xa_company_module (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented ID
    company_id BIGINT NOT NULL REFERENCES xa_company(id) ON DELETE CASCADE,
    module_id BIGINT NOT NULL REFERENCES xa_module(id) ON DELETE CASCADE,
    custom_name CITEXT NOT NULL CHECK (
        custom_name = trim(both ' ' FROM custom_name) -- ✅ Removes leading/trailing spaces
        AND custom_name = regexp_replace(custom_name, '\s+', ' ', 'g') -- ✅ Replaces multiple spaces inside with a single space
    ),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (company_id, module_id, custom_name) -- ✅ Ensures uniqueness per company
);

-- ✅ Indexes for Faster Queries
CREATE INDEX idx_xa_company_module_company ON xa_company_module(company_id);
CREATE INDEX idx_xa_company_module_module ON xa_company_module(module_id);
CREATE INDEX idx_xa_company_module_custom_name ON xa_company_module(custom_name);
--------------------------------------------------------------------------------
CREATE TABLE xa_company_module_privilege (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented ID
    company_module_id BIGINT NOT NULL REFERENCES xa_company_module(id) ON DELETE CASCADE,
    privilege_id BIGINT NOT NULL REFERENCES xa_privilege(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (company_id, module_privilege_id) -- ✅ Ensures uniqueness per company
);

-- ✅ Indexes for faster lookups
-- ✅ Indexes for Faster Queries
CREATE INDEX idx_xa_cmp_module_privilege_company_module ON xa_company_module_privilege(company_module_id);
CREATE INDEX idx_xa_cmp_module_privilege_privilege ON xa_company_module_privilege(privilege_id);

-------------------------------------------------------------------------------------------------
CREATE TABLE xa_user_company_privilege (
    id BIGSERIAL PRIMARY KEY, -- ✅ Auto-incremented ID
    user_company_id BIGINT NOT NULL REFERENCES xa_user_company(id) ON DELETE CASCADE, -- ✅ Links user to company
    company_module_privilege_id BIGINT NOT NULL REFERENCES xa_company_module_privilege(id) ON DELETE CASCADE, -- ✅ Links privilege to module & company
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_company_id, company_module_privilege_id) -- ✅ Prevents duplicate privilege assignments for a user
);

-- ✅ Indexes for Faster Lookups
CREATE INDEX idx_xa_user_company_privilege_user_company ON xa_user_company_privilege(user_company_id);
CREATE INDEX idx_xa_user_company_privilege_cmp_module_privilege ON xa_user_company_privilege(company_module_privilege_id);
