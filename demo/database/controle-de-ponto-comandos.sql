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

  
SELECT CASE WHEN tabela.nome_equipe = 'DRAGONS' THEN '1' ELSE '2' END AS prioridade, * FROM 
(SELECT DISTINCT  
	   usuario.nome,
	   usuario.matricula,
 	   equipe.nome_equipe
	   FROM usuario
	   LEFT JOIN equipe_usuario ON equipe_usuario.matricula = usuario.matricula
	   LEFT JOIN equipe ON equipe.id_equipe = equipe_usuario.id_equipe 
	   ) AS tabela
	   ORDER BY prioridade,tabela.nome 
	   
SELECT 
	CASE WHEN equipe.nome_equipe = 'DRAGONS' THEN 1 ELSE 2 END AS prioridade,
	cliente.empresa,
	cliente.responsavel
FROM 
	cliente
LEFT JOIN (SELECT * FROM equipe_cliente WHERE id_equipe = 1) AS cl ON cl.id_cliente = cliente.id_cliente
LEFT JOIN equipe ON equipe.id_equipe = cl.id_equipe
ORDER BY
	prioridade



SELECT   
	   CASE WHEN equipe.nome_equipe = 'DRAGONS' THEN '1' ELSE '2' END AS prioridade,
	   usuario.nome,
	   usuario.matricula,
 	   equipe.nome_equipe
	   FROM usuario
	   LEFT JOIN (SELECT * FROM equipe_usuario WHERE id_equipe = 2) AS eq ON eq.matricula = usuario.matricula
	   LEFT JOIN equipe ON equipe.id_equipe = eq.id_equipe
	   














SELECT 
	cl.empresa 
FROM 
	equipe_cliente ec 
INNER JOIN equipe eq ON eq.id_equipe = ec.id_equipe 
INNER JOIN cliente cl ON cl.id_cliente = ec.id_cliente
WHERE eq.nome_equipe = 'OMEGA'










--	WHEN hora.tipo_hora = 'Extra' THEN RANK() OVER (PARTITION BY usuario.matricula, TO_CHAR(hora.data_hora_inicial, 'DD-MM-YYYY') ORDER BY hora.data_hora_inicial DESC)



SELECT
	usuario.matricula,
	usuario.nome,
	EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 AS horas,	
	hora.data_hora_inicial,
	hora.data_hora_final,
	hora.tipo_hora,
	CASE 
		WHEN hora.tipo_hora = 'Extra' AND RANK() OVER (PARTITION BY  usuario.matricula, TO_CHAR(hora.data_hora_inicial, 'DD-MM-YYYY') ORDER BY hora.data_hora_inicial DESC) >= 2 THEN '100'
		WHEN hora.tipo_hora = 'Extra' THEN '75'
		ELSE '30' END AS verba,
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
WHERE usuario.matricula = '12345'
ORDER BY verba, hora.data_hora_inicial




-- NOVA VERSÃO
SELECT
	usuario.matricula,
	usuario.nome,
	CASE 
		WHEN hora.tipo_hora = 'Sobreaviso' THEN '3016'
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 2 THEN '1601'
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 2 THEN CONCAT('2h 1601 E ',EXTRACT(EPOCH FROM (AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-2,'h 1602')                           
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 1 THEN CONCAT(EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600,'h 3000')
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 1 THEN CONCAT('1h 3000 e ',EXTRACT(EPOCH FROM (AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-1,'h 1809')
		ELSE 'NULL' END AS verba,
	CASE 
		WHEN hora.tipo_hora = 'Sobreaviso' THEN EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 2 THEN EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600* 1.75
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 2 THEN  3.5 + ((EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-2)*2
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 1 THEN (EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600* 1.1429)*1.75
		WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 1 THEN 1.75 + (((EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-1)*1.429)*2
		ELSE 0.0 END AS horas_proporcionais,	
	hora.data_hora_inicial,
	hora.data_hora_final,
	hora.tipo_hora,
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
WHERE usuario.matricula = '12345'
ORDER BY hora.data_hora_inicial





CASE 
		WHEN hora.tipo_hora = 'Extra' AND RANK() OVER (PARTITION BY  usuario.matricula, TO_CHAR(hora.data_hora_inicial, 'DD-MM-YYYY') ORDER BY hora.data_hora_inicial DESC) >= 2 THEN '100'
		WHEN hora.tipo_hora = 'Extra' THEN '75'
		ELSE '30' END AS verba,

EXTRACT(HOUR FROM hora.data_hora_inicial) >= 7 AND EXTRACT(HOUR FROM hora.data_hora_inicial) < 17 THEN 'TRUE' ELSE 'FALSE' END AS verifica,
TO_CHAR(hora.data_hora_inicial::DATE, 'Dy'),