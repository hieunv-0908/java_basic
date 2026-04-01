create database if not exists cyberGaming;
use cyberGaming;

ALTER TABLE users ADD COLUMN email VARCHAR(100) UNIQUE;

CREATE TABLE if not exists users
(
    user_id    INT PRIMARY KEY AUTO_INCREMENT,
    user_code  VARCHAR(20),
    username   VARCHAR(50) UNIQUE,
    password   VARCHAR(50),
    full_name  VARCHAR(100),
    phone      VARCHAR(20),
    role       enum ('ADMIN' 'STAFF' 'CUSTOMER'),
    balance    DECIMAL(10, 3) DEFAULT 0,
    created_at DATETIME       DEFAULT CURRENT_TIMESTAMP,
    age       INT,
    email     VARCHAR(100) UNIQUE
);

CREATE TABLE if not exists machines
(
    machine_id     INT PRIMARY KEY AUTO_INCREMENT,
    machine_code   VARCHAR(20),
    area           enum ('STANDARD','VIP','STREAM'),
    config         TEXT,
    price_per_hour DECIMAL(10, 2),
    status         ENUM ('AVAILABLE','IN_USE','MAINTENANCE'),
    is_deleted     TINYINT(1) DEFAULT 0
);

alter table machines add column is_deleted TINYINT(1) default 0;


INSERT INTO machines (machine_code, area, config, price_per_hour, status) VALUES
-- STANDARD
('STD-01', 'STANDARD', 'CPU i5, RAM 8GB, GTX 1650, SSD 256GB', 8000, 'AVAILABLE'),
('STD-02', 'STANDARD', 'CPU i5, RAM 8GB, GTX 1650, SSD 256GB', 8000, 'IN_USE'),
('STD-03', 'STANDARD', 'CPU i5, RAM 8GB, GTX 1650, SSD 256GB', 8000, 'AVAILABLE'),
('STD-04', 'STANDARD', 'CPU i5, RAM 8GB, GTX 1650, SSD 256GB', 8000, 'MAINTENANCE'),
('STD-05', 'STANDARD', 'CPU i5, RAM 8GB, GTX 1650, SSD 256GB', 8000, 'AVAILABLE'),

-- VIP
('VIP-01', 'VIP', 'CPU i7, RAM 16GB, RTX 3060, SSD 512GB', 12000, 'AVAILABLE'),
('VIP-02', 'VIP', 'CPU i7, RAM 16GB, RTX 3060, SSD 512GB', 12000, 'IN_USE'),
('VIP-03', 'VIP', 'CPU i7, RAM 16GB, RTX 3060, SSD 512GB', 12000, 'AVAILABLE'),
('VIP-04', 'VIP', 'CPU i7, RAM 16GB, RTX 3060, SSD 512GB', 12000, 'AVAILABLE'),

-- STREAM
('STR-01', 'STREAM', 'CPU i9, RAM 32GB, RTX 4070, SSD 1TB, Dual Monitor', 20000, 'AVAILABLE'),
('STR-02', 'STREAM', 'CPU i9, RAM 32GB, RTX 4070, SSD 1TB, Dual Monitor', 20000, 'IN_USE'),
('STR-03', 'STREAM', 'CPU i9, RAM 32GB, RTX 4070, SSD 1TB, Dual Monitor', 20000, 'MAINTENANCE');

CREATE TABLE if not exists services
(
    service_id  INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100),
    description TEXT,
    price       DECIMAL(10, 2),
    is_deleted  TINYINT(1) DEFAULT 0
);

alter table services add column is_deleted TINYINT(1) default 0;

INSERT INTO services (name, description, price) VALUES
-- Dịch vụ gaming / phòng máy
('Thuê máy 1 giờ', 'Sử dụng máy tính trong 1 giờ', 10000),
('Thuê máy VIP 1 giờ', 'Máy cấu hình cao, ghế gaming', 20000),
('Qua đêm', 'Gói chơi đêm từ 22h - 6h', 50000),

-- Dịch vụ hỗ trợ
('Cài đặt game', 'Cài đặt game theo yêu cầu', 15000),
('Cài lại Windows', 'Cài mới hệ điều hành Windows', 100000),
('Vệ sinh máy tính', 'Làm sạch bụi, tra keo tản nhiệt', 80000),

-- Dịch vụ in ấn
('In đen trắng', 'In tài liệu đen trắng', 2000),
('In màu', 'In tài liệu màu', 5000),
('Scan tài liệu', 'Quét tài liệu sang file', 3000),

-- Dịch vụ tiện ích
('Thuê tai nghe', 'Tai nghe gaming', 5000),
('Thuê tay cầm', 'Tay cầm chơi game', 10000),
('Sạc điện thoại', 'Sạc pin điện thoại', 5000),

-- Dịch vụ nâng cao
('Thuê phòng riêng', 'Phòng chơi riêng 5-10 người', 100000),
('Tổ chức giải đấu', 'Setup thi đấu game', 300000),
('Streaming', 'Hỗ trợ livestream game', 50000);

CREATE TABLE if not exists foodAndDrinks
(
    foodAndDrink_id  INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100),
    description TEXT,
    price       DECIMAL(10, 2),
    stock       INT
);

INSERT INTO foodAndDrinks (name, description, price, stock) VALUES
-- Đồ ăn
('Phở bò', 'Phở bò truyền thống Việt Nam', 45000, 50),
('Bún chả', 'Bún chả Hà Nội', 40000, 40),
('Cơm tấm', 'Cơm tấm sườn bì chả', 35000, 60),
('Bánh mì thịt', 'Bánh mì kẹp thịt', 20000, 80),
('Gà rán', 'Gà rán giòn', 50000, 30),
('Pizza', 'Pizza phô mai', 120000, 20),
('Hamburger', 'Bánh hamburger bò', 60000, 25),
('Mì xào', 'Mì xào hải sản', 45000, 35),
('Lẩu thái', 'Lẩu thái chua cay', 150000, 15),
('Cá kho tộ', 'Cá kho truyền thống', 70000, 20),

-- Đồ uống
('Trà sữa', 'Trà sữa trân châu', 30000, 100),
('Cà phê đen', 'Cà phê đen nguyên chất', 20000, 70),
('Cà phê sữa', 'Cà phê sữa đá', 25000, 70),
('Nước cam', 'Nước cam tươi', 30000, 50),
('Sinh tố xoài', 'Sinh tố xoài mát lạnh', 35000, 40),
('Nước suối', 'Nước lọc đóng chai', 10000, 200),
('Pepsi', 'Nước ngọt có gas', 15000, 120),
('Coca Cola', 'Nước ngọt Coca Cola', 15000, 120),
('Trà đào', 'Trà đào cam sả', 35000, 60),
('Matcha đá xay', 'Matcha đá xay kem', 45000, 30);

CREATE TABLE if not exists bookings
(
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT,
    machine_id INT,
    start_time DATETIME,
    end_time   DATETIME,
    status     enum ('BOOKED','USING','DONE','CANCELLED'),

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (machine_id) REFERENCES machines (machine_id)
);

CREATE TABLE if not exists orders
(
    order_id     INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT,
    booking_id   INT,
    total_amount decimal(10, 3),
    status       enum ('PENDING','PREPARING','DONE'),
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (booking_id) REFERENCES bookings (booking_id)
);

CREATE TABLE if not exists order_items
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    order_id   INT,
    service_id INT,
    quantity   INT,
    price      DECIMAL(10, 2),

    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (service_id) REFERENCES services (service_id)
);

CREATE TABLE if not exists order_food_items
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    order_id   INT,
    foodAndDrink_id INT,
    quantity   INT,
    price      DECIMAL(10, 2),

    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (foodAndDrink_id) REFERENCES foodAndDrinks (foodAndDrink_id)
);

CREATE TABLE if not exists transactions
(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id        INT,
    amount         DECIMAL(10, 2),
    type           enum ('DEPOSIT','PAYMENT'),
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (user_id)
);


INSERT INTO users (user_code, username, password, full_name, age, phone, role, balance)
VALUES
-- ADMIN
('AD001', 'admin1', '123456', 'Nguyễn Văn Admin', 35, '0900000001', 'ADMIN', 0),
('AD002', 'admin2', '123456', 'Trần Thị Admin', 32, '0900000002', 'ADMIN', 0),

-- STAFF
('ST001', 'staff1', '123456', 'Lê Văn Staff', 28, '0900000003', 'STAFF', 0),
('ST002', 'staff2', '123456', 'Phạm Thị Staff', 26, '0900000004', 'STAFF', 0),

-- CUSTOMER
('CU001', 'user1', '123456', 'Nguyễn Văn A', 22, '0900000005', 'CUSTOMER', 100.500),
('CU002', 'user2', '123456', 'Trần Thị B', 24, '0900000006', 'CUSTOMER', 250.000),
('CU003', 'user3', '123456', 'Lê Văn C', 21, '0900000007', 'CUSTOMER', 75.250),
('CU004', 'user4', '123456', 'Phạm Thị D', 23, '0900000008', 'CUSTOMER', 10.000);
