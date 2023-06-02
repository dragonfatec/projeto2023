SELECT * FROM usuario
SELECT * FROM hora
SELECT * FROM equipe
SELECT * FROM cliente
SELECT * FROM equipe_cliente
SELECT * FROM equipe_usuario

DROP TABLE usuario;
DROP TABLE hora;
DROP TABLE equipe;
DROP TABLE cliente;
DROP TABLE equipe_cliente;
DROP TABLE equipe_usuario;

INSERT INTO usuario (matricula, senha, nome, cargo, situacao)  
VALUES ('123456', 'CAF1A3DFB505FFED0D024130F58C5CFA', 'Lucas Oliveira', 'RH', 'Ativo');
INSERT INTO usuario (matricula, senha, nome, cargo, situacao)  
VALUES ('12345', 'CAF1A3DFB505FFED0D024130F58C5CFA', 'Lukas Fernando', 'Gestor', 'Ativo');
INSERT INTO usuario (matricula, senha, nome, cargo, situacao)  
VALUES ('1234', 'CAF1A3DFB505FFED0D024130F58C5CFA', 'Pablo Cunha', 'Colaborador', 'Ativo');
INSERT INTO equipe (nome_equipe)
VALUES ('SEM EQUIPE');
INSERT INTO cliente (empresa, responsavel, email, telefone, projeto)
VALUES ('2RP', 'Adriano Souza', 'adriano.souza@2rp.com', '12987654321', 'Sistema de ponto');
INSERT INTO equipe_cliente (id_equipe, id_cliente)
VALUES (1,1);
INSERT INTO equipe_usuario (id_equipe, matricula)
VALUES (2,'48246655');
INSERT INTO equipe_usuario (id_equipe, matricula)
VALUES (5,'123456');
INSERT INTO equipe_usuario (id_equipe, matricula)
VALUES (3,'123');
INSERT INTO hora (matricula, data_hora_inicial, data_hora_final, justificativa, id_equipe, tipo_hora, id_cliente) 
VALUES ('12345', '2023/06/01 01:00', '2023/06/01 15:00', 'Trabalhando', 1, 'Sobreaviso', 1);

UPDATE equipe_usuario SET id_equipe = 3 WHERE matricula = '123';
UPDATE hora SET data_hora_inicial = '2023-06-01 10:00:00' WHERE id_hora = '11';
UPDATE usuario SET cargo = 'RH' WHERE matricula = '123456';

DELETE FROM cliente WHERE empresa = 'Acer'

-- QUERY

SELECT usuario.matricula,
	   usuario.nome
FROM usuario
LEFT JOIN equipe_usuario ON equipe_usuario.matricula = usuario.matricula
LEFT JOIN equipe ON equipe.id_equipe = equipe_usuario.id_equipe
WHERE nome_equipe != ''
ORDER BY equipe.nome_equipe LIKE 'DRAGONS' DESC

SELECT matricula, nome FROM usuario 
LEFT JOIN equipe_usuario ON equipe_usuario.matricula = usario.matricula
LEFT JOIN equipe ON equipe.id_equipe = equipe_usuario.id_equipe

SELECT CASE WHEN equipe.nome_equipe = 'DRAGONS' THEN '1' ELSE '2' END AS prioridade, 
	   usuario.nome 
	   FROM usuario 
	   LEFT JOIN equipe_usuario ON usuario.matricula = equipe_usuario.matricula
	   LEFT JOIN equipe ON equipe.id_equipe = equipe_usuario.id_equipe 
	   ORDER BY prioridade,nome
	   
SELECT 
	CASE WHEN equipe.nome_equipe = 'DRAGONS' THEN 1 ELSE 2 END AS prioridade,
	cliente.empresa,
	cliente.responsavel
FROM 
	cliente
LEFT JOIN equipe_cliente ON cliente.id_cliente = equipe_cliente.id_cliente
LEFT JOIN equipe ON equipe.id_equipe = equipe_cliente.id_equipe
ORDER BY
	prioridade





SELECT 
	cl.empresa 
FROM 
	equipe_cliente ec 
INNER JOIN equipe eq ON eq.id_equipe = ec.id_equipe 
INNER JOIN cliente cl ON cl.id_cliente = ec.id_cliente
WHERE eq.nome_equipe = 'OMEGA'













SELECT
	CASE 
	--	WHEN hora.tipo_hora = 'Extra' THEN RANK() OVER (PARTITION BY usuario.matricula, TO_CHAR(hora.data_hora_inicial, 'DD-MM-YYYY') ORDER BY hora.data_hora_inicial DESC)
		WHEN hora.tipo_hora = 'Extra' AND RANK() OVER (PARTITION BY  usuario.matricula, TO_CHAR(hora.data_hora_inicial, 'DD-MM-YYYY') ORDER BY hora.data_hora_inicial DESC) >= 2 THEN '100'
		WHEN hora.tipo_hora = 'Extra' THEN '75'
		ELSE '30' END AS ord,

	hora.data_hora_inicial,
	hora.data_hora_final,
	hora.tipo_hora,
	usuario.matricula,
	usuario.nome,
	cliente.empresa,
	cliente.responsavel,
	cliente.projeto,
	equipe.nome_equipe,
	hora.justificativa,
	hora.justificativa_status
FROM
	hora
LEFT JOIN usuario ON usuario.matricula = hora.matricula
LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente
LEFT JOIN equipe ON equipe.id_equipe = hora.id_equipe
WHERE hora.matricula = '12345'
ORDER BY ord



