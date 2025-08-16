-- Insert default categories
INSERT INTO categories (name) VALUES ('Electronics') ON CONFLICT (name) DO NOTHING;;
INSERT INTO categories (name) VALUES ('Clothing') ON CONFLICT (name) DO NOTHING;;
INSERT INTO categories (name) VALUES ('Books') ON CONFLICT (name) DO NOTHING;;


-- Products


INSERT INTO products (title, description, price, location, image_url, category_id)
VALUES ('Sample Book', 'A great read', 29.99, 'New York', '/images/book.jpg', 4) ON CONFLICT DO NOTHING;

INSERT INTO products (title, description, price, location, image_url, category_id)
VALUES ('mobile phone', 'High-quality phone apple iphone 16', 160000, 'San Francisco', '/images/phone.jpg', 2)
ON CONFLICT (title) DO NOTHING;