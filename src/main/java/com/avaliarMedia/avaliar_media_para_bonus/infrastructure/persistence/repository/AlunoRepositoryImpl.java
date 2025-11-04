package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AlunoRepository;
import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AlunoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementação do repositório de domínio usando JPA.
 * Converte entre entidades de domínio e entidades JPA.
 */
@Component
@RequiredArgsConstructor
public class AlunoRepositoryImpl implements AlunoRepository {
    
    private final AlunoJpaRepository jpaRepository;
    
    @Override
    public Aluno save(Aluno aluno) {
        AlunoEntity entity = toEntity(aluno);
        AlunoEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Aluno> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public Optional<Aluno> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(this::toDomain);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    private AlunoEntity toEntity(Aluno aluno) {
        return AlunoEntity.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .build();
    }
    
    private Aluno toDomain(AlunoEntity entity) {
        return Aluno.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .build();
    }
}

