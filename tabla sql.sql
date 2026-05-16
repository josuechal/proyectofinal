CREATE DATABASE inventario;
USE inventario;

INSERT INTO usuarios(usuario,password)
VALUES('admin','1234','admin'), ('josue','admin','usuario');

drop table productos;

CREATE TABLE usuarios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    usuario VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    rol VARCHAR(20),
    telefono VARCHAR(20),
    correo VARCHAR(100),
    estado VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE productos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50),
    nombre VARCHAR(100),
    descripcion TEXT,
    precio_compra DOUBLE,
    precio_venta DOUBLE,
    stock INT,
    stock_minimo INT,
    categoria_id INT,
    proveedor_id INT,
    imagen VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE clientes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    documento VARCHAR(20),
    correo VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE proveedores(
    id INT AUTO_INCREMENT PRIMARY KEY,
    empresa VARCHAR(100),
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    correo VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT
);

CREATE TABLE ventas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    usuario_id INT,
    total DOUBLE,
    ganancia DOUBLE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE detalle_ventas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    producto_id INT,
    cantidad INT,
    precio DOUBLE,
    subtotal DOUBLE
);

select * from usuarios;
select * from productos;
