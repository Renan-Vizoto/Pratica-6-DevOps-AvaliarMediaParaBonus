-- ============================================
-- Script SQL para MySQL - API Avaliar Média para Bônus
-- ============================================

-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS avaliar_media_bonus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE avaliar_media_bonus;

-- ============================================
-- Tabela: aluno
-- Representa um aluno do sistema
-- ============================================
CREATE TABLE IF NOT EXISTS aluno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Tabela: avaliacao
-- Representa a avaliação final de um aluno
-- Relacionamento 1:1 com aluno (um aluno tem uma avaliação)
-- ============================================
CREATE TABLE IF NOT EXISTS avaliacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aluno_id BIGINT NOT NULL UNIQUE,
    media_final DOUBLE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    INDEX idx_aluno_id (aluno_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Tabela: avaliacao_notas
-- Armazena as notas individuais de uma avaliação
-- Relacionamento N:1 com avaliacao (uma avaliação tem várias notas)
-- ============================================
CREATE TABLE IF NOT EXISTS avaliacao_notas (
    avaliacao_id BIGINT NOT NULL,
    nota DOUBLE NOT NULL,
    FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id) ON DELETE CASCADE,
    INDEX idx_avaliacao_id (avaliacao_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Exemplos de Dados para Teste (Opcional)
-- ============================================

-- Inserir aluno de exemplo
INSERT INTO aluno (nome, email) VALUES 
('João Silva', 'joao.silva@example.com'),
('Maria Santos', 'maria.santos@example.com'),
('Pedro Oliveira', 'pedro.oliveira@example.com');

-- Inserir avaliação com média > 7.0 (elegível para bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (1, 8.5);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(1, 8.0),
(1, 9.0),
(1, 8.5);

-- Inserir avaliação com média < 7.0 (não elegível para bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (2, 6.5);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(2, 6.0),
(2, 7.0),
(2, 6.5);

-- Inserir avaliação com média = 7.0 (não elegível para bônus)
INSERT INTO avaliacao (aluno_id, media_final) VALUES (3, 7.0);
INSERT INTO avaliacao_notas (avaliacao_id, nota) VALUES 
(3, 7.0),
(3, 7.0),
(3, 7.0);

-- ============================================
-- Consultas Úteis
-- ============================================

-- Ver todos os alunos com suas avaliações
SELECT 
    a.id AS aluno_id,
    a.nome,
    a.email,
    av.id AS avaliacao_id,
    av.media_final,
    CASE 
        WHEN av.media_final > 7.0 THEN 'Elegível para Bônus (3 cursos)'
        ELSE 'Não elegível para Bônus (0 cursos)'
    END AS status_bonus
FROM aluno a
LEFT JOIN avaliacao av ON a.id = av.aluno_id;

-- Ver notas de uma avaliação específica
SELECT 
    av.id AS avaliacao_id,
    a.nome AS aluno_nome,
    av.media_final,
    GROUP_CONCAT(an.nota ORDER BY an.nota SEPARATOR ', ') AS notas
FROM avaliacao av
JOIN aluno a ON av.aluno_id = a.id
LEFT JOIN avaliacao_notas an ON av.id = an.avaliacao_id
GROUP BY av.id, a.nome, av.media_final;

