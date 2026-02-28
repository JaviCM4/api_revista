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
    ('Inactivo'),
    ('Expirado'),
    ('Pendiente de aprobación');

INSERT INTO estado_bloqueo_anuncio (nombre) VALUES
    ('Activo'),
    ('Expirado'),
    ('Cancelado');

INSERT INTO tipo_costo (nombre) VALUES
    ('1 día'),
    ('3 días'),
    ('1 semana'),
    ('2 semanas');

INSERT INTO tipo_contacto (nombre) VALUES
    ('Correo electrónico'),
    ('Teléfono');

INSERT INTO pais (nombre) VALUES
    ('Guatemala');

INSERT INTO departamento (pais_id, nombre) VALUES
    (1, 'Guatemala'),
    (1, 'Quetzaltenango'),
    (1, 'San Marcos'),
    (1, 'Huehuetenango'),
    (1, 'Alta Verapaz'),
    (1, 'Petén'),
    (1, 'Escuintla'),
    (1, 'Sacatepéquez'),
    (1, 'Chimaltenango'),
    (1, 'Izabal');

INSERT INTO municipio (departamento_id, nombre) VALUES
    (1, 'Guatemala'),
    (1, 'Mixco'),
    (1, 'Villa Nueva'),
    (1, 'Petapa'),
    (1, 'San Miguel Petapa'),
    (2, 'Quetzaltenango'),
    (2, 'Salcajá'),
    (2, 'Almolonga'),
    (3, 'San Marcos'),
    (3, 'San Pedro Sacatepéquez'),
    (4, 'Huehuetenango'),
    (4, 'Chiantla'),
    (5, 'Cobán'),
    (5, 'Santa Cruz Verapaz'),
    (8, 'Antigua Guatemala');

INSERT INTO usuario (tipo_usuario_id, estado_usuario_id, tipo_sexo_id, municipio_id, nombres, apellidos, fecha_nacimiento, fotografia, descripcion, dinero_disponible) VALUES
-- Administradores (id 1, 2)
(1, 1, 1, 1,  'Carlos Alberto',  'Méndez López',    '1985-03-12', 'https://randomuser.me/api/portraits/men/1.jpg',   'Administrador principal del sistema.',        0),
(1, 1, 2, 2,  'Laura Patricia',  'Salazar Vásquez', '1990-07-25', 'https://randomuser.me/api/portraits/women/1.jpg', 'Administradora de contenidos.',               0),
-- Editores (id 3, 4, 5, 6, 7)
(2, 1, 1, 3,  'Roberto Carlos',  'Fuentes Pérez',   '1988-11-05', 'https://randomuser.me/api/portraits/men/2.jpg',   'Editor apasionado de tecnología.',          500),
(2, 1, 2, 6,  'María José',      'Hernández Gil',   '1992-04-18', 'https://randomuser.me/api/portraits/women/2.jpg', 'Editora especializada en ciencias.',         300),
(2, 1, 1, 9,  'Juan Pablo',      'Ruiz Morales',    '1986-09-30', 'https://randomuser.me/api/portraits/men/3.jpg',   'Editor de revistas deportivas.',             750),
(2, 1, 2, 13, 'Ana Lucía',       'Pérez Castillo',  '1994-01-22', 'https://randomuser.me/api/portraits/women/3.jpg', 'Editora de arte y cultura.',                 200),
(2, 1, 1, 1,  'Diego Alejandro', 'Torres Ramírez',  '1991-06-14', 'https://randomuser.me/api/portraits/men/4.jpg',   'Editor de economía y negocios.',             900),
-- Suscriptores (id 8, 9, 10, 11, 12)
(3, 1, 2, 2,  'Sofía Isabel',    'García López',    '1997-08-03', 'https://randomuser.me/api/portraits/women/4.jpg', 'Amante de la lectura digital.',               50),
(3, 1, 1, 3,  'Luis Enrique',    'Chávez Moreno',   '1995-12-11', 'https://randomuser.me/api/portraits/men/5.jpg',   'Estudiante universitario.',                   25),
(3, 1, 2, 6,  'Valentina',       'Gómez Santizo',   '1999-02-28', 'https://randomuser.me/api/portraits/women/5.jpg', 'Diseñadora gráfica freelance.',               75),
(3, 1, 1, 9,  'Andrés Felipe',   'Jiménez Cruz',    '1993-05-17', 'https://randomuser.me/api/portraits/men/6.jpg',   'Ingeniero en sistemas.',                      10),
(3, 1, 2, 13, 'Camila',          'Rodríguez Pinto', '1998-10-09', 'https://randomuser.me/api/portraits/women/6.jpg', 'Estudiante de medicina.',                     30),
-- Anunciantes (id 13, 14, 15)
(4, 1, 1, 1,  'Fernando',        'Castillo Ramos',  '1980-03-25', 'https://randomuser.me/api/portraits/men/7.jpg',   'Empresario dueño de tienda de tecnología.', 2000),
(4, 1, 2, 2,  'Gabriela',        'Lima Ortega',     '1984-07-19', 'https://randomuser.me/api/portraits/women/7.jpg', 'Propietaria de boutique de moda.',          1500),
(4, 1, 1, 3,  'Ricardo',         'Solano Barrios',  '1978-11-04', 'https://randomuser.me/api/portraits/men/8.jpg',   'Gerente de marketing digital.',             3000);

-- ============================================
-- CREDENCIALES
-- Contraseña real de prueba para todos: Test1234!
-- Hash BCrypt válido para "password" - reemplazar con tu PasswordEncoder
-- ============================================

INSERT INTO credencial (usuario_id, username, password) VALUES
    (1,  'admin.uno',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (2,  'admin.dos',       '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (3,  'editor.uno',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (4,  'editor.dos',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (5,  'editor.tres',       '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (6,  'editor.cuatro',        '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (7,  'editor.cinco',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (8,  'suscrip.uno',     '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (9,  'suscrip.dos',      '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (10, 'suscrip.tres', '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (11, 'suscrip.cuatro',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (12, 'suscrip.cinco',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (13, 'anunc.uno',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (14, 'anunc.dos',    '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6'),
    (15, 'anunc.tres',     '$2a$10$7jHGGPU1WgbYHiip56DfBeBoDQCXJpz9u.SKq2TRL.E5CfWHWC/h6');

INSERT INTO contacto (usuario_id, tipo_contacto_id, detalle) VALUES
    (3,  1, 'roberto.fuentes@techmag.gt'),
    (3,  2, '50245678901'),
    (4,  1, 'maria.hernandez@sciencereview.gt'),
    (5,  1, 'juan.ruiz@deportesmag.gt'),
    (6,  1, 'ana.perez@artcultura.gt'),
    (7,  1, 'diego.torres@economiaplus.gt'),
    (13, 1, 'fernando.castillo@techstore.gt'),
    (14, 1, 'gabriela.lima@boutiquemoda.gt'),
    (15, 1, 'ricardo.solano@marketingdigital.gt');

INSERT INTO categoria_preferencia (tipo_categoria_revista_id, tipo_preferencia_id, nombre) VALUES
    (1,  1, 'Programación como hobby'),
    (1,  2, 'Inteligencia Artificial'),
    (1,  3, 'Desarrollador de Software'),
    (2,  2, 'Investigación Científica'),
    (2,  3, 'Biólogo / Científico'),
    (3,  1, 'Fútbol amateur'),
    (3,  5, 'Baloncesto'),
    (4,  1, 'Cine y series');

INSERT INTO preferencia (usuario_id, categoria_preferencia_id) VALUES
    (8,  1),
    (9,  3),
    (11, 3),
    (11, 2),
    (12, 3),
    (3,  1),
    (3,  2),
    (4,  3),
    (5,  1),
    (7,  2);

INSERT INTO revista (usuario_id, titulo, descripcion, permitir_suscripcion, permitir_comentarios, permitir_reacciones, revista_activa, costo_dia, costo_bloqueo_anuncio, fecha_creacion) VALUES
    (3, 'Tech Monthly',        'La revista mensual de tecnología más completa de Centroamérica.',        TRUE,  TRUE,  TRUE, FALSE, 8,  5, '2025-01-10 00:00:00'),
    (3, 'Gadget World',        'Todo sobre los últimos gadgets y dispositivos del mercado.',             TRUE,  TRUE,  TRUE, FALSE,  6,  4, '2025-03-05 00:00:00'),
    (4, 'Science Today',       'Noticias y avances científicos al alcance de todos.',                   TRUE,  TRUE,  TRUE, FALSE, 7,  4, '2025-02-14 00:00:00'),
    (4, 'Bio & Nature',        'Explorando la biología y el mundo natural.',                            TRUE,  FALSE, TRUE, FALSE, 5,  3, '2025-05-20 00:00:00'),
    (5, 'Deporte Total',       'Todo sobre fútbol, baloncesto y más deportes.',                         TRUE,  TRUE,  TRUE, FALSE, 9,  6, '2025-01-25 00:00:00'),
    (5, 'Fitness Pro',         'Guías de entrenamiento y vida saludable para atletas.',                 TRUE,  TRUE,  FALSE, TRUE, 6,  4, '2025-04-10 00:00:00'),
    (6, 'Arte & Diseño',       'Revista dedicada al arte contemporáneo y diseño gráfico.',              TRUE,  TRUE,  TRUE, TRUE,  5,  3, '2025-03-18 00:00:00'),
    (6, 'Cultura Viva',        'La escena cultural de Guatemala y el mundo.',                           FALSE, TRUE,  TRUE, TRUE,  4,  2, '2025-06-01 00:00:00'),
    (7, 'Economía Hoy',        'Análisis económico y financiero para empresarios y profesionales.',     TRUE,  TRUE,  TRUE, TRUE,  10, 7, '2025-02-01 00:00:00'),
    (7, 'Negocios & Startups', 'El mundo emprendedor: casos de éxito, tendencias e inversión.',        TRUE,  FALSE, TRUE, TRUE,    8,  5, '2025-07-12 00:00:00');


INSERT INTO categoria_revista (revista_id, tipo_categoria_revista_id) VALUES
    (1,  1),
    (2,  1),
    (3,  2),
    (4,  2),
    (4,  7),
    (5,  3),
    (6,  3),
    (6,  7),
    (7,  9),
    (8,  9),
    (9,  6),
    (10, 6);


INSERT INTO etiqueta_revista (revista_id, detalle) VALUES
    (1,  'inteligencia-artificial'),
    (1,  'programacion'),
    (1,  'software'),
    (2,  'gadgets'),
    (2,  'smartphones'),
    (3,  'ciencia'),
    (3,  'investigacion'),
    (4,  'biologia'),
    (4,  'naturaleza'),
    (5,  'futbol'),
    (5,  'deportes'),
    (6,  'fitness'),
    (6,  'entrenamiento'),
    (7,  'arte'),
    (7,  'diseno'),
    (8,  'cultura'),
    (8,  'guatemala'),
    (9,  'economia'),
    (9,  'finanzas'),
    (10, 'startups'),
    (10, 'emprendimiento');


INSERT INTO edicion (revista_id, recurso, fecha_creacion) VALUES
    (1,  'https://storage.revistas.gt/tech-monthly/edicion-01.pdf',  '2025-01-15 00:00:00'),
    (1,  'https://storage.revistas.gt/tech-monthly/edicion-02.pdf',  '2025-02-15 00:00:00'),
    (1,  'https://storage.revistas.gt/tech-monthly/edicion-03.pdf',  '2025-03-15 00:00:00'),
    (2,  'https://storage.revistas.gt/gadget-world/edicion-01.pdf',  '2025-03-10 00:00:00'),
    (2,  'https://storage.revistas.gt/gadget-world/edicion-02.pdf',  '2025-04-10 00:00:00'),
    (3,  'https://storage.revistas.gt/science-today/edicion-01.pdf', '2025-02-20 00:00:00'),
    (3,  'https://storage.revistas.gt/science-today/edicion-02.pdf', '2025-03-20 00:00:00'),
    (4,  'https://storage.revistas.gt/bio-nature/edicion-01.pdf',    '2025-05-25 00:00:00'),
    (5,  'https://storage.revistas.gt/deporte-total/edicion-01.pdf', '2025-02-01 00:00:00'),
    (5,  'https://storage.revistas.gt/deporte-total/edicion-02.pdf', '2025-03-01 00:00:00'),
    (6,  'https://storage.revistas.gt/fitness-pro/edicion-01.pdf',   '2025-04-15 00:00:00'),
    (7,  'https://storage.revistas.gt/arte-diseno/edicion-01.pdf',   '2025-03-25 00:00:00'),
    (9,  'https://storage.revistas.gt/economia-hoy/edicion-01.pdf',  '2025-02-10 00:00:00'),
    (9,  'https://storage.revistas.gt/economia-hoy/edicion-02.pdf',  '2025-03-10 00:00:00'),
    (10, 'https://storage.revistas.gt/negocios/edicion-01.pdf',      '2025-07-20 00:00:00');


INSERT INTO suscripcion_revista (revista_id, usuario_id, fecha_suscripcion) VALUES
    (1,  8,  '2025-01-20 10:00:00'),
    (1,  9,  '2025-01-22 11:00:00'),
    (1,  11, '2025-02-01 09:00:00'),
    (2,  8,  '2025-03-12 14:00:00'),
    (2,  10, '2025-03-15 16:00:00'),
    (3,  9,  '2025-02-18 08:00:00'),
    (3,  12, '2025-03-01 10:00:00'),
    (4,  10, '2025-05-28 12:00:00'),
    (5,  8,  '2025-02-05 19:00:00'),
    (5,  11, '2025-02-10 20:00:00'),
    (6,  12, '2025-04-20 09:00:00'),
    (7,  10, '2025-04-01 13:00:00'),
    (8,  8,  '2025-06-10 15:00:00'),
    (9,  9,  '2025-02-15 07:00:00'),
    (10, 11, '2025-07-15 08:00:00');


INSERT INTO interaccion_revista (revista_id, usuario_id, me_gusta, fecha_me_gusta, comentario, fecha_comentario) VALUES
    (1,  8,  TRUE,  '2025-01-21 10:30:00', 'Excelente contenido, muy actualizado!',             '2025-01-21 10:35:00'),
    (1,  9,  TRUE,  '2025-01-23 14:00:00', 'La mejor revista tech de Guatemala.',               '2025-01-23 14:05:00'),
    (1,  11, TRUE,  '2025-02-02 09:15:00', 'Muy buen análisis del mercado tecnológico.',        '2025-02-02 09:20:00'),
    (2,  8,  TRUE,  '2025-03-13 11:00:00', 'Me encantaron las reseñas de smartphones.',         '2025-03-13 11:10:00'),
    (2,  10, FALSE, NULL,                  'Falta más contenido sobre wearables.',               '2025-03-16 08:00:00'),
    (3,  9,  TRUE,  '2025-02-19 16:45:00', 'Artículo sobre cambio climático muy informativo.',  '2025-02-19 17:00:00'),
    (3,  12, TRUE,  '2025-03-02 12:30:00', 'Me gusta el enfoque divulgativo.',                  '2025-03-02 12:45:00'),
    (5,  8,  TRUE,  '2025-02-06 20:00:00', 'Gran cobertura de la liga local!',                  '2025-02-06 20:15:00'),
    (5,  11, TRUE,  '2025-02-11 18:30:00', 'Excelentes estadísticas y análisis.',               '2025-02-11 18:45:00'),
    (9,  9,  TRUE,  '2025-02-16 07:30:00', 'Indispensable para cualquier empresario.',          '2025-02-16 07:45:00'),
    (1,  8,  FALSE, NULL,                  'Esperando la edición de abril con ansias.',          '2025-03-20 10:00:00'),
    (7,  10, TRUE,  '2025-04-02 13:00:00', 'El diseño editorial es impecable.',                 '2025-04-02 13:15:00'),
    (10, 11, TRUE,  '2025-07-16 09:00:00', 'Muy inspirador para emprendedores jóvenes.',        '2025-07-16 09:20:00');


INSERT INTO pago_revista (revista_id, pago, fecha_pago) VALUES
    (1,  240, '2025-02-01 00:00:00'),
    (1,  224, '2025-03-01 00:00:00'),
    (1,  248, '2025-04-01 00:00:00'),
    (2,  168, '2025-04-01 00:00:00'),
    (2,  186, '2025-05-01 00:00:00'),
    (3,  196, '2025-03-01 00:00:00'),
    (3,  210, '2025-04-01 00:00:00'),
    (4,  165, '2025-06-01 00:00:00'),
    (5,  279, '2025-02-01 00:00:00'),
    (5,  248, '2025-03-01 00:00:00'),
    (6,  195, '2025-05-01 00:00:00'),
    (7,  165, '2025-04-01 00:00:00'),
    (9,  300, '2025-03-01 00:00:00'),
    (9,  310, '2025-04-01 00:00:00'),
    (10, 255, '2025-08-01 00:00:00');

INSERT INTO costo_sugerido (tipo_costo_id, costo, dias) VALUES
    (1, 10,  1),
    (2, 25,  3),
    (3, 50,  7),
    (4, 85,  14);


INSERT INTO anuncio (usuario_id, tipo_anuncio_id, estado_anuncio_id, contenido, costo_total, fecha_creacion, fecha_vencimiento) VALUES
    (13, 1, 1, '¡Visita TechStore GT! Los mejores precios en laptops y accesorios. techstoreguatemala.com',             25, '2026-02-01 08:00:00', '2026-02-08 08:00:00'),
    (13, 2, 1, 'iPhone 16 Pro ya disponible en TechStore GT. ¡No te lo pierdas!',                                      50, '2026-02-01 08:00:00', '2026-02-15 08:00:00'),
    (13, 3, 2, 'https://youtube.com/watch?v=techstore-promo-2026',                                                      85, '2026-01-01 08:00:00', '2026-01-08 08:00:00'),
    (14, 2, 1, 'Nueva colección Primavera 2026 en Boutique Gabriela. ¡Estilos únicos para cada ocasión!',              50, '2026-02-05 10:00:00', '2026-02-12 10:00:00'),
    (14, 1, 3, 'Descuentos de hasta 50% en Boutique Gabriela. Solo por tiempo limitado.',                              10, '2026-01-15 10:00:00', '2026-01-16 10:00:00'),
    (15, 3, 1, 'https://youtube.com/watch?v=marketing-digital-gt-2026',                                                85, '2026-02-10 09:00:00', '2026-02-17 09:00:00'),
    (15, 2, 1, '¿Tu empresa necesita presencia digital? Contacta a Ricardo Solano Marketing y multiplica tus ventas.', 50, '2026-02-10 09:00:00', '2026-02-24 09:00:00'),
    (13, 1, 1, 'Garantía extendida gratis en todos los productos de TechStore GT durante febrero.',                    25, '2026-02-01 08:00:00', '2026-02-08 08:00:00'),
    (14, 3, 1, 'https://youtube.com/watch?v=boutique-gabriela-spring2026',                                             85, '2026-02-05 10:00:00', '2026-02-19 10:00:00'),
    (15, 1, 2, 'Webinar GRATIS: Cómo crear tu estrategia digital en 2026. Regístrate en ricardosolanomarketing.com',  10, '2026-01-20 09:00:00', '2026-01-21 09:00:00');

INSERT INTO contenido_extra (anuncio_id, recurso) VALUES
    (2, 'https://storage.revistas.gt/anuncios/techstore-iphone16.jpg'),
    (4, 'https://storage.revistas.gt/anuncios/boutique-primavera2026.jpg'),
    (7, 'https://storage.revistas.gt/anuncios/ricardosolano-banner.jpg'),
    (9, 'https://storage.revistas.gt/anuncios/boutique-spring-video-thumb.jpg');

INSERT INTO bloqueo_anuncio (revista_id, anuncio_id, estado_bloqueo_anuncio_id, pago, fecha_inicio_bloqueo, fecha_fin_bloqueo) VALUES
    (1, 1, 1, 35, '2026-02-01 00:00:00', '2026-02-08 00:00:00'),  -- Tech Monthly bloqueó 7 días (activo)
    (3, 3, 2, 13, '2026-01-10 00:00:00', '2026-01-13 00:00:00'),  -- Science Today bloqueó 3 días (expirado)
    (9, 6, 1, 70, '2026-02-05 00:00:00', '2026-02-19 00:00:00'),  -- Economía Hoy bloqueó 14 días (activo)
    (5, 2, 3, 18, '2026-01-20 00:00:00', '2026-01-23 00:00:00'),  -- Deporte Total canceló bloqueo
    (7, 4, 2, 17, '2026-01-15 00:00:00', '2026-01-22 00:00:00');  -- Arte & Diseño bloqueó 7 días (expirado)

INSERT INTO registro_anuncio (anuncio_id, url, cantidad_vistas, fecha_vista) VALUES
    (1,  'https://revistas.gt/revista/tech-monthly',        150, '2026-02-01 12:00:00'),
    (1,  'https://revistas.gt/revista/gadget-world',         87, '2026-02-02 15:00:00'),
    (2,  'https://revistas.gt/revista/tech-monthly',        210, '2026-02-01 12:00:00'),
    (2,  'https://revistas.gt/revista/gadget-world',        175, '2026-02-02 15:00:00'),
    (2,  'https://revistas.gt/revista/deporte-total',        95, '2026-02-03 18:00:00'),
    (4,  'https://revistas.gt/revista/arte-diseno',         130, '2026-02-05 11:00:00'),
    (4,  'https://revistas.gt/revista/cultura-viva',         68, '2026-02-06 14:00:00'),
    (6,  'https://revistas.gt/revista/economia-hoy',        200, '2026-02-10 09:30:00'),
    (6,  'https://revistas.gt/revista/negocios-startups',   185, '2026-02-11 10:00:00'),
    (7,  'https://revistas.gt/revista/economia-hoy',        140, '2026-02-10 09:30:00'),
    (8,  'https://revistas.gt/revista/tech-monthly',        115, '2026-02-01 12:00:00'),
    (9,  'https://revistas.gt/revista/arte-diseno',          92, '2026-02-05 11:00:00'),
    (9,  'https://revistas.gt/revista/cultura-viva',         54, '2026-02-06 14:00:00'),
    (10, 'https://revistas.gt/revista/economia-hoy',         78, '2026-01-20 09:00:00');
