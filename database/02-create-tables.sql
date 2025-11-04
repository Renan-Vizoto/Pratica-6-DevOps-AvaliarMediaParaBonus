-- ============================================
-- Script 2: Criar Tabelas
-- Execute este script após criar o banco
-- ============================================

USE avaliar_media_bonus;

-- ============================================
-- Tabela: aluno
-- Representa um aluno do sistema
-- ============================================
CREATE TABLE IF NOT EXISTS aluno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID único do aluno',
    nome VARCHAR(100) NOT NULL COMMENT 'Nome completo do aluno',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'Email único do aluno',
    INDEX idx_email (email)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci
  COMMENT='Tabela de alunos';

-- ============================================
-- Tabela: avaliacao
-- Representa a avaliação final de um aluno
-- Relacionamento 1:1 com aluno
-- ============================================
CREATE TABLE IF NOT EXISTS avaliacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID único da avaliação',
    aluno_id BIGINT NOT NULL UNIQUE COMMENT 'ID do aluno (FK)',
    media_final DOUBLE COMMENT 'Média final calculada',
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    INDEX idx_aluno_id (aluno_id)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci
  COMMENT='Tabela de avaliações dos alunos';

-- ============================================
-- Tabela: avaliacao_notas
-- Armazena as notas individuais de uma avaliação
-- Relacionamento N:1 com avaliacao
-- ============================================
CREATE TABLE IF NOT EXISTS avaliacao_notas (
    avaliacao_id BIGINT NOT NULL COMMENT 'ID da avaliação (FK)',
    nota DOUBLE NOT NULL COMMENT 'Valor da nota individual',
    FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id) ON DELETE CASCADE,
    INDEX idx_avaliacao_id (avaliacao_id)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci
  COMMENT='Tabela de notas individuais das avaliações';

-- ============================================
-- Verificar tabelas criadas
-- ============================================
SHOW TABLES;

-- Ver estrutura das tabelas
DESCRIBE aluno;
DESCRIBE avaliacao;
DESCRIBE avaliacao_notas;

