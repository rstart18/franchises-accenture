-- 1. Primero eliminamos las tablas intermedias que dependen de otras
DROP TABLE IF EXISTS branch_product;
DROP TABLE IF EXISTS branch;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS franchise;

-- 2. Crear tabla de franquicias
CREATE TABLE IF NOT EXISTS franchise (
                                         id BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL UNIQUE,
    nit VARCHAR(9) NOT NULL UNIQUE
    );

-- 3. Crear tabla de sucursales
CREATE TABLE IF NOT EXISTS branch (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL UNIQUE,
    franchise_id BIGINT NOT NULL,
    CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) REFERENCES franchise(id)
    );

-- 4. Crear tabla de productos
CREATE TABLE IF NOT EXISTS product (
                                       id BIGSERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL UNIQUE
    );

-- 5. Crear tabla intermedia branch-product con campo de stock
CREATE TABLE IF NOT EXISTS branch_product (
                                              id BIGSERIAL PRIMARY KEY,
                                              branch_id BIGINT NOT NULL,
                                              product_id BIGINT NOT NULL,
                                              stock INTEGER NOT NULL,
                                              CONSTRAINT fk_bp_branch FOREIGN KEY (branch_id) REFERENCES branch(id),
    CONSTRAINT fk_bp_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT uq_branch_product UNIQUE (branch_id, product_id)
    );
