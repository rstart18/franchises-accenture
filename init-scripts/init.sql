-- Crear tabla Franchise
CREATE TABLE IF NOT EXISTS franchise (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    nit  VARCHAR(9)   NOT NULL UNIQUE
    );

-- Crear tabla Branch
CREATE TABLE IF NOT EXISTS branch (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    franchise_id BIGINT NOT NULL,
    CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) REFERENCES franchise (id)
    );

-- Crear tabla Product
CREATE TABLE IF NOT EXISTS product (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL UNIQUE,
    stock INTEGER      NOT NULL
    );
