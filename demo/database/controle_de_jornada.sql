-- ### USUARIO ###
CREATE TABLE usuario(

	id_usuario SERIAL PRIMARY KEY,
	login VARCHAR(50) UNIQUE NOT NULL,
	senha VARCHAR(50) NOT NULL,
	matricula VARCHAR(50) UNIQUE NOT NULL,
	nome VARCHAR(255) NOT NULL,
	cargo VARCHAR(50) NOT NULL,
	id_equipe INTEGER NOT NULL,

	FOREIGN KEY (id_equipe)
	REFERENCES equipe(id_equipe)
	ON DELETE CASCADE
	ON UPDATE CASCADE

);
-- INSERINDO INFORMAÇÕES NA TABELA USUARIO
INSERT INTO usuario(login, senha, matricula, nome, cargo, id_equipe) VALUES ('lucas', 'lucas321', '654321', 'Lucas Oliveira', 'Usuario', 1);
INSERT INTO usuario(login, senha, matricula, nome, cargo, id_equipe) VALUES ('joao', 'joao1010', '321654', 'João Carlos', 'Admnistrador',2);
INSERT INTO usuario(login, senha, matricula, nome, cargo, id_equipe) VALUES ('batista', 'bat123', '123', 'batista', 'Gerente',3);
-- COMANDOS NA TABELA USUARIO
SELECT * FROM usuario;
DELETE FROM usuario WHERE id_usuario = 12;
DROP TABLE usuario
SELECT id_usuario FROM usuario WHERE login = 'lucas';


-- ### HORA ###
CREATE TABLE hora(

	id_hora SERIAL PRIMARY KEY,
	id_usuario INTEGER NOT NULL,
	data_hora_inicial TIMESTAMP NOT NULL,
	data_hora_final TIMESTAMP NOT NULL,
	justificativa VARCHAR(200),
	id_equipe INTEGER NOT NULL,
	tipo_hora VARCHAR(30) NOT NULL,
	id_cliente INTEGER,
	aprovacao VARCHAR(50),
	justificativa_aprovacao VARCHAR(200),

	FOREIGN KEY (id_usuario)
	REFERENCES usuario (id_usuario)
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
-- INSERINDO INFORMAÇÕES NA TABELA HORA
INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, id_equipe, tipo_hora) VALUES (1,'2023-03-27 08:00', '2023/03/27 15:00', 1, 'Hora Normal');
INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, justificativa, id_equipe, tipo_hora, id_cliente) VALUES (3, '2023/03/27 16:00', '2023/03/27 19:00', 'Resolvendo problema no CRUD', 1, 'Hora Normal', 1);
INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, id_equipe, tipo_hora) VALUES (2,'2023/03/27 08:00', '2023/03/27 14:00', 1, 'Hora Normal');
INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, id_equipe, tipo_hora) VALUES (3,'2023/03/20 08:00', '2023/03/20 15:00', 2, 'Hora Normal');
INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, id_equipe, tipo_hora) VALUES (3,'2023/04/02 08:00', '2023/03/27 15:00', 1, 'Hora Normal');
UPDATE hora SET aprovacao = 'Aprovado' WHERE id_hora = 4;
UPDATE hora SET justificativa_aprovacao = 'ok' WHERE id_hora = 4;
UPDATE hora SET aprovacao = 'Em andamento' WHERE id_hora = 5;
UPDATE hora SET aprovacao = 'Reprovado' WHERE id_hora = 3;
UPDATE hora SET justificativa_aprovacao = 'Não autorizado' WHERE id_hora = 3;

-- COMANDOS NA TABELA HORA
SELECT * FROM hora;
DELETE FROM hora WHERE id_hora = 15;
DROP TABLE hora

SELECT * FROM usuario
SELECT data_hora_inicial    AS "Data_Inicial",
	   data_hora_final      AS "Data_Final",
	   CASE WHEN cliente.nome_cliente IS NULL THEN '-' ELSE cliente.nome_cliente END,
	   CASE WHEN aprovacao IS NULL THEN 'Em andamento' ELSE aprovacao END AS status
       FROM hora
	   LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente
	   JOIN usuario ON usuario.id_usuario = hora.id_usuario
	   WHERE usuario.login = 'batista';

SELECT usuario.nome,
	   hora.data_hora_inicial,
	   hora.data_hora_final,
	   CASE WHEN cliente.nome_cliente IS NULL THEN '-' ELSE cliente.nome_cliente END,
	   hora.tipo_hora
       FROM hora
	   LEFT JOIN usuario ON usuario.id_usuario = hora.id_usuario
	   LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente
	   LEFT JOIN equipe ON equipe.id_equipe = hora.id_equipe
	   WHERE equipe.id_equipe = 1;








-- ### EQUIPE ###
CREATE TABLE equipe(

	id_equipe SERIAL PRIMARY KEY,
	nome_equipe VARCHAR(255) UNIQUE NOT NULL

);
-- INSERINDO INFORMAÇÕES NA TABELA EQUIPE
INSERT INTO equipe( nome_equipe) VALUES ('DRAGONS');
INSERT INTO equipe( nome_equipe) VALUES ('EQUIPE ONE');
INSERT INTO equipe( nome_equipe) VALUES ('EQUIPE TWO');
-- COMANDOS NA TABELA EQUIPE
SELECT * FROM equipe;
DROP TABLE equipe
SELECT id_equipe FROM equipe WHERE nome_equipe = 'DRAGONS'


-- ### CLIENTE ###
CREATE TABLE cliente(

	id_cliente SERIAL PRIMARY KEY,
	nome_cliente VARCHAR(255),
	responsavel VARCHAR(255),
	email VARCHAR(255),
	telefone VARCHAR(13),
	projeto VARCHAR(255)

);
-- INSERINDO INFORMAÇÕES NA TABELA CLIENTE
INSERT INTO cliente(nome_cliente, responsavel, email, telefone, projeto) VALUES ('Company ONE','Fernando', 'fernando@company.com','(12)987654321', 'modelando banco');
INSERT INTO cliente(nome_cliente, responsavel, email, telefone, projeto) VALUES ('Company TWO','Fabio', 'fabio@company.com','(12)912345678', 'desenvolvendo tela');
INSERT INTO cliente(nome_cliente, responsavel, email, telefone, projeto) VALUES ('Company THREE','Felipe', 'felipe@company.com','(12)956784321', 'corrigir backend');
-- COMANDOS NA TABELA CLIENTE
SELECT * FROM cliente;
DROP TABLE cliente;


-- ### RELACIONAMENTOS ###


-- ### EQUIPE_CLIENTE ###
CREATE TABLE equipe_cliente(

	id_equipe_cliente SERIAL PRIMARY KEY,
	id_equipe INTEGER NOT NULL,
	id_cliente INTEGER NOT NULL,

	FOREIGN KEY (id_equipe)
	REFERENCES equipe (id_equipe)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	FOREIGN KEY (id_cliente)
	REFERENCES cliente (id_cliente)
	ON DELETE CASCADE
	ON UPDATE CASCADE

);
-- INSERINDO INFORMAÇÕES NA TABELA EQUIPE_CLIENTE
INSERT INTO equipe_cliente(id_equipe, id_cliente) VALUES (1,2);
INSERT INTO equipe_cliente(id_equipe, id_cliente) VALUES (2,2);
INSERT INTO equipe_cliente(id_equipe, id_cliente) VALUES (3,1);
-- COMANDOS NA TABELA EQUIPE_CLIENTE
SELECT * FROM equipe
SELECT * FROM cliente
SELECT * FROM equipe
DROP TABLE equipe_cliente

SELECT eq.nome_equipe FROM equipe_usuario AS eu INNER JOIN equipe AS eq ON eu.id_equipe = eq.id_equipe INNER JOIN usuario AS us ON us.id_usuario = eu.id_usuario WHERE us.login = 'lucas';

SELECT cl.nome_cliente FROM equipe_cliente AS ec INNER JOIN equipe AS eq ON ec.id_equipe = eq.id_equipe INNER JOIN cliente AS cl ON cl.id_cliente = ec.id_cliente
WHERE eq.nome_equipe = 'DRAGONS'




-- ### EQUIPE_USUARIO ###
CREATE TABLE equipe_usuario(

	id_equipe INTEGER,
	id_usuario INTEGER,

	PRIMARY KEY (id_equipe, id_usuario),

	FOREIGN KEY (id_equipe)
	REFERENCES equipe (id_equipe)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	FOREIGN KEY (id_usuario)
	REFERENCES usuario (id_usuario)
	ON DELETE CASCADE
	ON UPDATE CASCADE

);
-- INSERINDO INFORMAÇÕES NA TABELA EQUIPE_USUARIO
INSERT INTO equipe_usuario(id_equipe, id_usuario) VALUES (2,2);
INSERT INTO equipe_usuario(id_equipe, id_usuario) VALUES (2,4);
INSERT INTO equipe_usuario(id_equipe, id_usuario) VALUES (3,3);
-- COMANDOS NA TABELA EQUIPE_USUARIO
SELECT * FROM usuario;
DROP TABLE equipe_usuario

SELECT eq.nome_equipe FROM equipe_usuario AS eu INNER JOIN equipe AS eq ON eu.id_equipe = eq.id_equipe INNER JOIN usuario AS us ON us.id_usuario = eu.id_usuario WHERE us.login = 'lucas';




-- ### CRIANDO AS VIEW's ###

-- VW_APONATEMENTO_HORAS
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