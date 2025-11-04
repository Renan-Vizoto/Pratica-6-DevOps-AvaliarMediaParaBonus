-- ============================================
-- Script 3: Inserir Dados de Teste (Opcional)
-- Execute este script para popular o banco com dados de exemplo
-- ============================================

USE avaliar_media_bonus;

-- Limpar dados existentes (se necessário)
-- DELETE FROM avaliacao_notas;
-- DELETE FROM avaliacao;
-- DELETE FROM aluno;

-- ============================================
-- Inserir Alunos de Exemplo
-- ============================================
INSERT INTO aluno (nome, email) VALUES 
('João Silva', 'joao.silva@example.com'),
('Maria Santos', 'maria.santos@example.com'),
('Pedro Oliveira', 'pedro.oliveira@example.com'),
('Ana Costa', 'ana.costa@example.com'),
('Carlos Pereira', 'carlos.pereira@example.com');

-- ============================================
-- Inserir Avaliações com Média > 7.0 (Elegíveis para Bônus)
-- ============================================

-- Aluno 1: Média 8.5 (elegível - 3 cursos bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (1, 8.5);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(1, 8.0),
(1, 9.0),
(1, 8.5);

-- Aluno 4: Média 8.0 (elegível - 3 cursos bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (4, 8.0);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(2, 7.5),
(2, 8.5),
(2, 8.0);

-- ============================================
-- Inserir Avaliações com Média <= 7.0 (NÃO Elegíveis)
-- ============================================

-- Aluno 2: Média 6.5 (não elegível - 0 cursos bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (2, 6.5);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(3, 6.0),
(3, 7.0),
(3, 6.5);

-- Aluno 3: Média 7.0 (não elegível - 0 cursos bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (3, 7.0);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(4, 7.0),
(4, 7.0),
(4, 7.0);

-- Aluno 5: Média 5.5 (não elegível - 0 cursos bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (5, 5.5);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(5, 5.0),
(5, 6.0),
(5, 5.5);

-- ============================================
-- Verificar Dados Inseridos
-- ============================================

-- Contar registros
SELECT 'Alunos' AS tabela, COUNT(*) AS total FROM aluno
UNION ALL
SELECT 'Avaliações', COUNT(*) FROM avaliacao
UNION ALL
SELECT 'Notas', COUNT(*) FROM avaliacao_notas;

