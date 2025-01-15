CREATE TABLE medicos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    documento VARCHAR(255) UNIQUE,
    especialidad VARCHAR(50),
    direccion JSONB
);