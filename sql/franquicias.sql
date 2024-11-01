
CREATE TABLE franquicia (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(255) NOT NULL
);

CREATE TABLE sucursal (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          id_franquicia BIGINT,
                          CONSTRAINT fk_franquicia FOREIGN KEY (id_franquicia) REFERENCES franquicia(id)
);

CREATE TABLE producto (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          stock INT NOT NULL,
                          id_sucursal BIGINT,
                          CONSTRAINT fk_sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id)
);