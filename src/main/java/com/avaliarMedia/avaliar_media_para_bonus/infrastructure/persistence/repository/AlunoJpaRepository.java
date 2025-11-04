package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório JPA para AlunoEntity.
 * Spring Data JPA implementa automaticamente os métodos CRUD.
 * 
 * @Repository: Marca como componente Spring e repositório JPA
 * JpaRepository: Fornece métodos como save(), findById(), findAll(), delete()
 */
@Repository
public interface AlunoJpaRepository extends JpaRepository<AlunoEntity, Long> {
    
    /**
     * Query method - Spring Data JPA gera a query automaticamente
     * findByEmail: SELECT * FROM aluno WHERE email = ?
     */
    Optional<AlunoEntity> findByEmail(String email);
}

