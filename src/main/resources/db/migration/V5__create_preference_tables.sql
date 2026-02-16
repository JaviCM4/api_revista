CREATE TABLE categoria_preferencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_categoria_revista_id INT NOT NULL,
    tipo_preferencia_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (tipo_categoria_revista_id) REFERENCES tipo_categoria_revista(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (tipo_preferencia_id) REFERENCES tipo_preferencia(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE preferencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    categoria_preferencia_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (categoria_preferencia_id) REFERENCES categoria_preferencia(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;