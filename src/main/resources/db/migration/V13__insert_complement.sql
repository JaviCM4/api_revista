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
    (4, 1, 2, 47, 'Mariana', 'Fuentes Aguilar', '1987-06-15', 'https://randomuser.me/api/portraits/women/8.jpg', 'Representante de agencia de viajes. Cuenta inactiva.', 0),

    -- NUEVOS EDITORES
    (2, 1, 1, 18, 'Diego Alejandro', 'Paredes Arana', '1991-06-27', 'https://randomuser.me/api/portraits/men/31.jpg', 'Editor de contenido educativo y negocios.', 650),

    -- NUEVOS SUSCRIPTORES
    (3, 1, 2, 21, 'Daniela Fernanda', 'Estrada Cifuentes', '2000-01-16', 'https://randomuser.me/api/portraits/women/31.jpg', 'Lectora frecuente de revistas académicas.', 120),
    (3, 1, 1, 35, 'Miguel Angel', 'Santos Giron', '1996-11-08', 'https://randomuser.me/api/portraits/men/32.jpg', 'Interesado en tecnología y economía.', 95),
    (3, 1, 2, 14, 'Paula Andrea', 'Roldan Mazariegos', '1998-04-02', 'https://randomuser.me/api/portraits/women/32.jpg', 'Suscriptora de contenido de salud y ciencia.', 80),

    -- NUEVO ANUNCIANTE
    (4, 1, 1, 26, 'Esteban', 'Maldonado Ruiz', '1989-09-09', 'https://randomuser.me/api/portraits/men/33.jpg', 'Anunciante de plataforma de cursos en linea.', 2600);


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
    (15, 'anunc.cuatro',   '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (16, 'editor.cinco',   '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (17, 'suscrip.seis',   '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (18, 'suscrip.siete',  '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (19, 'suscrip.ocho',   '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (20, 'anunc.cinco',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6');


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
    (12, 2, '12345678'),
    (16, 1, 'diego.paredes@editorial.gt'),
    (16, 2, '53124567'),
    (17, 1, 'daniela.estrada@correo.gt'),
    (17, 2, '56473829'),
    (18, 1, 'miguel.santos@correo.gt'),
    (18, 2, '59874612'),
    (19, 1, 'paula.roldan@correo.gt'),
    (19, 2, '51239874'),
    (20, 1, 'esteban.maldonado@anuncios.gt'),
    (20, 2, '57771234');


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
    (3, 'Tech Monthly', 'La revista mensual de tecnología más completa de Centroamérica.', TRUE, TRUE, TRUE, TRUE, 8, 5, DATE_SUB(CURRENT_DATE, INTERVAL 90 DAY)),
    (3, 'Gadget World', 'Todo sobre los últimos gadgets y dispositivos del mercado.', TRUE, TRUE, TRUE, FALSE, 6, 4, DATE_SUB(CURRENT_DATE, INTERVAL 70 DAY)),
    (4, 'Science Today', 'Noticias y avances científicos al alcance de todos.', TRUE, TRUE, TRUE, TRUE, 7, 4, DATE_SUB(CURRENT_DATE, INTERVAL 84 DAY)),
    (4, 'Bio & Nature', 'Explorando la biología y el mundo natural.', TRUE, FALSE, TRUE, FALSE, 5, 3, DATE_SUB(CURRENT_DATE, INTERVAL 58 DAY)),
    (5, 'Deporte Total', 'Todo sobre fútbol, baloncesto y más deportes.', TRUE, TRUE, TRUE, TRUE, 9, 6, DATE_SUB(CURRENT_DATE, INTERVAL 80 DAY)),
    (5, 'Fitness Pro', 'Guías de entrenamiento y vida saludable para atletas.', TRUE, TRUE, FALSE, TRUE, 6, 4, DATE_SUB(CURRENT_DATE, INTERVAL 50 DAY)),
    (16, 'Educa Futuro', 'Contenido para aprendizaje continuo, tendencias educativas y herramientas docentes.', TRUE, TRUE, TRUE, TRUE, 7, 5, DATE_SUB(CURRENT_DATE, INTERVAL 18 DAY)),
    (16, 'Mercado Creativo', 'Ideas para emprendimiento, marketing y economia creativa en la region.', TRUE, TRUE, TRUE, TRUE, 8, 5, DATE_SUB(CURRENT_DATE, INTERVAL 9 DAY));


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
    (6, 7),
    (7, 8),
    (7, 6),
    (8, 6),
    (8, 9);


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
    (6, 'entrenamiento'),
    (7, 'educacion-digital'),
    (7, 'aprendizaje-continuo'),
    (8, 'emprendimiento'),
    (8, 'marketing');


-- -------------------------------------------------------------
--  EDICIONES
-- -------------------------------------------------------------

INSERT INTO edicion (revista_id, recurso, fecha_creacion) VALUES
    (1, 'https://www.nih.am/assets/pdf/dhs/1b159e277e4c6aeb24d03d1e3df945c2.pdf',  DATE_SUB(CURRENT_DATE, INTERVAL 85 DAY)),
    (1, 'https://portal.ct.gov/sitecore-center/-/media/sitecore-center/digital-training-center/sample-pdf-for-the-media-library-2.pdf',  DATE_SUB(CURRENT_DATE, INTERVAL 55 DAY)),
    (1, 'https://secretariat.goa.gov.in/PDFs/sample.pdf',  DATE_SUB(CURRENT_DATE, INTERVAL 25 DAY)),
    (2, 'https://nativetheme.com/classic/wp-content/uploads/sites/6/2014/03/sample-pdf-3.pdf',  DATE_SUB(CURRENT_DATE, INTERVAL 65 DAY)),
    (3, 'https://sofibanque.com/wp-content/uploads/2018/10/pdf_link_testing.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 78 DAY)),
    (3, 'https://portal.ct.gov/-/media/Departments-and-Agencies/DPH/dph/environmental_health/2018-UPLOADS/TESTING-URL-LINK-INSERTS.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 48 DAY)),
    (5, 'https://www.nih.am/assets/pdf/dhs/1b159e277e4c6aeb24d03d1e3df945c2.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 74 DAY)),
    (5, 'https://secretariat.goa.gov.in/PDFs/sample.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 44 DAY)),
    (6, 'https://nativetheme.com/classic/wp-content/uploads/sites/6/2014/03/sample-pdf-3.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 45 DAY)),
    (7, 'https://www.africau.edu/images/default/sample.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 16 DAY)),
    (7, 'https://www.clickdimensions.com/links/TestPDFfile.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 8 DAY)),
    (8, 'https://www.orimi.com/pdf-test.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)),
    (8, 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY));

-- -------------------------------------------------------------
--  SUSCRIPCIONES
-- -------------------------------------------------------------

INSERT INTO suscripcion_revista (revista_id, usuario_id, fecha_suscripcion) VALUES
    (1,  7,  DATE_SUB(CURRENT_DATE, INTERVAL 80 DAY)),
    (1,  8,  DATE_SUB(CURRENT_DATE, INTERVAL 78 DAY)),
    (1,  10, DATE_SUB(CURRENT_DATE, INTERVAL 68 DAY)),
    (3,  8,  DATE_SUB(CURRENT_DATE, INTERVAL 76 DAY)),
    (3,  9,  DATE_SUB(CURRENT_DATE, INTERVAL 65 DAY)),
    (5,  7,  DATE_SUB(CURRENT_DATE, INTERVAL 72 DAY)),
    (5,  10, DATE_SUB(CURRENT_DATE, INTERVAL 67 DAY)),
    (6,  9,  DATE_SUB(CURRENT_DATE, INTERVAL 40 DAY)),
    (6,  10, DATE_SUB(CURRENT_DATE, INTERVAL 38 DAY)),
    (7,  17, DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY)),
    (7,  18, DATE_SUB(CURRENT_DATE, INTERVAL 13 DAY)),
    (7,  19, DATE_SUB(CURRENT_DATE, INTERVAL 11 DAY)),
    (8,  17, DATE_SUB(CURRENT_DATE, INTERVAL 8 DAY)),
    (8,  18, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)),
    (8,  19, DATE_SUB(CURRENT_DATE, INTERVAL 6 DAY));


-- -------------------------------------------------------------
--  INTERACCIONES
-- -------------------------------------------------------------

INSERT INTO interaccion_likes (revista_id, usuario_id, me_gusta, fecha_me_gusta) VALUES
    (1,  7,  TRUE,  DATE_SUB(CURRENT_DATE, INTERVAL 79 DAY)),
    (1,  8,  TRUE,  DATE_SUB(CURRENT_DATE, INTERVAL 77 DAY)),
    (1,  10, TRUE,  DATE_SUB(CURRENT_DATE, INTERVAL 67 DAY)),
    (3,  8,  TRUE,  DATE_SUB(CURRENT_DATE, INTERVAL 75 DAY)),
    (3,  9,  TRUE,  DATE_SUB(CURRENT_DATE, INTERVAL 64 DAY)),
    (5,  7,  TRUE,  DATE_SUB(CURRENT_DATE, INTERVAL 71 DAY)),
    (5,  10, TRUE,  DATE_SUB(CURRENT_DATE, INTERVAL 66 DAY)),
    (6,  9,  FALSE, NULL),
    (6,  10, FALSE, NULL),
    (7,  17, TRUE, DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)),
    (7,  18, TRUE, DATE_SUB(CURRENT_DATE, INTERVAL 12 DAY)),
    (8,  19, TRUE, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY));


INSERT INTO interaccion_comentarios (revista_id, usuario_id, comentario, fecha_comentario) VALUES
    (1,  7, 'Excelente contenido, muy actualizado!', DATE_SUB(CURRENT_DATE, INTERVAL 79 DAY)),
    (1,  8, 'La mejor revista tech de Guatemala.', DATE_SUB(CURRENT_DATE, INTERVAL 77 DAY)),
    (1,  10, 'Muy buen análisis del mercado tecnológico.', DATE_SUB(CURRENT_DATE, INTERVAL 67 DAY)),
    (1,  7, 'Esperando la próxima edición con ansias.', DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY)),
    (3,  8, 'El artículo sobre cambio climático es muy útil.', DATE_SUB(CURRENT_DATE, INTERVAL 75 DAY)),
    (3,  9, 'Me gusta el enfoque divulgativo.', DATE_SUB(CURRENT_DATE, INTERVAL 64 DAY)),
    (5,  7, 'Gran cobertura de la liga local!', DATE_SUB(CURRENT_DATE, INTERVAL 71 DAY)),
    (5,  10, 'Excelentes estadísticas y análisis.', DATE_SUB(CURRENT_DATE, INTERVAL 66 DAY)),
    (6,  9, 'El plan de entrenamiento de 8 semanas es genial.', DATE_SUB(CURRENT_DATE, INTERVAL 39 DAY)),
    (6,  10, 'Quisiera más contenido de nutrición.', DATE_SUB(CURRENT_DATE, INTERVAL 37 DAY)),
    (7,  17, 'Me gustó el enfoque práctico para docentes.', DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)),
    (8,  18, 'Excelente análisis para pequeños negocios.', DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY));


-- -------------------------------------------------------------
--  PAGOS DE REVISTA
-- -------------------------------------------------------------

INSERT INTO pago_revista (revista_id, pago, fecha_pago) VALUES
    (1,  240, DATE_SUB(CURRENT_DATE, INTERVAL 60 DAY)),
    (1,  224, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)),
    (1,  248, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)),
    (3,  196, DATE_SUB(CURRENT_DATE, INTERVAL 58 DAY)),
    (3,  210, DATE_SUB(CURRENT_DATE, INTERVAL 28 DAY)),
    (5,  279, DATE_SUB(CURRENT_DATE, INTERVAL 57 DAY)),
    (5,  248, DATE_SUB(CURRENT_DATE, INTERVAL 27 DAY)),
    (6,  195, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY)),
    (7,  126, DATE_SUB(CURRENT_DATE, INTERVAL 12 DAY)),
    (7,  133, DATE_SUB(CURRENT_DATE, INTERVAL 4 DAY)),
    (8,  128, DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY));


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
    (12, 1, 1,  25, DATE_SUB(CURRENT_DATE, INTERVAL 35 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 28 DAY)),
    (12, 2, 1,  50, DATE_SUB(CURRENT_DATE, INTERVAL 34 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY)),
    (12, 3, 2,  85, DATE_SUB(CURRENT_DATE, INTERVAL 60 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 53 DAY)),

    -- Anunciante 13
    (13, 1, 1,  50, DATE_SUB(CURRENT_DATE, INTERVAL 32 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 25 DAY)),
    (13, 1, 2,  10, DATE_SUB(CURRENT_DATE, INTERVAL 50 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 49 DAY)),

    -- Anunciante 14
    (14, 3, 1,  85, DATE_SUB(CURRENT_DATE, INTERVAL 29 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 22 DAY)),
    (14, 2, 1,  50, DATE_SUB(CURRENT_DATE, INTERVAL 29 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY)),

    -- Anunciante 15
    (15, 1, 2,  10, DATE_SUB(CURRENT_DATE, INTERVAL 45 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 44 DAY)),
    (15, 1, 2,  50, DATE_SUB(CURRENT_DATE, INTERVAL 45 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 38 DAY)),

    -- Anunciante 20
    (20, 1, 1,  50, DATE_SUB(CURRENT_DATE, INTERVAL 12 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY)),
    (20, 2, 1,  85, DATE_SUB(CURRENT_DATE, INTERVAL 10 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 4 DAY)),
    (20, 3, 2,  25, DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 13 DAY));


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
    (9, 'https://i.pinimg.com/736x/6f/26/84/6f2684269e84bae8a477cf6e16cf266d.jpg'),
    (10, 'https://images.pexels.com/photos/4145153/pexels-photo-4145153.jpeg'),
    (11, 'https://images.pexels.com/photos/4144224/pexels-photo-4144224.jpeg'),
    (11, 'Promocion de cursos intensivos para certificaciones tecnicas.'),
    (12, 'https://youtu.be/aqz-KE-bpKQ?si=Kc2Zy4tImkE0k7hD');



-- -------------------------------------------------------------
--  BLOQUEOS DE ANUNCIOS
-- -------------------------------------------------------------

INSERT INTO bloqueo_anuncio (revista_id, anuncio_id, estado_bloqueo_anuncio_id, pago, fecha_inicio_bloqueo, fecha_fin_bloqueo) VALUES
    (1, 1, 1, 35, DATE_SUB(CURRENT_DATE, INTERVAL 35 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 28 DAY)),
    (1, 2, 1, 40, DATE_SUB(CURRENT_DATE, INTERVAL 34 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY)),
    (3, 3, 2, 13, DATE_SUB(CURRENT_DATE, INTERVAL 58 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 55 DAY)),
    (5, 4, 1, 18, DATE_SUB(CURRENT_DATE, INTERVAL 31 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 24 DAY)),
    (6, 7, 2, 17, DATE_SUB(CURRENT_DATE, INTERVAL 28 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 21 DAY)),
    (7, 10, 1, 28, DATE_SUB(CURRENT_DATE, INTERVAL 11 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 6 DAY)),
    (8, 11, 1, 32, DATE_SUB(CURRENT_DATE, INTERVAL 9 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY)),
    (8, 12, 2, 14, DATE_SUB(CURRENT_DATE, INTERVAL 19 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY));


-- -------------------------------------------------------------
--  REGISTRO DE VISTAS DE ANUNCIOS
-- -------------------------------------------------------------

INSERT INTO registro_anuncio (anuncio_id, url, cantidad_vistas, fecha_vista) VALUES
    (1, 'https://revistas.gt/revista/tech-monthly',      150, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 35 DAY), INTERVAL 12 HOUR)),
    (1, 'https://revistas.gt/revista/gadget-world',       87, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 34 DAY), INTERVAL 15 HOUR)),
    (2, 'https://revistas.gt/revista/tech-monthly',      210, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 33 DAY), INTERVAL 12 HOUR)),
    (2, 'https://revistas.gt/revista/deporte-total',      95, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 31 DAY), INTERVAL 18 HOUR)),
    (4, 'https://revistas.gt/revista/science-today',     130, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), INTERVAL 11 HOUR)),
    (4, 'https://revistas.gt/revista/fitness-pro',        68, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 29 DAY), INTERVAL 14 HOUR)),
    (6, 'https://revistas.gt/revista/tech-monthly',      200, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 28 DAY), INTERVAL 9 HOUR)),
    (7, 'https://revistas.gt/revista/deporte-total',     140, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 28 DAY), INTERVAL 10 HOUR)),
    (7, 'https://revistas.gt/revista/fitness-pro',        85, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 27 DAY), INTERVAL 10 HOUR)),
    (8, 'https://revistas.gt/revista/tech-monthly',       78, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 44 DAY), INTERVAL 9 HOUR)),
    (10, 'https://revistas.gt/revista/educa-futuro',     165, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 10 DAY), INTERVAL 8 HOUR)),
    (11, 'https://revistas.gt/revista/mercado-creativo', 185, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 8 DAY), INTERVAL 13 HOUR)),
    (11, 'https://revistas.gt/revista/tech-monthly',      92, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 6 DAY), INTERVAL 16 HOUR)),
    (12, 'https://revistas.gt/revista/mercado-creativo',  74, DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL 18 DAY), INTERVAL 10 HOUR));