CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_usuario_id INT NOT NULL,
    estado_usuario_id INT NOT NULL,
    tipo_sexo_id INT NOT NULL,
    municipio_id INT NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fotografia TEXT,
    descripcion TEXT,
    dinero_disponible INT NOT NULL,
    FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (estado_usuario_id) REFERENCES estado_usuario(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (tipo_sexo_id) REFERENCES tipo_sexo(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (municipio_id) REFERENCES municipio(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;