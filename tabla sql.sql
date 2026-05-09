CREATE DATABASE inventario;
USE inventario;
CREATE TABLE usuarios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50),
    password VARCHAR(50)
);
INSERT INTO usuarios(usuario,password)
VALUES('admin','1234'),
('josue','admin');
select * from usuarios;
USE inventario;
CREATE TABLE productos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DOUBLE,
    stock INT
);
select * from productos;