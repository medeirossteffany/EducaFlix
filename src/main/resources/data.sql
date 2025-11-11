-- Usuário exemplo aluno
INSERT INTO usuario (nome, email, senha, role, cpf, area_interesse)
VALUES ('Maria Silva', 'maria@teste.com', '123', 'ALUNO', '12345678900', 'Tecnologia');

-- Usuário exemplo profissional
INSERT INTO usuario (nome, email, senha, role, area_atuacao, codigo_professor)
VALUES ('João Santos', 'joao@teste.com', '123', 'PROFISSIONAL', 'Educação', 'PROF001');

-- Trilha exemplo (criada pelo profissional de id = 2)
INSERT INTO trilha (titulo, descricao, categoria, carga_horaria, nivel, profissional_id)
VALUES ('Introdução à Programação', 'Aprenda o básico de lógica e Java', 'Tecnologia', 40, 'Iniciante', 2);
