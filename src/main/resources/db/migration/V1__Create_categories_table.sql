CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL
);



INSERT INTO categories (name) VALUES ('Woman’s Fashion');
INSERT INTO categories (name) VALUES ('Men’s Fashion');
INSERT INTO categories (name) VALUES ('Electronics');
INSERT INTO categories (name) VALUES ('Home & Lifestyle');
INSERT INTO categories (name) VALUES ('Medicine');
INSERT INTO categories (name) VALUES ('Sports & Outdoor');
INSERT INTO categories (name) VALUES ('Baby’s & Toys');
INSERT INTO categories (name) VALUES ('Groceries & Pets');
INSERT INTO categories (name) VALUES ('Health & Beauty');

INSERT INTO users (username, email, password, is_admin) 
VALUES ('admin', 'admin@example.com', '$2a$10$VbM7Q1yTOfj2lP.NcAwUS.8vCNdbpd7zVZQzO9iTg.qnBLyQQYY.i', true);
