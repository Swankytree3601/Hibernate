-- Insert desarrolladoras
INSERT INTO desarrolladora (nombre, pais, fundacion) VALUES
('FromSoftware', 'Japón', 1986),
('Rockstar Games', 'Estados Unidos', 1998),
('Nintendo', 'Japón', 1889),
('PlatinumGames', 'Japón', 2007),
('Square Enix', 'Japón', 1975),
('CD Projekt Red', 'Polonia', 2002),
('Capcom', 'Japón', 1979);

-- Insert juegos
INSERT INTO videojuego (titulo, genero, fechaLanzamiento, precio, desarrolladora_id) VALUES
('Dark Souls', 'RPG', '2011-09-22', 39.99, 1),
('Dark Souls II', 'RPG', '2014-03-11', 39.99, 1),
('Dark Souls III', 'RPG', '2016-03-24', 59.99, 1),
('Demon''s Souls', 'RPG', '2022-02-25', 69.99, 1), -- Dos comillas para una sola comilla

('Grand Theft Auto V', 'Acción', '2013-09-17', 49.99, 2),
('Red Dead Redemption 2', 'Acción', '2018-10-26', 59.99, 2),

('The Legend of Zelda: Breath of the Wild', 'Aventura', '2017-03-03', 59.99, 3),
('Super Mario Odyssey', 'Plataformas', '2017-10-27', 59.99, 3),

('Bayonetta', 'Hack and Slash', '2009-10-29', 29.99, 4),
('Bayonetta 2', 'Hack and Slash', '2014-09-20', 49.99, 4),

('Final Fantasy VII Remake', 'RPG', '2020-04-10', 69.99, 5),
('Grandia', 'RPG', '1997-12-18', 19.99, 5),

('The Witcher 3', 'RPG', '2015-05-19', 39.99, 6),

('Resident Evil 4', 'Survival Horror', '2005-01-11', 29.99, 7),
('Devil May Cry 5', 'Hack and Slash', '2019-03-08', 59.99, 7);

-- Insert jugadores
INSERT INTO jugador (nickname, email, fechaRegistro) VALUES
('Coscu777', 'player1@email.com', '2024-01-10'),
('Willyrex', 'dark@email.com', '2022-02-05'),
('Vegetta777', 'souls@email.com', '2025-03-12'),
('xXxNoobMaster69xXx', 'pro@email.com', '2026-02-07'),
('RetroLover', 'retro@email.com', '2021-11-12');

-- Insert Compras
INSERT INTO compra (jugador_id, videojuego_id, fechaCompra, precioFinal) VALUES
(1, 1, '2023-02-01', 39.99),
(1, 5, '2023-02-10', 49.99),
(2, 3, '2023-03-01', 59.99),
(2, 6, '2023-03-15', 59.99),
(3, 4, '2023-04-20', 69.99),
(3, 13, '2023-05-02', 39.99),
(4, 9, '2023-06-10', 29.99),
(4, 15, '2023-06-20', 59.99),
(5, 12, '2023-07-01', 19.99),
(5, 14, '2023-07-10', 29.99);