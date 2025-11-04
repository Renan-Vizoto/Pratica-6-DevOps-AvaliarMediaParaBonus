package com.avaliarMedia.avaliar_media_para_bonus.application.service;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AvaliacaoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Avaliacao;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AlunoRepository;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de aplicação para gerenciar Avaliações.
 * Contém a lógica de negócio para cálculo de média e bônus.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AvaliacaoService {
    
    private final AvaliacaoRepository avaliacaoRepository;
    private final AlunoRepository alunoRepository;
    
    public AvaliacaoDTO criar(AvaliacaoDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + dto.getAlunoId()));
        
        Avaliacao avaliacao = Avaliacao.builder()
                .aluno(aluno)
                .notas(dto.getNotas())
                .build();
        
        // Calcula a média final
        avaliacao.calcularMedia();
        
        Avaliacao saved = avaliacaoRepository.save(avaliacao);
        return toDTO(saved);
    }
    
    public AvaliacaoDTO buscarPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com ID: " + id));
    }
    
    public AvaliacaoDTO buscarPorAlunoId(Long alunoId) {
        return avaliacaoRepository.findByAlunoId(alunoId)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada para aluno ID: " + alunoId));
    }
    
    private AvaliacaoDTO toDTO(Avaliacao avaliacao) {
        return AvaliacaoDTO.builder()
                .id(avaliacao.getId())
                .alunoId(avaliacao.getAluno().getId())
                .notas(avaliacao.getNotas())
                .mediaFinal(avaliacao.getMediaFinal())
                .build();
    }
}

