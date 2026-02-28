CREATE TABLE revista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL ,
    permitir_suscripcion BOOLEAN NOT NULL ,
    permitir_comentarios BOOLEAN NOT NULL ,
    permitir_reacciones BOOLEAN NOT NULL ,
    revista_activa BOOLEAN NOT NULL,
    costo_dia INT NOT NULL,
    costo_bloqueo_anuncio INT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;