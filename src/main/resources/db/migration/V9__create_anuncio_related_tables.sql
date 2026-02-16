CREATE TABLE contenido_extra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    anuncio_id INT NOT NULL,
    recurso TEXT NOT NULL,
    FOREIGN KEY (anuncio_id) REFERENCES anuncio(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE bloqueo_anuncio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    revista_id INT NOT NULL,
    anuncio_id INT NOT NULL ,
    estado_bloqueo_anuncio_id INT NOT NULL,
    pago INT NOT NULL,
    fecha_inicio_bloqueo DATETIME NOT NULL,
    fecha_fin_bloqueo DATETIME NOT NULL,
    FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (anuncio_id) REFERENCES anuncio(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (estado_bloqueo_anuncio_id) REFERENCES estado_bloqueo_anuncio(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE registro_anuncio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    anuncio_id INT NOT NULL,
    url VARCHAR(500) NOT NULL,
    cantidad_vistas INT,
    fecha_vista DATETIME NOT NULL,
    FOREIGN KEY (anuncio_id) REFERENCES anuncio(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;