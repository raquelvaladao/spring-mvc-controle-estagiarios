
INSERT INTO `tecnologia` (`id`, `descricao`, `nome`) VALUES (1, 'Linguagem Java', 'JAVA');
INSERT INTO `tecnologia` (`id`, `descricao`, `nome`) VALUES (2, 'Linguagem C#', 'NET');

INSERT INTO `users` (`id`, `password`, `role`, `username`) VALUES (1, '$2a$10$Wv8r4XiHvTaxmBVaNd8z6O3MZ00f9Dr8wnsWYCqEwSO4DwD2CufaG', 'ADMIN', 'admin@gft.com');
INSERT INTO `users` (`id`, `password`, `role`, `username`) VALUES (2, '$2a$10$LkztSNP7oPgtzGjXrETR5.gG4FcZhrFySCEZZeNy/zYqQOoalWpZq', 'USER', 'scrum@gft.com');
INSERT INTO `users` (`id`, `password`, `role`, `username`) VALUES (3, '$2a$10$LkztSNP7oPgtzGjXrETR5.gG4FcZhrFySCEZZeNy/zYqQOoalWpZq', 'USER', 'scrum2@gft.com');

INSERT INTO `programa_starter` (`id`, `data_fim`, `data_inicio`, `nome`) VALUES (1, '2021-11-24 00:00:00', '2021-06-07 00:00:00', 'Turma 1');

INSERT INTO `modulo` (`id`, `nome`, `programa_starter_id`) VALUES (1, 'MVC', 1);
INSERT INTO `modulo` (`id`, `nome`, `programa_starter_id`) VALUES (2, 'TDD', 1);

INSERT INTO `grupo` (`id`, `modulo_id`, `scrum_master_id`, `tecnologia_id`) VALUES (1, 1, 2, 1);
INSERT INTO `grupo` (`id`, `modulo_id`, `scrum_master_id`, `tecnologia_id`) VALUES (2, 1, 1, 1);
INSERT INTO `grupo` (`id`, `modulo_id`, `scrum_master_id`, `tecnologia_id`) VALUES (3, 2, 3, 1);

INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (1, 'perfil1.jpg', 'NOVO', 'Henrique', 1);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (2, 'perfilM3.jpg', 'ASSO', 'Helena', 1);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (3, 'perfilM2.jpg', 'SOAP', 'Julia', 1);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (5, 'perfilH3.jpg', 'ASAS', 'Jose Leon', 2);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (7, 'perfilH5.jpg', 'JOPA', 'Erick', 1);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (8, 'perfilM4.jpg', 'ASCM', 'Larissa', 2);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (9, 'perfilM6.png', 'OJOL', 'Milena', 2);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (10, 'perfilH4.jpg', 'EDRT', 'Saulo', 2);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (11, 'perfilM4.jpg', 'ASDE', 'Gabriele', 1);
INSERT INTO `starter` (`id`, `foto`, `letras`, `nome`, `grupo_starters_id`) VALUES (12, 'perfilH2.jpg', 'FNDJ', 'Micael', 2);

INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (1, '2021-11-14 00:00:00', 'Entidades', 'Arrumou PC', 'Não', b'1', 1);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (2, '2021-11-29 00:00:00', 'Controllers', 'Fez autenticação e services', 'Não', b'1', 1);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (3, '2021-11-15 00:00:00', 'Controllers e services', 'Nada', 'Dentista', b'0', 2);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (4, '2021-11-30 00:00:00', 'Services', 'Views e template', 'Não', b'1', 2);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (5, '2021-11-09 00:00:00', 'curso', 'Contrllers', 'Não', b'1', 3);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (6, '2021-11-24 00:00:00', 'Entidades', 'Views e services', 'Não', b'1', 5);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (7, '2021-11-17 00:00:00', 'Views', 'Services, repositories, controllers', 'Não', b'1', 12);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (8, '2021-11-22 00:00:00', 'Controllers', 'Services e views', 'Não', b'1', 10);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (9, '2021-11-25 00:00:00', 'Views e controllers', 'Nada', 'Fila do banco', b'1', 8);
INSERT INTO `daily` (`id`, `data`, `fazendo`, `feito`, `impedimentos`, `presenca`, `starter_id`) VALUES (10, '2021-11-21 00:00:00', 'Entidades e services', 'Cursos obrigatórios', 'Não', b'1', 11);

INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (1, 'ESTUDO', 43, 1, 1);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (2, 'ESTUDO', 56, 2, 1);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (3, 'DESAFIO', 98, 1, 2);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (4, 'ESTUDO', 54, 1, 9);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (5, 'DESAFIO', 90, 1, 7);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (6, 'DESAFIO', 100, 1, 9);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (7, 'DESAFIO', 100, 1, 12);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (8, 'ESTUDO', 90, 1, 12);
INSERT INTO `projeto` (`id`, `etapa`, `nota`, `modulo_id`, `starter_id`) VALUES (9, 'ESTUDO', 85, 1, 11);
