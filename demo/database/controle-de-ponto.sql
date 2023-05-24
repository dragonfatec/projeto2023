CREATE TABLE usuario(
	
	matricula VARCHAR(50) PRIMARY KEY,
	senha VARCHAR(50) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	cargo VARCHAR(50) NOT NULL,
	situacao VARCHAR(50) NOT NULL

);
CREATE TABLE equipe(

	id_equipe SERIAL PRIMARY KEY,
	nome_equipe VARCHAR(255) UNIQUE NOT NULL

);
CREATE TABLE cliente(

	id_cliente SERIAL PRIMARY KEY,
	empresa VARCHAR(255),
	responsavel VARCHAR(255),
	email VARCHAR(255),
	telefone VARCHAR(13),
	projeto VARCHAR(255)

);
CREATE TABLE hora(

	id_hora SERIAL PRIMARY KEY,
	matricula VARCHAR(50) NOT NULL,
	data_hora_inicial TIMESTAMP NOT NULL,
	data_hora_final TIMESTAMP NOT NULL,
	justificativa VARCHAR(200),
	id_equipe INTEGER NOT NULL,
	tipo_hora VARCHAR(30) NOT NULL,
	id_cliente INTEGER,
	status VARCHAR(50),
	justificativa_status VARCHAR(200),

	FOREIGN KEY (matricula)
	REFERENCES usuario (matricula)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	FOREIGN KEY (id_equipe)
	REFERENCES equipe (id_equipe)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	FOREIGN KEY (id_cliente)
	REFERENCES cliente (id_cliente)
	ON DELETE CASCADE
	ON UPDATE CASCADE

);
---- RELACIONAMENTOS
CREATE TABLE equipe_cliente(

	id_equipe INTEGER,
	id_cliente INTEGER,
	PRIMARY KEY (id_equipe, id_cliente),

	FOREIGN KEY (id_equipe)
	REFERENCES equipe (id_equipe)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	FOREIGN KEY (id_cliente)
	REFERENCES cliente (id_cliente)
	ON DELETE CASCADE
	ON UPDATE CASCADE

);
CREATE TABLE equipe_usuario(

	id_equipe INTEGER,
	matricula VARCHAR(50),

	PRIMARY KEY (id_equipe, matricula),

	FOREIGN KEY (id_equipe)
	REFERENCES equipe (id_equipe)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	FOREIGN KEY (matricula)
	REFERENCES usuario (matricula)
	ON DELETE CASCADE
	ON UPDATE CASCADE

);