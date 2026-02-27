use videojuegos;

-- Crear tablas
-- Tabla de desarrolladoras
CREATE TABLE desarrolladora (
    iddesarrolladora INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    fundacion INT NOT NULL
);

-- Tabla de videojuegos
CREATE TABLE videojuego (
    idVideojuego INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    fechaLanzamiento DATE NOT NULL,
    precio DECIMAL(6,2) NOT NULL,
    desarrolladora_id INT NOT NULL,

    CONSTRAINT fk_videojuego_desarrolladora
        FOREIGN KEY (desarrolladora_id)
        REFERENCES desarrolladora (iddesarrolladora)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- Tabla de jugador
CREATE TABLE jugador (
    idJugador INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    fechaRegistro DATE NOT NULL
);

-- Tabla de compra
CREATE TABLE compra (
    idCompra INT AUTO_INCREMENT PRIMARY KEY,
    jugador_id INT NOT NULL,
    videojuego_id INT NOT NULL,
    fechaCompra DATE NOT NULL,
    precioFinal DECIMAL(6,2) NOT NULL,

    CONSTRAINT fk_compra_jugador
        FOREIGN KEY (jugador_id)
        REFERENCES jugador (idJugador)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_compra_videojuego
        FOREIGN KEY (videojuego_id)
        REFERENCES videojuego (idVideojuego)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);