-- Create database
CREATE DATABASE IF NOT EXISTS ecommerce_database;

-- Use database
USE ecommerce_database;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    age INT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert example data into users table
INSERT INTO users (name, email, age) VALUES 
('John Doe', 'john.doe@example.com', 35),
('Jane Smith', 'jane.smith@example.com', 28),
('Michael Johnson', 'michael.johnson@example.com', 45),
('Emily Williams', 'emily.williams@example.com', 30),
('William Brown', 'william.brown@example.com', 22),
('Olivia Martinez', 'olivia.martinez@example.com', 38),
('James Taylor', 'james.taylor@example.com', 40),
('Sophia Davis', 'sophia.davis@example.com', 33),
('Robert Clark', 'robert.clark@example.com', 27),
('Emma Garcia', 'emma.garcia@example.com', 31);

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT
);

-- Insert example data into products table
INSERT INTO products (name, price, description) VALUES 
('Laptop HP Pavilion', 799.99, 'Laptop with Intel Core i5 processor, 8GB RAM, and 256GB SSD.'),
('iPhone 13', 1099.99, 'Latest generation smartphone with OLED display and 12MP camera.'),
('Samsung 55" Smart TV', 899.00, '4K television with HDR technology and Tizen operating system.'),
('Xbox Series X', 499.99, 'Video game console capable of 4K gaming.'),
('Samsung Galaxy Tab S7+', 849.00, 'Tablet with 12.4" Super AMOLED display and included S Pen.'),
('Canon EOS R5', 3799.99, 'Professional mirrorless camera with 45MP sensor and 8K video recording.'),
('Apple Watch Series 7', 399.00, 'Smartwatch with Always-On display and blood oxygen sensor.'),
('Dyson V11 Absolute', 699.00, 'Cordless vacuum cleaner with powerful suction and up to 60 minutes of runtime.'),
('Sony WH-1000XM4', 349.99, 'Wireless headphones with noise cancellation and exceptional sound quality.'),
('Nintendo Switch', 299.99, 'Hybrid console for gaming at home or on the go.');

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Insert example data into orders table
INSERT INTO orders (user_id, product_id, quantity) VALUES 
(1, 2, 1),
(2, 1, 2),
(3, 3, 1),
(4, 5, 1),
(5, 4, 1),
(6, 7, 2),
(7, 9, 1),
(8, 8, 1),
(9, 10, 1),
(10, 6, 1);
