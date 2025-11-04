package com.avaliarMedia.avaliar_media_para_bonus.domain.repository;

import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import java.util.Optional;

/**
 * Interface do repositório no domínio (Clean Architecture).
 * Define contratos de acesso a dados sem depender de implementação.
 */
public interface AlunoRepository {
    Aluno save(Aluno aluno);
    Optional<Aluno> findById(Long id);
    Optional<Aluno> findByEmail(String email);
    void deleteById(Long id);
}

