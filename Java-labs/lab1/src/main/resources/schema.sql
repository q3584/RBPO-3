CREATE DATABASE IF NOT EXISTS garden_db CHARACTER SET utf8mb4;
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
    CONSTRAINT check_min_area CHECK (req_Size > 0)
    );
    
CREATE TABLE gardenbeds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    area DOUBLE NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    owner BIGINT NOT NULL,
    CONSTRAINT fk_gardenbed_user FOREIGN KEY (owner) REFERENCES users(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT check_area CHECK (area > 0)
);

CREATE TABLE plantings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plantnum INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    gardenbed BIGINT NOT NULL,
    crop BIGINT NOT NULL,
    CONSTRAINT fk_planting_gardenbed FOREIGN KEY (gardenbed) REFERENCES gardenbeds(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_planting_crop FOREIGN KEY (crop) REFERENCES crops(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT check_plantnum CHECK (plantnum > 0)
);

CREATE TABLE harvests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    mass DOUBLE NOT NULL,
    planting BIGINT NOT NULL,
    CONSTRAINT fk_harvest_planting FOREIGN KEY (planting) REFERENCES plantings(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT check_mass CHECK (mass > 0)
    );
