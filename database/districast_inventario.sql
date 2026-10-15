-- =====================================================
-- Proyecto: Districast - Modulo de Productos
-- Evidencia: GA7-220501096-AA2-EV01
-- Script de creacion de la base de datos
-- =====================================================

CREATE DATABASE IF NOT EXISTS districast_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_spanish_ci;

USE districast_db;

CREATE TABLE IF NOT EXISTS producto (
    id_producto   INT AUTO_INCREMENT PRIMARY KEY,
    codigo        VARCHAR(20)    NOT NULL UNIQUE,
    nombre        VARCHAR(100)   NOT NULL,
    categoria     VARCHAR(50)    NOT NULL,
    precio        DECIMAL(12,2)  NOT NULL CHECK (precio >= 0),
    stock         INT            NOT NULL DEFAULT 0 CHECK (stock >= 0),
    fecha_registro TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

-- Datos de prueba
INSERT INTO producto (codigo, nombre, categoria, precio, stock) VALUES
('PVC-001', 'Tubo PVC presion 1/2 pulgada x 6 m', 'Plomeria', 18500.00, 120),
('PVC-002', 'Codo PVC 90 grados 1/2 pulgada', 'Plomeria', 900.00, 500),
('LLV-010', 'Llave de paso cromada 1/2 pulgada', 'Griferia', 24000.00, 40),
('HER-020', 'Martillo de una 16 oz mango fibra', 'Herramientas', 32000.00, 25),
('ELE-030', 'Cinta aislante negra 18 m', 'Electricos', 3500.00, 200);
