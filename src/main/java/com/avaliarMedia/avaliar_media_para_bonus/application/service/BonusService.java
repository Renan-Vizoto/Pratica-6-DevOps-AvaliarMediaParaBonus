package com.avaliarMedia.avaliar_media_para_bonus.application.service;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.BonusResponseDTO;
import com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject.Media;
import com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject.QuantidadeCursos;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Avaliacao;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AlunoRepository;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço para calcular e verificar bônus de cursos.
 * Implementa a regra de negócio: Se nota > 7.0, o aluno ganha 3 cursos bônus.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BonusService {
    
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    
    /**
     * Calcula o bônus baseado na nota do aluno.
     * Se nota > 7.0, o aluno ganha 3 cursos bônus.
     * Caso contrário, não ganha nada.
     * 
     * @param alunoId ID do aluno
     * @return BonusResponseDTO com informações sobre elegibilidade e quantidade de bônus
     */
    public BonusResponseDTO calcularBonus(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + alunoId));
        
        Avaliacao avaliacao = avaliacaoRepository.findByAlunoId(alunoId)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada para aluno ID: " + alunoId));
        
        if (avaliacao.getNota() == null) {
            throw new RuntimeException("Nota não encontrada para aluno ID: " + alunoId);
        }
        
        // Calcula bônus baseado na nota
        QuantidadeCursos quantidade = QuantidadeCursos.calcularPorNota(avaliacao.getNota());
        boolean elegivel = quantidade.getQuantidade() > 0;
        
        return BonusResponseDTO.builder()
                .alunoId(aluno.getId())
                .nomeAluno(aluno.getNome())
                .nota(avaliacao.getNota())
                .elegivel(elegivel)
                .quantidadeCursosBonus(quantidade.getQuantidade())
                .descricao(gerarDescricao(avaliacao.getNota(), quantidade.getQuantidade()))
                .build();
    }
    
    private String gerarDescricao(Double nota, Integer quantidadeBonus) {
        if (quantidadeBonus > 0) {
            return String.format("Parabéns! Com nota %.1f (maior que 7.0), você ganhou %d cursos bônus!", 
                               nota, quantidadeBonus);
        } else {
            return String.format("Nota %.1f não é suficiente para bônus. Necessário nota superior a 7.0", nota);
        }
    }
}

