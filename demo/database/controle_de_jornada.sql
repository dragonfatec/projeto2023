-- ENTIDADES

CREATE TABLE usuario(
	id_usuario SERIAL PRIMARY KEY,
	login VARCHAR(50) UNIQUE NOT NULL,
	senha VARCHAR(50) UNIQUE NOT NULL,
	matricula VARCHAR(50) UNIQUE NOT NULL,
	nome VARCHAR(255) NOT NULL,
	tipo VARCHAR(10) NOT NULL

);

DELETE FROM usuario WHERE id_usuario = 4;
INSERT INTO usuario(login, senha, matricula, nome, tipo) VALUES ('lucas', 'lucas321', '654321', 'Lucas Oliveira', 'Usuario');
INSERT INTO usuario(login, senha, matricula, nome, tipo) VALUES ('joao', 'joao1010', '321654', 'João Carlos', 'Usuario');
INSERT INTO usuario(login, senha, matricula, nome, tipo) VALUES ('batista', 'bat123', '123', 'batista', 'Gerente');
SELECT * FROM usuario;

CREATE TABLE hora(
	id_hora SERIAL PRIMARY KEY,
	data_registro DATE NOT NULL,
	hora_inicio VARCHAR(5) NOT NULL,
	hora_fim VARCHAR(5) NOT NULL,
	justificativa VARCHAR(200),
	tipo VARCHAR(30) NOT NULL
	
);

INSERT INTO hora (data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES ('2023/03/27', '08:00', '12:00','Trabalhando no DB', 'Hora Normal');
INSERT INTO hora (data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES ('2023/03/27', '13:00', '17:00','Trabalhando no DB', 'Hora Normal');
INSERT INTO hora (data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES ('2023/03/27', '08:00', '12:00','Trabalhando no Front-End', 'Hora Normal');
INSERT INTO hora (data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES ('2023/03/27', '13:00', '15:00','Trabalhando no Front-End', 'Hora Normal');
INSERT INTO hora (data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES ('04/04/2023', '22:00', '23:00','Teste', 'Hora Normal');
SELECT * FROM hora;

DELETE FROM hora WHERE id_hora = 7;

CREATE TABLE centro_resultado(
	id_cr SERIAL PRIMARY KEY,
	centro_resultado VARCHAR(255) NOT NULL,
	cliente VARCHAR(255) NOT NULL
	
);

INSERT INTO centro_resultado( centro_resultado, cliente) VALUES ('DB', 'EMPRESA X');
INSERT INTO centro_resultado( centro_resultado, cliente) VALUES ('Front-end', 'EMPRESA Y');
SELECT * FROM centro_resultado;

-- RELACIONAMENTOS

CREATE TABLE usuario_hora(
	id_apontamento SERIAL NOT NULL,
	id_usuario INTEGER NOT NULL,
	id_hora INTEGER NOT NULL,
	
	PRIMARY KEY (id_usuario, id_hora),
	
	FOREIGN KEY (id_usuario)
	REFERENCES usuario (id_usuario),
	
	FOREIGN KEY (id_hora)
	REFERENCES hora (id_hora)
	
);

INSERT INTO usuario_hora(id_usuario, id_hora) VALUES (1,1),(1,2),
													 (2,3),(2,4);
SELECT * FROM usuario_hora;
SELECT usuario.nome       AS "Nome do Usuario",
	   hora.data_registro AS "Data do Evento",
	   hora.hora_inicio   AS "Hora Inicial",
	   hora.hora_fim      AS "Hora Fim"
	   FROM usuario_hora
	   JOIN usuario ON usuario.id_usuario   = usuario_hora.id_usuario
	   JOIN hora    ON hora.id_hora         = usuario_hora.id_hora; 

CREATE TABLE aprova(
	id_aprova SERIAL PRIMARY KEY,
	id_hora INTEGER NOT NULL,
	id_aprovador INTEGER NOT NULL,
	status BOOLEAN NOT NULL,
	just_aprovacao VARCHAR(255) NOT NULL,
	
	FOREIGN KEY (id_hora)
	REFERENCES hora (id_hora),
	
	FOREIGN KEY (id_aprovador)
	REFERENCES usuario (id_usuario)

);

INSERT INTO aprova (id_hora, id_aprovador, status, just_aprovacao) VALUES (1, 3, TRUE, 'Parametrizando DB');
INSERT INTO aprova (id_hora, id_aprovador, status, just_aprovacao) VALUES (2, 3, TRUE, 'Parametrizando DB');
INSERT INTO aprova (id_hora, id_aprovador, status, just_aprovacao) VALUES (3, 3, TRUE, 'Montando Site');
INSERT INTO aprova (id_hora, id_aprovador, status, just_aprovacao) VALUES (4, 3, FALSE, 'Não autorizado');
SELECT * FROM aprova;
SELECT usuario.nome       AS "Aprovador",
	   hora.data_registro AS "Data do Evento",
	   hora.hora_inicio   AS "Hora Inicial",
	   hora.hora_fim      AS "Hora Fim",
       status             AS "Status",
	   just_aprovacao     AS "Justificativa"
	   FROM aprova
	   JOIN usuario ON usuario.id_usuario = aprova.id_aprovador
	   JOIN hora    ON hora.id_hora       = aprova.id_hora;

CREATE TABLE usuario_cr (
	id_usuario_cr SERIAL NOT NULL,
	id_usuario INTEGER NOT NULL,
	id_cr INTEGER NOT NULL,
	
	PRIMARY KEY(id_usuario, id_cr),
	
	FOREIGN KEY (id_usuario)
	REFERENCES usuario (id_usuario),
	
	FOREIGN KEY (id_cr)
	REFERENCES centro_resultado (id_cr)

);

INSERT INTO usuario_cr (id_usuario, id_cr) VALUES (1, 1);
INSERT INTO usuario_cr (id_usuario, id_cr) VALUES (2, 2);
SELECT * FROM usuario_cr;
SELECT usuario.nome     AS "Nome do Usuario",
	   centro_resultado AS "Centro de Resultado"
	   FROM usuario_cr
	   JOIN usuario          ON usuario.id_usuario = usuario_cr.id_usuario
	   JOIN centro_resultado ON centro_resultado.id_cr = usuario_cr.id_cr;

-- CRIANDO AS VIEW's

CREATE VIEW vw_apontamento_horas AS 
SELECT usuario.nome       AS "Nome do Usuario",
	   hora.data_registro AS "Data do Evento",
	   hora.hora_inicio   AS "Hora Inicial",
	   hora.hora_fim      AS "Hora Fim"
	   FROM usuario_hora
	   JOIN usuario ON usuario.id_usuario   = usuario_hora.id_usuario
	   JOIN hora    ON hora.id_hora         = usuario_hora.id_hora; 
	   
SELECT * FROM vw_apontamento_horas;

CREATE VIEW vw_aprovacoes AS
SELECT usuario.nome       AS "Aprovador",
	   hora.data_registro AS "Data do Evento",
	   hora.hora_inicio   AS "Hora Inicial",
	   hora.hora_fim      AS "Hora Fim",
       status             AS "Status",
	   just_aprovacao     AS "Justificativa"
	   FROM aprova
	   JOIN usuario ON usuario.id_usuario = aprova.id_aprovador
	   JOIN hora    ON hora.id_hora       = aprova.id_hora;

SELECT * FROM vw_aprovacoes;

CREATE VIEW vw_cr_usuarios AS
SELECT usuario.nome     AS "Nome do Usuario",
	   centro_resultado AS "Centro de Resultado"
	   FROM usuario_cr
	   JOIN usuario          ON usuario.id_usuario = usuario_cr.id_usuario
	   JOIN centro_resultado ON centro_resultado.id_cr = usuario_cr.id_cr;
	   
SELECT * FROM vw_cr_usuarios;