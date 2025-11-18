package com.avaliarMedia.avaliar_media_para_bonus.domain.repository;

import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Avaliacao;
import java.util.Optional;

/**
 * Interface do repositório de Avaliação no domínio.
 * Mantém a camada de domínio independente da infraestrutura.
 */
public interface AvaliacaoRepository {
    Avaliacao save(Avaliacao avaliacao);
    Optional<Avaliacao> findById(Long id);
    Optional<Avaliacao> findByAlunoId(Long alunoId);
    void deleteById(Long id);
}

