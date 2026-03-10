CREATE TABLE categoria_revista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    revista_id INT NOT NULL,
    tipo_categoria_revista_id INT NOT NULL,
    FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tipo_categoria_revista_id) REFERENCES tipo_categoria_revista(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE etiqueta_revista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    revista_id INT NOT NULL,
    detalle VARCHAR(100) NOT NULL,
    FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE edicion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    revista_id INT NOT NULL,
    recurso TEXT NOT NULL,
    fecha_creacion DATE NOT NULL,
    FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE suscripcion_revista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    revista_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha_suscripcion DATE NOT NULL,
    FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_suscripcion (revista_id, usuario_id)
) ENGINE=InnoDB;

CREATE TABLE interaccion_likes (
     id INT AUTO_INCREMENT PRIMARY KEY,
     revista_id INT NOT NULL,
     usuario_id INT NOT NULL,
     me_gusta BOOLEAN,
     fecha_me_gusta DATE,
     FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE interaccion_comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    revista_id INT NOT NULL,
    usuario_id INT NOT NULL,
    comentario TEXT,
    fecha_comentario DATE,
    FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE pago_revista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    revista_id INT NOT NULL,
    pago INT NOT NULL,
    fecha_pago DATE NOT NULL,
    FOREIGN KEY (revista_id) REFERENCES revista(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;