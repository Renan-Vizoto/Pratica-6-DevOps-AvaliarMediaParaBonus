package com.avaliarMedia.avaliar_media_para_bonus.application.service;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AlunoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de aplicação para gerenciar Alunos.
 * Implementa lógica de negócio e coordena operações entre repositórios.
 * 
 * @Service: Marca como componente Spring (camada de serviço)
 * @Transactional: Gerencia transações automaticamente
 * @RequiredArgsConstructor: Injeta dependências via construtor (Lombok)
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AlunoService {
    
    private final AlunoRepository alunoRepository;
    
    public AlunoDTO criar(AlunoDTO dto) {
        Aluno aluno = Aluno.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .build();
        
        Aluno saved = alunoRepository.save(aluno);
        return toDTO(saved);
    }
    
    public AlunoDTO buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));
    }
    
    public AlunoDTO buscarPorEmail(String email) {
        return alunoRepository.findByEmail(email)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com email: " + email));
    }
    
    private AlunoDTO toDTO(Aluno aluno) {
        return AlunoDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .build();
    }
}

