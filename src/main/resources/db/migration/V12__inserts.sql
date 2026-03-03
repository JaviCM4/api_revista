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
--  PAÍS
-- -------------------------------------------------------------

INSERT INTO pais (nombre) VALUES
    ('Guatemala');

-- -------------------------------------------------------------
--  DEPARTAMENTOS
-- -------------------------------------------------------------

INSERT INTO departamento (pais_id, nombre) VALUES
    (1, 'Guatemala'),
    (1, 'El Progreso'),
    (1, 'Sacatepéquez'),
    (1, 'Chimaltenango'),
    (1, 'Escuintla'),
    (1, 'Santa Rosa'),
    (1, 'Sololá'),
    (1, 'Totonicapán'),
    (1, 'Quetzaltenango'),
    (1, 'Suchitepéquez'),
    (1, 'Retalhuleu'),
    (1, 'San Marcos'),
    (1, 'Huehuetenango'),
    (1, 'Quiché'),
    (1, 'Baja Verapaz'),
    (1, 'Alta Verapaz'),
    (1, 'Petén'),
    (1, 'Izabal'),
    (1, 'Zacapa'),
    (1, 'Chiquimula'),
    (1, 'Jalapa'),
    (1, 'Jutiapa');

-- -------------------------------------------------------------
--  MUNICIPIOS
-- -------------------------------------------------------------

INSERT INTO municipio (departamento_id, nombre) VALUES
-- Guatemala (1)
(1,  'Guatemala'),                     --  1
(1,  'Mixco'),                         --  2
(1,  'Villa Nueva'),                   --  3
(1,  'Petapa'),                        --  4
(1,  'San Miguel Petapa'),             --  5
(1,  'Chinautla'),                     --  6
(1,  'Santa Catarina Pinula'),         --  7
-- El Progreso (2)
(2,  'Guastatoya'),                    --  8
(2,  'Morazán'),                       --  9
(2,  'San Agustín Acasaguastlán'),     -- 10
-- Sacatepéquez (3)
(3,  'Antigua Guatemala'),             -- 11
(3,  'Jocotenango'),                   -- 12
(3,  'San Lucas Sacatepéquez'),        -- 13
(3,  'Santa Lucía Milpas Altas'),      -- 14
-- Chimaltenango (4)
(4,  'Chimaltenango'),                 -- 15
(4,  'San Andrés Itzapa'),             -- 16
(4,  'Patzún'),                        -- 17
(4,  'Tecpán Guatemala'),              -- 18
-- Escuintla (5)
(5,  'Escuintla'),                     -- 19
(5,  'Santa Lucía Cotzumalguapa'),     -- 20
(5,  'La Democracia'),                 -- 21
(5,  'Palín'),                         -- 22
-- Santa Rosa (6)
(6,  'Cuilapa'),                       -- 23
(6,  'Barberena'),                     -- 24
(6,  'Chiquimulilla'),                 -- 25
-- Sololá (7)
(7,  'Sololá'),                        -- 26
(7,  'Panajachel'),                    -- 27
(7,  'Santiago Atitlán'),              -- 28
-- Totonicapán (8)
(8,  'Totonicapán'),                   -- 29
(8,  'San Cristóbal Totonicapán'),     -- 30
(8,  'Momostenango'),                  -- 31
-- Quetzaltenango (9)
(9,  'Quetzaltenango'),                -- 32
(9,  'Salcajá'),                       -- 33
(9,  'Almolonga'),                     -- 34
(9,  'Coatepeque'),                    -- 35
(9,  'San Marcos Quetzaltenango'),     -- 36
-- Suchitepéquez (10)
(10, 'Mazatenango'),                   -- 37
(10, 'Cuyotenango'),                   -- 38
(10, 'San Antonio Suchitepéquez'),     -- 39
-- Retalhuleu (11)
(11, 'Retalhuleu'),                    -- 40
(11, 'San Sebastián'),                 -- 41
(11, 'Champerico'),                    -- 42
-- San Marcos (12)
(12, 'San Marcos'),                    -- 43
(12, 'San Pedro Sacatepéquez'),        -- 44
(12, 'Malacatán'),                     -- 45
(12, 'Tajumulco'),                     -- 46
-- Huehuetenango (13)
(13, 'Huehuetenango'),                 -- 47
(13, 'Chiantla'),                      -- 48
(13, 'La Democracia'),                 -- 49
(13, 'Jacaltenango'),                  -- 50
-- Quiché (14)
(14, 'Santa Cruz del Quiché'),         -- 51
(14, 'Chichicastenango'),              -- 52
(14, 'Nebaj'),                         -- 53
-- Baja Verapaz (15)
(15, 'Salamá'),                        -- 54
(15, 'Rabinal'),                       -- 55
(15, 'Cubulco'),                       -- 56
-- Alta Verapaz (16)
(16, 'Cobán'),                         -- 57
(16, 'Santa Cruz Verapaz'),            -- 58
(16, 'San Pedro Carchá'),              -- 59
(16, 'Lanquín'),                       -- 60
-- Petén (17)
(17, 'San Benito'),                    -- 61
(17, 'Santa Elena'),                   -- 62
(17, 'Flores'),                        -- 63
(17, 'San Andrés'),                    -- 64
-- Izabal (18)
(18, 'Puerto Barrios'),                -- 65
(18, 'Livingston'),                    -- 66
(18, 'El Estor'),                      -- 67
-- Zacapa (19)
(19, 'Zacapa'),                        -- 68
(19, 'Estanzuela'),                    -- 69
(19, 'Gualán'),                        -- 70
-- Chiquimula (20)
(20, 'Chiquimula'),                    -- 71
(20, 'Esquipulas'),                    -- 72
(20, 'Jocotán'),                       -- 73
-- Jalapa (21)
(21, 'Jalapa'),                        -- 74
(21, 'San Pedro Pinula'),
(21, 'Monjas'),
-- Jutiapa (22)
(22, 'Jutiapa'),
(22, 'Asunción Mita'),
(22, 'Santa Catarina Mita');


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
    (1, 'https://storage.revistas.gt/tech-monthly/edicion-01.pdf',  '2025-01-15 00:00:00'),
    (1, 'https://storage.revistas.gt/tech-monthly/edicion-02.pdf',  '2025-02-15 00:00:00'),
    (1, 'https://storage.revistas.gt/tech-monthly/edicion-03.pdf',  '2025-03-15 00:00:00'),
    (2, 'https://storage.revistas.gt/gadget-world/edicion-01.pdf',  '2025-03-10 00:00:00'),
    (3, 'https://storage.revistas.gt/science-today/edicion-01.pdf', '2025-02-20 00:00:00'),
    (3, 'https://storage.revistas.gt/science-today/edicion-02.pdf', '2025-03-20 00:00:00'),
    (5, 'https://storage.revistas.gt/deporte-total/edicion-01.pdf', '2025-02-01 00:00:00'),
    (5, 'https://storage.revistas.gt/deporte-total/edicion-02.pdf', '2025-03-01 00:00:00'),
    (6, 'https://storage.revistas.gt/fitness-pro/edicion-01.pdf',   '2025-04-15 00:00:00');


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

INSERT INTO interaccion_revista (revista_id, usuario_id, me_gusta, fecha_me_gusta, comentario, fecha_comentario) VALUES
    (1,  7,  TRUE,  '2025-01-21 10:30:00', 'Excelente contenido, muy actualizado!',        '2025-01-21 10:35:00'),
    (1,  8,  TRUE,  '2025-01-23 14:00:00', 'La mejor revista tech de Guatemala.',           '2025-01-23 14:05:00'),
    (1,  10, TRUE,  '2025-02-02 09:15:00', 'Muy buen análisis del mercado tecnológico.',    '2025-02-02 09:20:00'),
    (1,  7,  FALSE, NULL,                  'Esperando la edición de abril con ansias.',     '2025-03-20 10:00:00'),  -- comentario sin reacción
    (3,  8,  TRUE,  '2025-02-19 16:45:00', 'El artículo sobre cambio climático es muy útil.', '2025-02-19 17:00:00'),
    (3,  9,  TRUE,  '2025-03-02 12:30:00', 'Me gusta el enfoque divulgativo.',              '2025-03-02 12:45:00'),
    (5,  7,  TRUE,  '2025-02-06 20:00:00', 'Gran cobertura de la liga local!',              '2025-02-06 20:15:00'),
    (5,  10, TRUE,  '2025-02-11 18:30:00', 'Excelentes estadísticas y análisis.',           '2025-02-11 18:45:00'),
    (6,  9,  FALSE, NULL,                  'El plan de entrenamiento de 8 semanas es genial.', '2025-04-21 08:00:00'),
    (6,  10, FALSE, NULL,                  'Quisiera más contenido de nutrición.',           '2025-04-23 09:30:00');


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
    (13, 2, 1,  50, '2026-02-05 10:00:00', '2026-02-12 10:00:00'),
    (13, 1, 2,  10, '2026-01-15 10:00:00', '2026-01-16 10:00:00'),

    -- Anunciante 14
    (14, 3, 1,  85, '2026-02-10 09:00:00', '2026-02-17 09:00:00'),
    (14, 2, 1,  50, '2026-02-10 09:00:00', '2026-02-24 09:00:00'),

    -- Anunciante 15
    (15, 1, 2,  10, '2026-01-20 09:00:00', '2026-01-21 09:00:00'),
    (15, 2, 2,  50, '2026-01-20 09:00:00', '2026-01-27 09:00:00');


-- -------------------------------------------------------------
--  CONTENIDO EXTRA DE ANUNCIOS
-- -------------------------------------------------------------

INSERT INTO contenido_extra (anuncio_id, recurso) VALUES
    (1, 'https://storage.revistas.gt/anuncios/techstore-iphone16.jpg'),
    (2, 'https://storage.revistas.gt/anuncios/techstore-video-promo.mp4'),
    (3, 'https://storage.revistas.gt/anuncios/boutique-primavera2026.jpg'),
    (4, 'https://storage.revistas.gt/anuncios/ricardosolano-campana.mp4'),
    (5, 'https://storage.revistas.gt/anuncios/ricardosolano-banner.jpg'),
    (6, 'https://storage.revistas.gt/anuncios/mariana-viajes-banner.jpg'),
    (7, 'https://storage.revistas.gt/anuncios/ricardosolano-campana.mp4'),
    (8, 'https://storage.revistas.gt/anuncios/ricardosolano-banner.jpg'),
    (9, 'https://storage.revistas.gt/anuncios/mariana-viajes-banner.jpg');


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