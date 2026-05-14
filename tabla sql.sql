CREATE DATABASE inventario;
USE inventario;
CREATE TABLE usuarios(
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50),
    password VARCHAR(50),
    rol VARCHAR(20)
);
INSERT INTO usuarios(usuario,password)
VALUES('admin','1234','admin'), ('josue','admin','usuario');



CREATE TABLE productos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DOUBLE,
    stock INT
);

select * from usuarios;
select * from productos;
