ALTER TABLE medicos
DROP COLUMN direccion;

ALTER TABLE medicos
ADD COLUMN calle VARCHAR(100) NOT NULL,
ADD COLUMN distrito VARCHAR(100) NOT NULL,
ADD COLUMN complemento VARCHAR(100),
ADD COLUMN numero VARCHAR(20),
ADD COLUMN ciudad VARCHAR(100) NOT NULL;