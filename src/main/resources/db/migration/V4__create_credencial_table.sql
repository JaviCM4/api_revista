CREATE TABLE credencial (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255),
    token_recuperacion VARCHAR(255),
    fecha_fin_recuperacion DATETIME,
    token_verifcacion VARCHAR(255),
    fecha_fin_verificacion DATETIME,
    verificacion_activa TINYINT(1) NOT NULL DEFAULT 0,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;
