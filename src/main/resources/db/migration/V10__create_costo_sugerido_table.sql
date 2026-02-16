CREATE TABLE costo_sugerido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_costo_id INT NOT NULL,
    costo INT NOT NULL,
    dias INT NOT NULL,
    FOREIGN KEY (tipo_costo_id) REFERENCES tipo_costo(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;