CREATE TABLE anuncio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo_anuncio_id INT NOT NULL,
    estado_anuncio_id INT NOT NULL,
    costo_total INT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_vencimiento DATETIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (tipo_anuncio_id) REFERENCES tipo_anuncio(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (estado_anuncio_id) REFERENCES estado_anuncio(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;