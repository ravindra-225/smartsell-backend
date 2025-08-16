TRUNCATE TABLE cart_item RESTART IDENTITY CASCADE;
TRUNCATE TABLE cart RESTART IDENTITY CASCADE;
TRUNCATE TABLE products RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE categories RESTART IDENTITY CASCADE;


INSERT INTO categories (name) VALUES ('Electronics') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name) VALUES ('Mobile') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name) VALUES ('Books') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name) VALUES ('Clothing') ON CONFLICT (name) DO NOTHING;


INSERT INTO users (id, name, email, password, role) 
VALUES (1, 'Admin User', 'admin@smartsell.com', '$2a$10$examplehashedpassword', 'ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, name, email, password, role) 
VALUES (2, 'Test Seller', 'seller@smartsell.com', '$2a$10$examplehashedpassword', 'SELLER')
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, name, email, password, role) 
VALUES (3, 'Test Buyer', 'buyer@smartsell.com', '$2a$10$examplehashedpassword', 'BUYER')
ON CONFLICT (id) DO NOTHING;



INSERT INTO products (title, description, price, location, image_url, category_id)
SELECT 'iPhone 16', 'High-quality phone, Apple iPhone 16', 160000, 'San Francisco', '/images/phone.jpg', id
FROM categories WHERE name='Mobile'
ON CONFLICT (title) DO NOTHING;

INSERT INTO products (title, description, price, location, image_url, category_id)
SELECT 'Wireless Headphones', 'High-quality sound', 10000, 'New York', '/images/headphones.jpg', id
FROM categories WHERE name='Electronics'
ON CONFLICT (title) DO NOTHING;

INSERT INTO products (title, description, price, location, image_url, category_id)
SELECT 'The Great Book', 'Must-read book for developers', 499, 'Boston', '/images/book.jpg', id
FROM categories WHERE name='Books'
ON CONFLICT (title) DO NOTHING;

INSERT INTO products (title, description, price, location, image_url, category_id)
SELECT 'T-Shirt', 'Comfortable cotton t-shirt', 799, 'Los Angeles', '/images/tshirt.jpg', id
FROM categories WHERE name='Clothing'
ON CONFLICT (title) DO NOTHING;



INSERT INTO cart (id, user_id)
VALUES (1, 3)
ON CONFLICT (id) DO NOTHING;




INSERT INTO cart_item (id, cart_id, product_id, quantity)
VALUES (1, 1, 1, 1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO cart_item (id, cart_id, product_id, quantity)
VALUES (2, 1, 2, 2)
ON CONFLICT (id) DO NOTHING;
