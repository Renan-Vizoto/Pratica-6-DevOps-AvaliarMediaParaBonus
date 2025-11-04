package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Avaliacao;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AvaliacaoRepository;
import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AvaliacaoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementação do repositório de Avaliação usando JPA.
 */
@Component
@RequiredArgsConstructor
public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {
    
    private final AvaliacaoJpaRepository jpaRepository;
    private final AlunoJpaRepository alunoJpaRepository;
    
    @Override
    public Avaliacao save(Avaliacao avaliacao) {
        AvaliacaoEntity entity = toEntity(avaliacao);
        AvaliacaoEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Avaliacao> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public Optional<Avaliacao> findByAlunoId(Long alunoId) {
        return jpaRepository.findByAlunoId(alunoId)
                .map(this::toDomain);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    private AvaliacaoEntity toEntity(Avaliacao avaliacao) {
        var alunoEntity = alunoJpaRepository.findById(avaliacao.getAluno().getId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        
        return AvaliacaoEntity.builder()
                .id(avaliacao.getId())
                .aluno(alunoEntity)
                .notas(avaliacao.getNotas())
                .mediaFinal(avaliacao.getMediaFinal())
                .build();
    }
    
    private Avaliacao toDomain(AvaliacaoEntity entity) {
        var aluno = Aluno.builder()
                .id(entity.getAluno().getId())
                .nome(entity.getAluno().getNome())
                .email(entity.getAluno().getEmail())
                .build();
        
        return Avaliacao.builder()
                .id(entity.getId())
                .aluno(aluno)
                .notas(entity.getNotas())
                .mediaFinal(entity.getMediaFinal())
                .build();
    }
}

