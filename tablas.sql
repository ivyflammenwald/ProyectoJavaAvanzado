CREATE TABLE cliente(
id_cliente INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(40) NOT NULL,
direccion VARCHAR(40) NOT NULL
);

CREATE TABLE factura(
id_factura INT AUTO_INCREMENT PRIMARY KEY,
monto NUMERIC(10,2) NOT NULL
);

CREATE TABLE vehiculo(
id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
placas VARCHAR(8) NOT NULL,
marca VARCHAR(20) NOT NULL,
modelo VARCHAR(20) NOT NULL,
id_factura INT NOT NULL,
FOREIGN KEY (id_factura) REFERENCES factura(id_factura)
);

/*CREATE TABLE poliza(
id_poliza INT NOT NULL,
costo NUMERIC(10,2) NOT NULL,
prima NUMERIC(10,2) NOT NULL,
apertura DATE NOT NULL,
id_vehiculo INT NOT NULL,
id_cliente INT NOT NULL,
FOREIGN KEY (id_vehiculo) REFERENCES vehiculo(id_vehiculo),
FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
)*/
