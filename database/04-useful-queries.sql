-- ============================================
-- Script 4: Consultas Úteis
-- Consultas para visualizar e verificar os dados
-- ============================================

USE avaliar_media_bonus;

-- ============================================
-- Consulta 1: Listar todos os alunos com suas avaliações e status de bônus
-- ============================================
SELECT 
    a.id AS aluno_id,
    a.nome,
    a.email,
    av.id AS avaliacao_id,
    av.media_final,
    CASE 
        WHEN av.media_final > 7.0 THEN 'Elegível para Bônus (3 cursos)'
        WHEN av.media_final IS NULL THEN 'Sem avaliação'
        ELSE 'Não elegível para Bônus (0 cursos)'
    END AS status_bonus,
    CASE 
        WHEN av.media_final > 7.0 THEN 3
        ELSE 0
    END AS quantidade_cursos_bonus
FROM aluno a
LEFT JOIN avaliacao av ON a.id = av.aluno_id
ORDER BY a.id;

-- ============================================
-- Consulta 2: Ver notas detalhadas de cada avaliação
-- ============================================
SELECT 
    av.id AS avaliacao_id,
    a.id AS aluno_id,
    a.nome AS aluno_nome,
    av.media_final,
    GROUP_CONCAT(an.nota ORDER BY an.nota SEPARATOR ', ') AS notas_ordenadas,
    COUNT(an.nota) AS quantidade_notas
FROM avaliacao av
JOIN aluno a ON av.aluno_id = a.id
LEFT JOIN avaliacao_notas an ON av.id = an.avaliacao_id
GROUP BY av.id, a.id, a.nome, av.media_final
ORDER BY av.id;

-- ============================================
-- Consulta 3: Estatísticas de bônus
-- ============================================
SELECT 
    COUNT(*) AS total_alunos,
    SUM(CASE WHEN av.media_final > 7.0 THEN 1 ELSE 0 END) AS alunos_elegiveis,
    SUM(CASE WHEN av.media_final <= 7.0 OR av.media_final IS NULL THEN 1 ELSE 0 END) AS alunos_nao_elegiveis,
    SUM(CASE WHEN av.media_final > 7.0 THEN 3 ELSE 0 END) AS total_cursos_bonus_distribuidos
FROM aluno a
LEFT JOIN avaliacao av ON a.id = av.aluno_id;

-- ============================================
-- Consulta 4: Alunos elegíveis para bônus
-- ============================================
SELECT 
    a.id,
    a.nome,
    a.email,
    av.media_final,
    3 AS cursos_bonus
FROM aluno a
JOIN avaliacao av ON a.id = av.aluno_id
WHERE av.media_final > 7.0
ORDER BY av.media_final DESC;

-- ============================================
-- Consulta 5: Alunos NÃO elegíveis para bônus
-- ============================================
SELECT 
    a.id,
    a.nome,
    a.email,
    av.media_final,
    0 AS cursos_bonus
FROM aluno a
JOIN avaliacao av ON a.id = av.aluno_id
WHERE av.media_final <= 7.0 OR av.media_final IS NULL
ORDER BY av.media_final ASC;

