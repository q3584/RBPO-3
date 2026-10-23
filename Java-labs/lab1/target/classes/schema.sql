CREATE DATABASE IF NOT EXISTS garden_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_general_ci;
USE garden_db;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE crops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    season VARCHAR(50) NOT NULL,
    req_Size DOUBLE NOT NULL,
    );
    
CREATE TABLE gardenbeds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    area DOUBLE NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    owner BIGINT NOT NULL,
);

CREATE TABLE plantings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plantnum INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    gardenbed BIGINT NOT NULL,
    crop BIGINT NOT NULL,
);

CREATE TABLE harvests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    mass DOUBLE NOT NULL,
    planting BIGINT NOT NULL,
    );
