CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY, -- Use BIGSERIAL for auto-incrementing BIGINT in PostgreSQL
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY, -- Use BIGSERIAL for auto-incrementing BIGINT
    title VARCHAR(255) NOT NULL, -- Title is likely required
    description TEXT, -- Optional, variable-length text
    price DECIMAL(10,2) NOT NULL, -- Price is likely required and should be non-null
    location VARCHAR(255) NOT NULL, -- Location is likely required
    image_url VARCHAR(255), -- Added for image URL storage
    seller_id BIGINT, -- Added for seller reference (assuming a Users table)
    category_id BIGINT NOT NULL, -- Category is likely required
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (seller_id) REFERENCES users(id) -- Assuming a users table exists
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
);


INSERT INTO categories (name) VALUES ('Electronics'), ('Clothing'), ('Books');
