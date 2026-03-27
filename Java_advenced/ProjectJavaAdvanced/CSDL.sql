create database if not exists cyberGaming;
use cyberGaming;

CREATE TABLE if not exists users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_code VARCHAR(20),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    full_name VARCHAR(100),
    phone VARCHAR(20),
    role enum('ADMIN','STAFF','CUSTOMER'),
    balance DECIMAL(10,3) DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE if not exists machines (
    machine_id INT PRIMARY KEY AUTO_INCREMENT,
    machine_code VARCHAR(20),
    area enum('STANDARD','VIP','STREAM'),
    config TEXT,
    price_per_hour DECIMAL(10,2),
    status ENUM('AVAILABLE','IN_USE','MAINTENANCE') 
);

CREATE TABLE if not exists services (
    service_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2),
    stock INT
);

CREATE TABLE if not exists bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    machine_id INT,
    start_time DATETIME,
    end_time DATETIME,
    status enum('PENDING','CONFIRMED','SERVING','DONE'),
    
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (machine_id) REFERENCES machines(machine_id)
);

CREATE TABLE if not exists orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    booking_id INT,
    total_amount,
    status enum('PENDING','PREPARING','DONE'),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

CREATE TABLE if not exists order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    service_id INT,
    quantity INT,
    price DECIMAL(10,2),
    
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE if not exists transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    amount DECIMAL(10,2),
    type enum('DEPOSIT','PAYMENT'),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
