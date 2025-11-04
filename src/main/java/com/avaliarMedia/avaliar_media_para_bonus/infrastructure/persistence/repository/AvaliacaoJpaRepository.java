package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório JPA para AvaliacaoEntity.
 * 
 * JPA (Java Persistence API) - Padrão ORM:
 * - Mapeia objetos Java para tabelas SQL
 * - Gerencia ciclo de vida das entidades
 * - Fornece API unificada para diferentes bancos
 */
@Repository
public interface AvaliacaoJpaRepository extends JpaRepository<AvaliacaoEntity, Long> {
    
    /**
     * Query method para encontrar avaliação por ID do aluno
     */
    Optional<AvaliacaoEntity> findByAlunoId(Long alunoId);
}

