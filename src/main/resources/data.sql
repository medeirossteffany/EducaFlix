-- ========================================
-- USUÁRIOS ALUNOS
-- ========================================

INSERT INTO usuario (nome, email, senha, role, cpf, area_interesse)
VALUES ('Lucas Andrade', 'lucas@teste.com', '123', 'ALUNO', '12345678900', 'Inteligência Artificial aplicada');

INSERT INTO usuario (nome, email, senha, role, cpf, area_interesse)
VALUES ('Bianca Ribeiro', 'bianca@teste.com', '123', 'ALUNO', '98765432100', 'Cibersegurança e Privacidade');

INSERT INTO usuario (nome, email, senha, role, cpf, area_interesse)
VALUES ('Felipe Moreira', 'felipe@teste.com', '123', 'ALUNO', '11122233344', 'Sustentabilidade e ESG');

-- ========================================
-- USUÁRIOS PROFISSIONAIS
-- ========================================

INSERT INTO usuario (nome, email, senha, role, area_atuacao, codigo_professor)
VALUES ('Laura Menezes', 'laura@prof.com', '123', 'PROFISSIONAL', 'IA Generativa e Automação', 'PROF010');

INSERT INTO usuario (nome, email, senha, role, area_atuacao, codigo_professor)
VALUES ('Henrique Vasconcelos', 'henrique@prof.com', '123', 'PROFISSIONAL', 'Computação Quântica', 'PROF011');

-- ========================================
-- TRILHAS FUTURISTAS (Upskilling/Reskilling 2030+)
-- ========================================

INSERT INTO trilha (titulo, descricao, categoria, carga_horaria, nivel, profissional_id)
VALUES (
           'IA Generativa na Prática',
           'Aprenda a usar IA generativa e automação inteligente para se preparar para as profissões que dominarão o mercado em 2030+.',
           'Tecnologia do Futuro',
           40,
           'Intermediario',
           4
       );

INSERT INTO trilha (titulo, descricao, categoria, carga_horaria, nivel, profissional_id)
VALUES (
           'Automação Inteligente 2030+',
           'RPA avançado, copilotos e ferramentas que aumentarão a produtividade humana na próxima década.',
           'Tecnologia do Futuro',
           30,
           'Iniciante',
           4
       );

INSERT INTO trilha (titulo, descricao, categoria, carga_horaria, nivel, profissional_id)
VALUES (
           'Fundamentos da Computação Quântica',
           'Qubits, superposição e conceitos essenciais para quem busca reskilling em áreas emergentes de 2030+.',
           'Quântica',
           35,
           'Iniciante',
           5
       );

-- ========================================
-- INSCRIÇÕES
-- ========================================

INSERT INTO inscricao (aluno_id, trilha_id, status)
VALUES (1, 1, 'EM_ANDAMENTO');

INSERT INTO inscricao (aluno_id, trilha_id, status)
VALUES (2, 3, 'EM_ANDAMENTO');

INSERT INTO inscricao (aluno_id, trilha_id, status)
VALUES (3, 2, 'FINALIZADA');
