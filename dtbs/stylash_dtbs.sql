-- Tạo database
CREATE DATABASE IF NOT EXISTS stylash_database;

-- Sử dụng database
USE stylash_database;

-- Tạo bảng 'products' để lưu thông tin sản phẩm
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    image_url varchar(200),
    category_id int,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Tạo bảng 'reports' để hiển thị feedback
CREATE TABLE reports (
  id INT PRIMARY KEY AUTO_INCREMENT,
  image varchar(255),
  title varchar(255),
  description varchar(255),
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tạo bảng 'categories' để lưu thông tin danh mục sản phẩm
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description varchar(100),
    INDEX(name)
);

-- Tạo bảng 'users' để lưu thông tin người dùng
CREATE TABLE IF NOT EXISTS users (
    id Integer AUTO_INCREMENT PRIMARY KEY,
    avatar varchar(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number varchar(20),
    address varchar(50),
    role VARCHAR(100) NOT NULL
);

-- Tạo bảng 'likes' để lưu thông tin sản phẩm được thích
CREATE TABLE IF NOT EXISTS likes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    UNIQUE (user_id, product_id)
);

-- Tạo bảng 'orders' để lưu thông tin đơn hàng
CREATE TABLE IF NOT EXISTS orders (
  order_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  order_date DATETIME NOT NULL,
  total_amount DECIMAL(10, 2) NOT NULL,
  payment_status VARCHAR(20) NOT NULL,
  shipping_address VARCHAR(200) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE token (
  id INT PRIMARY KEY AUTO_INCREMENT,
  token VARCHAR(255) UNIQUE,
  tokenType ENUM('BEARER'),
  revoked BOOLEAN,
  expired BOOLEAN,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create Order_Items table
CREATE TABLE order_items (
  order_item_id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT,
  product_id INT,
  quantity INT NOT NULL,
  price_per_unit DECIMAL(10, 2) NOT NULL,
  voucher_value INT NOT NULL,
  shipping_value INT NOT NULL,
  size VARCHAR(10),
  color VARCHAR(10),
  FOREIGN KEY (order_id) REFERENCES orders(order_id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);

alter table order_items add column shipping_value INT

-- Tạo bảng 'vouchers' để lưu thông tin về voucher giảm giá
CREATE TABLE IF NOT EXISTS vouchers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    discount DECIMAL(10, 2) NOT NULL,
    expiration_date DATE NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);
