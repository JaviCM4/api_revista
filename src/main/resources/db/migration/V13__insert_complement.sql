-- -------------------------------------------------------------
--  TIPOS Y ESTADOS
-- -------------------------------------------------------------

INSERT INTO tipo_sexo (nombre) VALUES
    ('Masculino'),
    ('Femenino'),
    ('Otro');

INSERT INTO tipo_usuario (nombre) VALUES
    ('ADMIN'),
    ('EDITOR'),
    ('SUBSCRIBER'),
    ('ADVERTISER');

INSERT INTO estado_usuario (nombre) VALUES
    ('Activo'),
    ('Inactivo');

INSERT INTO tipo_categoria_revista (nombre) VALUES
    ('Tecnología'),
    ('Ciencia'),
    ('Deportes'),
    ('Entretenimiento'),
    ('Política'),
    ('Economía'),
    ('Salud'),
    ('Educación'),
    ('Arte y Cultura'),
    ('Gastronomía'),
    ('Viajes'),
    ('Moda');

INSERT INTO tipo_preferencia (nombre) VALUES
    ('Deporte Favorito'),
    ('Género Musical'),
    ('Tipo de Lectura'),
    ('Actividad Recreativa'),
    ('Área Académica'),
    ('Tecnología Favorita');

INSERT INTO tipo_anuncio (nombre) VALUES
    ('Texto'),
    ('Texto e Imagen'),
    ('Video');

INSERT INTO estado_anuncio (nombre) VALUES
    ('Activo'),
    ('Inactivo');

INSERT INTO estado_bloqueo_anuncio (nombre) VALUES
    ('Activo'),
    ('Inactivo');

INSERT INTO tipo_contacto (nombre) VALUES
    ('Correo electrónico'),
    ('Teléfono');


-- -------------------------------------------------------------
--  USUARIOS
-- -------------------------------------------------------------

INSERT INTO usuario (tipo_usuario_id, estado_usuario_id, tipo_sexo_id, municipio_id,nombres, apellidos, fecha_nacimiento, fotografia, descripcion, dinero_disponible) VALUES
    -- ADMINISTRADORES
    (1, 1, 1, 1, 'Carlos Alberto', 'Méndez López', '1985-03-12', 'https://randomuser.me/api/portraits/men/1.jpg', 'Administrador principal del sistema.', 0),
    (1, 1, 2, 2, 'Laura Patricia', 'Salazar Vásquez', '1990-07-25', 'https://randomuser.me/api/portraits/women/1.jpg', 'Administradora de contenidos. Cuenta inactiva por baja temporal.', 0),

    -- EDITORES
    (2, 1, 1, 3, 'Roberto Carlos', 'Fuentes Pérez', '1988-11-05', 'https://randomuser.me/api/portraits/men/2.jpg', 'Editor apasionado de tecnología.', 500),
    (2, 1, 2, 32, 'María José', 'Hernández Gil', '1992-04-18', 'https://randomuser.me/api/portraits/women/2.jpg', 'Editora especializada en ciencias.', 300),
    (2, 1, 1, 44, 'Juan Pablo', 'Ruiz Morales', '1986-09-30', 'https://randomuser.me/api/portraits/men/3.jpg', 'Editor de revistas deportivas.', 750),
    (2, 1, 2, 57, 'Ana Lucía', 'Pérez Castillo', '1994-01-22', 'https://randomuser.me/api/portraits/women/3.jpg', 'Editora de arte y cultura. Cuenta suspendida.', 0),

    -- SUSCRIPTORES
    (3, 1, 2, 2, 'Sofía Isabel', 'García López', '1997-08-03', 'https://randomuser.me/api/portraits/women/4.jpg', 'Amante de la lectura digital.', 50),
    (3, 1, 1, 3, 'Luis Enrique', 'Chávez Moreno', '1995-12-11', 'https://randomuser.me/api/portraits/men/5.jpg', 'Estudiante universitario.', 25),
    (3, 1, 2, 32, 'Valentina', 'Gómez Santizo', '1999-02-28', 'https://randomuser.me/api/portraits/women/5.jpg', 'Diseñadora gráfica freelance.', 75),
    (3, 1, 1, 11, 'Andrés Felipe', 'Jiménez Cruz', '1993-05-17', 'https://randomuser.me/api/portraits/men/6.jpg', 'Ingeniero en sistemas.', 10),
    (3, 1, 2, 57, 'Camila', 'Rodríguez Pinto', '1998-10-09', 'https://randomuser.me/api/portraits/women/6.jpg', 'Estudiante de medicina. Suscripción no renovada.', 0),

    -- ANUNCIANTES
    (4, 1, 1, 1, 'Fernando', 'Castillo Ramos', '1980-03-25', 'https://randomuser.me/api/portraits/men/7.jpg', 'Empresario dueño de tienda de tecnología.', 2000),
    (4, 1, 2, 2, 'Gabriela', 'Lima Ortega', '1984-07-19', 'https://randomuser.me/api/portraits/women/7.jpg', 'Propietaria de boutique de moda.', 1500),
    (4, 1, 1, 3, 'Ricardo', 'Solano Barrios', '1978-11-04', 'https://randomuser.me/api/portraits/men/8.jpg', 'Gerente de marketing digital.', 3000),
    (4, 1, 2, 47, 'Mariana', 'Fuentes Aguilar', '1987-06-15', 'https://randomuser.me/api/portraits/women/8.jpg', 'Representante de agencia de viajes. Cuenta inactiva.', 0);


-- -------------------------------------------------------------
--  CREDENCIALES
-- -------------------------------------------------------------

INSERT INTO credencial (usuario_id, username, password) VALUES
    (1,  'admin.uno',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (2,  'admin.dos',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (3,  'editor.uno',     '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (4,  'editor.dos',     '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (5,  'editor.tres',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (6,  'editor.cuatro',  '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (7,  'suscrip.uno',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (8,  'suscrip.dos',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (9,  'suscrip.tres',   '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (10, 'suscrip.cuatro', '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (11, 'suscrip.cinco',  '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (12, 'anunc.uno',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (13, 'anunc.dos',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (14, 'anunc.tres',     '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (15, 'anunc.cuatro',   '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6');


-- -------------------------------------------------------------
--  CONTACTOS
-- -------------------------------------------------------------

INSERT INTO contacto (usuario_id, tipo_contacto_id, detalle) VALUES
    (1,  1, 'javiercoyoy202030556@cunoc.edu.gt'),
    (1,  2, '59431341'),
    (3,  1, 'coyoyjav08@gmail.com'),
    (3,  2, '54781259'),
    (5,  1, 'juan.ruiz@deportesmag.gt'),
    (5,  2, '50267890123'),
    (7, 1, 'isaias23cymr@gmail.com'),
    (7, 2, '34567890'),
    (8, 1, 'gabriela.lima@boutiquemoda.gt'),
    (8, 2, '23456789'),
    (12, 1, 'javic4m@gmail.com'),
    (12, 2, '12345678');


-- -------------------------------------------------------------
--  PREFERENCIAS
-- -------------------------------------------------------------

INSERT INTO categoria_preferencia (tipo_categoria_revista_id, tipo_preferencia_id, nombre) VALUES
    (1, 1, 'Programación como hobby'),
    (1, 2, 'Inteligencia Artificial'),
    (1, 3, 'Desarrollador de Software'),
    (2, 2, 'Investigación Científica'),
    (2, 3, 'Biólogo / Científico'),
    (3, 1, 'Fútbol amateur'),
    (3, 5, 'Baloncesto'),
    (4, 1, 'Cine y series'),
    (9, 4, 'Pintura y escultura'),
    (6, 5, 'Finanzas personales');


-- -------------------------------------------------------------
--  PREFERENCIA
-- -------------------------------------------------------------

INSERT INTO preferencia (usuario_id, categoria_preferencia_id) VALUES
    (7,  2),
    (7,  3),
    (8,  4),
    (9,  4),
    (9,  5),
    (10, 6),
    (10, 7),
    (3,  8),
    (3,  9),
    (4,  10);


-- -------------------------------------------------------------
--  REVISTAS
-- -------------------------------------------------------------

INSERT INTO revista (usuario_id, titulo, descripcion, permitir_suscripcion, permitir_comentarios, permitir_reacciones, revista_activa, costo_dia, costo_bloqueo_anuncio, fecha_creacion) VALUES
    (3, 'Tech Monthly', 'La revista mensual de tecnología más completa de Centroamérica.', TRUE, TRUE, TRUE, TRUE, 8, 5, '2025-01-10 00:00:00'),
    (3, 'Gadget World', 'Todo sobre los últimos gadgets y dispositivos del mercado.', TRUE, TRUE, TRUE, FALSE, 6, 4, '2025-03-05 00:00:00'),
    (4, 'Science Today', 'Noticias y avances científicos al alcance de todos.', TRUE, TRUE, TRUE, TRUE, 7, 4, '2025-02-14 00:00:00'),
    (4, 'Bio & Nature', 'Explorando la biología y el mundo natural.', TRUE, FALSE, TRUE, FALSE, 5, 3, '2025-05-20 00:00:00'),
    (5, 'Deporte Total', 'Todo sobre fútbol, baloncesto y más deportes.', TRUE, TRUE, TRUE, TRUE, 9, 6, '2025-01-25 00:00:00'),
    (5, 'Fitness Pro', 'Guías de entrenamiento y vida saludable para atletas.', TRUE, TRUE, FALSE, TRUE, 6, 4, '2025-04-10 00:00:00');


-- -------------------------------------------------------------
--  CATEGORÍAS DE REVISTAS
-- -------------------------------------------------------------

INSERT INTO categoria_revista (revista_id, tipo_categoria_revista_id) VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 2),
    (4, 7),
    (5, 3),
    (6, 3),
    (6, 7);


-- -------------------------------------------------------------
--  ETIQUETAS DE REVISTAS
-- -------------------------------------------------------------

INSERT INTO etiqueta_revista (revista_id, detalle) VALUES
    (1, 'inteligencia-artificial'),
    (1, 'programacion'),
    (1, 'software'),
    (2, 'gadgets'),
    (2, 'smartphones'),
    (3, 'ciencia'),
    (3, 'investigacion'),
    (4, 'biologia'),
    (4, 'naturaleza'),
    (5, 'futbol'),
    (5, 'deportes'),
    (6, 'fitness'),
    (6, 'entrenamiento');


-- -------------------------------------------------------------
--  EDICIONES
-- -------------------------------------------------------------

INSERT INTO edicion (revista_id, recurso, fecha_creacion) VALUES
    (1, 'https://www.nih.am/assets/pdf/dhs/1b159e277e4c6aeb24d03d1e3df945c2.pdf',  '2025-01-15 00:00:00'),
    (1, 'https://portal.ct.gov/sitecore-center/-/media/sitecore-center/digital-training-center/sample-pdf-for-the-media-library-2.pdf',  '2025-02-15 00:00:00'),
    (1, 'https://secretariat.goa.gov.in/PDFs/sample.pdf',  '2025-03-15 00:00:00'),
    (2, 'https://nativetheme.com/classic/wp-content/uploads/sites/6/2014/03/sample-pdf-3.pdf',  '2025-03-10 00:00:00'),
    (3, 'https://sofibanque.com/wp-content/uploads/2018/10/pdf_link_testing.pdf', '2025-02-20 00:00:00'),
    (3, 'https://portal.ct.gov/-/media/Departments-and-Agencies/DPH/dph/environmental_health/2018-UPLOADS/TESTING-URL-LINK-INSERTS.pdf', '2025-03-20 00:00:00'),
    (5, 'https://www.nih.am/assets/pdf/dhs/1b159e277e4c6aeb24d03d1e3df945c2.pdf', '2025-02-01 00:00:00'),
    (5, 'https://secretariat.goa.gov.in/PDFs/sample.pdf', '2025-03-01 00:00:00'),
    (6, 'https://nativetheme.com/classic/wp-content/uploads/sites/6/2014/03/sample-pdf-3.pdf', '2025-04-15 00:00:00');

-- -------------------------------------------------------------
--  SUSCRIPCIONES
-- -------------------------------------------------------------

INSERT INTO suscripcion_revista (revista_id, usuario_id, fecha_suscripcion) VALUES
    (1,  7,  '2025-01-20 10:00:00'),
    (1,  8,  '2025-01-22 11:00:00'),
    (1,  10, '2025-02-01 09:00:00'),
    (3,  8,  '2025-02-18 08:00:00'),
    (3,  9,  '2025-03-01 10:00:00'),
    (5,  7,  '2025-02-05 19:00:00'),
    (5,  10, '2025-02-10 20:00:00'),
    (6,  9,  '2025-04-20 09:00:00'),
    (6,  10, '2025-04-22 10:00:00');


-- -------------------------------------------------------------
--  INTERACCIONES
-- -------------------------------------------------------------

INSERT INTO interaccion_likes (revista_id, usuario_id, me_gusta, fecha_me_gusta) VALUES
    (1,  7,  TRUE,  '2025-01-21 10:30:00'),
    (1,  8,  TRUE,  '2025-01-23 14:00:00'),
    (1,  10, TRUE,  '2025-02-02 09:15:00'),
    (3,  8,  TRUE,  '2025-02-19 16:45:00'),
    (3,  9,  TRUE,  '2025-03-02 12:30:00'),
    (5,  7,  TRUE,  '2025-02-06 20:00:00'),
    (5,  10, TRUE,  '2025-02-11 18:30:00'),
    (6,  9,  FALSE, NULL),
    (6,  10, FALSE, NULL);


INSERT INTO interaccion_comentarios (revista_id, usuario_id, comentario, fecha_comentario) VALUES
    (1,  7, 'Excelente contenido, muy actualizado!', '2025-01-21 10:35:00'),
    (1,  8, 'La mejor revista tech de Guatemala.', '2025-01-23 14:05:00'),
    (1,  10, 'Muy buen análisis del mercado tecnológico.', '2025-02-02 09:20:00'),
    (1,  7, 'Esperando la edición de abril con ansias.', '2025-03-20 10:00:00'),
    (3,  8, 'El artículo sobre cambio climático es muy útil.', '2025-02-19 17:00:00'),
    (3,  9, 'Me gusta el enfoque divulgativo.', '2025-03-02 12:45:00'),
    (5,  7, 'Gran cobertura de la liga local!', '2025-02-06 20:15:00'),
    (5,  10, 'Excelentes estadísticas y análisis.', '2025-02-11 18:45:00'),
    (6,  9, 'El plan de entrenamiento de 8 semanas es genial.', '2025-04-21 08:00:00'),
    (6,  10, 'Quisiera más contenido de nutrición.', '2025-04-23 09:30:00');


-- -------------------------------------------------------------
--  PAGOS DE REVISTA
-- -------------------------------------------------------------

INSERT INTO pago_revista (revista_id, pago, fecha_pago) VALUES
    (1,  240, '2025-02-01 00:00:00'),
    (1,  224, '2025-03-01 00:00:00'),
    (1,  248, '2025-04-01 00:00:00'),
    (3,  196, '2025-03-01 00:00:00'),
    (3,  210, '2025-04-01 00:00:00'),
    (5,  279, '2025-02-01 00:00:00'),
    (5,  248, '2025-03-01 00:00:00'),
    (6,  195, '2025-05-01 00:00:00');


-- -------------------------------------------------------------
--  COSTOS SUGERIDOS
-- -------------------------------------------------------------

INSERT INTO costo_sugerido (costo, dias) VALUES
    ( 10,  1),
    ( 25,  3),
    ( 50,  7),
    ( 85, 14);


-- -------------------------------------------------------------
--  ANUNCIOS
-- -------------------------------------------------------------

INSERT INTO anuncio (usuario_id, tipo_anuncio_id, estado_anuncio_id, costo_total, fecha_creacion, fecha_vencimiento) VALUES
    -- Anunciante 12
    (12, 1, 1,  25, '2026-02-01 08:00:00', '2026-02-08 08:00:00'),
    (12, 2, 1,  50, '2026-02-01 08:00:00', '2026-02-15 08:00:00'),
    (12, 3, 2,  85, '2026-01-01 08:00:00', '2026-01-08 08:00:00'),

    -- Anunciante 13
    (13, 1, 1,  50, '2026-02-05 10:00:00', '2026-02-12 10:00:00'),
    (13, 1, 2,  10, '2026-01-15 10:00:00', '2026-01-16 10:00:00'),

    -- Anunciante 14
    (14, 3, 1,  85, '2026-02-10 09:00:00', '2026-02-17 09:00:00'),
    (14, 2, 1,  50, '2026-02-10 09:00:00', '2026-02-24 09:00:00'),

    -- Anunciante 15
    (15, 1, 2,  10, '2026-01-20 09:00:00', '2026-01-21 09:00:00'),
    (15, 1, 2,  50, '2026-01-20 09:00:00', '2026-01-27 09:00:00');


-- -------------------------------------------------------------
--  CONTENIDO EXTRA DE ANUNCIOS
-- -------------------------------------------------------------

INSERT INTO contenido_extra (anuncio_id, recurso) VALUES
    (1, 'Prueba de texto para el almancenamiento de la informacion'),
    (2, 'https://tse4.mm.bing.net/th/id/OIP.RCio942ArFiqE_G1B4KFvAHaE8?rs=1&pid=ImgDetMain&o=7&rm=3'),
    (2, 'Vista de Guatemala para el recuerdo y notificacion de los interesados'),
    (3, 'https://youtu.be/5ehRoUav9aM?si=Uc7jxOsDF9iX5bVC'),
    (4, 'https://michaelshapiro.net/wp-content/uploads/2019/09/Guatemala-1280x1006.jpg'),
    (5, 'https://ychef.files.bbci.co.uk/976x549/p08qwn2b.jpg'),
    (6, 'https://youtu.be/SiSTxIB2mvo?si=d9NRyYNhXahaFDCl'),
    (7, 'https://pswscience.org/wp-content/uploads/2023/01/PSW-2472-Hansen-danta...fares-glonal-conservation.png'),
    (7, 'ggggggggggggggggggg gggggg ggggg ggg ggg gggggggggggggg'),
    (8, 'https://tse2.mm.bing.net/th/id/OIP.x4Ft--VqGT1IMxQrkO7GZgHaE8?rs=1&pid=ImgDetMain&o=7&rm=3'),
    (9, 'https://i.pinimg.com/736x/6f/26/84/6f2684269e84bae8a477cf6e16cf266d.jpg');



-- -------------------------------------------------------------
--  BLOQUEOS DE ANUNCIOS
-- -------------------------------------------------------------

INSERT INTO bloqueo_anuncio (revista_id, anuncio_id, estado_bloqueo_anuncio_id, pago, fecha_inicio_bloqueo, fecha_fin_bloqueo) VALUES
    (1, 1, 1, 35, '2026-02-01 00:00:00', '2026-02-08 00:00:00'),
    (1, 2, 1, 40, '2026-02-01 00:00:00', '2026-02-15 00:00:00'),
    (3, 3, 2, 13, '2026-01-10 00:00:00', '2026-01-13 00:00:00'),
    (5, 4, 1, 18, '2026-02-05 00:00:00', '2026-02-12 00:00:00'),
    (6, 7, 2, 17, '2026-01-15 00:00:00', '2026-01-22 00:00:00');


-- -------------------------------------------------------------
--  REGISTRO DE VISTAS DE ANUNCIOS
-- -------------------------------------------------------------

INSERT INTO registro_anuncio (anuncio_id, url, cantidad_vistas, fecha_vista) VALUES
    (1, 'https://revistas.gt/revista/tech-monthly',      150, '2026-02-01 12:00:00'),
    (1, 'https://revistas.gt/revista/gadget-world',       87, '2026-02-02 15:00:00'),
    (2, 'https://revistas.gt/revista/tech-monthly',      210, '2026-02-01 12:00:00'),
    (2, 'https://revistas.gt/revista/deporte-total',      95, '2026-02-03 18:00:00'),
    (4, 'https://revistas.gt/revista/science-today',     130, '2026-02-05 11:00:00'),
    (4, 'https://revistas.gt/revista/fitness-pro',        68, '2026-02-06 14:00:00'),
    (6, 'https://revistas.gt/revista/tech-monthly',      200, '2026-02-10 09:30:00'),
    (7, 'https://revistas.gt/revista/deporte-total',     140, '2026-02-10 09:30:00'),
    (7, 'https://revistas.gt/revista/fitness-pro',        85, '2026-02-11 10:00:00'),
    (8, 'https://revistas.gt/revista/tech-monthly',       78, '2026-01-20 09:00:00');