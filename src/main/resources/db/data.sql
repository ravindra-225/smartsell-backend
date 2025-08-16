-- ============================
-- 0️⃣ Clear tables first
-- ============================
TRUNCATE TABLE cart_item RESTART IDENTITY CASCADE;
TRUNCATE TABLE cart RESTART IDENTITY CASCADE;
TRUNCATE TABLE products RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE categories RESTART IDENTITY CASCADE;

-- ============================
-- 1️⃣ Categories
-- ============================
INSERT INTO categories (name) VALUES ('Electronics');
INSERT INTO categories (name) VALUES ('Mobile');
INSERT INTO categories (name) VALUES ('Books');
INSERT INTO categories (name) VALUES ('Clothing');

-- ============================
-- 2️⃣ Users (BCrypt hashed passwords)
-- ============================
INSERT INTO users (name, email, password, role) 
VALUES 
('Admin User', 'admin@smartsell.com', '$2a$10$7z0NnG2mQ5o3yYQ8eFmXkuV/g5bVvJ7YJXZqTZxqv6BUNz9U1K2dG', 'ADMIN'),  -- password: admin123
('Test Seller', 'seller@smartsell.com', '$2a$10$WzC1zYd/0RYY5bk0iDq2OeYzFGB2Y4lNexs.k2O7ZyK5zZ5M0xvTa', 'SELLER'), -- password: seller123
('Test Buyer', 'buyer@smartsell.com', '$2a$10$9dL6K0pVj8U1eWxXjNz5C.NXzQWz0GpY/u1oT6Z3O0Npxq7v6F2yG', 'BUYER');   -- password: buyer123

-- ============================
-- 3️⃣ Products
-- ============================
INSERT INTO products (title, description, price, location, image_url, category_id)
VALUES 
('iPhone 16', 'High-quality phone, Apple iPhone 16', 160000, 'San Francisco', '/images/phone.jpg', (SELECT id FROM categories WHERE name='Mobile')),
('Wireless Headphones', 'High-quality sound', 10000, 'New York', '/images/headphones.jpg', (SELECT id FROM categories WHERE name='Electronics')),
('The Great Book', 'Must-read book for developers', 499, 'Boston', '/images/book.jpg', (SELECT id FROM categories WHERE name='Books')),
('T-Shirt', 'Comfortable cotton t-shirt', 799, 'Los Angeles', '/images/tshirt.jpg', (SELECT id FROM categories WHERE name='Clothing'));

-- ============================
-- 4️⃣ Cart
-- ============================
INSERT INTO cart (user_id)
VALUES ((SELECT id FROM users WHERE email='buyer@smartsell.com'));

-- ============================
-- 5️⃣ Cart Items
-- ============================
INSERT INTO cart_item (cart_id, product_id, quantity)
VALUES 
((SELECT id FROM cart WHERE user_id=(SELECT id FROM users WHERE email='buyer@smartsell.com')), 
 (SELECT id FROM products WHERE title='iPhone 16'), 1),
((SELECT id FROM cart WHERE user_id=(SELECT id FROM users WHERE email='buyer@smartsell.com')), 
 (SELECT id FROM products WHERE title='Wireless Headphones'), 2);
