-- Create database
CREATE DATABASE IF NOT EXISTS verdevivo CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE verdevivo;

-- Table: users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Table: plants
CREATE TABLE plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(100),
    description TEXT,
    is_watered BOOLEAN DEFAULT FALSE,
    user_id INT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
